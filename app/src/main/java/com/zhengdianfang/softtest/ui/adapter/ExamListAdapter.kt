package com.zhengdianfang.softtest.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.zhengdianfang.softtest.R
import com.zhengdianfang.softtest.bean.Exam
import com.zhengdianfang.softtest.bean.ExamList
import java.util.*

/**
 * Created by zheng on 2016/11/27.
 */
class ExamListAdapter(val datas:ArrayList<ExamList>) : BaseExpandableListAdapter() {
    override fun getGroup(position: Int): ExamList {
        return  datas[position]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return datas[groupPosition].list?.count() != 0
    }

    override fun hasStableIds(): Boolean {
       return true
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return datas[groupPosition].list?.count() ?: 0
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Exam? {
       return datas[groupPosition].list?.get(childPosition)
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }


    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
       return groupPosition + childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return datas.count()
    }


    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        var itemView = convertView
        if (convertView == null){
            itemView = LayoutInflater.from(parent?.context).inflate(R.layout.adapter_exam_list_group_item_layout, parent, false)
            itemView?.tag = GroupViewHolder(itemView)
        }
        val groupViewHolder = itemView?.tag as GroupViewHolder
        val textView = groupViewHolder.itemView as TextView
        textView.text = datas[groupPosition].name
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, if (isExpanded)  R.drawable.arrow_up else R.drawable.arrow_down, 0)
        return itemView!!
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        var itemView = convertView
        if (convertView == null){
            itemView = LayoutInflater.from(parent?.context).inflate(R.layout.adapter_exam_list_child_item_layout, parent, false)
            itemView?.tag = ChildViewHolder(itemView as TextView)
        }
        val childViewHolder = itemView?.tag as ChildViewHolder
        childViewHolder.itemView.text = datas[groupPosition].list?.get(childPosition)?.name
        return itemView!!
    }

    inner class GroupViewHolder(val itemView:View)

    inner class ChildViewHolder(val itemView:TextView)
}