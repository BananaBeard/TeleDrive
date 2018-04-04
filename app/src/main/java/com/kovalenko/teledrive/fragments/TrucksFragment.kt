package com.kovalenko.teledrive.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kovalenko.teledrive.activity.NewTruckActivity
import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.fragments.listfragments.truck.AllTrucksFragment
import kotlinx.android.synthetic.main.fragment_trucks.*

class TrucksFragment: Fragment() {

    private lateinit var mPagerAdapter: PagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_trucks, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPagerAdapter = object : FragmentPagerAdapter(activity!!.supportFragmentManager) {
            val mFragments = arrayOf(
                    AllTrucksFragment(),
                    AllTrucksFragment(),
                    AllTrucksFragment()
            )
            val mFragmentNames = arrayOf(
                    "All truck",
                    "Avl trucks",
                    "Busy trucks"
            )

            override fun getItem(position: Int) = mFragments[position]
            override fun getCount() = mFragments.size
            override fun getPageTitle(position: Int) = mFragmentNames[position]
        }

        container_truck.adapter = mPagerAdapter
        tabs_truck.setupWithViewPager(container_truck)

        fab_new_truck.setOnClickListener {
            val intent = Intent(activity, NewTruckActivity::class.java)
            startActivity(intent)
        }
    }
}