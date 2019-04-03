package com.xingfeng.android.easyrouter

import android.content.Context
import android.content.Intent

/**
 * @Author: 王立
 * @Date: 2019/3/10 17:44
 * @Desc:
 */

val routeMap = mapOf(
    "easyrouter://demo/secondActivity" to SecondActivity::class.java
)

fun goToPages(context: Context, url: String) {
    for ((key, value) in routeMap) {
        if (key == url) {
            context.startActivity(Intent(context, value))
        } else if (url.startsWith("http")) {
            context.startActivity(Intent(context, WebViewActivity::class.java).apply {
                putExtra("external_url", url)
            })
        }
    }
}