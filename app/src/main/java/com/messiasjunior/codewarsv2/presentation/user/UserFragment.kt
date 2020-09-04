package com.messiasjunior.codewarsv2.presentation.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.messiasjunior.codewarsv2.R
import com.messiasjunior.codewarsv2.model.ChallengeType
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment() {
    private val args by navArgs<UserFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        setupBottomNavigation()
    }

    private fun setupViewPager() {
        userViewPager.adapter = ChallengesPageAdapter(childFragmentManager, args.user)
        userViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                userBottomNavigation?.menu?.getItem(position)?.let {
                    userBottomNavigation.selectedItemId = it.itemId
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun setupBottomNavigation() {
        userBottomNavigation.setOnNavigationItemSelectedListener {
            val type = when (it.itemId) {
                R.id.userBottomAuthoredCompleted -> ChallengeType.AUTHORED
                R.id.userBottomNavigationCompleted -> ChallengeType.COMPLETED
                else -> TODO()
            }

            userViewPager.currentItem = type.ordinal

            return@setOnNavigationItemSelectedListener true
        }
    }
}
