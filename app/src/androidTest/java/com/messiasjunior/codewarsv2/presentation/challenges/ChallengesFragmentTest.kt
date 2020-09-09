package com.messiasjunior.codewarsv2.presentation.challenges

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
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
import com.messiasjunior.codewarsv2.model.Challenge
import com.messiasjunior.codewarsv2.model.ChallengeType
import com.messiasjunior.codewarsv2.model.User
import com.messiasjunior.codewarsv2.repository.ChallengeRepository
import com.messiasjunior.codewarsv2.util.createFakeFragmentInjector
import com.messiasjunior.codewarsv2.util.getResourcePagedListFrom
import com.messiasjunior.codewarsv2.util.resource.Resource
import com.messiasjunior.codewarsv2.util.withRecyclerView
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChallengesFragmentTest {
    private val mockChallengeRepository = mockk<ChallengeRepository>()
    private val app = ApplicationProvider.getApplicationContext<CodewarsApplication>().apply {
        dispatchingAndroidInjector = createFakeFragmentInjector<ChallengesFragment> {
            viewModelFactory = ChallengesViewModel.Factory(mockChallengeRepository)
        }
    }
    private val user = User(username = "username", null, null, null)
    private val bundle = ChallengesFragment.getBundle(ChallengeType.COMPLETED, user)

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun should_show_empty_list_indicator() {
        every { mockChallengeRepository.findChallenges(any(), any()) }.returns(
            getResourcePagedListFrom(emptyList())
        )

        launchFragmentInContainer<ChallengesFragment>(
            themeResId = R.style.AppTheme,
            fragmentArgs = bundle
        )

        onView(withId(R.id.challengesEmptyListIndicator)).check(matches(isDisplayed()))
    }

    @Test
    fun should_show_loading_indicator() {
        every { mockChallengeRepository.findChallenges(any(), any()) }.returns(
            MutableLiveData(Resource.loading())
        )

        launchFragmentInContainer<ChallengesFragment>(
            themeResId = R.style.AppTheme,
            fragmentArgs = bundle
        )

        onView(withId(R.id.challengesProgressBar)).check(matches(isDisplayed()))
    }

    @Test
    fun should_items_on_list() {
        val challenges = listOf(
            Challenge(null, "id1", "challenge 1"),
            Challenge(null, "id3", null)
        )

        every { mockChallengeRepository.findChallenges(any(), any()) }.returns(
            getResourcePagedListFrom(challenges)
        )

        launchFragmentInContainer<ChallengesFragment>(
            themeResId = R.style.AppTheme,
            fragmentArgs = bundle
        )

        onView(withId(R.id.challengesRecyclerView)).check(matches(hasChildCount(challenges.size)))

        onView(
            allOf(
                withId(R.id.listItemChallengeName),
                isDescendantOfA(withRecyclerView(R.id.challengesRecyclerView).atPosition(0))
            )
        )
            .check(matches(withText(challenges[0].name)))

        onView(
            allOf(
                withId(R.id.listItemChallengeName),
                isDescendantOfA(withRecyclerView(R.id.challengesRecyclerView).atPosition(1))
            )
        )
            .check(matches(withText(R.string.loading)))
    }

    @Test
    fun should_show_end_of_list_indicator() {
        val challenges = listOf(
            Challenge(null, "id1", "challenge 1"),
            Challenge(null, "id3", null)
        )

        every { mockChallengeRepository.findChallenges(any(), any()) }.returns(
            getResourcePagedListFrom(challenges, endOfList = true)
        )

        launchFragmentInContainer<ChallengesFragment>(
            themeResId = R.style.AppTheme,
            fragmentArgs = bundle
        )

        onView(withId(R.id.challengesEndOfListIndicator)).check(matches(isDisplayed()))
    }
}
