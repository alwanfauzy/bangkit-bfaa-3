package com.alwan.bangkitbfaa3.ui.main

import android.view.KeyEvent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alwan.bangkitbfaa3.R
import org.junit.Before
import org.junit.Test

class MainActivityTest{
    private val dummyUsername = "alwan"

    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun actionBar() {
        onView(withId(R.id.menu_search)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText(dummyUsername))
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(pressKey(KeyEvent.KEYCODE_ENTER))
        onView(withId(androidx.appcompat.R.id.search_close_btn)).perform(click())
        onView(withId(R.id.menu_favorite)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.menu_settings)).perform(click())
        onView(isRoot()).perform(pressBack())
    }
}