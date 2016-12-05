package com.zhengdianfang.softtest.ui

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.zhengdianfang.softtest.R
import com.zhengdianfang.softtest.SoftTestApplication
import com.zhengdianfang.softtest.bean.Exam
import com.zhengdianfang.softtest.bean.Question
import com.zhengdianfang.softtest.presenter.ReportPresenter
import com.zhengdianfang.softtest.presenter.contract.ReportConract
import kotlinx.android.synthetic.main.fragment_report_layout.*

/**
 * Created by zheng on 2016/12/2.
 */
class ReportFragment : Fragment() , ReportConract.View{

    val index by lazy { arguments?.getInt("index") }
    val exam by lazy { arguments?.getParcelable<Exam>("exam") }
    val question by lazy { arguments?.getParcelable<Question>("question") }
    val loadingDialog by lazy { ProgressDialog(context) }
    var mReportPresenter:ReportPresenter ?= null

    companion object{
        fun newInstance(context: Context, index: Int, exam:Exam?, question:Question?):Fragment{
            return Fragment.instantiate(context, ReportFragment::class.java.name, {val bundle = Bundle();
                bundle.putInt("index", index); bundle.putParcelable("exam", exam);bundle.putParcelable("question", question);bundle}.invoke())
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_report_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mReportPresenter = ReportPresenter(this)
        phoneEdit.setText(SoftTestApplication.instance.loginUser?.uid)
        if (exam == null){
            toolbarTitleView.text = "意见反馈"
            submitView.setOnClickListener {
                if (checkParamsInput()){
                    val email = emailEdit.text.toString()
                    val phone = phoneEdit.text.toString()
                    val content = contentEdit.text.toString()
                    mReportPresenter?.reportSuggest(phone, email, content)
                }
            }
        }else{
            toolbarTitleView.text = "错误反馈"
            bottomAelrtView.text = getString(R.string.fragment_reprot_question_error_alert, "《${exam?.name}》第${index!! + 1}题")
            submitView.setOnClickListener {
                if (checkParamsInput()){
                    val email = emailEdit.text.toString()
                    val phone = phoneEdit.text.toString()
                    val content = contentEdit.text.toString()
                    mReportPresenter?.reportQeustionError(phone, email, question?.code!!, question?.order.toString()!!, content)
                }
            }
        }


        backView.setOnClickListener {
            finish()
        }
    }

    override fun showLoadingDialog() {
        loadingDialog.setMessage("提交中...")
        loadingDialog.show()
    }

    override fun hideLoadingDialog() {
        loadingDialog.dismiss()
    }

    override fun onSubmitSuccess() {
        Toast.makeText(context, "提交成功", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun finish(){
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
    }

    override fun onSubmitFail() {
        Toast.makeText(context, "提交失败", Toast.LENGTH_SHORT).show()
    }

    private fun checkParamsInput():Boolean{
        var res = true
        val email = emailEdit.text.toString()
        val phone = phoneEdit.text.toString()
        val content = contentEdit.text.toString()

        if (phone.isNullOrBlank()){
            Toast.makeText(context, R.string.login_please_input_phone, Toast.LENGTH_SHORT).show()
            res = false
        }

        if (email.isNullOrBlank()){
            Toast.makeText(context, R.string.fragment_report_please_input_email, Toast.LENGTH_SHORT).show()
            res = false
        }

        if (content.isNullOrBlank()){
            Toast.makeText(context, R.string.fragment_report_please_input_content, Toast.LENGTH_SHORT).show()
            res = false
        }
        return res
    }
}