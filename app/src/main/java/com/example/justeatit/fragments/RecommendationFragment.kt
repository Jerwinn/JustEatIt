package com.example.justeatit.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import com.example.justeatit.R
import com.example.justeatit.adapter.RecommendationAdapter
import com.example.justeatit.model.Recommendation
import com.example.justeatit.navigationFragment.NavigationBarFragment

/**
 * @author jerwinesguerra
 *
 * This class is made to show the recommended dishes of each restaurant through the use of recyclerView
 * and taking in inputs from firebase.
 */
class RecommendationFragment : Fragment() {
    private var firebase: FirebaseDatabase? = null
    private var firebaseReference: DatabaseReference? = null
    var adapter: RecommendationAdapter? = null
    var recyclerView: RecyclerView? = null
    var recommendationList = ArrayList<Recommendation>()

    /**
     * This creates the view of the recommendation view. This takes the image, restaurant name and dish
     * from firebase and displays it.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        firebase = FirebaseDatabase.getInstance("https://justeatit-4af7e-default-rtdb.europe-west1.firebasedatabase.app")
        firebaseReference = firebase!!.getReference("restaurants")

        //firebase listener which takes in the data from firebase and sets it to a recycler view
        val firebaseListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val child: MutableIterable<DataSnapshot> = snapshot.children
                child.forEach {

                    val recommendation = Recommendation(
                        it.child("dish_img").value.toString(),
                        it.child("restaurant_name").value.toString(),
                        it.child("dish").value.toString())

                    recommendationList.add(recommendation)
                }
                adapter = RecommendationAdapter(recommendationList)
                recyclerView?.adapter = adapter
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Database error", error.message)
            }
        }

        //Recycler view for the recommended dish is set
        firebaseReference?.addValueEventListener(firebaseListener)
        val view = inflater.inflate(R.layout.fragment_recommendation,container,false)
        recyclerView = view.findViewById(R.id.recommendationRecyclerView)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = GridLayoutManager(context,1,GridLayoutManager.VERTICAL,false)

        //a navigation button to allow the users to back to the home screen.
        view.findViewById<Button>(R.id.home_recommendation).setOnClickListener {
            val navLogin = activity as NavigationBarFragment
            navLogin.navigateFrag(HomeScreenFragment(), false)
        }
        return view
    }
}