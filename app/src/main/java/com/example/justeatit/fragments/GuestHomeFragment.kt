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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.justeatit.R
import com.example.justeatit.activity.RetrieveReviewsActivity
import com.example.justeatit.adapter.RestaurantAdapter
import com.example.justeatit.model.Restaurants
import com.example.justeatit.navigationFragment.NavigationBarFragment
import com.google.firebase.database.*

/**
 * @author jerwinesguerra
 * This is a class for the guest home screen fragment. It is used to show a different page compared to the
 * logged in users.
 */
class GuestHomeFragment : Fragment() {
    private var database: FirebaseDatabase? = null
    private var reference: DatabaseReference? = null
    var adapter: RestaurantAdapter? = null
    var recyclerView: RecyclerView? = null
    var restaurantList = ArrayList<Restaurants>()

    /**
     * This function creates the page for the users.
     * Showing the different restaurants and their details that are registered with the application.
     * Similar to logged in users images are clickable however it will show them the review pages instead of
     * the page where they can submit reviews.
     *
     * @return view This is the guest home screen
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        database = FirebaseDatabase.getInstance("https://justeatit-4af7e-default-rtdb.europe-west1.firebasedatabase.app")
        reference = database?.getReference("restaurants")

        val firebaseListener = object: ValueEventListener {
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
                        Toast.makeText(context, "Viewing " + restaurantList[position].restaurantName, Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, RetrieveReviewsActivity::class.java)
                        intent.putExtra("restaurantName",restaurantList[position].restaurantName)
                        intent.putExtra("image",restaurantList[position].image)
                        intent.putExtra("description",restaurantList[position].restaurantDescription)
                        startActivity(intent)
                    }

                })

            }

            /**
             * function made if the firebase has given an error.
             */
            override fun onCancelled(error: DatabaseError) {
                Log.d("test error", error.message)
            }
        }

        // This is where the recyclerview for the guest user home screen is set.
        reference?.addValueEventListener(firebaseListener)

        val view = inflater.inflate(R.layout.fragment_guest, container, false)

        recyclerView = view.findViewById(R.id.guestScreenRecyclerView)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = GridLayoutManager(context,2, GridLayoutManager.VERTICAL,false)

        view.findViewById<Button>(R.id.backToHomeScreen).setOnClickListener{
            val navLogin = activity as NavigationBarFragment
            navLogin.navigateFrag(LoginFragment(),false)
        }
        return view
    }
}