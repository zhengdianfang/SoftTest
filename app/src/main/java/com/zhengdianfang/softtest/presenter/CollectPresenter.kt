package com.zhengdianfang.softtest.presenter

import android.content.Context
import com.fasterxml.jackson.core.type.TypeReference
import com.zhengdianfang.softtest.SoftTestApplication
import com.zhengdianfang.softtest.bean.Question
import com.zhengdianfang.softtest.common.Constants
import com.zhengdianfang.softtest.net.Api
import com.zhengdianfang.softtest.net.ExamApi
import com.zhengdianfang.softtest.presenter.contract.CollectComeConract
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * Created by zheng on 2016/11/29.
 */
class CollectPresenter(val context: Context,  val view:CollectComeConract.View?) : CollectComeConract.Presenter {

    override fun start() {
        val uid = SoftTestApplication.instance.loginUser?.token ?: ""
        Api.instance.request().create(ExamApi::class.java).getMyCollects(uid)
                .map { json->
                    Api.instance.json().readValue<ArrayList<Question>>(json.get("list").toString(), object : TypeReference<ArrayList<Question>>() {})
                }
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    list->
                    view?.onShowCollect(list)

                },{
                    e->
                    view?.onShowFailMsg(e.message ?: "网络异常")
                })
    }

    override fun collectRequest(code: String, questionId: String) {
        val uid = SoftTestApplication.instance.loginUser?.token ?: ""
        Api.instance.request().create(ExamApi::class.java).collectQuestion(uid, code, questionId)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    list ->
                    view?.onCollectResult()
                }, {
                    e ->
                    view?.onShowFailMsg(e.message ?: "网络异常")
                })
    }

    override fun cancelCollectRequest(code: String, questionId: String) {
        val uid = SoftTestApplication.instance.loginUser?.token ?: ""
        Api.instance.request().create(ExamApi::class.java).cancelCollectQuestion(uid, code, questionId)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    list ->
                    view?.onCancelCollectResult()
                }, {
                    e ->
                    view?.onShowFailMsg(e.message ?: "网络异常")
                })
    }

    override fun checkCollectState(code: String, questionId: String) {
        val uid = SoftTestApplication.instance.loginUser?.token ?: ""
        Api.instance.request().create(ExamApi::class.java).checkCollectState(uid, code, questionId)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    json ->
                         val loginTime  = json.get("loginTime").asText()
                         if (loginTime != SoftTestApplication.instance.loginUser?.loginTime){
                             view?.otherPlaceLogin()
                         }else{
                             view?.onCheckCollectState(json.get("Collection").asText() == "1")
                         }

                }, {
                    e ->
                    view?.onShowFailMsg(e.message ?: "网络异常")
                })
    }
}
