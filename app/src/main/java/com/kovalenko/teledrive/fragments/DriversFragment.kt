package com.kovalenko.teledrive.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.activity.newactivity.NewDriverActivity
import com.kovalenko.teledrive.fragments.listfragments.driver.AllDriversFragment
import com.kovalenko.teledrive.fragments.listfragments.driver.AvailableDriversFragment
import com.kovalenko.teledrive.fragments.listfragments.driver.BusyDriversFragment

import kotlinx.android.synthetic.main.fragment_drivers.*

class DriversFragment: Fragment() {

    private lateinit var mPagerAdapter: PagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_drivers, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPagerAdapter = object : FragmentStatePagerAdapter(childFragmentManager) {

            val mFragments = arrayOf(
                    AllDriversFragment(),
                    AvailableDriversFragment(),
                    BusyDriversFragment()
            )
            val mFragmentNames = arrayOf(
                    "Усі",
                    "Вільні",
                    "Зайняті"
            )
            override fun getItem(position: Int) = mFragments[position]
            override fun getCount() = mFragments.size
            override fun getPageTitle(position: Int) = mFragmentNames[position]

        }

        container_driver.adapter = mPagerAdapter
        tabs_driver.setupWithViewPager(container_driver)

        fab_new_driver.setOnClickListener {
            val intent = Intent(activity, NewDriverActivity::class.java)
            startActivity(intent)
        }
    }
}