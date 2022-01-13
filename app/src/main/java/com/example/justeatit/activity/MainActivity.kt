package com.example.justeatit.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.justeatit.R
import com.example.justeatit.fragments.HomeScreenFragment
import com.example.justeatit.fragments.LoginFragment
import com.example.justeatit.navigationFragment.NavigationBarFragment

/**
 * @author jerwinesguerra
 *
 * This is the main activity page used as a launcher for the app
 * enabling users what to do next.
 */
class MainActivity : AppCompatActivity(), NavigationBarFragment {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var myToolbar: Toolbar

    /**
     * This is the launcher for the activity, it firstly checks if the user is
     * logged in and if so they will be taken to the home screen otherwise they are
     * returned to the login page.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAuth = Firebase.auth
        val currentUser = firebaseAuth.currentUser

        if(currentUser != null ){
            supportFragmentManager.beginTransaction()
                .add(R.id.container, HomeScreenFragment()).addToBackStack(null)
                .commit()

        }else {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, LoginFragment())
                .commit()
        }
    }

    /**
     * Makes an instance of the toolbar and implements the functions of the icons
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.toolbar_back -> this.moveTaskToBack(true)
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
}