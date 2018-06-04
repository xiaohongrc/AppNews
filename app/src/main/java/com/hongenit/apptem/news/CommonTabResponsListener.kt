package com.hongenit.apptem.news

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hongenit.apptem.MyApplicaiont
import com.hongenit.apptem.net.ResponseListener
import com.hongenit.apptem.util.LogUtil

/**
 * Created by Xiaohong on 2018/4/28.
 * desc:
 */
abstract class CommonTabResponsListener : ResponseListener() {
    override fun onComplete(jsonString: String?) {
        println("CommonTabResponsListener result string = " + jsonString)
        val result = AnalyzeResposeData(jsonString)
        if (result != null && result.size > 0) {
            MyApplicaiont.runOnUiThread(Runnable {
                run {
                    onAnalyzeComplete(result)
                }

            })
        } else {
            onError()
        }
    }

//
//    "albumUrl": "http://www.win4000.com/mobile_detail_144855.html",
//    "_id": {
//        "$oid": "5ace1f60ec82f81323d5553f"
//    },
//    "albumPhotos": [{
//        "photoUrl": "http://pic1.win4000.com/mobile/2018-04-04/5ac4789ba2f72.jpg"
//    }, {
//        "photoUrl": "http://pic1.win4000.com/mobile/2018-04-04/5ac4789c99128.jpg"
//    }, {
//        "photoUrl": "http://pic1.win4000.com/mobile/2018-04-04/5ac4789d94f7a.jpg"
//    }],
//    "albumIntro": "蔡徐坤帅气图片高清手机壁纸。分享一组壁纸尺寸：690*1227。蔡徐坤（August），1998年8月2日出生于湖南省，中国内地男歌手、演员。更多蔡徐坤壁纸图片尽在美桌网。",
//    "width": "640",
//    "title": "蔡徐坤帅气图片高清手机壁纸",
//    "height": "1136",
//    "whichClassify": "明星",
//    "thumbnailUrl ": "http://pic1.win4000.com/mobile/2018-04-04/5ac478a441168_250_350.jpg"

    private fun AnalyzeResposeData(jsonString: String?): ArrayList<NewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean>? {
        val gson = Gson()
        val newsData = gson.fromJson<NewsBean>(jsonString, TypeToken.get(NewsBean::class.java).getType())
        val pagebean = newsData.showapi_res_body?.pagebean
        return pagebean?.contentlist

    }

    override fun onError() {
        LogUtil.e("CommonTabResponsListener", "error")
        MyApplicaiont.runOnUiThread(Runnable {
            run {
                onAnalyzeError()
            }

        })
    }

    abstract fun onAnalyzeComplete(result: ArrayList<NewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean>)
    abstract fun onAnalyzeError()
}