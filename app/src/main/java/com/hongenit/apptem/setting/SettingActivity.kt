package com.hongenit.apptem.setting

import android.os.Bundle
import com.hongenit.apptem.R
import com.hongenit.apptem.common.BaseActivity
import com.hongenit.apptem.common.Constants
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * Created by Xiaohong on 2018/5/17.
 * desc:
 */
class SettingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        tv_download_path.setText(Constants.CACHE_IMAGES.absolutePath)

    }


}