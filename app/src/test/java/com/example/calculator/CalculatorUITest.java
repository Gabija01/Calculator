package com.example.calculator;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class CalculatorUITest {

    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void testAdditionUI() {
        Espresso.onView(withId(R.id.btn1)).perform(typeText("2"));
        Espresso.onView(withId(R.id.btn2)).perform(typeText("3"));
        Espresso.onView(withId(R.id.btn9)).perform(click());
        Espresso.onView(withId(R.id.btnCE)).check(matches(withText("5")));
    }
}