package com.zhengdianfang.softtest.net

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectReader
import com.fasterxml.jackson.databind.ObjectWriter
import com.zhengdianfang.softtest.common.DESUtils
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * Created by zheng on 2016/11/25.
 */
class CustomConverterFactory(val mapper: ObjectMapper) : Converter.Factory() {



    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, Any> {
        var decodeable = false
        annotations?.forEach {
            if (it is DESDecode){
                decodeable = true
            }
        }
        val javaType = mapper.typeFactory.constructType(type)
        val reader = mapper.reader(javaType)
       return CustomResponseBodyConverter(reader, decodeable)
    }

    override fun requestBodyConverter(type: Type?, parameterAnnotations: Array<out Annotation>?, methodAnnotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<Any, RequestBody> {
        val javaType = mapper.typeFactory.constructType(type)
        val writer = mapper.writerWithType(javaType)
        return CustomRequestBodyConverter(writer)
    }

   
}

class CustomResponseBodyConverter<T>(val reader: ObjectReader, val decodeable: Boolean) : Converter<ResponseBody, T>{
    override fun convert(value: ResponseBody?): T {
        var  decode = value?.string() ?: ""
        if (decodeable){
            decode = DESUtils.decode(decode)
        }
        return reader.readValue(decode)
    }

}

class CustomRequestBodyConverter<T>(val writer: ObjectWriter) : Converter<T, RequestBody>{
    override fun convert(value: T): RequestBody {
        val bytes = writer.writeValueAsBytes(value)
        return RequestBody.create(MediaType.parse("html/text; charset=UTF-8"), bytes)
    }

}