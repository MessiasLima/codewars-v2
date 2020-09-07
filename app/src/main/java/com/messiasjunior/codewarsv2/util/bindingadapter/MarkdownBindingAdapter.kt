package com.messiasjunior.codewarsv2.util.bindingadapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import io.noties.markwon.Markwon

@BindingAdapter("markdown")
fun parseMarkdown(view: TextView, markdown: String?) {
    markdown?.let {
        val markwon = Markwon.create(view.context)
        markwon.setMarkdown(view, it)
    }
}
