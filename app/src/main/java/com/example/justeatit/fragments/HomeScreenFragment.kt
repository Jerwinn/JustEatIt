package com.example.justeatit.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.justeatit.R
import com.example.justeatit.activity.LocationServiceActivity
import com.example.justeatit.activity.RestaurantPageActivity
import com.example.justeatit.adapter.RestaurantAdapter
import com.example.justeatit.model.Restaurants
import com.example.justeatit.navigationFragment.NavigationBarFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import androidx.recyclerview.widget.GridLayoutManager as GridLayoutManager

/**
 * @author jerwinesguerra
 * This is a class for the logged in users, it shows the home screen fragment. However compared to guest page
 * this has extra features which include having the ability to click on an image to send reviews.
 * Bonus feature of having a map view of the restaurants location
 * Another bonus feature of showing recommended dishes for the restaurants.
 */
class HomeScreenFragment : Fragment() {
    private var database: FirebaseDatabase? = null
    private var reference: DatabaseReference? = null
    var adapter: RestaurantAdapter? = null
    var recyclerView: RecyclerView? = null
    var restaurantList = ArrayList<Restaurants>()

    /**
     * Creates the view screen for the restaurants which shows the image, restaurant name and description.
     * This is done by taking in data from firebase.
     * onClickListeners are set to the images to allow users to be able to send review for that restaurant.
     *
     * @return view This is the home screen for the logged in users
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        database = FirebaseDatabase.getInstance("https://justeatit-4af7e-default-rtdb.europe-west1.firebasedatabase.app")
        reference = database?.getReference("restaurants")

        val firebaseListener = object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val  child: MutableIterable<DataSnapshot> = snapshot.children
                child.forEach{

                    val restaurants = Restaurants(
                        it.child("restaurant_img").value.toString(),
                        it.child("restaurant_name").value.toString(),
                        it.child("description").value.toString())
                    restaurantList.add(restaurants)

                }

                adapter = RestaurantAdapter(restaurantList)
                recyclerView?.adapter = adapter


                adapter!!.setOnItemClickListener(object: RestaurantAdapter.ItemClickListener {
                    override fun onItemClick(position: Int) {
                        Toast.makeText(context, restaurantList[position].restaurantName + " was clicked.",Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, RestaurantPageActivity::class.java)
                        intent.putExtra("restaurantName",restaurantList[position].restaurantName)
                        intent.putExtra("image", restaurantList[position].image)
                        intent.putExtra("description",restaurantList[position].restaurantDescription)
                        startActivity(intent)
                    }
                })
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("test error", error.message)
            }
        }

        reference?.addValueEventListener(firebaseListener)

        val view = inflater.inflate(R.layout.fragment_home_screen, container, false)

        // Recyclerview settings set here.
        recyclerView = view.findViewById(R.id.homeScreenRecyclerView)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)

        // Button to log out the user
        view.findViewById<Button>(R.id.logoutButton).setOnClickListener{
            Firebase.auth.signOut()
            val navLogin = activity as NavigationBarFragment
            navLogin.navigateFrag(LoginFragment(),false)
        }

        // Button to show bonus feature one
        view.findViewById<Button>(R.id.locationButton).setOnClickListener{
            val intent = Intent (requireContext(), LocationServiceActivity::class.java)
            startActivity(intent)
        }

        // Button to show bonus feature two
        view.findViewById<Button>(R.id.recommendation).setOnClickListener{
            val navRecommendation = activity as NavigationBarFragment
            navRecommendation.navigateFrag(RecommendationFragment(),false)
        }

        return view
    }
}