package com.zhengdianfang.softtest.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.zhengdianfang.softtest.R
import com.zhengdianfang.softtest.net.Api
import kotlinx.android.synthetic.main.fragment_photo_layout.*
import uk.co.senab.photoview.PhotoViewAttacher

/**
 * Created by zheng on 2016/12/2.
 */
class PhotoPreviewFragment : Fragment() {

    companion object{
        fun newInstance(context: Context, url:String): Fragment? {
            return Fragment.instantiate(context, PhotoPreviewFragment::class.java.name, {val bundle = Bundle(); bundle.putString("url", url);bundle}.invoke())
        }
    }

    val photoUrl by lazy { arguments?.getString("url") }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_photo_layout, container ,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (photoUrl == Api.QR_CODE_URL){
            Glide.with(this).load(photoUrl).diskCacheStrategy(DiskCacheStrategy.NONE).into(photoView)
        }else{
            Glide.with(this).load(photoUrl).into(photoView)
        }

        photoView.onViewTapListener = PhotoViewAttacher.OnViewTapListener { view, x, y -> activity?.supportFragmentManager?.beginTransaction()?.remove(this@PhotoPreviewFragment)?.commit() }
    }

}