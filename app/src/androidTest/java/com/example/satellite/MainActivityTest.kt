package com.example.satellite

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4

import androidx.test.filters.LargeTest
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun checkRecyclerViewIsVisible() {
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun checkProgressBarIsHiddenAfterDataLoads() {

        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))


        Thread.sleep(5000)


        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))


        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun testSearchFunctionality() {
        onView(withId(R.id.searchEditText))
            .perform(typeText("Starship"), closeSoftKeyboard())

        Thread.sleep(2000)

        onView(withId(R.id.recyclerView))
            .check(matches(hasMinimumChildCount(1)))
    }
}