package com.hongenit.apptem.net

import com.hongenit.apptem.util.LogUtil
import okhttp3.*
import java.io.IOException

/**
 * Created by hongenit on 18/1/29.
 * 网络请求实现类，基于okhttp3实现
 */

class NetClient : INetClient {

    private val TAG = javaClass.simpleName
    override fun sendRequest(request: Request, listener: ResponseListener) {
        LogUtil.d(TAG,request.toString())
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(p0: Call?, p1: IOException?) {
                LogUtil.e(TAG, p1.toString())
                listener.onError()
            }

            override fun onResponse(p0: Call?, p1: Response?) {
                LogUtil.i(TAG, p1.toString())
                val responseString = p1?.body()?.string()
                listener.onComplete(responseString)
                // 缓存网络数据
//                FileUtil.cacheJsonData(request.url().toString(),responseString!!)
            }
        })
    }


    override fun sendRequest(request: Request): String {
        var response = ""
        response = OkHttpClient().newCall(request).execute().toString()

        return response

    }


}
