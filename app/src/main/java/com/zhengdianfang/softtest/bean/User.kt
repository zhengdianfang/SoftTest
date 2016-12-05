package com.zhengdianfang.softtest.bean

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by zheng on 2016/11/25.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class User : Parcelable {

    var uid = ""
    var token = ""
    var name = ""
    var loginTime = ""


    override fun describeContents(): Int {
       return 0
    }

    override fun writeToParcel(dest: Parcel?, p1: Int) {
        dest?.writeString(uid)
        dest?.writeString(token)
        dest?.writeString(name)
    }

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User {
                val user = User()
                user.uid = source.readString()
                user.token = source.readString()
                user.name =  source.readString()

                return user
            }

            override fun newArray(size: Int): Array<User?> {
                return arrayOfNulls(size)
            }
        }
    }
}