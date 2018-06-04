package com.hongenit.apptem.news

import android.content.Context
import com.hongenit.apptem.MyApplicaiont
import com.hongenit.apptem.R
import com.hongenit.apptem.util.LogUtil
import com.hongenit.apptem.util.ToastUtil

/**
 * Created by hongenit on 18/1/31.
 * 展示图片的presenter
 */

class CommonTabPresenter(context: Context) : ICommonTabPresenter, CommonTabResponsListener() {
    private val TAG: String = "CommonTabPresenter"
    private val FIRST_PAGE = 1

    override fun onAnalyzeComplete(result: ArrayList<NewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean>) {
        MyApplicaiont.runOnUiThread(Runnable {
            run {
                if (mView.isVisible) {
                    if (isLoadMore) {
                        mView.addData(result)
                    } else {
                        mView.replaceData(result)
                    }
                }
            }
        })

    }

    override fun onAnalyzeError() {
        LogUtil.e(TAG, "error")
        if (isLoadMore) {
            ToastUtil.showToast(mContext.getString(R.string.no_more_data))
        } else {
            ToastUtil.showToast(mContext.getString(R.string.msg_get_data_error))
        }

    }

    private var isLoadMore: Boolean = false
    private var mContext: Context = context
    private var mCommonTabModel: CommonTabModel = CommonTabModel(this)

    //    override fun onSuccess(picList: ArrayList<AlbumBean>) {
//        if (mView.isVisible) {
//            if (isLoadMore) {
//                mView.addData(picList)
//            } else {
//                mView.replaceData(picList)
//            }
//        }
//
//    }
//    override fun onError(msg: String?) {
//        if (isLoadMore){
//            ToastUtil.showToast(mContext.getString(R.string.no_more_data))
//        }else{
//            ToastUtil.showToast(mContext.getString(R.string.msg_get_data_error))
//        }
//    }


    override fun start(channelId: String) {
        mCommonTabModel.reqNewsList(channelId, FIRST_PAGE, this)
    }

    private lateinit var mView: NewsFragment

    override fun setView(fragment: NewsFragment) {
        mView = fragment
    }

    private var mPageNum: Int = FIRST_PAGE

    override fun requestData(isLoadMore: Boolean, channelId: String) {
        this.isLoadMore = isLoadMore
        if (isLoadMore) {
            mPageNum++
        } else {
            mPageNum = FIRST_PAGE
        }
        mCommonTabModel.reqNewsList(channelId, mPageNum, this)

    }


}
