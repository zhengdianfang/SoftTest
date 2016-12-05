package com.zhengdianfang.softtest.common

import android.util.Base64
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec
import javax.crypto.spec.IvParameterSpec



/**
 * Created by zheng on 2016/11/25.
 */
object DESUtils {

    private val TRANSFORMATION = "DES/CBC/PKCS5Padding"//DES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
    private val ALGORITHM = "DES"//DES是加密方式

    fun encode(data: String): String {
        return encode(Constants.DES_KEY, data.toByteArray())
    }

    /**
     * DES算法，加密

     * @param data 待加密字符串
     * *
     * @param key  加密私钥，长度不能够小于8位
     * *
     * @return 加密后的字节数组，一般结合Base64编码使用
     */
    fun encode(key: String, data: String): String {
        return encode(key, data.toByteArray())
    }


    /**
     * DES算法，加密

     * @param data 待加密字符串
     * *
     * @param key  加密私钥，长度不能够小于8位
     * *
     * @return 加密后的字节数组，一般结合Base64编码使用
     */
    fun encode(key: String, data: ByteArray): String {
        try {
            val cipher = Cipher.getInstance(TRANSFORMATION)
            val iv = IvParameterSpec(key.toByteArray())
            cipher.init(Cipher.ENCRYPT_MODE, getRawKey(key), iv)
            val bytes = cipher.doFinal(data)
            val encodeToString = Base64.encodeToString(bytes, Base64.DEFAULT)
            return encodeToString
        } catch (e: Exception) {
            return ""
        }

    }

    // 对密钥进行处理
    @Throws(Exception::class)
    private fun getRawKey(key: String): Key {
        val dks = DESKeySpec(key.toByteArray())
        val keyFactory = SecretKeyFactory.getInstance(ALGORITHM)
        return keyFactory.generateSecret(dks)
    }

    fun decode(data: String): String {
        return decode(Constants.DES_KEY, Base64.decode(data, Base64.DEFAULT))
    }

    /**
     * 获取编码后的值

     * @param key
     * *
     * @param data
     * *
     * @return
     */
    fun decode(key: String, data: String): String {
        return decode(key, Base64.decode(data, Base64.DEFAULT))
    }

    /**
     * DES算法，解密

     * @param data 待解密字符串
     * *
     * @param key  解密私钥，长度不能够小于8位
     * *
     * @return 解密后的字节数组
     */
    fun decode(key: String, data: ByteArray): String {
        try {
            val cipher = Cipher.getInstance(TRANSFORMATION)
            val iv = IvParameterSpec(key.toByteArray())
            cipher.init(Cipher.DECRYPT_MODE, getRawKey(key), iv)
            val original = cipher.doFinal(data)
            val originalString = String(original)
            return originalString
        } catch (e: Exception) {
            return ""
        }

    }

}