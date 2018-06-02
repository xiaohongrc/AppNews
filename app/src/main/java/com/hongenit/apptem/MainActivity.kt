package com.hongenit.apptem

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.view.View
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.hongenit.apptem.common.BaseActivity
import com.hongenit.apptem.common.Constants
import com.hongenit.apptem.setting.SettingActivity
import com.hongenit.apptem.util.EventUtil
import com.hongenit.apptem.util.LogUtil
import com.hongenit.apptem.util.Utils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v) {
            tv_menu_item1 -> {
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
        loadAdView()
    }

    private fun loadAdView() {
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                println("onAdLoaded")

                super.onAdLoaded()
            }

            override fun onAdClosed() {
                println("onAdClosed")

                super.onAdClosed()
            }

            override fun onAdClicked() {
                println("onAdClicked")

                super.onAdClicked()
            }

            override fun onAdFailedToLoad(p0: Int) {
                println("onAdFailedToLoad code = " + p0)
                super.onAdFailedToLoad(p0)
            }

        }
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
                fl_main_content.setTranslationX(drawerViewWidth!! * slideOffset)

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


}
