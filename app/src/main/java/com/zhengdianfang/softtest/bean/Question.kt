package com.zhengdianfang.softtest.bean

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.zhengdianfang.softtest.net.QuestionAnswerDeserializer
import com.zhengdianfang.softtest.net.QuestionChooseeserializer
import com.zhengdianfang.softtest.net.QuestionTitleDeserializer
import java.util.*

/**
 * Created by zheng on 2016/11/27.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class Question : Parcelable{

    @JsonProperty("Id")
    var id = 0f
    var code = ""
    @JsonProperty("TiHao")
    var order = 0

    @JsonProperty("BiaoTi")
    @JsonDeserialize(using = QuestionTitleDeserializer::class)
    var titles:ArrayList<QuestionTitle>? = null

    @JsonProperty("XuanXiang")
    @JsonDeserialize(using = QuestionChooseeserializer::class)
    var chooses:ArrayList<QuestionChoose>? = null

    @JsonProperty("CanKao")
    var suggest = ""

    @JsonProperty("JieXi")
    @JsonDeserialize(using = QuestionAnswerDeserializer::class)
    var anwsers:ArrayList<QuestionAnwer>? = null

    @JsonIgnoreProperties
    var showAnaser = false

    @JsonIgnoreProperties
    var selectChooses = mutableMapOf<Int, String>()

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, p1: Int) {
        dest?.writeFloat(id)
        dest?.writeString(code)
        dest?.writeInt(order)
        dest?.writeList(titles)
        dest?.writeList(chooses)
        dest?.writeString(suggest)
        dest?.writeList(anwsers)


    }

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<Question> = object : Parcelable.Creator<Question> {
            override fun createFromParcel(source: Parcel): Question {
                val question = Question()
                question.id = source.readFloat()
                question.code = source.readString()
                question.order = source.readInt()
                question.titles = { val l = ArrayList<QuestionTitle>(); source.readList(l, QuestionTitle::class.java.classLoader); l }.invoke()
                question.chooses = { val l = ArrayList<QuestionChoose>(); source.readList(l, QuestionChoose::class.java.classLoader); l }.invoke()
                question.suggest = source.readString()
                question.anwsers = { val l = ArrayList<QuestionAnwer>(); source.readList(l, QuestionAnwer::class.java.classLoader); l }.invoke()
                return question
            }

            override fun newArray(size: Int): Array<Question?> {
                return arrayOfNulls(size)
            }
        }
    }
}