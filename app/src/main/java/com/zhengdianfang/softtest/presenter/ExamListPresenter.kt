package com.zhengdianfang.softtest.presenter

import android.content.Context
import com.fasterxml.jackson.core.type.TypeReference
import com.orhanobut.logger.Logger
import com.zhengdianfang.softtest.SoftTestApplication
import com.zhengdianfang.softtest.bean.ExamList
import com.zhengdianfang.softtest.common.Constants
import com.zhengdianfang.softtest.net.Api
import com.zhengdianfang.softtest.net.ExamApi
import com.zhengdianfang.softtest.presenter.contract.ExamListConract
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * Created by zheng on 2016/11/27.
 */
class ExamListPresenter(val context: Context, val view:ExamListConract.View?) : ExamListConract.Presenter{

    var bundleId = Constants.SOFT_TYPES[0].bundleIdentifier
        set(value) {
            field = value
            requestExamList()
        }

    override fun start() {
        requestExamList()
    }

    private fun requestExamList(){
        Api.instance.request().create(ExamApi::class.java).getExamList(SoftTestApplication.instance.loginUser?.token ?: "", bundleId)
                .doOnNext { json->
                    Logger.t(ExamListPresenter::class.java.simpleName).d(json.toString())
                }
                .map { json-> Api.instance.json().readValue<ArrayList<ExamList>>(json.get("list").toString()
                        , object :TypeReference<ArrayList<ExamList>>(){}) }
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    list->
                        view?.onShowExamList(list)
                },{
                    e->
                        view?.onShowFail(e.message ?: "")
                })
    }
}