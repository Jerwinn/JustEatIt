package com.example.justeatit.activity

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.justeatit.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_location_service.*

/**
 * @author jerwinesguerra
 *
 * this class is made to show the first bonus feature, this class makes use of the Support Map Fragment and google maps
 * to show the location map.
 */
class LocationServiceActivity : AppCompatActivity() {

    lateinit var mapFragment: SupportMapFragment
    private lateinit var googleMap: GoogleMap

    /**
     * The google map activity is launched and then it shows the location of the restaurants through a
     * marker point. This marker point then shows the restaurants title along with a description.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_service)

        mapFragment = supportFragmentManager.findFragmentById(R.id.googleMaps) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback { 
            googleMap = it
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return@OnMapReadyCallback
            }

            googleMap.isMyLocationEnabled = true
            googleMap.uiSettings.isMapToolbarEnabled = true
            googleMap.uiSettings.isZoomControlsEnabled = true
            googleMap.uiSettings.isCompassEnabled = true

            val restaurant1 = LatLng(51.625583,-3.929806)
            googleMap.addMarker(MarkerOptions().position(restaurant1).title("Viva Brazil").snippet("Brazilian Cuisine"))

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(restaurant1,10f))

            val restaurant2 = LatLng(51.618130,-3.966560)
            googleMap.addMarker(MarkerOptions().position(restaurant2).title("Romeat").snippet("Pizza"))


            val restaurant3 = LatLng(51.623230,-3.915260)
            googleMap.addMarker(MarkerOptions().position(restaurant3).title("SaraZtro").snippet("Greek"))


            val restaurant4 = LatLng(51.627760,-3.969580)
            googleMap.addMarker(MarkerOptions().position(restaurant4).title("Jasmine Restaurant").snippet("Mediterranean"))


            val restaurant5 = LatLng(51.614792,-3.936104)
            googleMap.addMarker(MarkerOptions().position(restaurant5).title("Luna's Vegan Corner").snippet("Vegan"))


            val restaurant6 = LatLng(51.616178,-3.941936)
            googleMap.addMarker(MarkerOptions().position(restaurant6).title("Kuro' Restaurant").snippet("Modern British"))



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
     * Makes an instance of the toolbar
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
}