package com.kovalenko.teledrive

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.kovalenko.teledrive.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nav_view.setNavigationItemSelectedListener { item ->
            item.isChecked = true
            drawer_layout.closeDrawers()

            var fragment: Fragment
            var fragmentId = ""

            when(item.itemId) {
                R.id.nav_loads -> {
                    fragment = LoadsFragment()
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.content_layout, fragment, "LOADS").commit()
                }
                R.id.nav_drivers -> {
                    fragment = DriversFragment()
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.content_layout, fragment, "LOADS").commit()
                }
                R.id.nav_trucks -> {
                    fragment = TrucksFragment()
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.content_layout, fragment, "LOADS").commit()
                }
                R.id.nav_facilities -> {
                    fragment = FacilitiesFragment()
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.content_layout, fragment, "LOADS").commit()
                }
            }

            true
        }

        var startFragment = LoadsFragment()

        supportFragmentManager.beginTransaction()
                .add(R.id.content_layout, startFragment).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val i = item.itemId
        return if (i == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private val TAG = "MainActivity"
    }
}