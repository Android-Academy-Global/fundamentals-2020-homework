package com.android.academy.fundamentals.homework.features.moviedetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.android.academy.fundamentals.homework.R
import com.android.academy.fundamentals.homework.model.ActorData

class ActorsListAdapter : ListAdapter<ActorData, ActorsListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_actor, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val actorImage: ImageView = itemView.findViewById(R.id.actor_image)
        private val actorName: TextView = itemView.findViewById(R.id.actor_name)

        fun bind(item: ActorData) {
            actorImage.load(item.imageUrl)
            actorName.text = item.name
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ActorData>() {
        override fun areItemsTheSame(oldItem: ActorData, newItem: ActorData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ActorData, newItem: ActorData): Boolean {
            return oldItem == newItem
        }
    }
}