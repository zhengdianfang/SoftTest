package com.zhengdianfang.softtest.common

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.zhengdianfang.softtest.SoftTestApplication
import com.zhengdianfang.softtest.ui.LoginFragment

/**
 * Created by zheng on 2016/11/25.
 */
object Constants {

    val DES_KEY = "ruankao8"
    val SETTING_CACHE = "setting_cache"

    val SOFT_TYPES = arrayOf(SoftType("com.HuiTongZhiYuan.RuanKao", "软件设计师"),
                        SoftType("com.HuiTongZhiYuan.RuanKaoWL", "网络工程师"),
                        SoftType("com.HuiTongZhiYuan.RuanKaoSJK", "数据库系统工程师"),
                        SoftType("com.HuiTongZhiYuan.RuanKaoDMT", "多媒体应用设计师"),
                        SoftType("com.HuiTongZhiYuan.RuanKaoXTJC", "系统集成项目管理工程师"),
                        SoftType("com.HuiTongZhiYuan.RuanKaoXXXTJLS", "信息系统监理师"),
                        SoftType("com.HuiTongZhiYuan.RuanKaoXXXTGL", "信息系统管理工程师"),
                        SoftType("com.HuiTongZhiYuan.RuanKaoDZSW", "电子商务设计师"),
                        SoftType("com.HuiTongZhiYuan.RuanKaoQRS", "嵌入式系统设计师"),
                        SoftType("com.HuiTongZhiYuan.RuanKaoRJPC", "软件评测师"),
                        SoftType("com.HuiTongZhiYuan.RuanKaoXXAQ", "信息安全工程师")
            )

    fun checkLoginState(activity: AppCompatActivity):Boolean{
        if (SoftTestApplication.instance.loginUser == null){
            activity.supportFragmentManager.beginTransaction()
                    .add(android.R.id.content, Fragment.instantiate(activity, LoginFragment::class.java.name))
                    .addToBackStack("login")
                    .commit()
            return false
        }
        return true
    }
}

