package com.zhengdianfang.softtest.presenter

import com.fasterxml.jackson.core.type.TypeReference
import com.orhanobut.logger.Logger
import com.zhengdianfang.softtest.SoftTestApplication
import com.zhengdianfang.softtest.bean.Question
import com.zhengdianfang.softtest.net.Api
import com.zhengdianfang.softtest.net.DidNotPayException
import com.zhengdianfang.softtest.net.ExamApi
import com.zhengdianfang.softtest.presenter.contract.ExamDetailConract
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * Created by zheng on 2016/11/27.
 */
class ExamDetailPresenter(val code:String, val view:ExamDetailConract.View?) : ExamDetailConract.Presenter{

    override fun start() {
        val uid = SoftTestApplication.instance.loginUser?.token ?: ""
        Api.instance.request().create(ExamApi::class.java).getExamDetail(uid, code)
                .map { json->
                    Logger.t(ExamDetailPresenter::class.java.simpleName).d(json.toString())
                    var errorCode = json.get("errorCode").asInt()
                    if (errorCode == 1){
                        throw  DidNotPayException(json.get("msg").asText())
                    }
                    Api.instance.json().readValue<ArrayList<Question>>(json.get("list").toString(), object : TypeReference<ArrayList<Question>>() {})
                }
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    list->
                         view?.onShowExamDetail(list)

                },{
                    e->
                        view?.onShowFailMsg(e.message ?: "网络异常")
                })
    }
}