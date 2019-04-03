package com.xingfeng.android.easyrouter

import android.content.Context
import android.content.Intent

/**
 * @Author: 王立
 * @Date: 2019/3/22 17:37
 * @Desc:
 */
//降级策略接口
interface DegradeListener {
    fun onFail(context: Context, msg: String)
}

class MyDegrageListener : DegradeListener {
    override fun onFail(context: Context, msg: String) {
        context.startActivity(Intent(context, DegradeActivity::class.java).apply {
            putExtra("error_msg",msg)
        })
    }
}



