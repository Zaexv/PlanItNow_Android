package com.planitnow.usecases.homefeed

import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.graphql.models.AllPlansQuery
import com.planitnow.databinding.FragmentHomeBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import android.widget.Toast.makeText as makeText1
import android.widget.Toast.makeText as toastMakeText

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: FloatingActionButton = binding.gotoCreateplanbutton;
            textView.setOnClickListener {
                val apolloClient = ApolloClient(serverUrl = "http://10.0.2.2:8000/graphql")
                   runBlocking {
                       val response = try {
                           val res = apolloClient.query(AllPlansQuery())
                           Toast.makeText(context, res.data?.toString(), Toast.LENGTH_LONG).show()
                           println("Aw yeah!" + res.data?.toString())
                       } catch (e: ApolloException) {
                           println("Oh no!")
                           e.printStackTrace()
                           Toast.makeText(context, "Oh no!", Toast.LENGTH_LONG).show()
                       };


                   }


            }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}