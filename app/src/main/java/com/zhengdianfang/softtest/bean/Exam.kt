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
class Exam : Parcelable {

    var id = 0f
    var code = ""
    var name = ""
    var type = 1
    var ps = ""
    var dispaly = 1



    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, p1: Int) {
        dest?.writeFloat(id)
        dest?.writeString(code)
        dest?.writeString(name)
        dest?.writeInt(type)
        dest?.writeString(ps)
        dest?.writeInt(dispaly)
    }

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<Exam> = object : Parcelable.Creator<Exam> {
            override fun createFromParcel(source: Parcel): Exam {
                val exam = Exam()
                exam.id = source.readFloat()
                exam.code = source.readString()
                exam.name = source.readString()
                exam.type = source.readInt()
                exam.ps = source.readString()
                exam.dispaly = source.readInt()
                return exam
            }

            override fun newArray(size: Int): Array<Exam?> {
                return arrayOfNulls(size)
            }
        }
    }
}