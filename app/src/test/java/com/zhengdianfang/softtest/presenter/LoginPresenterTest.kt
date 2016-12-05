package com.zhengdianfang.softtest.presenter

import android.util.Base64
import com.zhengdianfang.softtest.net.Api
import com.zhengdianfang.softtest.net.LoginApi
import org.junit.Test
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec
import javax.crypto.spec.IvParameterSpec


/**
 * Created by zheng on 2016/11/25.
 */
class LoginPresenterTest {
    @Test
    fun login() {
        val encodeStr = encode("ruankao8", "18511177916".toByteArray())
        println("encode phone $encodeStr")
        Api.instance.request().create(LoginApi::class.java).loginRequest(encodeStr, "123456")
                .subscribe({
                    json->
                         println("login response json $json")
                },{e->
                    println(e.message)
                })
        Thread.sleep(5*1000)
    }

    @Test
    fun checkInputs() {

    }


    fun encode(key: String, data: ByteArray): String {
        try {
            val cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")
            val iv = IvParameterSpec(ByteArray(8))
            cipher.init(Cipher.ENCRYPT_MODE, getRawKey(key), iv)
            val bytes = cipher.doFinal(data)
            return Base64.encodeToString(bytes, Base64.DEFAULT)
        } catch (e: Exception) {
            return ""
        }

    }

    @Throws(Exception::class)
    private fun getRawKey(key: String): Key {
        val dks = DESKeySpec(key.toByteArray())
        val keyFactory = SecretKeyFactory.getInstance("DES")
        return keyFactory.generateSecret(dks)
    }

}