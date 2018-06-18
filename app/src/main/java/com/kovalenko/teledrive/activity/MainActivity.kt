package com.kovalenko.teledrive.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu)

        nav_view.setNavigationItemSelectedListener { item ->
            item.isChecked = true
            drawer_layout.closeDrawers()

            var fragment: Fragment

            when(item.itemId) {
                R.id.nav_loads -> {
                    fragment = LoadsFragment()
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.content_layout, fragment, "LOADS").commit()
                    supportActionBar!!.title = "Замовлення"

                }
                R.id.nav_drivers -> {
                    fragment = DriversFragment()
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.content_layout, fragment, "LOADS").commit()
                    supportActionBar!!.title = "Водії"
                }
                R.id.nav_trucks -> {
                    fragment = TrucksFragment()
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.content_layout, fragment, "LOADS").commit()
                    supportActionBar!!.title = "Транспортні засоби"
                }
                R.id.nav_facilities -> {
                    fragment = FacilitiesFragment()
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.content_layout, fragment, "LOADS").commit()
                    supportActionBar!!.title = "Об'єкти"
                }
                R.id.nav_map -> {
                    startActivity(getIntent<MapsActivity>())
                }
            }
            true
        }

        var startFragment = LoadsFragment()

        supportFragmentManager.beginTransaction()
                .add(R.id.content_layout, startFragment).commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.action_logout -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
                return true
            }
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val TAG = "MainActivity"
    }
}