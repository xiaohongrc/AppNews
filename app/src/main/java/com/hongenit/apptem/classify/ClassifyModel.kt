package com.hongenit.apptem.classify

import com.hongenit.apptem.MyApplicaiont
import com.hongenit.apptem.db.ISqliteDB
import com.hongenit.apptem.db.SqliteDBImpl
import com.hongenit.apptem.net.WebServiceImpl
import com.hongenit.apptem.util.LogUtil

/**
 * Created by hongenit on 2018/6/2.
 * desc: 频道
 */
class ClassifyModel(classifyPresenter: ClassifyPresenter) {

    var mPressenter: ClassifyPresenter
    var sqliteDBImpl: ISqliteDB

    init {
        mPressenter = classifyPresenter
        sqliteDBImpl = SqliteDBImpl(MyApplicaiont.getAppContext()!!)
    }

    val webservice = WebServiceImpl()

//    companion object {
//        lateinit var mInstances: ClassifyModel
//        fun getInstance(): ClassifyModel {
//            if (mInstances == null) {
//                mInstances = ClassifyModel()
//            }
//            return mInstances
//        }
//    }


    fun getClassifyList() {
        val allChannel = sqliteDBImpl.queryAllChannel()
        if (allChannel.size > 0) {
            mPressenter.requestDataComplete(allChannel)
        } else {
            webservice.getClassifyList(object : ClassifyResponseListener() {
                override fun onAnalyzeComplete(result: ArrayList<ClassifyTypeBean>) {
                    MyApplicaiont.runOnUiThread(Runnable {
                        run {
                            mPressenter.requestDataComplete(result)
                        }
                    })
                    // 插入到数据库
                    for (channelBean in result){
                        sqliteDBImpl.insertChannel(channelBean)
                    }
                }

                override fun onAnalyzeError() {
                    LogUtil.e("ClassifyModel", "get classify data error")
                }
            })

        }

    }

}