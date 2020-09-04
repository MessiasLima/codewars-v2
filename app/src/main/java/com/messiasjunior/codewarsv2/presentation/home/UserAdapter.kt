package com.messiasjunior.codewarsv2.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.messiasjunior.codewarsv2.R
import com.messiasjunior.codewarsv2.databinding.ListItemUserBinding
import com.messiasjunior.codewarsv2.model.User
import java.util.Locale

class UserAdapter(
    private val homeViewModel: HomeViewModel
) : PagedListAdapter<User, UserViewHolder>(DIFF_UTIL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { user ->
            holder.bind(user)
            holder.itemView.setOnClickListener { homeViewModel.selectUser(user) }
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.username == newItem.username
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.username == newItem.username &&
                    oldItem.name == newItem.name &&
                    oldItem.honor == newItem.honor &&
                    oldItem.bestLanguage == newItem.bestLanguage
            }
        }
    }
}

class UserViewHolder(
    private val binding: ListItemUserBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User) {
        binding.name = user.displayName

        binding.honor = user.honor

        binding.rank = user.leaderboardPosition?.toString() ?: binding.root.context.getString(
            R.string.unavailable
        )

        binding.bestLanguage = binding.root.context.getString(
            R.string.best_language,
            user.bestLanguage?.name?.capitalize(Locale.getDefault()),
            user.bestLanguage?.rank?.score
        )
    }

    companion object {
        fun create(parent: ViewGroup): UserViewHolder {
            val binding = ListItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return UserViewHolder(binding)
        }
    }
}
