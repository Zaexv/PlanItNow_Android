package com.planitnow.usecases.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.planitnow.R
import com.planitnow.databinding.FragmentSearchBinding
import com.planitnow.usecases.homefeed.HomeAdapter
import com.planitnow.usecases.notifications.NotificationsRouter

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private var _binding: FragmentSearchBinding? = null
    private lateinit var searchAdapter: SearchAdapter


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var toolbar: Toolbar


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)

        searchAdapter = SearchAdapter(searchViewModel)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.searchPlanRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchPlanRecyclerView.adapter = searchAdapter


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenResumed {
            initializeToolbar()
            searchViewModel.searchOrRecommendPlans("")
            searchAdapter.notifyDataSetChanged()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun initializeToolbar() {

        toolbar = (activity as AppCompatActivity).findViewById<Toolbar>(R.id.main_toolbar)
        toolbar.menu.clear()
        toolbar.inflateMenu(R.menu.search_menu)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_add_friend -> Toast.makeText(
                    toolbar.context,
                    "Añade amigo :)",
                    Toast.LENGTH_LONG
                ).show()
                R.id.action_notifications -> NotificationsRouter().launch(requireContext())
                else -> Toast.makeText(toolbar.context, "purzao", Toast.LENGTH_LONG).show()
            }
            false
        }

        val searchView =
            (activity as AppCompatActivity).findViewById<SearchView>(R.id.action_search)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                lifecycleScope.launchWhenResumed {
                    searchViewModel.searchOrRecommendPlans(p0!!)
                    searchAdapter.notifyDataSetChanged()
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0.equals("")) {
                    lifecycleScope.launchWhenResumed {
                        searchViewModel.searchOrRecommendPlans(p0!!)
                        searchAdapter.notifyDataSetChanged()
                    }
                }
                return true
            }
        })


    }

}