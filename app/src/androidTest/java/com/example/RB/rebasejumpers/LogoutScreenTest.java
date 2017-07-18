package com.example.RB.rebasejumpers;

import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * The type Login activity test.
 */
@RunWith(AndroidJUnit4.class)
public class LogoutScreenTest {


    @Rule
    public final ActivityTestRule<LogoutScreen> rule =
            new ActivityTestRule<>(LogoutScreen.class, true, false);

    /**
     * On create.
     *
     * @throws Exception the exception
     */
    @Test
    public void onCreate() throws Exception {
        rule.launchActivity(new Intent());

        Intents.init();

        onView(withId(R.id.item_list_button)).perform(click());
        intended(hasComponent(ItemView.class.getName()));

        Intents.release();
    }
}