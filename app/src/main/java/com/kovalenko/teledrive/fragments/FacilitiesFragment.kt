package com.kovalenko.teledrive.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.activity.NewFacilityActivity
import com.kovalenko.teledrive.fragments.listfragments.facility.FacilityListFragment
import kotlinx.android.synthetic.main.fragment_facilities.*

class FacilitiesFragment: Fragment() {

    private lateinit var mPagerAdapter: PagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_facilities, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPagerAdapter = object : FragmentStatePagerAdapter(fragmentManager) {

            val mFragments = arrayOf(FacilityListFragment())

            override fun getItem(position: Int) = mFragments[position]
            override fun getCount() = mFragments.size
        }

        container_facility.adapter = mPagerAdapter

        fab_new_facility.setOnClickListener {
            val intent = Intent(activity, NewFacilityActivity::class.java)
            startActivity(intent)
        }
    }
}