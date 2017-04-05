package com.zhengdianfang.softtest.ui

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.zhengdianfang.softtest.R
import com.zhengdianfang.softtest.bean.Question
import com.zhengdianfang.softtest.net.Api
import com.zhengdianfang.softtest.ui.views.ChooseLineanerLayout
import kotlinx.android.synthetic.main.fragment_question_item_layout.*

/**
 * Created by zheng on 2016/11/28.
 */
abstract class QuestionItemFragment : Fragment() {

    val questionBean by lazy { arguments?.getParcelable<Question>("question") }
    val index by lazy { arguments?.getInt("index") }
    val examCode by lazy { arguments?.getString("examCode") }

    protected var mark = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_question_item_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (questionBean != null){
            initQuestionTitles()
            assemberViews()
            initQuestionAnswer()
            hideAnaswerViews()
        }
    }

    abstract fun assemberViews()

    open protected fun initQuestionAnswer() {
        addDivierView(R.string.fragment_question_item_answer_label)
        questionBean?.anwsers?.forEachIndexed { i, questionAnswer ->
            var itemView = getItemView(questionAnswer.type, questionAnswer.content)
            itemView.visibility = View.GONE
            contentFrame.addView(itemView)
        }
    }

    open protected fun initQuestionSuggest() {
        mark = contentFrame.childCount -1
        addDivierView(R.string.fragment_question_item_suggest_label)
        val suggestView = getItemView("text", questionBean?.suggest ?: "")
        contentFrame.addView(suggestView)

    }

    protected fun initQuestonChoose() {
        addDivierView(R.string.fragment_question_item_choose_label)
        questionBean?.chooses?.forEachIndexed { i, questionChoose ->
            var itemView = getItemView(questionChoose.type, questionChoose.content)
            contentFrame.addView(itemView)
        }

    }

    protected fun initQuestionTitles() {
        addDivierView(getTitle())
        questionBean?.titles?.forEachIndexed { i, questionTitle ->
            var itemView = getItemView(questionTitle.type, questionTitle.content)
            contentFrame.addView(itemView)
        }
    }

    protected fun initSelectAnswers(){
        addDivierView(R.string.fragment_question_item_selece_answer_label)
        questionBean?.suggest?.split("|")?.forEachIndexed { i, s ->
            val chooseLineanerLayout = ChooseLineanerLayout(context, questionBean?.selectChooses!![i] ?: "")
            chooseLineanerLayout.index = i
            chooseLineanerLayout.onSelectItemListener = {view, index, position->
                questionBean?.selectChooses?.put(index, chooseLineanerLayout.arrays[position])
            }
            contentFrame.addView(chooseLineanerLayout)
        }
    }

    protected fun addDivierView(titleId:Int){
        addDivierView(getString(titleId))
    }

    protected fun addDivierView(title:String){
        val titleView = TextView(context)
        titleView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        titleView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
        titleView.text = title
        titleView.gravity = Gravity.CENTER_VERTICAL
        titleView.setTextColor(Color.WHITE)
        titleView.setPadding(16, 16, 16, 16)
        titleView.textSize = 16f
        contentFrame.addView(titleView)
    }


    protected fun getItemView(type:String, content:String):View{
        var view:View? = null
        when(type){
            "text"-> {
                view = LayoutInflater.from(context).inflate(R.layout.frame_question_title_text_layout, null ,false)
                (view as TextView).text = content
            }
            "imge"-> {
                view = LayoutInflater.from(context).inflate(R.layout.frame_question_title_image_layout, null ,false)
                Glide.with(context).load(Api.IMAGE_HOST_URL + content).diskCacheStrategy(DiskCacheStrategy.RESULT).fitCenter().into(view as ImageView)
                view.setOnClickListener {
                    activity?.supportFragmentManager?.beginTransaction()?.add(android.R.id.content, PhotoPreviewFragment.newInstance(context, Api.IMAGE_HOST_URL + content))
                            ?.addToBackStack("preview")?.commit()
                }
            }
        }
        return  view!!
    }

    private fun getTitle():String{
        if (examCode == "1_22220101"){
            val year = questionBean?.code?.substring(2,6)
            val month = if (questionBean?.code?.substring(6,8) == "01" ) "上半年" else "下半年"
            return "${year}年 ${month} 第${index !! + 1}题"
        }
        return getString(R.string.fragment_question_item_title_label, index!! + 1)
    }

    fun showAnaswerViews(){
        (0..contentFrame.childCount - 1).map { i-> contentFrame.getChildAt(i) }
                .forEachIndexed { i, view ->
                    if (i >= mark){
                        view.visibility = View.VISIBLE
                    }
                }
    }

    fun hideAnaswerViews(){
        (0..contentFrame.childCount - 1).map { i-> contentFrame.getChildAt(i) }
                .forEachIndexed { i, view ->
                    if (i >mark){
                        view.visibility = View.GONE
                    }
                }
    }

}