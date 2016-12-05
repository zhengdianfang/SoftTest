package com.zhengdianfang.softtest.ui.views

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.zhengdianfang.softtest.R

/**
 * Created by zheng on 2016/11/28.
 */
class ChooseLineanerLayout(context: Context?, val chooseAnswer:String) : ViewGroup(context) {

    private var totalWidth = 0
    private var childSize = 0
    private var first = true
    private var spacing = 16
    private var selectItemIndex = -1
    var arrays = arrayOf("A", "B", "C", "D")
    var onSelectItemListener:(View, Int, Int)->Unit = {v, i ,i1->}
    var index = 0

    init {

        setBackgroundColor(Color.WHITE)
        setPadding(0, 0, 0, 16)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        setMeasuredDimension(widthMeasureSpec,MeasureSpec.makeMeasureSpec(width/4, heightMode))
    }

    override fun onLayout(change: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        totalWidth = right - left
        childSize = (totalWidth - spacing * 5) / 4
        if (first){
            notifyDataSetChanged()
        }
        first = false
    }

    private fun notifyDataSetChanged() {
        if (childSize > 0){

            removeAllViews()
            arrays.forEachIndexed { i, s ->
                val textView = TextView(context)
                val layoutParams = LinearLayout.LayoutParams(childSize, childSize)
                textView.layoutParams = layoutParams
                textView.text = s
                textView.setTextColor(Color.BLACK)
                textView.textSize = 40f
                textView.setPadding(0, getPaddingTopSize(), 0, 0)
                textView.setBackgroundColor(if (chooseAnswer != s ) Color.LTGRAY else ContextCompat.getColor(context, R.color.colorPrimary))
                textView.gravity = Gravity.CENTER
                textView.setOnClickListener {v->
                    selectItemIndex = i
                    bindClickEvent()
                    onSelectItemListener(v, index, selectItemIndex)
                }
                val left = 0 + i * childSize  + spacing *(i + 1)
                val top = spacing
                textView.layout(left, top , left + childSize, childSize + top)
                addView(textView)
            }
        }
    }

    private fun getPaddingTopSize(): Int {
        val paint = Paint()
        paint.textSize = 40f
        val fontHeight = Math.ceil((paint.fontMetrics.descent - paint.fontMetrics.top).toDouble()) + 2
        return ((childSize - fontHeight)/6).toInt()
    }

    private fun bindClickEvent(){
        (0..childCount-1)
                .map { getChildAt(it) }
                .forEachIndexed { i, view ->
                    if (i == selectItemIndex){
                        view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    }else{
                        view.setBackgroundColor(Color.LTGRAY)
                    }
                }
    }

}