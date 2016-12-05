package com.zhengdianfang.softtest.presenter.contract

import com.zhengdianfang.softtest.bean.Question
import com.zhengdianfang.softtest.presenter.BasePresenter
import java.util.*

/**
 * Created by zheng on 2016/11/29.
 */
interface CollectComeConract {
    interface Presenter : BasePresenter{
        fun collectRequest(code:String, questionId:String)
        fun cancelCollectRequest(code:String, questionId:String)
        fun checkCollectState(code:String, questionId:String)
    }

    interface  View {
        fun onShowCollect(list:ArrayList<Question>)
        fun onShowFailMsg(msg:String)
        fun onCollectResult()
        fun onCancelCollectResult()
        fun onCheckCollectState(collect:Boolean)
        fun otherPlaceLogin()
    }
}