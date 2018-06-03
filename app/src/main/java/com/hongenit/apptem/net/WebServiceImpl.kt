package com.hongenit.apptem.net

import android.text.TextUtils
import com.show.api.ShowApiRequest
import kotlin.concurrent.thread

/**
 * Created by Xiaohong on 2018/4/26.
 * desc:
 */
class WebServiceImpl : IWebService {

    override fun getClassifyList(listener: ResponseListener) {

        Thread {
            kotlin.run {
                val res = ShowApiRequest("http://route.showapi.com/109-34", "57420", "d4974c51934f4a63a50a2c11a31e2ebe").post()
                if (TextUtils.isEmpty(res)) {
                    listener.onError()
                } else {
                    listener.onComplete(res)
                }
            }
        }.start()

    }

    override fun getNewsInfoList(channelId: String, page: Int, listener: ResponseListener) {
        Thread {
            kotlin.run {
                val res = ShowApiRequest("http://route.showapi.com/109-35", "57420", "d4974c51934f4a63a50a2c11a31e2ebe")
                        .addTextPara("channelId", channelId)
                        .addTextPara("channelName", "")
                        .addTextPara("title", "")
                        .addTextPara("page", page.toString())
                        .addTextPara("needContent", "0")
                        .addTextPara("needHtml", "0")
                        .addTextPara("needAllList", "0")
                        .addTextPara("maxResult", "30")
                        .addTextPara("id", "")
                        .post()

                if (TextUtils.isEmpty(res)) {
                    listener.onError()
                } else {
                    listener.onComplete(res)
                }

            }
        }.start()

    }

}