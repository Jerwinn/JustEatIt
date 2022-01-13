package com.example.justeatit.navigationFragment
import androidx.fragment.app.Fragment

/**
 * @author jerwinesguerra
 * This interface is made as a means of navigating through the fragments.
 */
interface NavigationBarFragment {
    fun navigateFrag(fragment: Fragment, addToStack: Boolean)
}