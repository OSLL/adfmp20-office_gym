package ru.adfmp.officegym


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.adfmp.officegym.adapters.viewholders.WorkoutViewHolder
import ru.adfmp.officegym.database.repositories.StatisticsRepository
import java.lang.Thread.sleep

@LargeTest
@RunWith(AndroidJUnit4::class)
class NewStatisticTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun newStatisticTest() {
        sleep(1000)
        onView(withId(R.id.workout_list))
            .perform(RecyclerViewActions.actionOnItemAtPosition<WorkoutViewHolder>(0, click()))

        val appCompatButton = onView(
            allOf(
                withId(R.id.start_workout_button), withText("Start!"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        sleep(1000)
        val appCompatImageButton = onView(
            allOf(
                withId(R.id.mute_button), withContentDescription("NextExercise"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        3
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())
        sleep(6000)

        val appCompatImageButton2 = onView(
            allOf(
                withId(R.id.next_exercise_button), withContentDescription("NextExercise"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        3
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton2.perform(click())

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.nav_statistics), withContentDescription("Statistics"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val date = StatisticsRepository.dateToYearMonthDay(StatisticsRepository.currentDay())[2].toString()
        val dayView = onView(
            allOf(
                withText(date),
                isDisplayed()
            )
        )
        dayView.perform(click())

        sleep(1000)
        val textView = onView(
            allOf(
                withId(android.R.id.message)
            )
        )
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
