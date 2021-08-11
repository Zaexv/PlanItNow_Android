package com.planitnow.usecases.viewplan

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import coil.api.load
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.graphql.models.DetailedPlanQuery
import com.planitnow.R
import com.planitnow.backend.ApolloQueryHandler
import com.planitnow.databinding.ActivityMainBinding
import com.planitnow.databinding.ActivityViewPlanBinding
import com.planitnow.model.session.Session
import com.planitnow.usecases.login.LoginViewModel

class ViewPlanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewPlanBinding
    private val viewPlanViewModel : ViewPlanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val planID = intent.getStringExtra("id")
        println("El plan ID es $planID")
        println("El intent es:" + intent.toString())
        lifecycleScope.launchWhenResumed {
            val detailedPlan = viewPlanViewModel.getPlanById(planID!!)
            initView()
            bindPlan(detailedPlan)
        }

        binding = ActivityViewPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initView() {
        if(viewPlanViewModel.detailedPlan.owner.id == Session.instance.me.id) {
            binding.viewPlanButtonDelete.visibility = View.VISIBLE
            binding.viewPlanOwnerCard.visibility = View.INVISIBLE
        }
    }

    private fun bindPlan(detailedPlan: DetailedPlanQuery.DetailedPlan) {
        binding.viewPlanTitle.text = detailedPlan.title
        binding.viewPlanDescription.text = detailedPlan.description
        binding.viewPlanLocation.text = detailedPlan.location
        binding.viewPlanDate.text = detailedPlan.initDate.toString()
        binding.viewPlanInitHour.text = detailedPlan.initHour.toString()
        binding.viewPlanEndHour.text = detailedPlan.endHour.toString()
        binding.viewPlanImage.load("https://image.pngaaa.com/519/2451519-middle.png"){
                placeholder(R.drawable.ic_launcher_foreground)
            }
        binding.viewPlanOwnerName.text = detailedPlan.owner.username
        binding.viewPlanOwnerProfilePicture.load("https://streammentor.com/wp-content/uploads/2020/12/output-onlinepngtools9.png"){
            placeholder(R.drawable.ic_home_black_24dp)
        }
    }


}