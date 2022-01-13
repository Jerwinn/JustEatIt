package com.example.justeatit.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.justeatit.R
import com.example.justeatit.model.Database
import com.example.justeatit.navigationFragment.NavigationBarFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_restaurant_page.*

/**
 * @author jerwinesguerra
 * The purpose of this activity is to show the user the review screen.
 */
class RestaurantPageActivity : AppCompatActivity(), NavigationBarFragment {
    private lateinit var  firebase: FirebaseDatabase
    private lateinit var firebaseReference:DatabaseReference
    private lateinit var buttonSendReview: Button
    private lateinit var buttonViewReviews: Button
    private lateinit var restaurantName: TextView
    private lateinit var restaurantImage: ImageView
    private lateinit var restaurantDescription: TextView

    /**
     * This launches the restaurant page's review activity which shows the restaurants details
     * To take the reviews it takes in the strings from the text box provided,
     * when the submit button is click then it is sent onto the database.
     * finally another button is provided to show the users the reviews.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_page)

        firebase = FirebaseDatabase.getInstance("https://justeatit-4af7e-default-rtdb.europe-west1.firebasedatabase.app")
        firebaseReference = firebase.getReference("Reviews")
        restaurantName = findViewById(R.id.individual_page_title)
        restaurantImage = findViewById(R.id.restaurant_page_image)
        restaurantDescription = findViewById(R.id.individual_page_description)
        buttonSendReview = findViewById(R.id.submit_review)
        buttonViewReviews = findViewById(R.id.view_reviews)

        val bundle : Bundle?= intent.extras
        val heading = bundle!!.getString("restaurantName")
        val description = bundle.getString("description")
        val image2 = bundle.getInt("image")

        restaurantName.text = heading
        restaurantDescription.text = description
        restaurantImage.setImageResource(image2)

       buttonSendReview.setOnClickListener(){
           sendFirebase()
        }
        buttonViewReviews.setOnClickListener{
            startActivity(Intent(applicationContext, RetrieveReviewsActivity::class.java))
        }
    }

    /**
     * Makes an instance of the toolbar and implements the functions of the icons
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.toolbar_back -> this.onBackPressed()
            R.id.exit_to_home_screen -> FirebaseAuth.getInstance().signOut()
            R.id.toolbar_search -> Toast.makeText(applicationContext, "search button clicked", Toast.LENGTH_SHORT).show()
            R.id.settings_toolbar -> Toast.makeText(applicationContext, "settings button clicked", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Makes an instance of the toolbar
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    /**
     * This function is used as a way to navigate through the fragments.
     */
    override fun navigateFrag(fragment: Fragment, addToStack: Boolean) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(R.id.container,fragment)

        if(addToStack){
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    /**
     * This function is used to send the values from the input fields to the firebase.
     */
    private fun sendFirebase(){

       val review = individual_page_time_of_visit_review.text.toString().trim()
        val dateOfVisit = individual_page_time_of_visit_review.text.toString().trim()
        val address = individual_address.text.toString().trim()
        val rating = ratingBar.rating.toInt()

        if(review.isNotEmpty() && dateOfVisit.isNotEmpty()){
            val model = Database(review, dateOfVisit, rating, address)
            val id = firebaseReference.push().key

            firebaseReference.child(id!!).setValue(model)

        }else{
            Toast.makeText(applicationContext, "Invalid Review - All fields must be filled", Toast.LENGTH_SHORT).show()
        }

    }
}