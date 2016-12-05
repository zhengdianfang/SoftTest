package com.zhengdianfang.softtest.presenter.contract

import com.zhengdianfang.softtest.bean.Question
import com.zhengdianfang.softtest.presenter.BasePresenter
import java.util.*

/**
 * Created by zheng on 2016/11/27.
 */
interface ExamDetailConract {

    interface Presenter : BasePresenter{

    }

    interface  View {
        fun onShowExamDetail(list: ArrayList<Question>)
        fun onShowFailMsg(msg:String)

    }
}