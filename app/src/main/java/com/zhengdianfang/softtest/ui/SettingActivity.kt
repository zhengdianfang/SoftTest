package com.zhengdianfang.softtest.ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import com.umeng.socialize.ShareAction
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.zhengdianfang.softtest.R
import com.zhengdianfang.softtest.SoftTestApplication
import com.zhengdianfang.softtest.net.Api
import com.zhengdianfang.softtest.net.LoginApi
import kotlinx.android.synthetic.main.activity_setting.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class SettingActivity : AppCompatActivity() {

    private val resetPwdDialog by lazy {
        val inputEdit = EditText(this)
        inputEdit.inputType = InputType.TYPE_CLASS_NUMBER
        val dialog = AlertDialog.Builder(this).setView(inputEdit).setTitle("找回密码").setPositiveButton("确定") { p0, p1 ->
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

    private val loadingDialog by lazy {
        val dialog = ProgressDialog(this)
        dialog.setMessage("正在发送短信...")
        dialog
    }

    private val logouDialog by lazy {
        val dialog = AlertDialog.Builder(this).setTitle("提示").setMessage("确定退出此账户?").setPositiveButton("确定") { p0, p1 ->
            SoftTestApplication.instance.logout()
            Toast.makeText(this, "退出成功", Toast.LENGTH_SHORT).show()
            finish()
        }.setNegativeButton("取消") { p0, p1 ->
            p0.dismiss()
        }
        dialog
    }

    private val  shareAction by lazy { ShareAction(this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
            .withTitle("软考路上，你不是一个人在独行！")
            .withText("软考-历年真题，助你一臂之力！有内容、有个性、充满正能量的复习资料")
            .withMedia(UMImage(this, R.mipmap.ic_launcher))
            .withTargetUrl(Api.SHARE_URL) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setSupportActionBar(toolbar)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_HOME_AS_UP

        //我的收藏
        myCollectView.setOnClickListener {
            startActivity(Intent(this , MyCollectActivity::class.java))
        }

        //退出账户
        logoutButton.setOnClickListener {
            logouDialog.show()
        }

        resetPasswordView.setOnClickListener {
            resetPwdDialog.show()
        }

        reportErrorQuestionView.setOnClickListener {
            supportFragmentManager.beginTransaction().add(android.R.id.content, ReportFragment.newInstance(this,0, null, null))
                    .addToBackStack("report").commit()
        }

        payqrView.setOnClickListener {
            supportFragmentManager.beginTransaction().add(android.R.id.content, PhotoPreviewFragment.newInstance(this,Api.QR_CODE_URL))
                    .addToBackStack("pay").commit()
        }

        shareView.setOnClickListener {
            shareAction.open()

        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun resetPwdRequest(phone:String){
        Api.instance.request().create(LoginApi::class.java).resetPwdRequest(phone)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    json->
                        loadingDialog.dismiss()
                        Toast.makeText(this, json.get("msg").asText(), Toast.LENGTH_SHORT).show()
                },{e->
                    loadingDialog.dismiss()
                    Toast.makeText(this,e.message, Toast.LENGTH_SHORT).show()
                })
    }
}
