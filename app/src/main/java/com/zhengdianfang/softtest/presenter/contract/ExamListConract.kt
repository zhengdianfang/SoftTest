package com.zhengdianfang.softtest.presenter.contract

import com.zhengdianfang.softtest.bean.ExamList
import com.zhengdianfang.softtest.presenter.BasePresenter
import java.util.*

/**
 * Created by zheng on 2016/11/27.
 */
interface ExamListConract {

    interface Presenter : BasePresenter

    interface View{
        fun onShowExamList(list:ArrayList<ExamList>)
        fun onShowFail(msg:String)
    }
}