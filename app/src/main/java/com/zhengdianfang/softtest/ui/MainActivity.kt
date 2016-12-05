package com.zhengdianfang.softtest.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.umeng.socialize.UmengTool
import com.zhengdianfang.softtest.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        UmengTool.getSignature(this)
    }

    fun toggleMenu(){
        if (activity_main.isDrawerOpen(Gravity.START)){
            activity_main.closeDrawer(Gravity.START)
        }else{
            activity_main.openDrawer(Gravity.START)

        }
    }
}
