package com.zhengdianfang.softtest.ui

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import com.zhengdianfang.softtest.R
import com.zhengdianfang.softtest.SoftTestApplication
import com.zhengdianfang.softtest.bean.Question
import com.zhengdianfang.softtest.presenter.CollectPresenter
import com.zhengdianfang.softtest.presenter.contract.CollectComeConract
import kotlinx.android.synthetic.main.activity_exam_detail_layout.*
import java.util.*

/**
 * Created by zheng on 2016/11/27.
 */
class MyCollectActivity : AppCompatActivity() , CollectComeConract.View, ViewPager.OnPageChangeListener {


    private var questionList: ArrayList<Question>? = null
    private var mCollectPresenter: CollectComeConract.Presenter? = null
    private var currentFragment:QuestionItemFragment? = null
    private var colleced = false

    private val  progressDialog by lazy {
        val dialog = ProgressDialog(this)
        dialog.setMessage("正在加载...")
        dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam_detail_layout)
        setSupportActionBar(toolbar)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_HOME_AS_UP
        questionViewPager.adapter = QuestionViewPagerAdapter(this, supportFragmentManager)
        questionViewPager.addOnPageChangeListener(this)
        questionViewPager.offscreenPageLimit = 3
        questionTitleView.setText(R.string.activity_my_collect_label)
        mCollectPresenter = CollectPresenter(this, this)
        progressDialog.show()
        mCollectPresenter?.start()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_exam_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val collectMenu = menu?.findItem(R.id.collectMenu)
        if (colleced) collectMenu?.setIcon(R.drawable.ic_colleced) else collectMenu?.setIcon(R.drawable.ic_uncollect)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home->onBackPressed()
            R.id.collectMenu->{
                val question = questionList?.get(questionViewPager.currentItem)
                if (colleced){
                    mCollectPresenter?.cancelCollectRequest(question?.code!!, item?.order.toString())
                    Toast.makeText(this, "取消收藏成功", Toast.LENGTH_SHORT).show()
                }else{
                    mCollectPresenter?.collectRequest(question?.code!!, item?.order.toString())
                    Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show()
                }
                colleced = colleced.not()
                if (colleced) item?.setIcon(R.drawable.ic_colleced) else item?.setIcon(R.drawable.ic_uncollect)
            }
            R.id.showAnswerMenu->{
                //显示答案
                questionList?.get(questionViewPager.currentItem)?.showAnaser = true
                currentFragment?.showAnaswerViews()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onShowFailMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        progressDialog.dismiss()
    }


    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        if (position > 0){
            questionList?.get(position - 1)?.selectChooses = currentFragment?.questionBean?.selectChooses!!
        }
        checkNowQuestionCollectState(position)
        invalidateOptionsMenu()
    }

    override fun onShowCollect(list: ArrayList<Question>) {
        this.questionList = list
        if (this.questionList != null){
            questionViewPager.adapter.notifyDataSetChanged()
            checkNowQuestionCollectState(0)
        }
        progressDialog.dismiss()
    }


    override fun onCollectResult() {
        colleced = true
        invalidateOptionsMenu()
    }

    override fun onCancelCollectResult() {
        colleced = false
        invalidateOptionsMenu()
    }

    override fun onCheckCollectState(collect: Boolean) {
        colleced = collect
        invalidateOptionsMenu()
    }

    override fun otherPlaceLogin() {
        SoftTestApplication.instance.logout()
    }



    private fun checkNowQuestionCollectState(position: Int){
        val item = questionList?.get(position)
        if (item != null){
            mCollectPresenter?.checkCollectState(item?.code!!, item?.order!!.toString())
        }
    }


    inner class QuestionViewPagerAdapter(val context: Context, fm: FragmentManager?) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            val item = questionList?.get(position)
            val code = item?.code?.takeLast(2)!!
            if (code == "01"){
                return MoonQuestonItemFragment.newInstance(context, null, item, position)
            }
            return  AfterMoonQuestonItemFragment.newInstance(context, null, item, position)
        }

        override fun getCount(): Int {
            return questionList?.count() ?: 0
        }

        override fun setPrimaryItem(container: ViewGroup?, position: Int, `object`: Any?) {
            currentFragment = `object` as? QuestionItemFragment
            super.setPrimaryItem(container, position, `object`)
        }

    }

}