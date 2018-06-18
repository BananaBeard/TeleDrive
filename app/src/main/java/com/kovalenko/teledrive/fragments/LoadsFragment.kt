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
import com.kovalenko.teledrive.activity.newactivity.NewLoadActivity
import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.fragments.listfragments.load.ActiveLoadsFragment
import com.kovalenko.teledrive.fragments.listfragments.load.AllLoadsFragment
import com.kovalenko.teledrive.fragments.listfragments.load.AvailableLoadsFragment
import kotlinx.android.synthetic.main.fragment_loads.*

class LoadsFragment: Fragment() {

    private lateinit var mPagerAdapter: PagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_loads, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPagerAdapter = object : FragmentStatePagerAdapter(childFragmentManager) {
            val mFragments = arrayOf(
                    AllLoadsFragment(),
                    AvailableLoadsFragment(),
                    ActiveLoadsFragment()
            )
            val mFragmentNames = arrayOf(
                    "Усі",
                    "Доступні",
                    "Активні"
            )

            override fun getItem(position: Int) = mFragments[position]
            override fun getCount() = mFragments.size
            override fun getPageTitle(position: Int) = mFragmentNames[position]
        }

        container_load.adapter = mPagerAdapter
        tabs_load.setupWithViewPager(container_load)

        fab_new_load.setOnClickListener {
            val intent = Intent(activity, NewLoadActivity::class.java)
            startActivity(intent)
        }
    }
}

