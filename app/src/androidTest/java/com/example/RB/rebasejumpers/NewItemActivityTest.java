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
public class NewItemActivityTest {
    @Rule
    public final ActivityTestRule<NewItemActivity> rule =
            new ActivityTestRule<>(NewItemActivity.class, true, false);

    /**
     * Tests if NewItemActivity properly transfers to ItemView when a new item is added
     * when the button is clicked
     */
    @Test
    public void newItemTest() throws InterruptedException {
        rule.launchActivity(new Intent());

        Intents.init();
        //Simulate filling out fields and pushing submit new item button
        onView(withId(R.id.submit_new_item_buttom)).perform(click());

        intended(hasComponent(ItemView.class.getName()));
        Intents.release();
    }

}

