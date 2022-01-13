package com.example.justeatit.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.justeatit.R
import com.example.justeatit.model.Recommendation
import com.squareup.picasso.Picasso

/**
 * @author jerwinesguerra
 *
 * This class is made as an adapter for the recommended dish bonus feature
 * @constructor recommendationList This takes a list of the recommendations made from the
 * recommendation model
 */
class RecommendationAdapter(private var recommendationList:MutableList<Recommendation>):RecyclerView.Adapter<RecommendationAdapter.RecommendationViewHolder>() {

    /**
     * This creates the view holder for the recommendation view
     * @return layoutView this returns the recommendation view xml file allowing it to be launched
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendationViewHolder {
        val layoutView: View = LayoutInflater.from(parent.context).inflate(R.layout.recommendation_view, parent, false)
        return RecommendationViewHolder(layoutView)
    }

    /**
     * Sets the positions of the list of recommended dishes by position
     */
    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        Picasso.get().load(recommendationList[position].image).into(holder.dishImage)
        holder.restaurantName.text = recommendationList[position].restaurantName
        holder.recommendedDish.text = recommendationList[position].dishes
    }

    /**
     * @return the size of the recommendation list
     */
    override fun getItemCount(): Int {
        return recommendationList.size
    }

    /**
     * This sets the objects used by the recyclerViews view holder.
     */
    class RecommendationViewHolder(view: View): RecyclerView.ViewHolder(view){
        val dishImage: ImageView = view.findViewById(R.id.recommendation_image)
        val restaurantName: TextView = view.findViewById(R.id.recommendation_restaurant_title)
        val recommendedDish: TextView = view.findViewById(R.id.recommendation_restaurant_recommended)
        }
    }


