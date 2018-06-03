package com.hongenit.apptem.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.hongenit.apptem.MyApplicaiont
import com.hongenit.apptem.R
import com.hongenit.apptem.common.BaseActivity
import kotlinx.android.synthetic.main.activity_webview.*

/**
 * Created by hongenit on 2018/6/3.
 * desc:
 */
class WebViewActivity : BaseActivity() {
    companion object {
        fun start(context: Context, url: String) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.data = Uri.parse(url)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    private var mWebView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        initViews()
        loadAdView()
    }


    private fun loadAdView() {
        val adRequest = AdRequest.Builder().build()
        adView_webview.loadAd(adRequest)
        adView_webview.adListener = object : AdListener() {
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

    private fun initViews() {
        initWebView()
        iv_back.setOnClickListener(View.OnClickListener { finish() })

    }

    private fun initWebView() {
        val webviewContainer = findViewById(R.id.webview_container) as ViewGroup
        val url = intent.dataString
        val webView = WebView(MyApplicaiont.getAppContext())
        webView.settings.javaScriptEnabled = true
        //        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        //        webView.getSettings().setSupportMultipleWindows(true);
        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }
        }
        webView.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)
            }
        }
        webView.loadUrl(url)
        webviewContainer.addView(webView)
        mWebView = webView
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (mWebView != null) {
            mWebView!!.removeAllViews()
            // in android 5.1(sdk:21) we should invoke this to avoid memory leak
            // see (https://coolpers.github.io/webview/memory/leak/2015/07/16/
            // android-5.1-webview-memory-leak.html)
            (mWebView!!.parent as ViewGroup).removeView(mWebView)
            mWebView!!.tag = null
            mWebView!!.clearHistory()
            mWebView!!.destroy()
            mWebView = null
        }
    }


}