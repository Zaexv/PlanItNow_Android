package com.planitnow.usecases.notifications

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.planitnow.R
import com.planitnow.databinding.ActivityNotificationsBinding
import com.planitnow.usecases.homefeed.HomeAdapter
import com.planitnow.usecases.viewplan.ViewPlanViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NotificationsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationsBinding
    private val notificationsViewModel: NotificationsViewModel by viewModels()
    private lateinit var notificationsAdapter: NotificationsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        binding.listNotificationsRecyclerView.layoutManager = LinearLayoutManager(this)
        notificationsAdapter = NotificationsAdapter(notificationsViewModel)


        binding.listNotificationsRecyclerView.adapter = notificationsAdapter


        runBlocking {
            notificationsViewModel.refreshReceivedFriendRequests()
            notificationsAdapter.notifyDataSetChanged()
        }
        setContentView(binding.root)
    }

    override fun onResume() {
        lifecycleScope.launchWhenResumed {
            notificationsViewModel.refreshReceivedFriendRequests()
            notificationsAdapter.notifyDataSetChanged()
        }
        super.onResume()
    }

}