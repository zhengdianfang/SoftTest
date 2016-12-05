package com.zhengdianfang.softtest.net

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.zhengdianfang.softtest.bean.QuestionAnwer
import com.zhengdianfang.softtest.bean.QuestionChoose
import com.zhengdianfang.softtest.bean.QuestionTitle
import java.util.*

/**
 * Created by zheng on 2016/11/28.
 */

class QuestionTitleDeserializer : JsonDeserializer<ArrayList<QuestionTitle>>(){
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): ArrayList<QuestionTitle> {
        var t = ArrayList<QuestionTitle>()
        val text = p?.text
        if (text?.isNotBlank()!!){
            t =  Api.instance.json().readValue<ArrayList<QuestionTitle>>(text, object : TypeReference<ArrayList<QuestionTitle>>() {})
        }
        return  t
    }

}
class QuestionAnswerDeserializer : JsonDeserializer<ArrayList<QuestionAnwer>>(){
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): ArrayList<QuestionAnwer> {
        var t = ArrayList<QuestionAnwer>()
        val text = p?.text
        if (text?.isNotBlank()!!){
            t =  Api.instance.json().readValue<ArrayList<QuestionAnwer>>(text, object : TypeReference<ArrayList<QuestionAnwer>>() {})
        }
        return  t
    }

}
class QuestionChooseeserializer : JsonDeserializer<ArrayList<QuestionChoose>>(){
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): ArrayList<QuestionChoose> {
        var t = ArrayList<QuestionChoose>()
        val text = p?.text
        if (text?.isNotBlank()!!){
            t =  Api.instance.json().readValue<ArrayList<QuestionChoose>>(text, object : TypeReference<ArrayList<QuestionChoose>>() {})
        }
        return  t
    }

}