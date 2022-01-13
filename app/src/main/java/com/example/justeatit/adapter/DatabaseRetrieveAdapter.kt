package com.example.justeatit.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.justeatit.R
import com.example.justeatit.model.Database
import kotlinx.android.synthetic.main.activity_review_layout.view.*

/**
 * @author jerwinesguera
 *
 * this class is used as an adapter from the recycler view
 * @constructor list This is an arraylist made from the database model
 */
class DatabaseRetrieveAdapter (private var list:ArrayList<Database>) : RecyclerView.Adapter<DatabaseRetrieveAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var review: TextView = itemView.tv_review
        var timeOfVisit: TextView = itemView.tv_timeOfVisit
        var rating: RatingBar = itemView.ratingBarReview
        var address: TextView = itemView.tv_address
    }

    /**
     * @return the activity review layout as the layout for recyclerview
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_review_layout, parent, false))
    }

    /**
     * This binds the objects from the view holder which allows their positions to be stored.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.review.text=list[position].reviews
        holder.timeOfVisit.text=list[position].timeOfVisit
        holder.rating.numStars=list[position].Rating
        holder.address.text=list[position].address
    }

    /**
     * returns the size of the list.
     */
    override fun getItemCount(): Int {
        return list.size
    }
}