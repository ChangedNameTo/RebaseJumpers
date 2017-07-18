package com.example.RB.rebasejumpers;

import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class MapActivityTest {

    @Rule
    public final ActivityTestRule<LogoutScreen> rule =
            new ActivityTestRule<>(LogoutScreen.class, true, false);

    /**
     * Tests if LoginActivity properly transfers to RegitstrationActivity
     * when the button is clicked
     */
    @Test
    public void mapsTest() throws InterruptedException {
        rule.launchActivity(new Intent());

        Intents.init();
        //Simulate filling out fields and pushing register button
        onView(withId(R.id.maps_view_button)).perform(click());

        intended(hasComponent(MapsActivity.class.getName()));
        Intents.release();
    }
}