package com.hongenit.apptem.channelchoose

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.hongenit.apptem.R
import com.hongenit.apptem.classify.ClassifyTypeBean
import com.hongenit.apptem.common.BaseActivity
import com.hongenit.apptem.db.SqliteDBImpl
import com.hongenit.apptem.util.LogUtil
import kotlinx.android.synthetic.main.activity_channel_choose.*

/**
 * Created by hongenit on 2018/6/3.
 * desc:选择频道
 */
class ChannelChooseActivity : BaseActivity() {
    val TAG = this::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel_choose)


        val sqliteDBImpl = SqliteDBImpl(this)

        val beforeTiem = System.currentTimeMillis()
        LogUtil.d(TAG, "before = " + beforeTiem)
        val queryAllChannel = sqliteDBImpl.queryAllChannel()
        val afterTime = System.currentTimeMillis() - beforeTiem
        LogUtil.d(TAG, "after = " + afterTime)

        for (channel in queryAllChannel) {
            LogUtil.d(TAG, "channel = " + channel)
        }


        initViews(queryAllChannel)


    }

    private fun initViews(queryAllChannel: ArrayList<ClassifyTypeBean>) {

        val mInflater = LayoutInflater.from(this)

        for (channel in queryAllChannel) {

            val tv:TextView = mInflater.inflate(R.layout.tv_channel,fl_channels,false) as TextView


            tv.setText(channel.channelName)
//            tv.background = R.drawable.select
            fl_channels.addView(tv)
        }
    }


}