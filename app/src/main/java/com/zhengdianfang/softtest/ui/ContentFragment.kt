package com.zhengdianfang.softtest.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.zhengdianfang.softtest.R
import com.zhengdianfang.softtest.bean.ExamList
import com.zhengdianfang.softtest.common.Constants
import com.zhengdianfang.softtest.presenter.ExamListPresenter
import com.zhengdianfang.softtest.presenter.contract.ExamListConract
import com.zhengdianfang.softtest.ui.adapter.ExamListAdapter
import kotlinx.android.synthetic.main.fragment_content.*
import java.util.*

/**
 * Created by zheng on 2016/11/25.
 */
class ContentFragment : Fragment(), ExamListConract.View{

    private var mPresenter:ExamListConract.Presenter? = null
    private val examlist:ArrayList<ExamList> = ArrayList()
    private val examListAdapter by lazy { ExamListAdapter(examlist) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_content, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenter = ExamListPresenter(context, this)
        initToolbar()
        examListVIew.setAdapter(examListAdapter)
        examListVIew.setOnChildClickListener { expandableListView, view, groupPosition, childPosition, l ->
            if ( Constants.checkLoginState(activity as AppCompatActivity)){
                val exam = examListAdapter.datas[groupPosition].list?.get(childPosition)
                if (null != exam){
                    startActivity(Intent(context, ExamDetailActivity::class.java).putExtra("exam", exam))
                }
            }
            true
        }
        mPresenter?.start()
    }

    private fun initToolbar() {
        leftMenu.setOnClickListener {
            if (activity is MainActivity){
                (activity as MainActivity)?.toggleMenu()
            }
        }
        rightMenu.setOnClickListener {
            if (Constants.checkLoginState(activity as AppCompatActivity)){
                startActivity(Intent(context, SettingActivity::class.java))
            }
        }
    }

    override fun onShowExamList(list: ArrayList<ExamList>) {
        examlist.clear()
        examlist.addAll(list)
        examListAdapter.notifyDataSetChanged()
    }

    override fun onShowFail(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

}
