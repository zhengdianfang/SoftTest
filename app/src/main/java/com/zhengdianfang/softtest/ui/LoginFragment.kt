package com.zhengdianfang.softtest.ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.text.Html
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.zhengdianfang.softtest.R
import com.zhengdianfang.softtest.net.Api
import com.zhengdianfang.softtest.net.LoginApi
import com.zhengdianfang.softtest.presenter.LoginPresenter
import com.zhengdianfang.softtest.presenter.contract.LoginContract
import kotlinx.android.synthetic.main.fragment_login_layout.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by zheng on 2016/11/25.
 */
class LoginFragment : Fragment(), LoginContract.View{

    private val loadingDialog:ProgressDialog by lazy { ProgressDialog(context) }
    private var mPresenter: LoginContract.Presenter? = null
    private var state = 0 //0 : login 1:register

    private val resetPwdDialog by lazy {
        val inputEdit = EditText(context)
        inputEdit.setHint(R.string.login_please_input_phone)
        inputEdit.inputType = InputType.TYPE_CLASS_NUMBER
        val dialog = AlertDialog.Builder(context).setView(inputEdit).setTitle("找回密码").setPositiveButton("确定") { p0, p1 ->
            val phone = inputEdit.text.toString()
            if (phone.isNotBlank()){
                loadingDialog.show()
                resetPwdRequest(phone)
            }
            p0.dismiss()
        }.setNegativeButton("取消") { p0, p1 ->
            p0.dismiss()
        }
        dialog
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_login_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenter = LoginPresenter(context, this)

        confrimPwdEdit.visibility = View.GONE
        loginButton.setOnClickListener {
            val username = usernameEdit.text.toString()
            val password = passwordEdit.text.toString()
            val confrimpwd = confrimPwdEdit.text.toString()
            if (mPresenter?.checkInputs(username, password,confrimpwd, state)!!){
               if (state ==0) mPresenter?.login(username, password) else mPresenter?.register(username, password)
            }
        }

        switchLoginReTextView.setOnClickListener {
            state = if (state == 0) 1 else 0
            if (state ==0) switchLoginReTextView.setText(R.string.register_label) else switchLoginReTextView.setText(R.string.login_button_label)
            if (state ==0) loginButton.setText(R.string.login_button_label) else loginButton.setText(R.string.register_label)
            confrimPwdEdit.visibility = if (state == 0) View.GONE else View.VISIBLE
            confrimPwdEdit.setText("")
            passwordEdit.setText("")
        }

        agreementView.text = Html.fromHtml(getString(R.string.agreement_text))
        agreementView.setOnClickListener {
            startActivity(Intent(context, AgreementWebActivity::class.java))
        }

        forgetPwd.setOnClickListener {
            resetPwdDialog.show()
        }
    }

    override fun showLoadingDialog() {
        loadingDialog.setMessage(getString(R.string.fragment_login_loading_label))
        loadingDialog.show()
    }

    override fun hideLoadingDialog() {
        loadingDialog.dismiss()
    }

    override fun loginSuccess() {
        Toast.makeText(context, R.string.login_success_label, Toast.LENGTH_SHORT).show()
        activity.supportFragmentManager.beginTransaction().remove(this).commit()
    }

    override fun loginFail(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        loadingDialog.dismiss()
    }

    private fun resetPwdRequest(phone:String){
        Api.instance.request().create(LoginApi::class.java).resetPwdRequest(phone)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    json->
                    loadingDialog.dismiss()
                    Toast.makeText(context, json.get("msg").asText(), Toast.LENGTH_SHORT).show()
                },{e->
                    loadingDialog.dismiss()
                    Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
                })
    }
}