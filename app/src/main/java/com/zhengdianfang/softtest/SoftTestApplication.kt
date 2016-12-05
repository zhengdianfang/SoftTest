package com.zhengdianfang.softtest

import android.app.Application
import android.content.Context
import android.support.v4.content.SharedPreferencesCompat
import com.bumptech.glide.Glide
import com.umeng.socialize.PlatformConfig
import com.umeng.socialize.UMShareAPI
import com.zhengdianfang.softtest.bean.User
import com.zhengdianfang.softtest.common.Constants
import kotlin.properties.Delegates

/**
 * Created by zheng on 2016/11/25.
 */
class SoftTestApplication : Application(){

    companion object{
        var instance:SoftTestApplication by Delegates.notNull<SoftTestApplication>()
    }

    var loginUser: User? = null

    override fun onCreate() {
        super.onCreate()
        instance = this

        PlatformConfig.setWeixin("wxd2428cf6423cd1fa", "850692c6ae2db15f9eb0717af66282ab")
        UMShareAPI.get(this)
    }

    fun logout() {
        Glide.get(this).clearMemory()
        val sharedPreferences = getSharedPreferences(Constants.SETTING_CACHE, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit().putString("phone", "").putString("password", "")
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor)
        loginUser = null
    }

}