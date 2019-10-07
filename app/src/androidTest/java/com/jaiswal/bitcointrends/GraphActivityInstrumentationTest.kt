package com.jaiswal.bitcointrends

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test


class GraphActivityInstrumentationTest {
    @Rule @JvmField
    var mActivityRule: ActivityTestRule<GraphActivity> = ActivityTestRule<GraphActivity>(GraphActivity::class.java)

    @Test
    @Throws(Exception::class)
    fun clickFetchDataButton_showsProgressBar() {
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.button1)).perform(click())
        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()))
        onView(withId(R.id.graph)).check(matches(isDisplayed()))
    }

    @Test
    @Throws(Exception::class)
    fun clickRefreshDataButton_showsProgressBar() {
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.button2)).perform(click())
        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()))
        onView(withId(R.id.graph)).check(matches(isDisplayed()))
    }
}