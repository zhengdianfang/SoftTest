package com.zhengdianfang.softtest.ui

import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.Target
import com.orhanobut.logger.Logger
import com.zhengdianfang.softtest.R
import com.zhengdianfang.softtest.net.Api
import kotlinx.android.synthetic.main.fragment_free_question_alert_fragment.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.File

/**
 * Created by zheng on 2016/12/6.
 */
class FreeQuestionAlertFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_free_question_alert_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Glide.with(this).load(Api.QR_CODE_URL).diskCacheStrategy(DiskCacheStrategy.NONE).into(qrCodeImageView)
        freeBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }

        saveToAlumbBtn.setOnClickListener {
            Observable.create<File> { sub->
                if (sub.isUnsubscribed.not()){
                    val futureTarget = Glide.with(this).load(Api.QR_CODE_URL).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get()
                    if (futureTarget.exists()){
                        sub.onNext(futureTarget)
                    }
                    sub.onCompleted()
                }
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe {file->
                        Logger.d("FreeQuestionAlertFragment", file.absolutePath)
                        MediaStore.Images.Media.insertImage(context?.contentResolver, file.absolutePath, file.name, "")
                        Toast.makeText(context, getString(R.string.fragment_question_alert_save_alert), Toast.LENGTH_SHORT).show()
                    }


        }
    }

}