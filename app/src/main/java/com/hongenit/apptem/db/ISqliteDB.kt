package com.hongenit.apptem.db

import com.hongenit.apptem.classify.ClassifyTypeBean


/**
 * Created by Xiaohong on 2018/5/10.
 * desc:
 */
interface ISqliteDB {

    fun insertChannel(picBean: ClassifyTypeBean)
    fun queryAllChannel(): ArrayList<ClassifyTypeBean>

}