package com.zhengdianfang.softtest.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.fasterxml.jackson.databind.JsonNode
import com.orhanobut.logger.Logger
import com.zhengdianfang.softtest.R
import com.zhengdianfang.softtest.SoftTestApplication
import com.zhengdianfang.softtest.installer.AutoInstaller
import com.zhengdianfang.softtest.net.Api
import com.zhengdianfang.softtest.net.LoginApi
import kotlinx.android.synthetic.main.activity_main.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val updateDialog by lazy {
        AlertDialog.Builder(this).setTitle("提示")
    }

    private val  progressDialog by lazy {
        val dialog = ProgressDialog(this)
        dialog.setTitle("正在下载")
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        dialog
    }

    private val contentFragment by lazy { supportFragmentManager?.findFragmentById(R.id.contentFragment) as ContentFragment }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkVersion()
    }


    fun toggleMenu(){
        if (activity_main.isDrawerOpen(Gravity.START)){
            activity_main.closeDrawer(Gravity.START)
        }else{
            activity_main.openDrawer(Gravity.START)

        }
    }

    fun changeBundle(pos:Int){
        contentFragment?.onChangeBundle(pos)
    }

    fun changeToolbarTitle(title:String){
        contentFragment?.setToolbarTitle(title)
    }

    fun checkVersion(){
        Api.instance.request().create(LoginApi::class.java).upgradeApp(SoftTestApplication.instance.loginUser?.token ?: "")
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({json->
                    Logger.t(MainActivity::class.java.simpleName).json(json.toString())
                    val jsonNode = json.get("android")
                    var newVersion = jsonNode.get("ver").asText()
                    var nowVersion = applicationContext.packageManager.getPackageInfo(applicationContext.packageName, 0).versionName
                    if (newVersion.compareTo(nowVersion) > 0){
                        showUpgradeDialog(jsonNode)
                    }
                }, Throwable::printStackTrace)
    }

    private fun showUpgradeDialog(jsonNode: JsonNode) {
        val url = jsonNode.get("url").asText()
        val desc = jsonNode.get("desc").asText()
        val isForced = jsonNode.get("isForced").asInt()
        updateDialog.setMessage(desc)
        if (isForced != 1) {
            updateDialog.setNegativeButton("取消", { p0, p1 ->
                p0.dismiss()
            })
        }
        updateDialog.setPositiveButton("确定", { p0, p1 ->
            p0.dismiss()
            downloadDialog(url)
        })
        updateDialog.show()
    }

    private fun downloadDialog(url:String){

        val aDefault = AutoInstaller.getDefault(this)
        aDefault.setOnStateChangedListener(object : AutoInstaller.OnStateChangedListener {
            override fun onStart() {
                progressDialog.show()
            }

            override fun onProgress(progress: Int, max: Int) {
                progressDialog.max = max
                progressDialog.progress= progress
            }

            override fun onComplete() {

                progressDialog.dismiss()
            }

            override fun onNeed2OpenService() {
            }

        })
        aDefault.installFromUrl(url)

    }
}
