package com.zhengdianfang.softtest.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import com.zhengdianfang.softtest.R
import com.zhengdianfang.softtest.net.Api
import kotlinx.android.synthetic.main.activity_agreement_web.*

class AgreementWebActivity : AppCompatActivity() {

    private var webView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agreement_web)

        webView = WebView(this)
        webView?.settings?.setSupportZoom(false)
        webView?.loadUrl(Api.AGREEMENT_URL)
        activity_agreement_web.addView(webView)

    }

    override fun onDestroy() {
        super.onDestroy()
        webView?.destroy()
        webView = null
    }
}
