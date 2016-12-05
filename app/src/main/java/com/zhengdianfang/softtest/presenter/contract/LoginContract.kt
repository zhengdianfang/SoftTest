package com.zhengdianfang.softtest.presenter.contract

import com.zhengdianfang.softtest.presenter.BasePresenter

/**
 * Created by zheng on 2016/11/25.
 */
interface LoginContract {

    interface Presenter : BasePresenter{
        fun login(phone:String, password:String)
        fun register(phone:String, password:String)
        fun checkInputs(phone:String, password:String, confrimpwd:String, state:Int):Boolean
    }

    interface View {
        fun showLoadingDialog()
        fun hideLoadingDialog()
        fun loginSuccess()
        fun loginFail(msg:String)

    }

}