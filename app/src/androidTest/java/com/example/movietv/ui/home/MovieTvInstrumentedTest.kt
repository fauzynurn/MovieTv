package com.example.movietv.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.example.movietv.R
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test

class MovieTvInstrumentedTest {
    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun MovieTvItemShouldAppear() {
        onView(withId(R.id.movie_rv)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_rv)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(11))
        onView(withId(R.id.view_pager)).perform(swipeLeft())
        onView(withId(R.id.tvshow_rv)).check(matches(isDisplayed()))
        onView(withId(R.id.tvshow_rv)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(11))
    }

    @Test
    fun DetailMovieShouldShowExpectedData(){
        onView(withId(R.id.movie_rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.backdrop_img)).check(matches(isDisplayed()))
        onView(withId(R.id.backdrop_img)).check(matches(isDisplayed()))

        onView(withId(R.id.title)).check(matches(isDisplayed()))
        onView(withId(R.id.title)).check(matches(withText("Project Power")))

        onView(withId(R.id.release_date)).check(matches(isDisplayed()))
        onView(withId(R.id.release_date)).check(matches(withText("Released at 2020-08-14")))

        onView(withId(R.id.genre_list)).check(matches(isDisplayed()))
        onView(withId(R.id.genre_list)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(2))

        onView(withId(R.id.rating)).check(matches(isDisplayed()))
        onView(withId(R.id.rating)).check(matches(withText("6.7")))

        onView(withId(R.id.vote)).check(matches(isDisplayed()))
        onView(withId(R.id.vote)).check(matches(withText("940 rated")))

        onView(withId(R.id.duration)).check(matches(isDisplayed()))
        onView(withId(R.id.duration)).check(matches(withText("1h 53m")))

        onView(withId(R.id.language)).check(matches(isDisplayed()))
        onView(withId(R.id.language)).check(matches(withText("EN")))

        onView(withId(R.id.overview)).check(matches(isDisplayed()))
        onView(withId(R.id.overview)).check(matches(withText("An ex-soldier, a teen and a cop collide in New Orleans as they hunt for the source behind a dangerous new pill that grants users temporary superpowers.")))

        onView(withId(R.id.scroll_view)).perform(swipeUp())

        onView(withId(R.id.cast_list)).check(matches(isDisplayed()))
        onView(withId(R.id.cast_list)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
    }

    @Test
    fun DetailTvShowShouldShowExpectedData(){
        onView(withId(R.id.view_pager)).perform(swipeLeft())

        onView(withId(R.id.tvshow_rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.backdrop_img)).check(matches(isDisplayed()))
        onView(withId(R.id.backdrop_img)).check(matches(isDisplayed()))

        onView(withId(R.id.title)).check(matches(isDisplayed()))
        onView(withId(R.id.title)).check(matches(withText("Lucifer")))

        onView(withId(R.id.release_date)).check(matches(not(isDisplayed())))

        onView(withId(R.id.genre_list)).check(matches(isDisplayed()))
        onView(withId(R.id.genre_list)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(2))

        onView(withId(R.id.rating)).check(matches(isDisplayed()))
        onView(withId(R.id.rating)).check(matches(withText("8.5")))

        onView(withId(R.id.vote)).check(matches(isDisplayed()))
        onView(withId(R.id.vote)).check(matches(withText("4491 rated")))

        onView(withId(R.id.duration)).check(matches(isDisplayed()))
        onView(withId(R.id.duration)).check(matches(withText("0h 45m")))

        onView(withId(R.id.language)).check(matches(isDisplayed()))
        onView(withId(R.id.language)).check(matches(withText("EN")))

        onView(withId(R.id.overview)).check(matches(isDisplayed()))
        onView(withId(R.id.overview)).check(matches(withText("Bored and unhappy as the Lord of Hell, Lucifer Morningstar abandoned his throne and retired to Los Angeles, where he has teamed up with LAPD detective Chloe Decker to take down criminals. But the longer he's away from the underworld, the greater the threat that the worst of humanity could escape.")))

        onView(withId(R.id.scroll_view)).perform(swipeUp())

        onView(withId(R.id.cast_title)).check(matches(not(isDisplayed())))
        onView(withId(R.id.cast_list)).check(matches(not(isDisplayed())))
    }
}