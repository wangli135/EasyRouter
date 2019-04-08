package com.xingfeng.android.easyrouter

import android.app.Application
import com.xingfeng.android.api.EasyRouter

/**
 * @Author: 王立
 * @Date: 2019/3/29 17:55
 * @Desc:
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        EasyRouter.getInstance().init("easyrouter","demo")
    }
}