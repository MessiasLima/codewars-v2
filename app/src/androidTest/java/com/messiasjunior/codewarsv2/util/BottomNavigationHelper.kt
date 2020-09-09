package com.messiasjunior.codewarsv2.util

import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.hamcrest.Description

class HasCheckedItemMatcher(
    private val id: Int
) : BoundedMatcher<View, BottomNavigationView>(BottomNavigationView::class.java) {
    private val checkedIds: MutableSet<Int> = HashSet()
    private var itemFound = false
    private var triedMatching = false

    override fun describeTo(description: Description) {
        if (!triedMatching) {
            description.appendText("BottomNavigationView")
            return
        }

        description.appendText("BottomNavigationView to have a checked item with id=")
        description.appendValue(id)

        if (itemFound) {
            description.appendText(", but selection was=")
            description.appendValue(checkedIds)
        } else {
            description.appendText(", but it doesn't have an item with such id")
        }
    }

    override fun matchesSafely(navigationView: BottomNavigationView): Boolean {
        triedMatching = true

        val menu: Menu = navigationView.menu

        for (i in 0 until menu.size()) {
            val item: MenuItem = menu.getItem(i)
            if (item.isChecked) {
                checkedIds.add(item.itemId)
            }
            if (item.itemId == id) {
                itemFound = true
            }
        }

        return checkedIds.contains(id)
    }
}

fun hasCheckedItem(id: Int) = HasCheckedItemMatcher(id)
