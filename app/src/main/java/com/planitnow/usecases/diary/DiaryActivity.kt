package com.planitnow.usecases.diary

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.planitnow.R
import com.planitnow.databinding.ActivityDiaryBinding

class DiaryActivity: AppCompatActivity() {

    private val diaryViewModel : DiaryViewModel by viewModels()
    private lateinit var binding: ActivityDiaryBinding
    private lateinit var diaryAdapter: DiaryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryBinding.inflate(layoutInflater)

        diaryAdapter = DiaryAdapter(diaryViewModel)
        binding.listPlanRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.listPlanRecyclerView.adapter = diaryAdapter

        lifecycleScope.launchWhenResumed {
            diaryViewModel.refreshPlans()
            diaryAdapter.notifyDataSetChanged()
        }

        setContentView(binding.root)

        initializeToolbar()

    }

    private fun initializeToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        toolbar.findViewById<ImageView>(R.id.profile_logo).visibility = View.GONE
        toolbar.findViewById<ImageView>(R.id.app_logo).visibility = View.GONE

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}