package com.example.justeatit.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.justeatit.R
import com.example.justeatit.model.Restaurants
import com.squareup.picasso.Picasso

/**
 * @author jerwinesguerra
 *
 * This class is made as an adapter for the recommended dish bonus feature
 * @constructor restaurantList This takes a list of the restaurants made from the
 * restaurant model
 */
class RestaurantAdapter(private var restaurantList:MutableList<Restaurants>):RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {
    private lateinit var mListener: ItemClickListener

    /**
     * an interface which allows images to be clicked based on position
     */
    interface ItemClickListener{
        fun onItemClick(position: Int)
    }

    /**
     * This sets the onClickListener for the restaurant images.
     */
    fun setOnItemClickListener(listener: ItemClickListener){
        mListener = listener

    }

    /**
     * This function creates the restaurant the recyclerView for the restaurants.
     * @return layoutView this is the recyclerView layout
     * @return mListener the onClickListener for the images.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantViewHolder {
        val layoutView:View = LayoutInflater.from(parent.context).inflate(R.layout.restaurants_card_view, parent, false)
        return RestaurantViewHolder(layoutView, mListener)
    }

    /**
     * Sets the content of the recyclerview based on the position.
     */
    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        Picasso.get().load(restaurantList[position].image).into(holder.restaurantImage)
        holder.restaurantName.text = restaurantList[position].restaurantName
        holder.restaurantDescription.text = restaurantList[position].restaurantDescription
    }

    /**
     * @return the size of the list
     */
    override fun getItemCount(): Int {
        return restaurantList.size
    }

    /**
     * This class sets the content for the recyclerView along with an onClickListener
     * which will then allow the images of the restaurants to be clickable.
     */
    class RestaurantViewHolder(view: View, listener: ItemClickListener): RecyclerView.ViewHolder(view){
        val restaurantImage: ImageView = view.findViewById(R.id.restaurant_image)
        val restaurantName: TextView = view.findViewById(R.id.restaurant_title)
        val restaurantDescription: TextView = view.findViewById(R.id.restaurant_description)

        init{
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}