package com.hongenit.apptem.channelchoose

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

import java.util.ArrayList

/**
 * Created by hongenit on 2018/5/5.
 * desc:
 */

class FlowLayout(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs) {
    /**
     * 用来保存每行views的列表
     */
    private val mViewLinesList = ArrayList<List<View>>()
    /**
     * 用来保存行高的列表
     */
    private val mLineHeights = ArrayList<Int>()

    override fun generateLayoutParams(attrs: AttributeSet): ViewGroup.LayoutParams {
        return ViewGroup.MarginLayoutParams(context, attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 获取父容器为FlowLayout设置的测量模式和大小
        val iWidthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val iHeightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val iWidthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val iHeightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec)


        var measuredWith = 0
        var measuredHeight = 0
        var iCurLineW = 0
        var iCurLineH = 0
        if (iWidthMode == View.MeasureSpec.EXACTLY && iHeightMode == View.MeasureSpec.EXACTLY) {
            measuredWith = iWidthSpecSize
            measuredHeight = iHeightSpecSize
        } else {
            var iChildWidth: Int
            var iChildHeight: Int
            val childCount = childCount
            var viewList: MutableList<View> = ArrayList()
            for (i in 0 until childCount) {
                val childView = getChildAt(i)
                measureChild(childView, widthMeasureSpec, heightMeasureSpec)
                val layoutParams = childView.layoutParams as ViewGroup.MarginLayoutParams
                iChildWidth = childView.measuredWidth + layoutParams.leftMargin +
                        layoutParams.rightMargin
                iChildHeight = childView.measuredHeight + layoutParams.topMargin +
                        layoutParams.bottomMargin

                if (iCurLineW + iChildWidth > iWidthSpecSize) {
                    /**1、记录当前行的信息 */

                    //1、记录当前行的最大宽度，高度累加
                    measuredWith = Math.max(measuredWith, iCurLineW)
                    measuredHeight += iCurLineH
                    //2、将当前行的viewList添加至总的mViewsList，将行高添加至总的行高List
                    mViewLinesList.add(viewList)
                    mLineHeights.add(iCurLineH)


                    /**2、记录新一行的信息 */

                    //1、重新赋值新一行的宽、高
                    iCurLineW = iChildWidth
                    iCurLineH = iChildHeight

                    // 2、新建一行的viewlist，添加新一行的view
                    viewList = ArrayList()
                    viewList.add(childView)


                } else {
                    // 记录某行内的消息
                    //1、行内宽度的叠加、高度比较
                    iCurLineW += iChildWidth
                    iCurLineH = Math.max(iCurLineH, iChildHeight)

                    // 2、添加至当前行的viewList中
                    viewList.add(childView)
                }

                /*****3、如果正好是最后一行需要换行 */
                if (i == childCount - 1) {
                    //1、记录当前行的最大宽度，高度累加
                    measuredWith = Math.max(measuredWith, iCurLineW)
                    measuredHeight += iCurLineH

                    //2、将当前行的viewList添加至总的mViewsList，将行高添加至总的行高List
                    mViewLinesList.add(viewList)
                    mLineHeights.add(iCurLineH)

                }
            }


        }
        // 最终目的
        setMeasuredDimension(measuredWith, measuredHeight)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var left: Int
        var top: Int
        var right: Int
        var bottom: Int
        var curTop = 0
        var curLeft = 0
        val lineCount = mViewLinesList.size
        for (i in 0 until lineCount) {
            val viewList = mViewLinesList[i]
            val lineViewSize = viewList.size
            for (j in 0 until lineViewSize) {
                val childView = viewList[j]
                val layoutParams = childView.layoutParams as ViewGroup.MarginLayoutParams

                left = curLeft + layoutParams.leftMargin
                top = curTop + layoutParams.topMargin
                right = left + childView.measuredWidth
                bottom = top + childView.measuredHeight
                childView.layout(left, top, right, bottom)
                curLeft += childView.measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin
            }
            curLeft = 0
            curTop += mLineHeights[i]
        }
        mViewLinesList.clear()
        mLineHeights.clear()
    }

    interface OnItemClickListener {
        fun onItemClick(v: View, index: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {

        val childCount = childCount
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            childView.setOnClickListener { v -> listener.onItemClick(v, i) }
        }

    }
}
