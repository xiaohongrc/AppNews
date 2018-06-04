package com.hongenit.apptem.net

/**
 * Created by Xiaohong on 2018/4/26.
 * desc:
 */
abstract class ResponseListener {

    abstract fun onComplete(jsonString: String?)

    abstract fun onError()

}