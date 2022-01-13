
package com.example.justeatit.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import com.example.justeatit.R
import com.example.justeatit.navigationFragment.NavigationBarFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * @author jerwinesguerra
 * this is a class built to make the login screen
 */
class LoginFragment : Fragment() {
    private lateinit var loginUsername: EditText
    private lateinit var loginPassword: EditText
    private lateinit var firebaseAuth: FirebaseAuth

    /**
     * This creates the login screen where users can pick whether to log in, register or use a
     * guest user.
     *
     * @return view the login screen
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        loginUsername = view.findViewById(R.id.loginUsername)
        loginPassword = view.findViewById(R.id.loginPassword)
        firebaseAuth = Firebase.auth

        view.findViewById<Button>(R.id.registerButton).setOnClickListener{
            val navRegister = activity as NavigationBarFragment
            navRegister.navigateFrag(RegistrationFragment(), addToStack = false)
        }

        view.findViewById<Button>(R.id.loginButton).setOnClickListener{
            emptyValidation()
        }

        view.findViewById<Button>(R.id.guestLoginButton).setOnClickListener{
            val navGuest = activity as NavigationBarFragment
            navGuest.navigateFrag(GuestHomeFragment(), addToStack = false)
        }
        return view
    }

    /**
     * This sets the validations for the users log in. Allowing users to know whether they trying to
     * logged in with an invalid username or password.
     */
    private fun emptyValidation() {
        val errorIcon = AppCompatResources.getDrawable(requireContext(),
            R.drawable.ic_baseline_error_outline_24
        )

        errorIcon?.setBounds(0,0,errorIcon.intrinsicHeight,errorIcon.intrinsicWidth)
        when{
            TextUtils.isEmpty(loginUsername.text.toString().trim()) -> {
                loginUsername.setError("Invalid Username", errorIcon)
            }
            TextUtils.isEmpty(loginUsername.text.toString().trim()) -> {
                loginUsername.setError("Invalid Password", errorIcon)
            }

            loginUsername.text.toString().isNotEmpty() &&
                    loginPassword.text.toString().isNotEmpty()  -> {
                if(loginUsername.text.toString().matches(Regex("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                            "\\@" +
                            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                            "(" +
                            "\\." +
                            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                            ")+"))){

                    loginToFirebase()
                    //  Toast.makeText(context, "Login Successful",Toast.LENGTH_SHORT).show()

                }else{
                    loginUsername.setError("Invalid email has been entered", errorIcon)
                }
            }
        }
    }

    /**
     * Function which will then allow users to log in to the app through firebase.
     */
    private fun loginToFirebase() {
        firebaseAuth.signInWithEmailAndPassword(loginUsername.text.toString(),
            loginPassword.text.toString()).addOnCompleteListener{
                task ->
            if(task.isSuccessful){
                val navigationBarFragment = activity as NavigationBarFragment
                navigationBarFragment.navigateFrag(HomeScreenFragment(),addToStack = true)
            }else{
                Toast.makeText(context, "Login Failed",Toast.LENGTH_SHORT).show()
            }
        }
    }


}