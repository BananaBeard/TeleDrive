package com.kovalenko.teledrive

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.kovalenko.teledrive.fragments.AllLoadsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mPagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPagerAdapter = object : FragmentPagerAdapter(supportFragmentManager) {
            val mFragments = arrayOf(
                    AllLoadsFragment(),
                    AllLoadsFragment(),
                    AllLoadsFragment()
            )
            val mFragmentNames = arrayOf(
                    "All loads",
                    "Avl loads",
                    "Active loads"
            )

            override fun getItem(position: Int) = mFragments[position]
            override fun getCount() = mFragments.size
            override fun getPageTitle(position: Int) = mFragmentNames[position]
        }

        container_load.adapter = mPagerAdapter
        tabs_load.setupWithViewPager(container_load)

        fab_new_load.setOnClickListener {
            startActivity(getIntent<NewLoadActivity>())
        }
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