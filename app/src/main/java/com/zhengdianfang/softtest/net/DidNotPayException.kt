package com.zhengdianfang.softtest.net

import com.zhengdianfang.softtest.bean.Question
import java.util.*

/**
 * Created by zheng on 2016/11/27.
 */
class DidNotPayException(message: String?, val freeList:ArrayList<Question>?) : Exception(message)