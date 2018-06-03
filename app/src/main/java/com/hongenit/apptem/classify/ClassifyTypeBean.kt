package com.hongenit.apptem.classify

/**
 * Created by hongenit on 2018/6/2.
 * desc:
 */
data class ClassifyTypeBean(var channelId: String, var channelName: String, var choosenTime: Long) {
    override fun toString(): String {
        return "ClassifyTypeBean(channelId='$channelId', channelName='$channelName', choosenTime=$choosenTime)"
    }
}