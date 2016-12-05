package com.zhengdianfang.softtest.presenter

import com.zhengdianfang.softtest.net.Api
import com.zhengdianfang.softtest.net.ExamApi
import com.zhengdianfang.softtest.presenter.contract.ReportConract
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by zheng on 2016/12/2.
 */
class ReportPresenter(val view:ReportConract.View?) : ReportConract.Presenter{


    override fun reportSuggest(phone: String, email: String, content: String) {
        view?.showLoadingDialog()
        Api.instance.request().create(ExamApi::class.java).reportQuestion(phone, email, "Android", "Android", content)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    json->
                    view?.onSubmitSuccess()
                    view?.hideLoadingDialog()
                },{
                    e->
                    view?.onSubmitFail()
                    view?.hideLoadingDialog()
                })
    }

    override fun reportQeustionError(phone: String, email: String, code: String, tihao: String, content: String) {
        view?.showLoadingDialog()
        Api.instance.request().create(ExamApi::class.java).reportQuestion(phone, email, code, tihao, content)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    json->
                        view?.onSubmitSuccess()
                        view?.hideLoadingDialog()
                },{
                    e->
                        view?.onSubmitFail()
                        view?.hideLoadingDialog()
                })
    }

    override fun start() {
    }
}