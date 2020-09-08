package com.messiasjunior.codewarsv2.presentation.home

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.messiasjunior.codewarsv2.R
import com.messiasjunior.codewarsv2.di.CodewarsApplication
import com.messiasjunior.codewarsv2.model.User
import com.messiasjunior.codewarsv2.repository.UserRepository
import com.messiasjunior.codewarsv2.util.createFakeFragmentInjector
import com.messiasjunior.codewarsv2.util.getPagedListFrom
import com.messiasjunior.codewarsv2.util.withRecyclerView
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {
    private val mockUserRepository = mockk<UserRepository>()
    private val app = ApplicationProvider.getApplicationContext<CodewarsApplication>().apply {
        dispatchingAndroidInjector = createFakeFragmentInjector<HomeFragment> {
            viewModelFactory = HomeViewModel.Factory(mockUserRepository)
        }
    }

    @Before
    fun setup() {
        every { mockUserRepository.findAll(any()) }.returns(getPagedListFrom(emptyList()))
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun should_show_empty_list_indicator() {
        launchFragmentInContainer<HomeFragment>(themeResId = R.style.AppTheme)
        onView(withId(R.id.homeEmptyListMessageTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.homeEmptyListMessage)).check(matches(isDisplayed()))
    }

    @Test
    fun should_items_from_users_list() {
        val users = listOf(
            User("user1", "User 1", 1, 10),
            User("user2", null, 1, null),
            User("user3", "null", null, 10)
        )

        every { mockUserRepository.findAll(any()) }.returns(getPagedListFrom(users))

        launchFragmentInContainer<HomeFragment>(themeResId = R.style.AppTheme)

        onView(withId(R.id.homeEmptyListMessageTitle)).check(matches(not(isDisplayed())))
        onView(withId(R.id.homeEmptyListMessage)).check(matches(not(isDisplayed())))
        onView(withId(R.id.homeUserRecyclerView)).check(matches(hasChildCount(3)))

        // Name provided
        onView(
            allOf(
                withId(R.id.userListItemName),
                isDescendantOfA(withRecyclerView(R.id.homeUserRecyclerView).atPosition(0))
            )
        ).check(matches(withText(users[0].name)))

        // Name not provided: should show username
        onView(
            allOf(
                withId(R.id.userListItemName),
                isDescendantOfA(withRecyclerView(R.id.homeUserRecyclerView).atPosition(1))
            )
        ).check(matches(withText(users[1].username)))

        // Rank provided
        onView(
            allOf(
                withId(R.id.userListItemRank),
                isDescendantOfA(withRecyclerView(R.id.homeUserRecyclerView).atPosition(1))
            )
        ).check(matches(withText(users[1].leaderboardPosition.toString())))

        // Rank not provided: should show unavailable
        onView(
            allOf(
                withId(R.id.userListItemRank),
                isDescendantOfA(withRecyclerView(R.id.homeUserRecyclerView).atPosition(2))
            )
        ).check(matches(withText(R.string.unavailable)))
    }
}
