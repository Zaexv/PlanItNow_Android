package com.planitnow.usecases.homefeed

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.graphql.models.AllPlansQuery
import com.planitnow.databinding.CardPlanBinding
import coil.api.load
import com.planitnow.R
import com.planitnow.usecases.viewplan.ViewPlanRouter


class HomeAdapter(var homeViewModel : HomeViewModel) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>(){


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
        holder.binding.planHourTextView.text = " " + plan.initHour + " " + plan.endHour
        holder.binding.planDateTextView.text = plan.initDate.toString()
        holder.binding.planLocationTextView.text = plan.location
        holder.binding.planImageView.load("https://res.cloudinary.com/demo/image/upload/c_crop,h_200,w_300/sample.jpg"){
            placeholder(R.drawable.ic_launcher_foreground)
        }
        if (position == homeViewModel.allPlans.size - 1) {
            onEndOfListReached?.invoke()
        }

        holder.binding.root.setOnClickListener(){
            ViewPlanRouter().launch(holder.binding.root.context)
        }

    }



}