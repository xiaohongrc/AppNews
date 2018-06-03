package com.hongenit.apptem.news

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hongenit.apptem.R
import com.hongenit.apptem.common.BaseFragment
import com.hongenit.apptem.util.ImageLoadUtil
import com.hongenit.apptem.util.LogUtil
import com.hongenit.apptem.util.WebViewActivity
import kotlinx.android.synthetic.main.fragment_news.*
import java.util.*

const val KEY_ARGUMENTS_CHANNELID: String = "KEY_ARGUMENTS_CHANNELID"

/**
 * Created by Xiaohong on 2018/5/28.
 * desc:
 */
class NewsFragment : BaseFragment() {
    val TAG = "NewsFragment"

    var mNewsList: ArrayList<NewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> = arrayListOf()

    companion object {

        fun getInstance(title: String): NewsFragment? {
            val fragment = NewsFragment()
            val bundle = Bundle()
            bundle.putString(KEY_ARGUMENTS_CHANNELID, title)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getFragmentContentId(): Int {
        return R.layout.fragment_news
    }

    private lateinit var mPresenter: ICommonTabPresenter

    override fun initView() {
        rvList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvList.adapter = HomeAdapter()

//        rvList.addItemDecoration()
        mPresenter = CommonTabPresenter(context!!)
        mPresenter.setView(this)
        //刷新
        srlLayout.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        srlLayout.setOnRefreshListener {
            mPresenter.requestData(false, mChannelId)
        }

        rvList.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val linearLayoutManager = recyclerView?.layoutManager as LinearLayoutManager
                    val lastPosition = linearLayoutManager.findLastVisibleItemPosition()
                    if (lastPosition >= mNewsList.size - 2) {
                        mPresenter.requestData(true, mChannelId)
                    }
                }
            }

        })

    }


    override fun initData() {
        srlLayout.isRefreshing = true
        mPresenter.start(mChannelId)
        println("mChannelId = " + mChannelId)
    }


    private lateinit var mChannelId: String

    override fun initParams() {
        mChannelId = arguments!!.getString(KEY_ARGUMENTS_CHANNELID)
    }


    inner class HomeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {
        override fun onClick(v: View?) {
            val pos = v?.getTag() as Int
            val newsLink = mNewsList[pos].link
            LogUtil.d(TAG, "link  = " + newsLink)
            if (!newsLink.isNullOrEmpty()) {
                WebViewActivity.start(this@NewsFragment.context!!, newsLink!!)
            }

        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = layoutInflater.inflate(R.layout.item_news, parent, false)
            return HomeViewHolder(view)
        }

        override fun getItemCount(): Int {
            return mNewsList.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val homeViewHolder = holder as HomeViewHolder
            homeViewHolder.itemView.setTag(position)
            homeViewHolder.itemView.setOnClickListener(this)
            val bean = mNewsList.get(position)
            holder.tv_title.setText(bean.title)
            holder.tv_date.setText(bean.getPubTime())
            holder.tv_desc.setText(bean.desc)
            holder.tv_source.setText(bean.source)
            val imageurls = bean.imageurls
            if (imageurls != null && imageurls.size > 0) {
                ImageLoadUtil.newInstance()?.loadImage(context!!, holder.iv_img, imageurls[0].url)
            }

//            holder.iv_choose.visibility = if (bean.isChoosed()) View.VISIBLE else View.GONE
        }

        inner class HomeViewHolder(convertView: View) : RecyclerView.ViewHolder(convertView) {
            val tv_title: TextView
            val tv_date: TextView
            val tv_desc: TextView
            val tv_source: TextView
            val iv_choose: ImageView
            val iv_img: ImageView

            init {
                tv_title = convertView.findViewById(R.id.tv_title)
                tv_desc = convertView.findViewById(R.id.tv_desc)
                tv_date = convertView.findViewById(R.id.tv_date)
                iv_choose = convertView.findViewById(R.id.iv_choose)
                tv_source = convertView.findViewById(R.id.tv_source)
                iv_img = convertView.findViewById(R.id.iv_img)

            }
        }


    }

    // 刷新数据
    fun replaceData(list: ArrayList<NewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean>) {
        mNewsList.clear()
        mNewsList.addAll(list)
        if (isVisible) {
            rvList.adapter.notifyDataSetChanged()
            srlLayout.isRefreshing = false
        }

    }

    // 加载更多时添加数据
    fun addData(list: ArrayList<NewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean>) {
        if (list.size > 0) {
            mNewsList.addAll(list)
            rvList.adapter.notifyDataSetChanged()
        }
        srlLayout.isRefreshing = false
    }


}