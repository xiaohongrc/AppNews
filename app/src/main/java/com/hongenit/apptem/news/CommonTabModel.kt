package com.hongenit.apptem.news

import com.hongenit.apptem.net.ResponseListener
import com.hongenit.apptem.net.WebServiceImpl
import com.hongenit.apptem.util.EventUtil.FirebaseEventParams.urlWithParam
import java.util.*

/**
 * Created by hongenit on 18/1/31.
 * 获取图片信息的model
 */


private val TAG: String = "CommonTabModel"


class CommonTabModel() {
    val webservice = WebServiceImpl()

    lateinit var mPressenter: CommonTabPresenter

    constructor(commonTabPresenter: CommonTabPresenter) : this() {
        mPressenter = commonTabPresenter
    }

    fun reqNewsList(channelId: String, page: Int, response: ResponseListener) {
        webservice.getNewsInfoList(channelId, page, response)
    }


}