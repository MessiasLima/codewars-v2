package com.messiasjunior.codewarsv2.presentation.challenges

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.messiasjunior.codewarsv2.R
import com.messiasjunior.codewarsv2.databinding.ListItemChallengeBinding
import com.messiasjunior.codewarsv2.model.Challenge

class ChallengesAdapter(
    private val challengesViewModel: ChallengesViewModel
) : PagedListAdapter<Challenge, RecyclerView.ViewHolder>(DIFF_UTIL) {
    var showEndOfListIndicator: Boolean = false
        set(value) {
            if (value) {
                notifyItemInserted(itemCount)
            }
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_CHALLENGE -> ChallengesViewHolder.create(parent)
            ITEM_TYPE_FOOTER -> FooterViewHolder.create(parent)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ChallengesViewHolder) bindChallenge(holder, position)
    }

    private fun bindChallenge(holder: ChallengesViewHolder, position: Int) {
        val challenge = getItem(position)
        holder.bind(challenge)
        challenge?.let {
            holder.itemView.setOnClickListener {
                challengesViewModel.onChallengeClicked(challenge)
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (showEndOfListIndicator) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) {
            ITEM_TYPE_CHALLENGE
        } else {
            ITEM_TYPE_FOOTER
        }
    }

    companion object {
        private const val ITEM_TYPE_CHALLENGE = 1
        private const val ITEM_TYPE_FOOTER = 2

        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Challenge>() {
            override fun areItemsTheSame(oldItem: Challenge, newItem: Challenge): Boolean {
                return oldItem.codewarsID == newItem.codewarsID
            }

            override fun areContentsTheSame(oldItem: Challenge, newItem: Challenge): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}

class ChallengesViewHolder private constructor(
    private val binding: ListItemChallengeBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(challenge: Challenge?) {
        binding.challengeName = challenge?.name ?: itemView.context.getString(R.string.loading)
    }

    companion object {
        fun create(parent: ViewGroup): ChallengesViewHolder {
            val binding = ListItemChallengeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ChallengesViewHolder(binding)
        }
    }
}

class FooterViewHolder private constructor(view: View) : RecyclerView.ViewHolder(view) {
    companion object {
        fun create(parent: ViewGroup): FooterViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_challenge_footer,
                parent,
                false
            )
            return FooterViewHolder(view)
        }
    }
}
