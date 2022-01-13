package com.example.justeatit.fragments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.justeatit.R
import com.example.justeatit.activity.RetrieveReviewsActivity
import com.example.justeatit.model.Database
import com.example.justeatit.navigationFragment.NavigationBarFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.core.Context
import kotlinx.android.synthetic.main.activity_restaurant_page.*

/**
 * @author jerwinesguerra
 *
 * This class is made to show the review page of the restaurant. It sends and receives data from firebase
 */
class RestaurantPageFragment : AppCompatActivity(), NavigationBarFragment {
    private lateinit var  firebase: FirebaseDatabase
    private lateinit var firebaseReference:DatabaseReference
    private lateinit var buttonSendReview: Button
    private lateinit var buttonViewReviews: Button

    /**
     * Initial data received from firebase is used to show the details of the restaurant to be reviewed
     * It then has Edit text, to take input from the user which will then be sent to the database.
     * Bundles are set to be sent and used in the next activity.
     * Two buttons are also present to send data to firebase and to see the reviews.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_page)

        firebase = FirebaseDatabase.getInstance("https://justeatit-4af7e-default-rtdb.europe-west1.firebasedatabase.app")
        firebaseReference = firebase.getReference("Reviews")


        val restaurantName : TextView = findViewById(R.id.individual_page_title)
        val restaurantImage : ImageView = findViewById(R.id.restaurant_page_image)
        val restaurantDescription: TextView = findViewById(R.id.individual_page_description)
        buttonSendReview = findViewById(R.id.submit_review)
        buttonViewReviews = findViewById(R.id.view_reviews)

        val bundle : Bundle?= intent.extras
        val heading = bundle!!.getString("restaurantName")
        val description = bundle.getString("description")
        val image2 = bundle.getInt("image")

        restaurantName.text = heading
        restaurantDescription.text = description
        restaurantImage.setImageResource(image2)

       buttonSendReview.setOnClickListener{
           sendFirebase()
           Toast.makeText(applicationContext, "Review successfully sent",Toast.LENGTH_SHORT).show()
        }
        buttonViewReviews.setOnClickListener{
            startActivity(Intent(applicationContext, RetrieveReviewsActivity::class.java))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.toolbar_back -> this.onBackPressed()
            R.id.exit_to_home_screen -> FirebaseAuth.getInstance().signOut()
            R.id.toolbar_search -> Toast.makeText(applicationContext, "search button clicked", Toast.LENGTH_SHORT).show()
            R.id.settings_toolbar -> Toast.makeText(applicationContext, "settings button clicked", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun navigateFrag(fragment: Fragment, addToStack: Boolean) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(R.id.container,fragment)

        if(addToStack){
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    private fun sendFirebase(){
        val review = individual_page_time_of_visit_review.text.toString().trim()
        val dateOfVisit = individual_page_time_of_visit_review.text.toString().trim()
        val rating = ratingBar.rating.toInt()
        val address = individual_address.text.toString().trim()

        if(review.isNotEmpty() && dateOfVisit.isNotEmpty()){
            val model = Database(review, dateOfVisit, rating, address)
            val id = firebaseReference.push().key
            firebaseReference.child(id!!).setValue(model)
        }
        else{
            Toast.makeText(applicationContext, "Invalid Review - All fields must be filled", Toast.LENGTH_SHORT).show()
        }
    }
}