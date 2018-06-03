package com.hongenit.apptem.net

/**
 * Created by Xiaohong on 2018/4/26.
 * desc:
 */
class RequestUrl {
    companion object {
        val BASE_SERVER_URL = "http://47.104.214.219:8099/"
        // 分类列表
        val CLASSIFY_LIST = BASE_SERVER_URL + "get_classify_list"
        // 图集列表
        val ALBUM_INFO_LIST = BASE_SERVER_URL + "get_album_info_list"

    }


}