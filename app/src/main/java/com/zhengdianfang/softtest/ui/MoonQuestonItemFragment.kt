package com.zhengdianfang.softtest.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import com.zhengdianfang.softtest.bean.Question

/**
 * Created by zheng on 2016/11/30.
 */
class MoonQuestonItemFragment : QuestionItemFragment() {

    companion object{
        fun newInstance(context: Context, examCode:String?,  question: Question?, index:Int): Fragment {
            return Fragment.instantiate(context, MoonQuestonItemFragment::class.java.name,
                    {val bundle = Bundle(); bundle.putParcelable("question", question);bundle.putInt("index", index);
                        bundle.putString("examCode", examCode);bundle}.invoke())
        }
    }

    override fun assemberViews() {
        initQuestonChoose()
        initSelectAnswers()
        initQuestionSuggest()
    }
}