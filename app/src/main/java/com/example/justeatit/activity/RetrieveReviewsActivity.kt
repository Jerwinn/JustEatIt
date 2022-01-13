package com.example.justeatit.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.justeatit.R
import com.example.justeatit.adapter.DatabaseRetrieveAdapter
import com.example.justeatit.model.Database
import com.google.firebase.database.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_reviews.*

/**
 * @author jerwinesguerra
 *
 * This activity is made to show the reviews given by the users
 */
class RetrieveReviewsActivity : AppCompatActivity() {
    private lateinit var  firebase: FirebaseDatabase
    private lateinit var firebaseReference: DatabaseReference

    /**
     * This launches the review activity.
      */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews)

        firebase = FirebaseDatabase.getInstance("https://justeatit-4af7e-default-rtdb.europe-west1.firebasedatabase.app")
        firebaseReference = firebase.getReference("Reviews")

        retrieveData()
    }

    /**
     * This retrieves the data from firebase and takes them to be added to a recyclerview
     */
    private fun retrieveData(){
        firebaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var listReview = ArrayList<Database>()

                for(data in snapshot.children){
                    val model = data.getValue(Database::class.java)
                    listReview.add(model as Database);
                }
                if(listReview.size>0){
                    var adapter = DatabaseRetrieveAdapter(listReview)
                    activity_reviews_recyclerview.adapter = adapter
                }

            }

            /**
             * If retrieving from firebase did not work then this is triggered and the error is shown.
             */
            override fun onCancelled(error: DatabaseError) {
                Log.e("Cancelled", error.toString())
            }

        })
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
     * Makes an instance of the toolbar.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
}