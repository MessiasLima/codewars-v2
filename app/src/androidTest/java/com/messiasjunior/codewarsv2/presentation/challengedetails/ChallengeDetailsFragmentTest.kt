package com.messiasjunior.codewarsv2.presentation.challengedetails

import androidx.core.os.bundleOf
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.snackbar.SnackbarContentLayout
import com.messiasjunior.codewarsv2.R
import com.messiasjunior.codewarsv2.di.CodewarsApplication
import com.messiasjunior.codewarsv2.model.Challenge
import com.messiasjunior.codewarsv2.repository.ChallengeRepository
import com.messiasjunior.codewarsv2.util.createFakeFragmentInjector
import com.messiasjunior.codewarsv2.util.resource.Resource
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ChallengeDetailsFragmentTest {
    private val mockChallengeRepository = mockk<ChallengeRepository>()
    private val app = ApplicationProvider.getApplicationContext<CodewarsApplication>().apply {
        dispatchingAndroidInjector = createFakeFragmentInjector<ChallengeDetailsFragment> {
            viewModelFactory = ChallengeDetailsViewModel.Factory(mockChallengeRepository)
        }
    }

    @Test
    fun should_show_loading_indicator() {
        coEvery { mockChallengeRepository.getChallengeDetails(any()) }.returns(Resource.loading())

        val bundle = bundleOf(
            Pair("challenge", Challenge(1L, "id1", "Challenge 1", "Description"))
        )

        launchFragmentInContainer<ChallengeDetailsFragment>(
            themeResId = R.style.AppTheme,
            fragmentArgs = bundle
        )

        onView(isAssignableFrom(ContentLoadingProgressBar::class.java)).check(matches(isDisplayed()))
    }

    @Test
    fun should_show_challenge_content() {

        val challenge = Challenge(1L, "id1", "Challenge 1", "Description")

        coEvery { mockChallengeRepository.getChallengeDetails(any()) }.returns(
            Resource.success(challenge)
        )

        val bundle = bundleOf(
            Pair("challenge", challenge)
        )

        launchFragmentInContainer<ChallengeDetailsFragment>(
            themeResId = R.style.AppTheme,
            fragmentArgs = bundle
        )

        onView(withId(R.id.challengeDetailsName)).check(matches(withText(challenge.name)))
        onView(withId(R.id.challengeDetailsDescription)).check(matches(withText(challenge.description)))
    }

    @Test
    fun should_show_snack_bar_on_error() {

        val challenge = Challenge(1L, "id1", "Challenge 1", "Description")

        coEvery { mockChallengeRepository.getChallengeDetails(any()) }.returns(
            Resource.error(throwable = IOException())
        )

        val bundle = bundleOf(
            Pair("challenge", challenge)
        )

        launchFragmentInContainer<ChallengeDetailsFragment>(
            themeResId = R.style.AppTheme,
            fragmentArgs = bundle
        )

        onView(isAssignableFrom(SnackbarContentLayout::class.java)).check(matches(isDisplayed()))
        onView(withText(R.string.try_again)).check(matches(isDisplayed()))
    }
}
