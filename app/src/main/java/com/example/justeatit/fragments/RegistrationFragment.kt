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
 *
 * This class is made to enable users to register onto the app by using an email and password.
 * Then it is stored to firebase as a reference for future logins.
 */
class RegistrationFragment : Fragment() {
    private lateinit var passwordConfirm: EditText
    private lateinit var registerPassword: EditText
    private lateinit var registerUsername: EditText
    private lateinit var firebaseAuth: FirebaseAuth

    /**
     * This creates the view for the registration, making use of text views to take in input.
     * Once done with typing the user can then register by clicking the register button.
     *
     * @return view This is screen where the user can register.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_registration, container,false )
        registerUsername = view.findViewById(R.id.registrationUsername)
        registerPassword = view.findViewById(R.id.registrationPassword)
        passwordConfirm = view.findViewById(R.id.confirmPassword)
        firebaseAuth = Firebase.auth

        view.findViewById<Button>(R.id.registerAccountButton).setOnClickListener {
            emptyValidation()

        }
        return view
    }

    /**
     * This function is made as a validation to ensure users enter a valid email and password
     * @return boolean true if user is successful otherwise false in making a login credentials
     */
    private fun emptyValidation(): Boolean {
        val errorIcon = AppCompatResources.getDrawable(requireContext(),
            R.drawable.ic_baseline_error_outline_24
        )
        errorIcon?.setBounds(0,0,errorIcon.intrinsicHeight,errorIcon.intrinsicWidth)
        when{
            TextUtils.isEmpty(registerUsername.text.toString().trim()) -> {
                registerUsername.setError("Invalid Username", errorIcon)
            }
            TextUtils.isEmpty(registerUsername.text.toString().trim()) -> {
                registerUsername.setError("Invalid Password", errorIcon)
            }
            TextUtils.isEmpty(registerUsername.text.toString().trim()) -> {
                registerUsername.setError("Invalid Confirmation Password", errorIcon)
            }
            registerUsername.text.toString().isNotEmpty() &&
                    registerPassword.text.toString().isNotEmpty() &&
                    passwordConfirm.text.toString().isNotEmpty() -> {
                        if(registerUsername.text.toString().matches(Regex("[" +
                                    "a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                                    "\\@" +
                                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                                    "(" +
                                    "\\." +
                                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                                    ")+"))){
                            if(passwordConfirm.text.toString().length>8){

                                if(registerPassword.text.toString() == passwordConfirm.text.toString()){
                                    signupToFirebase()
                                    return true


                                }else{
                                    passwordConfirm.setError("Password not matching", errorIcon)
                                }

                            }else{
                                registerPassword.setError("Password must have 8 or more characters", errorIcon)
                            }
                        }else{
                            registerUsername.setError("Invalid email has been entered", errorIcon)
                        }
                    }
        }
        return false
    }

    /**
     * Function which will send the users chosen username and password to firebase to enable future login.
     */
    private fun signupToFirebase(){
        firebaseAuth.createUserWithEmailAndPassword(registerUsername.text.toString(), registerPassword.text.toString())
            .addOnCompleteListener{
                task -> if(task.isSuccessful){
                val navigationBarFragment = activity as NavigationBarFragment
                navigationBarFragment.navigateFrag(HomeScreenFragment(), addToStack = true)

            }else{
               Toast.makeText(context,task.exception?.message,Toast.LENGTH_SHORT).show()

            }
            }
    }
}