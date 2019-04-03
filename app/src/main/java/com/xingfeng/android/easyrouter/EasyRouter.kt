package com.xingfeng.android.easyrouter

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import java.net.URI

/**
 * @Author: 王立
 * @Date: 2019/3/10 17:44
 * @Desc:
 */

val routeMap = mapOf(
    "/secondActivity" to SecondActivity::class.java,
    "/paramsActivity" to WithParamsActivity::class.java
)

fun goToPages(context: Context, url: String) {
    if (url.contains("/secondActivity")) {
        AlertInterceptor().interceptor(context, URI(url),
            object : OnSuccessListener {
                override fun onSuccess(context: Context, uri: URI) {
                    urlProcess(context, url)?.apply {
                        context.startActivity(this)
                    }
                }
            },
            object : OnErrorListener {
                override fun onError() {
                    Toast.makeText(context, "用户取消了跳转", Toast.LENGTH_SHORT).show()
                }
            }
        )
    } else {
        urlProcess(context, url)?.apply {
            context.startActivity(this)
        }
    }
}

fun urlProcess(context: Context, url: String): Intent? {
    if (url.startsWith("http")) {
        return Intent(context, WebViewActivity::class.java).apply {
            putExtra("external_url", url)
        }
    }
    //本app支持的scheme
    else if (url.startsWith("easyrouter")) {
        with(URI(url)) {
            for ((key, value) in routeMap) {
                if (key.equals(path)) {
                    return Intent(context, value).apply {
                        if (!TextUtils.isEmpty(query)) {
                            query.split("&").forEach {
                                it.split("=").apply {
                                    putExtra(get(0), get(1))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    return Intent(context, DegradeActivity::class.java).apply {
        putExtra("error_msg", "没有找到目标页面")
    }
}
