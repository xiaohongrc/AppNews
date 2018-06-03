package com.hongenit.apptem.classify

import android.support.design.widget.TabLayout
import com.hongenit.apptem.R
import com.hongenit.apptem.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_classify.*

/**
 * Created by hongenit on 18/1/30.
 * 风格分类fragment
 */
class StyleClassifyFragment : BaseFragment() {

    companion object {
        fun newInstance(): StyleClassifyFragment {
            val fragment = StyleClassifyFragment()
            return fragment
        }
    }

    override fun getFragmentContentId(): Int {
        return R.layout.fragment_classify
    }

    private lateinit var mvpAdapter: NewsVpAdapter
    private lateinit var mPresenter: ClassifyPresenter

    override fun initView() {

        mvpAdapter = NewsVpAdapter(context, childFragmentManager)
        vpArea.adapter = mvpAdapter
        tabLayout.setupWithViewPager(vpArea)
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
    }

    override fun initData() {
        mPresenter = ClassifyPresenter(this)
        mPresenter.requestClassifyData()


    }

    fun RefreshData(result: ArrayList<ClassifyTypeBean>) {
        mvpAdapter.setData(result)
        for (item in result){
            println(item)

        }
        mvpAdapter.notifyDataSetChanged()

    }



}