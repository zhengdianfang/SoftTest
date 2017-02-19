package com.zhengdianfang.softtest.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zhengdianfang.softtest.R
import com.zhengdianfang.softtest.common.Constants
import com.zhengdianfang.softtest.presenter.LoginPresenter
import com.zhengdianfang.softtest.presenter.contract.LoginContract

/**
 * Created by zheng on 2016/11/28.
 */
class LoadingActivity : AppCompatActivity() , LoginContract.View{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        val sharedPreferences = getSharedPreferences(Constants.SETTING_CACHE, Context.MODE_PRIVATE)
        val lastPhone = sharedPreferences.getString("phone", "")
        val password = sharedPreferences.getString("password", "")
        val loginPresenter = LoginPresenter(this, this)
        if (lastPhone.isNotBlank() && password.isNotBlank()){
            loginPresenter.login(lastPhone, password)
        }else{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

    override fun showLoadingDialog() {
    }


    override fun hideLoadingDialog() {

    }

    override fun loginSuccess() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun loginFail(msg: String) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}