package ru.adfmp.officegym


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
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
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.adfmp.officegym.adapters.viewholders.WorkoutViewHolder

@LargeTest
@RunWith(AndroidJUnit4::class)
class EditExerciseTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun editExerciseTest() {
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.nav_profile), withContentDescription("Profile"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val appCompatImageView = onView(
            allOf(
                withId(R.id.edit_exercises_image), withContentDescription("Edit exercises"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.profile_grid_layout),
                        2
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.edit_exercises_list))
            .perform(RecyclerViewActions.actionOnItemAtPosition<WorkoutViewHolder>(0, click()))

        val appCompatEditText = onView(
            allOf(
                withId(R.id.edit_exercise_name), withText("Squat"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        2
                    ),
                    0
                )
            )
        )
        appCompatEditText.perform(scrollTo(), replaceText("Squat2"))

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.edit_exercise_name), withText("Squat2"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        2
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(closeSoftKeyboard())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.edit_exercise_duration), withText("20"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        1
                    ),
                    1
                )
            )
        )
        appCompatEditText3.perform(scrollTo(), replaceText("30"))

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.edit_exercise_duration), withText("30"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText4.perform(closeSoftKeyboard())

        val appCompatEditText5 = onView(
            allOf(
                withId(R.id.edit_exercise_intensity), withText("7"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        2
                    ),
                    1
                )
            )
        )
        appCompatEditText5.perform(scrollTo(), replaceText("5"))

        val appCompatEditText6 = onView(
            allOf(
                withId(R.id.edit_exercise_intensity), withText("5"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        2
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText6.perform(closeSoftKeyboard())

        val appCompatButton = onView(
            allOf(
                withId(R.id.save), withText("save"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        0
                    ),
                    1
                )
            )
        )
        appCompatButton.perform(scrollTo(), click())

        val textView = onView(
            allOf(
                withId(R.id.exercise_name), withText("Squat2"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.view.ViewGroup::class.java),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Squat2")))

        val textView2 = onView(
            allOf(
                withId(R.id.exercise_duration), withText("recommended duration: 30"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.view.ViewGroup::class.java),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("recommended duration: 30")))

        val textView3 = onView(
            allOf(
                withId(R.id.exercise_intensity), withText("intensity: 5"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.view.ViewGroup::class.java),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("intensity: 5")))
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
