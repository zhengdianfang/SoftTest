package com.zhengdianfang.softtest.ui

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.zhengdianfang.softtest.R
import com.zhengdianfang.softtest.common.Constants
import kotlinx.android.synthetic.main.fragment_left_menu_layout.*

/**
 * Created by zheng on 2016/11/25.
 */
class LeftMenuFragment : Fragment() {

    val DEFAULT_SELECTED = 0
    var selectItemIndex = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater?.inflate(R.layout.fragment_left_menu_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Constants.SOFT_TYPES.forEachIndexed { i, softType ->
            val textView = TextView(context)
            textView.text = softType.name
            textView.setTextColor(Color.WHITE)
            textView.textSize = 20f
            textView.setPadding(32, 32, 0 , 32)
            textView.setOnClickListener {
                selectedItemView(i)
                if ( activity is MainActivity){
                    val ac = (activity as? MainActivity)
                    ac?.changeBundle(selectItemIndex)
                    ac?.toggleMenu()

                }
            }
            val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.topMargin = 48
            softTypesFrame.addView(textView)
        }
        selectedItemView(DEFAULT_SELECTED)
    }

    private fun selectedItemView(selectIndex:Int){
        selectItemIndex = selectIndex
        for (i in 0..softTypesFrame.childCount - 1){
            val childAt = softTypesFrame.getChildAt(i) as TextView
            if (i == selectIndex){
                childAt.setBackgroundColor(Color.WHITE)
                childAt.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
            }else{
                childAt.setBackgroundColor(Color.TRANSPARENT)
                childAt.setTextColor(Color.WHITE)
            }
        }

        if ( activity is MainActivity){
            val ac = (activity as? MainActivity)
            ac?.changeToolbarTitle(Constants.SOFT_TYPES[selectItemIndex].name)

        }
    }

}