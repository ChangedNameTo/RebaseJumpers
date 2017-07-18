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

/**
 * Method Contract for NewItemActivityTest:
 * Signature: The method this calls are a few methods in NewItemActivity, such as newItem()
 * and the onCreate() methods, which both take in no parameters.
 * Preconditions: True, a user can make a new item at any time.
 * Postconditions: After the call is completed, it makes sure once the user adds the new item in
 * the text field, it goes throw to ItemView.
 * Framing Conditions: There are no real instance variables that were altered since a new item
 * starts with the user's information they provide in the text field about the new item they would
 * like to add.
 * Invariants: It will always be true that the new item added is a lost item, so the checkbox will
 * be unchecked and the "found" variable will be false.
 */
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
    public void newItemTest() {
        rule.launchActivity(new Intent());

        Intents.init();
        //Simulate filling out fields and pushing submit new item button
        onView(withId(R.id.submit_new_item_buttom)).perform(click());

        intended(hasComponent(ItemView.class.getName()));
        Intents.release();
    }

}

