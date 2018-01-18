package de.uni_stuttgart.informatik.sopra.sopraapp.view;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest2 {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    @Test
    public void loginActivityTest2() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.startLandwirtButton), withText("Farmer Login"),
                        childAtPosition(
                                allOf(withId(R.id.email_login_form),
                                        childAtPosition(
                                                withId(R.id.login_form),
                                                0)),
                                3)));
        appCompatButton.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                allOf(withId(R.id.map_fragment),
                                        childAtPosition(
                                                withId(R.id.sliding_layout),
                                                0)),
                                4),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.field_button),
                        childAtPosition(
                                allOf(withId(R.id.field_fab_and_label),
                                        childAtPosition(
                                                withId(R.id.map_fragment),
                                                3)),
                                1),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction floatingActionButton3 = onView(
                allOf(withId(R.id.field_button),
                        childAtPosition(
                                allOf(withId(R.id.field_fab_and_label),
                                        childAtPosition(
                                                withId(R.id.map_fragment),
                                                3)),
                                1),
                        isDisplayed()));
        floatingActionButton3.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.text_field_name),
                        childAtPosition(
                                allOf(withId(R.id.newFieldDialog),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("ffff"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.add_field_dialog_finish_button), withText("Finished"),
                        childAtPosition(
                                allOf(withId(R.id.newFieldDialog),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                7),
                        isDisplayed()));
        appCompatButton2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction floatingActionButton4 = onView(
                allOf(withId(R.id.damages_button),
                        childAtPosition(
                                allOf(withId(R.id.damage_fab_and_label),
                                        childAtPosition(
                                                withId(R.id.map_fragment),
                                                2)),
                                1),
                        isDisplayed()));
        floatingActionButton4.perform(click());

        ViewInteraction floatingActionButton5 = onView(
                allOf(withId(R.id.damages_button),
                        childAtPosition(
                                allOf(withId(R.id.damage_fab_and_label),
                                        childAtPosition(
                                                withId(R.id.map_fragment),
                                                2)),
                                1),
                        isDisplayed()));
        floatingActionButton5.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.add_damage_dialog_finish_button), withText("Finished"),
                        childAtPosition(
                                allOf(withId(R.id.edit_name),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction tabView = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.tab_layout),
                                0),
                        1),
                        isDisplayed()));
        tabView.perform(click());

        ViewInteraction tabView2 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.tab_layout),
                                0),
                        0),
                        isDisplayed()));
        tabView2.perform(click());

        ViewInteraction tabView3 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.tab_layout),
                                0),
                        3),
                        isDisplayed()));
        tabView3.perform(click());

        ViewInteraction tabView4 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.tab_layout),
                                0),
                        1),
                        isDisplayed()));
        tabView4.perform(click());

        ViewInteraction floatingActionButton6 = onView(
                allOf(withId(R.id.damages_button),
                        childAtPosition(
                                allOf(withId(R.id.damage_fab_and_label),
                                        childAtPosition(
                                                withId(R.id.map_fragment),
                                                2)),
                                1),
                        isDisplayed()));
        floatingActionButton6.perform(click());

        ViewInteraction floatingActionButton7 = onView(
                allOf(withId(R.id.damages_button),
                        childAtPosition(
                                allOf(withId(R.id.damage_fab_and_label),
                                        childAtPosition(
                                                withId(R.id.map_fragment),
                                                2)),
                                1),
                        isDisplayed()));
        floatingActionButton7.perform(click());

        ViewInteraction floatingActionButton8 = onView(
                allOf(withId(R.id.damages_button),
                        childAtPosition(
                                allOf(withId(R.id.damage_fab_and_label),
                                        childAtPosition(
                                                withId(R.id.map_fragment),
                                                2)),
                                1),
                        isDisplayed()));
        floatingActionButton8.perform(click());

        ViewInteraction floatingActionButton9 = onView(
                allOf(withId(R.id.damages_button),
                        childAtPosition(
                                allOf(withId(R.id.damage_fab_and_label),
                                        childAtPosition(
                                                withId(R.id.map_fragment),
                                                2)),
                                1),
                        isDisplayed()));
        floatingActionButton9.perform(click());

        ViewInteraction floatingActionButton10 = onView(
                allOf(withId(R.id.damages_button),
                        childAtPosition(
                                allOf(withId(R.id.damage_fab_and_label),
                                        childAtPosition(
                                                withId(R.id.map_fragment),
                                                2)),
                                1),
                        isDisplayed()));
        floatingActionButton10.perform(click());

        ViewInteraction floatingActionButton11 = onView(
                allOf(withId(R.id.damages_button),
                        childAtPosition(
                                allOf(withId(R.id.damage_fab_and_label),
                                        childAtPosition(
                                                withId(R.id.map_fragment),
                                                2)),
                                1),
                        isDisplayed()));
        floatingActionButton11.perform(click());

        ViewInteraction tabView5 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.tab_layout),
                                0),
                        0),
                        isDisplayed()));
        tabView5.perform(click());

    }
}
