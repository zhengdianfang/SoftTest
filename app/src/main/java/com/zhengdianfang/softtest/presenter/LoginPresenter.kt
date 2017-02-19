package com.zhengdianfang.softtest.presenter

import android.content.Context
import android.support.v4.content.SharedPreferencesCompat
import com.zhengdianfang.softtest.R
import com.zhengdianfang.softtest.SoftTestApplication
import com.zhengdianfang.softtest.bean.User
import com.zhengdianfang.softtest.common.Constants
import com.zhengdianfang.softtest.common.RegexUtils
import com.zhengdianfang.softtest.net.Api
import com.zhengdianfang.softtest.net.LoginApi
import com.zhengdianfang.softtest.presenter.contract.LoginContract
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by zheng on 2016/11/25.
 */
class LoginPresenter(val context: Context, val view : LoginContract.View?) : LoginContract.Presenter{

    override fun login(phone: String, password: String) {
        view?.showLoadingDialog()
        Api.instance.request().create(LoginApi::class.java).loginRequest(phone, password)
                .doOnNext { json->
                    val sharedPreferences = context.getSharedPreferences(Constants.SETTING_CACHE, Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit().putString("phone", phone).putString("password", password)
                    SharedPreferencesCompat.EditorCompat.getInstance().apply(editor)
                }
                .map { json-> Api.instance.json().readValue(json.toString(), User::class.java) }
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    user->
                        SoftTestApplication.instance.loginUser = user
                        view?.loginSuccess()
                        view?.hideLoadingDialog()
                },{e->
                    view?.loginFail(e.message ?: "")
                })
    }

    override fun register(phone: String, password: String) {
        view?.showLoadingDialog()
        Api.instance.request().create(LoginApi::class.java).registeRequest(phone, password)
                .doOnNext { json->
                    val sharedPreferences = context.getSharedPreferences(Constants.SETTING_CACHE, Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit().putString("last_user", json.toString())
                    SharedPreferencesCompat.EditorCompat.getInstance().apply(editor)
                }
                .map({ jsonObj ->
                    if (jsonObj.get("errorCode").asInt() != 0) {
                        throw NullPointerException(jsonObj.get("msg").asText())
                    }
                    jsonObj
                })
                .map { json-> Api.instance.json().readValue(json.toString(), User::class.java) }
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    user->
                    SoftTestApplication.instance.loginUser = user
                    view?.loginSuccess()
                    view?.hideLoadingDialog()
                },{e->
                    view?.loginFail(e.message ?: "")
                })
    }


    override fun checkInputs(phone: String, password: String,  confrimpwd:String, state:Int):Boolean {
        var res = false
        if (phone.isBlank()){
            view?.loginFail(context.getString(R.string.login_please_input_phone))
        }else if (!RegexUtils.isMobileExact(phone)){
            view?.loginFail(context.getString(R.string.login_please_input_phone))
        }else if (password.isBlank()){
            view?.loginFail(context.getString(R.string.login_please_input_password))
        }else{
            res = true
        }
        if (state == 1){
            if (confrimpwd.isBlank() ){
                view?.loginFail(context.getString(R.string.fragment_login_please_input_confrim_pwd))
                res =false
            }else if (confrimpwd != password){
                view?.loginFail(context.getString(R.string.fragment_login_please_input_pwd_equals))
                res = false
            }else{
                res = true
            }
        }
        return res
    }

    override fun start() {
        //no things
    }
}