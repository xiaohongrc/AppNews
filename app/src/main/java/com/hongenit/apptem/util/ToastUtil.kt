package com.hongenit.picseei18n.util

import android.widget.Toast
import com.hongenit.apptem.MyApplicaiont

/**
 * Created by hongenit on 18/2/1.
 *  ToastUtil
 */
object ToastUtil {
    fun showToast(msg: String) {
        Toast.makeText(MyApplicaiont.getAppContext(), msg, Toast.LENGTH_SHORT).show()
    }


}