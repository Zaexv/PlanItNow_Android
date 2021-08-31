package com.planitnow.usecases.viewplan

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import coil.api.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.graphql.models.DetailedPlanQuery
import com.planitnow.R
import com.planitnow.backend.ApolloQueryHandler
import com.planitnow.databinding.ActivityMainBinding
import com.planitnow.databinding.ActivityViewPlanBinding
import com.planitnow.model.session.Session
import com.planitnow.usecases.login.LoginViewModel
import com.planitnow.usecases.mainactivity.MainActivityRouter
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class ViewPlanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewPlanBinding
    private val viewPlanViewModel: ViewPlanViewModel by viewModels()

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
        if (viewPlanViewModel.detailedPlan.owner.id == Session.instance.me.id) {
            binding.viewPlanOwnerCard.visibility = View.GONE
            binding.viewPlanButtonDelete.visibility = View.VISIBLE
            binding.viewPlanParticipateButton.visibility = View.GONE
            binding.viewPlanButtonDelete.setOnClickListener() {

                //TODO añadir cuadro de Diálogo ¿Seguro que quieres borrar el plan?

                lifecycleScope.launchWhenResumed {
                    val ok = viewPlanViewModel.deleteViewingPlan()
                    if (ok) {
                        Toast.makeText(
                            this@ViewPlanActivity,
                            resources.getString(R.string.success_deleting_plan) + " " + viewPlanViewModel.detailedPlan.title,
                            Toast.LENGTH_LONG
                        ).show()
                        MainActivityRouter().launch(this@ViewPlanActivity)
                    } else {
                        Toast.makeText(
                            this@ViewPlanActivity,
                            R.string.error_deleting_plan,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        } else {
            binding.viewPlanParticipateButton.setOnClickListener() {
                lifecycleScope.launchWhenResumed {
                    viewPlanViewModel.participateInPlan(this@ViewPlanActivity)
                    bindPlan(viewPlanViewModel.detailedPlan)
                }
            }
        }
    }

    private fun bindPlan(detailedPlan: DetailedPlanQuery.DetailedPlan) {
        if(viewPlanViewModel.userIsParticipating) {
            binding.viewPlanParticipateButton.text = "Desapuntarse"
        } else {
            binding.viewPlanParticipateButton.text = "Apuntarse"
        }
        binding.viewPlanTitle.text = detailedPlan.title
        binding.viewPlanDescription.text = detailedPlan.description
        binding.viewPlanLocation.text = detailedPlan.location
        var parsedDate = LocalDate.parse(
            detailedPlan.initDate.toString(),
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        )
        val fechaEspanol = DateTimeFormatter.ofPattern("EEEE, dd/MMMM/yyyy", Locale("es", "ES"))
        binding.viewPlanDate.text = fechaEspanol.format(parsedDate)
        val initHour = detailedPlan.initHour.toString().subSequence(0, 5)
        val endHour = detailedPlan.endHour.toString().subSequence(0, 5)
        binding.viewPlanInitHour.text = "De $initHour"
        binding.viewPlanEndHour.text = " a $endHour"
        binding.viewPlanImage.load(detailedPlan.urlPlanPicture) {
            placeholder(R.drawable.ic_launcher_foreground)
        }

        binding.viewPlanOwnerPublicName.text = detailedPlan.owner.userProfile!!.publicUsername
        binding.viewPlanOwnerName.text = "@" + detailedPlan.owner.username
        binding.viewPlanOwnerProfilePicture.load(detailedPlan.owner.userProfile!!.urlProfilePicture) {
            scale(Scale.FILL)
            placeholder(R.drawable.ic_home_black_24dp)
            transformations(CircleCropTransformation())
        }

        //TODO poner algo más elegante
        val list =
            detailedPlan.participatingPlan.map { participatingPlan -> participatingPlan.participantUser.user.username }
        binding.viewPlanNumberParticipantsText.text =
            list.size.toString() + "/" + detailedPlan.maxParticipants + " Participantes"
        binding.viewPlanParticipantsText.text = list.toString()

    }


}