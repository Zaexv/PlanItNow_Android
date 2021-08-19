package com.planitnow.usecases.homefeed

import android.app.AlertDialog
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.graphql.models.AllPlansQuery
import com.planitnow.R
import com.planitnow.backend.ApolloQueryHandler
import com.planitnow.databinding.FragmentHomeBinding
import com.planitnow.usecases.createplan.CreatePlanRouter
import com.planitnow.usecases.notifications.NotificationsRouter
import com.planitnow.usecases.viewplan.ViewPlanRouter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import android.widget.Toast.makeText as makeText1
import android.widget.Toast.makeText as toastMakeText

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var homeAdapter: HomeAdapter


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var toolbar: Toolbar


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)


        homeAdapter = HomeAdapter(homeViewModel)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.listPlanRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.listPlanRecyclerView.adapter = homeAdapter
        binding.gotoCreateplanbutton.setOnClickListener() {
            CreatePlanRouter().launch(requireContext())
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenResumed {
            initializeToolbar()
            homeViewModel.refreshPlans()
            homeAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeToolbar(){
        toolbar = (activity as AppCompatActivity).findViewById<Toolbar>(R.id.main_toolbar)
        toolbar.menu.clear()
        toolbar.inflateMenu(R.menu.home_menu)
        toolbar.setOnMenuItemClickListener{ item ->
            when (item.itemId){
                R.id.action_add_friend -> Toast.makeText(toolbar.context,"Añade amigo :)",Toast.LENGTH_LONG).show()
                R.id.action_notifications -> NotificationsRouter().launch(requireContext())
                else -> Toast.makeText(toolbar.context,"purzao",Toast.LENGTH_LONG).show()
            }
            false
        }

    }

}