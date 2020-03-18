package ru.adfmp.officegym.ui

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import kotlinx.android.synthetic.main.fragment_statistics.*
import ru.adfmp.officegym.R
import ru.adfmp.officegym.database.repositories.StatisticsRepository
import ru.adfmp.officegym.models.StatisticsViewModel
import ru.adfmp.officegym.utils.InjectorUtils

class StatisticsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val statisticsModel: StatisticsViewModel by viewModels {
            InjectorUtils.provideStatisticsViewModelFactory(this)
        }
        val root = inflater.inflate(R.layout.fragment_statistics, container, false)

        statisticsModel.statistics.observe(viewLifecycleOwner, Observer { statisticsList ->
            val labels = statisticsList.map { StatisticsRepository.dateToString(it.date) }
            val count = statisticsList.map { it.totalCount }
            val intensity = statisticsList.map { it.totalIntensity }
            val duration = statisticsList.map { it.totalDuration }

            initChart("Total count", labels, count, chart1)
            initChart("Total intensity", labels, intensity, chart2)
            initChart("Total duration", labels, duration, chart3)

            calendarView.removeDecorators()

            val days = statisticsList.associateBy {
                val (year, month, date) = StatisticsRepository.dateToYearMonthDay(it.date)
                CalendarDay.from(year, month + 1, date)
            }
            calendarView.addDecorator(EventDecorator(days.keys.toHashSet()))
            calendarView.setOnDateChangedListener { _, date, selected ->
                calendarView.clearSelection()
                if (selected && date in days) {
                    val statistic = days.getValue(date)
                    val alertDialog = AlertDialog.Builder(requireContext()).create()
                    alertDialog.setTitle("Activity on ${date.day}.${date.month}.${date.year}:")
                    alertDialog.setMessage(
                        """
                        |Total count: ${statistic.totalCount}
                        |Total duration: ${statistic.totalDuration}
                        |Total intensity: ${statistic.totalIntensity}
                        """.trimMargin()
                    )
                    alertDialog.show()
                }
            }
        })
        return root
    }

    private fun resetChart(chart: BarChart) = chart.apply {
        fitScreen()
        data?.clearValues()
        xAxis.valueFormatter = null
        notifyDataSetChanged()
        clear()
        invalidate()
    }

    private fun initChart(
        name: String,
        labels: List<String>,
        statisticsData: List<Int>,
        chart: BarChart
    ) {
        val dataPoints =
            statisticsData.mapIndexed { i, value -> BarEntry(i.toFloat(), value.toFloat()) }
        val barDataSet = BarDataSet(dataPoints, name).apply {
            colors = ColorTemplate.PASTEL_COLORS.toList()
            axisDependency = YAxis.AxisDependency.LEFT
        }

        resetChart(chart)

        chart.apply {
            data = BarData(barDataSet).apply {
                barWidth = BAR_WIDTH
                setValueTextSize(VALUES_TEXT_SIZE)
            }

            description.apply {
                text = name
                textSize = TITLE_TEXT_SIZE
                textColor = Color.BLACK
            }

            axisLeft.apply {
                textSize = AXIS_TEXT_SIZE
                axisMinimum = Y_MIN
            }

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                textSize = AXIS_TEXT_SIZE
                granularity = X_GRANULARITY
                setValueFormatter { value, _ ->
                    labels.getOrElse(value.toInt()) { "" }
                }
            }

            axisRight.isEnabled = false
            legend.isEnabled = false
            isHighlightPerTapEnabled = false
            isHighlightPerDragEnabled = false
            setVisibleXRange(MIN_BARS_VISIBLE, MAX_BARS_VISIBLE)
            moveViewToX(dataPoints.size.toFloat())

            invalidate()
        }
    }

    companion object {
        private const val Y_MIN = 0f
        private const val VALUES_TEXT_SIZE = 15f
        private const val AXIS_TEXT_SIZE = 10f
        private const val TITLE_TEXT_SIZE = 15f
        private const val X_GRANULARITY = 1f
        private const val BAR_WIDTH = 0.5f
        private const val MIN_BARS_VISIBLE = 2f
        private const val MAX_BARS_VISIBLE = 5f

    }
}

class EventDecorator(private val dates: HashSet<CalendarDay>) : DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(DotSpan(10f, Color.RED))
    }
}
