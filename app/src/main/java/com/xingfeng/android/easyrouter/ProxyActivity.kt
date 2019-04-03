package com.xingfeng.android.easyrouter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.xingfeng.android.api.EasyRouter

class ProxyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        intent.data?.apply {
//            goToPages(this@ProxyActivity, toString())
//            finish()
//        }
        EasyRouter.getInstance().goToPages(this, intent?.data.toString())
    }
}
