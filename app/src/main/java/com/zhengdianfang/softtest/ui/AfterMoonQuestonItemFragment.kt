package com.zhengdianfang.softtest.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.zhengdianfang.softtest.R
import com.zhengdianfang.softtest.bean.Question
import kotlinx.android.synthetic.main.fragment_question_item_layout.*

/**
 * Created by zheng on 2016/11/30.
 */
class AfterMoonQuestonItemFragment : QuestionItemFragment() {

    companion object{
        fun newInstance(context: Context, examCode:String?, question:Question?, index:Int):Fragment{
           return Fragment.instantiate(context, AfterMoonQuestonItemFragment::class.java.name,
                    {val bundle = Bundle(); bundle.putParcelable("question", question);bundle.putInt("index", index); bundle}.invoke())
        }
    }
    
    override fun assemberViews() {
        initQuestionSuggest()
    }

    override fun initQuestionSuggest() {
        mark = contentFrame.childCount -1
        addDivierView(R.string.fragment_question_item_suggest_label)
        questionBean?.anwsers?.forEachIndexed { i, questionAnswer ->
            var itemView = getItemView(questionAnswer.type, questionAnswer.content)
            itemView.visibility = View.GONE
            contentFrame.addView(itemView)
        }
    }
}