package com.hongenit.apptem.classify

import com.hongenit.apptem.MyApplicaiont
import com.hongenit.apptem.util.LogUtil
import com.hongenit.apptem.net.ResponseListener
import org.json.JSONObject

/**
 * Created by Xiaohong on 2018/4/27.
 * desc:
 */
open abstract class ClassifyResponseListener : ResponseListener() {
    val TAG = "ClassifyResponseListener"
    override fun onComplete(jsonString: String?) {
        LogUtil.d(TAG, "response json String = " + jsonString)

        val result = AnalyzeResposeData(jsonString)
        if (result.size > 0) {
            MyApplicaiont.runOnUiThread(Runnable {
                run {
                    onAnalyzeComplete(result)
                }
            })
        } else {
            onError()
        }
    }


    /**
     *
    {
    "showapi_res_error": "",
    "showapi_res_code": 0,
    "showapi_res_body": {
    "totalNum": 44,
    "ret_code": 0,
    "channelList": [
    {
    "channelId": "5572a108b3cdc86cf39001cd",
    "name": "国内焦点"
    },
    ...
    }
     */
    private fun AnalyzeResposeData(responseString: String?): ArrayList<ClassifyTypeBean> {
        val resultList = ArrayList<ClassifyTypeBean>()

        val jsonObject = JSONObject(responseString)
        val showapi_res_code = jsonObject.getInt("showapi_res_code")
        if (showapi_res_code != 0) {
            onError()
            return resultList
        }

        val jsonObjectBody = jsonObject.getJSONObject("showapi_res_body")
        val jsonArray = jsonObjectBody.getJSONArray("channelList")
        val length = jsonArray.length()
        for (i in 0..length - 1) {
            val classifyJson = jsonArray.getJSONObject(i)
            val channelId = classifyJson.getString("channelId")
            val name = classifyJson.getString("name")
            val typeBean = ClassifyTypeBean(channelId, name, 0)
            resultList.add(typeBean)
            LogUtil.d(TAG, "channel($i)  = $typeBean")
        }

        return resultList

    }

    override fun onError() {
        LogUtil.e(TAG, "ClassifyResponseListener error ")

        MyApplicaiont.runOnUiThread(Runnable {
            run {
                onAnalyzeError()
            }

        })

    }

    abstract fun onAnalyzeComplete(result: ArrayList<ClassifyTypeBean>)
    abstract fun onAnalyzeError()


}