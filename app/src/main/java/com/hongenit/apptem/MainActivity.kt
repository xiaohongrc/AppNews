package com.hongenit.apptem

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.view.View
import android.widget.Toast
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.hongenit.apptem.channelchoose.ChannelChooseActivity
import com.hongenit.apptem.common.BaseActivity
import com.hongenit.apptem.common.Constants
import com.hongenit.apptem.setting.SettingActivity
import com.hongenit.apptem.util.EventUtil
import com.hongenit.apptem.util.LogUtil
import com.hongenit.apptem.util.ToastUtil
import com.hongenit.apptem.util.Utils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v) {
            tv_menu_item1 -> {
                marketComment()
            }

            tv_menu_item2 -> {
                Utils.feedback(this@MainActivity, getString(R.string.feedback_title), getString(R.string.feedback_email))
                EventUtil.menu_click_feedback()
            }

            tv_menu_item3 -> {
                startActivity(Intent(this@MainActivity, SettingActivity::class.java))
                EventUtil.menu_click_setting()
            }

            close_menu -> {
                if (drawer_layout.isDrawerOpen(rl_menu)) {
                    drawer_layout.closeDrawer(rl_menu)
                    EventUtil.drawer_close_btn_click()
                }
            }

            ib_open_drawer -> {
                if (!drawer_layout.isDrawerOpen(rl_menu)) {
                    drawer_layout.openDrawer(rl_menu)
                    EventUtil.drawer_open_btn_click()
                }
            }

        }

    }

    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initAdMob()
    }


    private fun initAdMob() {
        MobileAds.initialize(this, Constants.ADMOB_APP_ID)
    }


    private fun initView() {
        ib_open_drawer.setOnClickListener(this)
        initDrawer()
    }

    private fun initDrawer() {
        var versionName = "1.0"
        try {
            versionName = packageManager.getPackageInfo(packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        app_version.setText(versionName)
        drawer_layout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {
                println("new state  =" + newState)
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                //设置主布局随菜单滑动而滑动
                val drawerViewWidth = drawerView.width
                fl_main_content.setTranslationX(drawerViewWidth * slideOffset)

                //设置控件最先出现的位置
                val padingLeft = drawerViewWidth.toDouble() * (1 - 0.618) * (1 - slideOffset).toDouble()
                rl_menu.setPadding(padingLeft.toInt(), 0, 0, 0)
            }

            override fun onDrawerClosed(drawerView: View) {
                LogUtil.i(TAG, "onDrawerClosed")
                val hashMap: HashMap<String, String?>? = hashMapOf()
                hashMap?.put(EventUtil.FirebaseEventParams.drawer_close, "onDrawerClosed")
                EventUtil.drawer_action(hashMap)
            }

            override fun onDrawerOpened(drawerView: View) {
                LogUtil.i(TAG, "onDrawerOpened")
                val hashMap: HashMap<String, String?>? = hashMapOf()
                hashMap?.put(EventUtil.FirebaseEventParams.drawer_open, "onDrawerOpened")
                EventUtil.drawer_action(hashMap)
            }
        })

        tv_menu_item1.setOnClickListener(this)
        tv_menu_item2.setOnClickListener(this)
        tv_menu_item3.setOnClickListener(this)
        close_menu.setOnClickListener(this)

    }


    // 分享
    private fun share() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_SUBJECT, "粤语自己学")
        intent.putExtra(Intent.EXTRA_TEXT, "推荐一个练习粤语和英语的应用（粤语自己学），下载请到应用商店或戳此链接：http://a.app.qq.com/o/simple.jsp?pkgname=hong.cantonese")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(Intent.createChooser(intent, title))
    }

    // 调起应用市场评论
    private fun marketComment() {
        try {
            val uri = Uri.parse("market://details?id=" + packageName)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.no_this_app), Toast.LENGTH_SHORT).show()
        }

    }


    override fun onBackPressed() {
        if (exitConfirm()) {
            super.onBackPressed()
        }

    }

    private var mExitTime: Long = 0

    private fun exitConfirm(): Boolean {
        if (System.currentTimeMillis() - mExitTime < 2000) {
            return true
        } else {
            ToastUtil.showToast(getString(R.string.str_exit_touch_commit))
            mExitTime = System.currentTimeMillis()
            return false
        }
    }


}
