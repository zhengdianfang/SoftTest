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
class QuestionTitle : Parcelable {
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
        @JvmField final val CREATOR: Parcelable.Creator<QuestionTitle> = object : Parcelable.Creator<QuestionTitle> {
            override fun createFromParcel(source: Parcel): QuestionTitle {
                val questionTitle = QuestionTitle()
                questionTitle.type = source.readString()
                questionTitle.content = source.readString()
                return questionTitle
            }

            override fun newArray(size: Int): Array<QuestionTitle?> {
                return arrayOfNulls(size)
            }
        }
    }
}