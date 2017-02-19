package com.zhengdianfang.softtest.net

import com.orhanobut.logger.Logger
import com.zhengdianfang.softtest.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.internal.Util
import java.io.IOException

/**
 * Created by zheng on 2016/11/25.
 */
class LoggerInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (BuildConfig.DEBUG) {
            Logger.t(LoggerInterceptor::class.java.simpleName).d(String.format("Sending request %s",
                    request.url()))

        }
                var response = chain.proceed(request)

        if (BuildConfig.DEBUG) {
            val responseString = response.body().string()
            val newResponse = response.newBuilder().body(ResponseBody.create(response.body().contentType(), responseString.toByteArray(Util.UTF_8))).build()

            Logger.t(LoggerInterceptor::class.java.simpleName).d(responseString)

            response = newResponse
        }


        return response
    }
}