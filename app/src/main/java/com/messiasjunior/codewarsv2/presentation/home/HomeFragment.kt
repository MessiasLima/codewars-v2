package com.messiasjunior.codewarsv2.presentation.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.messiasjunior.codewarsv2.R
import com.messiasjunior.codewarsv2.databinding.FragmentHomeBinding
import com.messiasjunior.codewarsv2.exception.UserNotFountException
import com.messiasjunior.codewarsv2.repository.UserRepository
import com.messiasjunior.codewarsv2.util.event.EventObserver
import com.messiasjunior.codewarsv2.util.resource.ResourceObserver
import dagger.android.support.AndroidSupportInjection
import java.io.IOException
import javax.inject.Inject

class HomeFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: HomeViewModel.Factory
    private val viewModel by viewModels<HomeViewModel> { viewModelFactory }
    private lateinit var binding: FragmentHomeBinding

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOnUserSelectedEventHandler()
        setupOnErrorEventHandler()
        setupSavedUsersRecyclerView()
    }

    private fun setupOnUserSelectedEventHandler() {
        viewModel.userSelectedEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                val directions = HomeFragmentDirections.showDetailsFromUser(it, it.displayName)
                findNavController().navigate(directions)
            }
        )
    }

    private fun setupOnErrorEventHandler() {
        viewModel.onErrorEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                val message = when (it) {
                    is UserNotFountException -> getString(R.string.user_not_found, it.message)
                    is IOException -> getString(R.string.verify_network_connection)
                    else -> getString(R.string.generic_error_message)
                }

                Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
            }
        )
    }

    private fun setupSavedUsersRecyclerView() {
        val userAdapter = UserAdapter(viewModel)
        with(binding.homeUserRecyclerView) {
            adapter = userAdapter
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.savedUsersResource.observe(
            viewLifecycleOwner,
            ResourceObserver(userAdapter::submitList)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home, menu)
        setupSearchItem(menu.findItem(R.id.menuHomeSearch))
    }

    private fun setupSearchItem(searchItem: MenuItem?) = searchItem?.let { menuItem ->
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let(viewModel::searchUser)
                menuItem.collapseActionView()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuHomeSortByHonor -> {
                viewModel.setSortOrder(UserRepository.SortOrder.HONOR)
                true
            }

            R.id.menuHomeSortByRecent -> {
                viewModel.setSortOrder(UserRepository.SortOrder.SEARCH_DATE)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
