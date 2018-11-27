package com.example.fauzi.selectedmatchschedule

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.example.fauzi.selectedmatchschedule.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest{
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun testAppBehaviour() {

        // Main Fragment Next Match
        Thread.sleep(2000)
        onView(withId(nextButton)).check(matches(isDisplayed()))
        onView(withId(nextButton)).perform(click())
        Thread.sleep(2000)
        onView(withId(R.id.recycler_view)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        // Detail Match - fav - back
        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Added to favorite")).check(matches(isDisplayed()))
        pressBack()

        // MainFragment Prev Match
        Thread.sleep(2000)
        onView(withId(prevButton)).check(matches(isDisplayed()))
        onView(withId(prevButton)).perform(click())
        Thread.sleep(2000)
        onView(withId(R.id.recycler_view)).perform(scrollToPosition<RecyclerView.ViewHolder>(14))
        onView(withId(R.id.recycler_view)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(14, click()))

        // Detail Match - fav - back
        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Added to favorite")).check(matches(isDisplayed()))
        pressBack()

        // click fav bottom navigation
        onView(withId(bottom_navigation)).check(matches(isDisplayed()))
        onView(withId(favorite_match)).perform(click())

        // Detail Match - unfav - back
        Thread.sleep(2000)
        onView(withId(R.id.recycler_view)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Removed from favorite")).check(matches(isDisplayed()))
        Thread.sleep(2000)
        pressBack()

        // refresh - click - Detail Match - unfav - back
        onView(withId(detail_layout)).perform(swipeDown())
        Thread.sleep(2000)
        onView(withId(R.id.recycler_view)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Removed from favorite")).check(matches(isDisplayed()))
        pressBack()


        //klik nav_menu list team
        onView(withId(bottom_navigation)).check(matches(isDisplayed()))
        onView(withId(club)).perform(click())

        //type edit text - searh - click
        Thread.sleep(2000)
        onView(withId(search_team)).check(matches(isDisplayed()))
        onView(withId(search_team)).perform(typeText("Barcel"))
        onView(withId(search_team_button)).perform(click())
        onView(withId( R.id.recycler_view)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        // Detail Team - fav
        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Added to favorite")).check(matches(isDisplayed()))

        // show rv player list - click - back
        Thread.sleep(2000)
        onView(withId(showPlayerList)).check(matches(isDisplayed()))
        onView(withId(showPlayerList)).perform(click())
        onView(withId( R.id.recycler_view)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(8, click()))
        Thread.sleep(2000)
        pressBack()

        // unfav
        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Removed from favorite")).check(matches(isDisplayed()))
        pressBack()

    }
}