package com.planitnow.usecases.homefeed

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.graphql.models.AllPlansQuery
import com.planitnow.databinding.CardPlanBinding
import coil.api.load
import coil.size.Scale
import coil.transform.BlurTransformation
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.planitnow.R
import com.planitnow.usecases.viewplan.ViewPlanActivity
import com.planitnow.usecases.viewplan.ViewPlanRouter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class HomeAdapter(var homeViewModel: HomeViewModel) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {


    class ViewHolder(val binding: CardPlanBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return homeViewModel.allPlans.size
    }

    var onEndOfListReached: (() -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plan = homeViewModel.allPlans.get(position)
        holder.binding.planTitleTextView.text = plan.title
        holder.binding.planDescriptionTextView.text = plan.description
        val initHour = plan.initHour.toString().subSequence(0, 5)
        val endHour = plan.endHour.toString().subSequence(0, 5)
        holder.binding.planHourTextView.text = "De $initHour a $endHour"
        var parsedDate = LocalDate.parse(plan.initDate.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val fechaEspanol = DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy", Locale("es","ES"))
        holder.binding.planDateTextView.text = fechaEspanol.format(parsedDate)
        holder.binding.planLocationTextView.text = plan.location
        holder.binding.planImageView.load(plan.urlPlanPicture) {
            scale(Scale.FILL)
            placeholder(R.drawable.ic_launcher_foreground)
            transformations(RoundedCornersTransformation(5f))
        }
        if (position == homeViewModel.allPlans.size - 1) {
            onEndOfListReached?.invoke()
        }

        holder.binding.root.setOnClickListener() {
            ViewPlanRouter().launchToId(holder.binding.root.context, plan.id)
            println("Intentando navegar a $plan.id")
        }

    }


}