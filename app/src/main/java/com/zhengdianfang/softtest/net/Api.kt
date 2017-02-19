package com.zhengdianfang.softtest.net

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import rx.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by zheng on 2016/11/25.
 */
class Api {

    companion object{
        private val BASE_URL = "http://www.91ruankao.com/app/"
        val IMAGE_HOST_URL = "http://www.91ruankao.com/admin/RuanJianSheJiShi/"
        val AGREEMENT_URL = BASE_URL + "RuanKaoAgreement/"
        val QR_CODE_URL = BASE_URL + "RadioList/wxPayAndroid.png"
        val SHARE_URL = BASE_URL + "HttpIndex"
        val instance by lazy { Api() }
    }

    private val objectMapper: ObjectMapper by lazy {    var om = ObjectMapper().registerModule(KotlinModule())
        om.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        om.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
        om}

    private val client : Retrofit by lazy {  Retrofit.Builder().baseUrl(BASE_URL)
                                            .client(initOkHttp())
                                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                            .addConverterFactory(CustomConverterFactory(objectMapper))
                                            .build()
    }

    fun request():Retrofit{
        return client
    }

    fun json():ObjectMapper{
        return objectMapper
    }

    private fun initOkHttp(): OkHttpClient? {
        val client = OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(FeildInterceptor())
                .addInterceptor(LoggerInterceptor())
                .build()
        return client

    }
}

interface LoginApi{

    @FormUrlEncoded
    @POST("RegisterLogin/")
    fun loginRequest(@Field("uid") uid:String, @Field("pawd") pawd:String, @Field("type") type:Int = 1):Observable<JsonNode>

    @FormUrlEncoded
    @POST("RegisterLogin/")
    fun registeRequest(@Field("uid") uid:String, @Field("pawd") pawd:String, @Field("type") type:Int = 2):Observable<JsonNode>

    @FormUrlEncoded
    @POST("FindPawd/")
    fun resetPwdRequest(@Field("uid") uid:String):Observable<JsonNode>

    @FormUrlEncoded
    @POST("SiteGetver/")
    fun upgradeApp(@Field("uid") uid:String, @Field("BundleIdentifier") BundleIdentifier:String = "com.HuiTongZhiYuan.RuanKao"):Observable<JsonNode>
}

interface ExamApi{

    @DESDecode
    @FormUrlEncoded
    @POST("AllExam0121/")
    fun getExamList(@Field("uid") uid:String, @Field("BundleIdentifier") BundleIdentifier:String = "com.HuiTongZhiYuan.RuanKao"):Observable<JsonNode>

    @DESDecode
    @FormUrlEncoded
    @POST("RadioList/")
    fun getExamDetail(@Field("uid") uid:String, @Field("code") code:String):Observable<JsonNode>


    @FormUrlEncoded
    @POST("Collection/")
    fun collectQuestion(@Field("uid") uid:String, @Field("code") code:String,  @Field("TiHao") questionId:String,  @Field("type") type:Int = 1):Observable<JsonNode>

    @FormUrlEncoded
    @POST("Collection/")
    fun cancelCollectQuestion(@Field("uid") uid:String, @Field("code") code:String,  @Field("TiHao") questionId:String,  @Field("type") type:Int = 0):Observable<JsonNode>

    @FormUrlEncoded
    @POST("CheckCollection/")
    fun checkCollectState(@Field("uid") uid:String, @Field("code") code:String,  @Field("TiHao") questionId:String):Observable<JsonNode>

    @DESDecode
    @FormUrlEncoded
    @POST("MyCollection/")
    fun getMyCollects(@Field("uid") uid:String):Observable<JsonNode>

    @FormUrlEncoded
    @POST("Report/")
    fun reportQuestion(@Field("uid") uid:String, @Field("email") email:String,  @Field("code") code:String, @Field("TiHao") TiHao:String, @Field("content") content:String):Observable<JsonNode>
}