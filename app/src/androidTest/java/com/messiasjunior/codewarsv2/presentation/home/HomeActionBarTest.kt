package com.messiasjunior.codewarsv2.presentation.home

import android.view.KeyEvent
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.ContentLoadingProgressBar
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.snackbar.Snackbar
import com.messiasjunior.codewarsv2.R
import com.messiasjunior.codewarsv2.di.CodewarsApplication
import com.messiasjunior.codewarsv2.exception.UserNotFountException
import com.messiasjunior.codewarsv2.presentation.MainActivity
import com.messiasjunior.codewarsv2.repository.UserRepository
import com.messiasjunior.codewarsv2.util.createFakeFragmentInjector
import com.messiasjunior.codewarsv2.util.getPagedListFrom
import com.messiasjunior.codewarsv2.util.resource.Resource
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActionBarTest {
    private val mockUserRepository = mockk<UserRepository>()
    private val app = ApplicationProvider.getApplicationContext<CodewarsApplication>().apply {
        dispatchingAndroidInjector = createFakeFragmentInjector<HomeFragment> {
            viewModelFactory = HomeViewModel.Factory(mockUserRepository)
        }
    }

    @Before
    fun setup() {
        every { mockUserRepository.findAll(any()) }.returns(getPagedListFrom(emptyList()))
        launchActivity<MainActivity>()
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun should_show_loading_when_search_by_username() {
        coEvery { mockUserRepository.searchUser(any()) }.returns(Resource.loading())

        onView(withId(R.id.menuHomeSearch))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(isAssignableFrom(AutoCompleteTextView::class.java))
            .perform(typeText("g964"))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))

        onView(isAssignableFrom(ContentLoadingProgressBar::class.java)).check(matches(isDisplayed()))
    }

    @Test
    fun should_show_snackbar_when_search_fails() {
        coEvery { mockUserRepository.searchUser(any()) }.answers {
            Resource.error(
                throwable = UserNotFountException(
                    invocation.args[0] as String
                )
            )
        }

        onView(withId(R.id.menuHomeSearch))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(isAssignableFrom(AutoCompleteTextView::class.java))
            .perform(typeText("g964"))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))

        onView(isAssignableFrom(Snackbar.SnackbarLayout::class.java)).check(matches(isDisplayed()))
    }

    @Test
    fun should_reorder_items_by_honor() {

        onView(withId(R.id.menuHomeSort))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withText(R.string.sort_by_honor))
            .perform(click())

        verify(exactly = 1) {
            mockUserRepository.findAll(UserRepository.SortOrder.HONOR)
        }
    }

    @Test
    fun should_reorder_items_by_recent() {

        verify(exactly = 1) {
            mockUserRepository.findAll(UserRepository.SortOrder.SEARCH_DATE)
        }

        onView(withId(R.id.menuHomeSort))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withText(R.string.sort_by_recent))
            .perform(click())

        verify(exactly = 2) {
            mockUserRepository.findAll(UserRepository.SortOrder.SEARCH_DATE)
        }
    }

    @Test
    fun should_show_application_name() {
        onView(
            allOf(
                isAssignableFrom(AppCompatTextView::class.java),
                withParent(isAssignableFrom(Toolbar::class.java))
            )
        )
            .check(matches(withText(R.string.app_name)))
    }
}
