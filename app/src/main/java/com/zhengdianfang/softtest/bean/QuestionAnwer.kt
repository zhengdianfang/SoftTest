package com.zhengdianfang.softtest.bean

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by zheng on 2016/11/27.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class QuestionAnwer : Parcelable{
    var type = ""
    var content = ""

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, p1: Int) {
        dest?.writeString(type)
        dest?.writeString(content)
    }

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<QuestionAnwer> = object : Parcelable.Creator<QuestionAnwer> {
            override fun createFromParcel(source: Parcel): QuestionAnwer {
                val questionAnwer = QuestionAnwer()
                questionAnwer.type = source.readString()
                questionAnwer.content = source.readString()
                return questionAnwer
            }

            override fun newArray(size: Int): Array<QuestionAnwer?> {
                return arrayOfNulls(size)
            }
        }
    }
}