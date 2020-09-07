package com.messiasjunior.codewarsv2.presentation.challenges

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.messiasjunior.codewarsv2.R
import com.messiasjunior.codewarsv2.databinding.ListItemChallengeBinding
import com.messiasjunior.codewarsv2.model.Challenge

class ChallengesAdapter(
    private val challengesViewModel: ChallengesViewModel
) : PagedListAdapter<Challenge, ChallengesViewHolder>(DIFF_UTIL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengesViewHolder {
        return ChallengesViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ChallengesViewHolder, position: Int) {
        val challenge = getItem(position)
        holder.bind(challenge)
        challenge?.let {
            holder.itemView.setOnClickListener {
                challengesViewModel.onChallengeClicked(challenge)
            }
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Challenge>() {
            override fun areItemsTheSame(oldItem: Challenge, newItem: Challenge): Boolean {
                return oldItem.id == newItem.id
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
