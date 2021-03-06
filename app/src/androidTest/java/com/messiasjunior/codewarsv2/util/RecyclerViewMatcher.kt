package com.messiasjunior.codewarsv2.util

import android.content.res.Resources
import android.content.res.Resources.NotFoundException
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

/**
 * @author dannyroa
 * https://github.com/dannyroa/espresso-samples/blob/master/RecyclerView/app/src/androidTest/java/com/dannyroa/espresso_samples/recyclerview/RecyclerViewMatcher.java
 */
class RecyclerViewMatcher(private val recyclerViewId: Int) {

    fun atPosition(position: Int): Matcher<View> {
        return atPositionOnView(position, -1)
    }

    private fun atPositionOnView(position: Int, targetViewId: Int): Matcher<View> {
        return RecyclerViewTypeSafeMatcher(recyclerViewId, position, targetViewId)
    }
}

fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
    return RecyclerViewMatcher(recyclerViewId)
}

class RecyclerViewTypeSafeMatcher(
    private val recyclerViewId: Int,
    private val position: Int,
    private val targetViewId: Int
) : TypeSafeMatcher<View>() {
    private var resources: Resources? = null
    private var childView: View? = null

    override fun describeTo(description: Description) {
        var idDescription = recyclerViewId.toString()
        if (this.resources != null) {
            idDescription = try {
                this.resources?.getResourceName(recyclerViewId)!!
            } catch (resourceNotFound: NotFoundException) {
                String.format(
                    "%s (resource name not found)",
                    Integer.valueOf(recyclerViewId)
                )
            }
        }

        description.appendText("with id: $idDescription")
    }

    override fun matchesSafely(view: View?): Boolean {
        resources = view?.resources

        if (childView == null) {
            val recyclerView = view?.rootView?.findViewById(recyclerViewId) as RecyclerView
            childView = if (recyclerView.id == recyclerViewId) {
                recyclerView.findViewHolderForAdapterPosition(position)!!.itemView
            } else {
                return false
            }
        }

        return if (targetViewId == -1) {
            view == childView
        } else {
            val targetView = childView!!.findViewById<View>(targetViewId)
            view == targetView
        }
    }
}
