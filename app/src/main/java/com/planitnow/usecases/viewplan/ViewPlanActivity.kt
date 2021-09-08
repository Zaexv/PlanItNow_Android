package com.planitnow.usecases.viewplan

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import coil.api.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.graphql.models.DetailedPlanQuery
import com.planitnow.R
import com.planitnow.databinding.ActivityViewPlanBinding
import com.planitnow.model.session.Session
import com.planitnow.usecases.editplan.EditPlanRouter
import com.planitnow.usecases.mainactivity.MainActivityRouter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class ViewPlanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewPlanBinding
    private val viewPlanViewModel: ViewPlanViewModel by viewModels()
    private lateinit var toolbar: Toolbar

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
        initializeToolbar()
        if (viewPlanViewModel.detailedPlan.owner.id == Session.instance.me.id) {
            binding.viewPlanOwnerCard.visibility = View.GONE
            binding.viewPlanParticipateButton.visibility = View.GONE
            addToolbarMenuListener()
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
        if (viewPlanViewModel.userIsParticipating) {
            binding.viewPlanParticipateButton.text = getText(R.string.join_plan)
        } else {
            binding.viewPlanParticipateButton.text = getText(R.string.leave_plan)
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
        binding.viewPlanOwnerProfilePicture.load(detailedPlan.owner.userProfile.urlProfilePicture) {
            scale(Scale.FILL)
            placeholder(R.drawable.ic_home_black_24dp)
            transformations(CircleCropTransformation())
        }

        val list =
            detailedPlan.participatingPlan.map { participatingPlan -> participatingPlan.participantUser.user.username }
        binding.viewPlanNumberParticipantsText.text =
            list.size.toString() + "/" + detailedPlan.maxParticipants + " " + getText(R.string.participants)
        binding.viewPlanParticipantsText.text = list.toString()
    }

    private fun deleteThisPlan() {
        lifecycleScope.launchWhenResumed {
            val ok = viewPlanViewModel.deleteViewingPlan()
            if (ok) {
                Toast.makeText(
                    this@ViewPlanActivity,
                    resources.getString(R.string.success_deleting_plan) + " " + viewPlanViewModel.detailedPlan.title,
                    Toast.LENGTH_SHORT
                ).show()
                MainActivityRouter().launch(this@ViewPlanActivity)
            } else {
                Toast.makeText(
                    this@ViewPlanActivity,
                    R.string.error_deleting_plan,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initializeToolbar() {
        toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        findViewById<ImageView>(R.id.profile_logo).visibility = View.GONE
        findViewById<ImageView>(R.id.app_logo).visibility = View.GONE
        toolbar.title = getText(R.string.view_plan)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun addToolbarMenuListener() {
        toolbar.inflateMenu(R.menu.view_plan_menu)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_delete_plan -> showDeleteDialog()
                R.id.action_edit_plan -> EditPlanRouter().launch(this)
                else -> Toast.makeText(toolbar.context, "purzao", Toast.LENGTH_SHORT).show()
            }
            false
        }
    }

    private fun showDeleteDialog() {
        lateinit var dialog: AlertDialog

        val builder = AlertDialog.Builder(this)

        builder.setTitle(getText(R.string.delete_plan))
        builder.setMessage(R.string.sure_delete_plan)

        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> deleteThisPlan()
            }
        }

        builder.setPositiveButton(getText(R.string.yes), dialogClickListener)
        builder.setNeutralButton(getText(R.string.cancel), dialogClickListener)

        dialog = builder.create()
        dialog.show()
    }
}