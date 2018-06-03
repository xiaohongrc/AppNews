package com.hongenit.apptem.classify

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.hongenit.apptem.news.NewsFragment

/**
 * Created by hongenit on 2018/6/2.
 * desc:
 */
class NewsVpAdapter(context: Context?, fm: FragmentManager) : FragmentPagerAdapter(fm) {
    val TAG = javaClass::class
    var mFragmentDataList: ArrayList<ClassifyTypeBean> = ArrayList<ClassifyTypeBean>()

    fun setData(data: ArrayList<ClassifyTypeBean>) {
        mFragmentDataList = data

    }

    override fun getItem(position: Int): Fragment? {
        return NewsFragment.getInstance(mFragmentDataList[position].channelId)
    }

    override fun getCount(): Int {
        return mFragmentDataList.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mFragmentDataList[position].channelName
    }


}