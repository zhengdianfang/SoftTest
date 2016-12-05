package com.zhengdianfang.softtest.net

import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response
import java.net.URLDecoder

/**
 * Created by zheng on 2016/11/25.
 */
class FeildInterceptor : Interceptor{

    override fun intercept(chain: Interceptor.Chain?): Response {
        val request = chain?.request()
        val body = request?.body()
        val builder = FormBody.Builder()
        if (body is FormBody){
            for (i in 0.. body.size()-1){
                val decode = URLDecoder.decode(body.encodedValue(i), "UTF-8")
                builder.add(body.encodedName(i), decode)
            }
        }
        builder.addEncoded("platform", "Android")
        val newRequest = request?.newBuilder()?.post(builder.build())?.build()
        return chain?.proceed(newRequest)!!
    }
}