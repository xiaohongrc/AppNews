package com.hongenit.apptem.net

/**
 * Created by hongenit on 2018/4/26.
 * desc:
 */
interface IWebService {

    //    获取频道列表
    fun getClassifyList(listener: ResponseListener)


    //    获取新闻列表
    fun getNewsInfoList(channelId: String, page: Int, listener: ResponseListener)


}