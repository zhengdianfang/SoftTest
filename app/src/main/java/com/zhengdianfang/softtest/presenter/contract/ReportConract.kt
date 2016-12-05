package com.zhengdianfang.softtest.presenter.contract

import com.zhengdianfang.softtest.presenter.BasePresenter

/**
 * Created by zheng on 2016/12/2.
 */
interface ReportConract {

    interface Presenter : BasePresenter{
        fun reportSuggest(phone:String, email:String, content:String)
        fun reportQeustionError(phone:String, email:String, code:String, tihao:String, content:String)
    }

    interface View{
        fun showLoadingDialog()
        fun hideLoadingDialog()
        fun onSubmitSuccess()
        fun onSubmitFail()
    }
}