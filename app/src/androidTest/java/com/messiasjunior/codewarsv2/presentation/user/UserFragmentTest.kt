package com.messiasjunior.codewarsv2.presentation.user

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.messiasjunior.codewarsv2.R
import com.messiasjunior.codewarsv2.di.CodewarsApplication
import com.messiasjunior.codewarsv2.model.User
import com.messiasjunior.codewarsv2.presentation.challenges.ChallengesFragment
import com.messiasjunior.codewarsv2.presentation.challenges.ChallengesViewModel
import com.messiasjunior.codewarsv2.repository.ChallengeRepository
import com.messiasjunior.codewarsv2.util.createFakeFragmentInjector
import com.messiasjunior.codewarsv2.util.getResourcePagedListFrom
import com.messiasjunior.codewarsv2.util.hasCheckedItem
import com.messiasjunior.codewarsv2.util.nthChildOf
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserFragmentTest {

    private val mockChallengeRepository = mockk<ChallengeRepository>()
    private val app = ApplicationProvider.getApplicationContext<CodewarsApplication>().apply {
        dispatchingAndroidInjector = createFakeFragmentInjector<ChallengesFragment> {
            viewModelFactory = ChallengesViewModel.Factory(mockChallengeRepository)
        }
    }

    @Before
    fun setup() {
        every { mockChallengeRepository.findChallenges(any(), any()) }
            .returns(getResourcePagedListFrom(emptyList()))

        val toolbarTitle = "custom title"
        val args = bundleOf(
            Pair("user", User(username = "teste", null, null, null)),
            Pair("toolbarTitle", toolbarTitle)
        )
        launchFragmentInContainer<UserFragment>(themeResId = R.style.AppTheme, fragmentArgs = args)
    }

    @Test
    fun should_there_is_a_view_pager_with_two_fragments() {
        onView(withId(R.id.userViewPager)).check(matches(hasChildCount(2)))
    }

    @Test
    fun should_there_is_a_bottom_navigation_with_two_options() {
        onView(isAssignableFrom(BottomNavigationMenuView::class.java))
            .check(matches(hasChildCount(2)))
    }

    @Test
    fun should_select_item_pager_at_button_click() {

        // Verify initial state
        onView(nthChildOf(withId(R.id.userViewPager), 0))
            .check(matches(isDisplayed()))

        onView(nthChildOf(withId(R.id.userViewPager), 1))
            .check(matches(not(isDisplayed())))

        // Click on menu
        onView(nthChildOf(isAssignableFrom(BottomNavigationMenuView::class.java), 1))
            .perform(click())

        // Verify current state
        onView(nthChildOf(withId(R.id.userViewPager), 0))
            .check(matches(not(isDisplayed())))

        onView(nthChildOf(withId(R.id.userViewPager), 1))
            .check(matches(isDisplayed()))

        // Click on menu to back to the initial state
        onView(nthChildOf(isAssignableFrom(BottomNavigationMenuView::class.java), 0))
            .perform(click())

        // Verify initial state
        onView(nthChildOf(withId(R.id.userViewPager), 0))
            .check(matches(isDisplayed()))

        onView(nthChildOf(withId(R.id.userViewPager), 1))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun should_select_bottom_navigation_button_when_swipe_view_pager() {
        onView(withId(R.id.userBottomNavigation))
            .check(matches(hasCheckedItem(R.id.userBottomNavigationCompleted)))

        onView(withId(R.id.userViewPager)).perform(swipeLeft())

        onView(withId(R.id.userBottomNavigation))
            .check(matches(hasCheckedItem(R.id.userBottomNavigationAuthored)))

        onView(withId(R.id.userViewPager)).perform(swipeRight())

        onView(withId(R.id.userBottomNavigation))
            .check(matches(hasCheckedItem(R.id.userBottomNavigationCompleted)))
    }
}
