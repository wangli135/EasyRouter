package com.xingfeng.android.easyrouter

import android.app.AlertDialog
import android.content.Context
import java.net.URI

/**
 * @Author: 王立
 * @Date: 2019/3/22 17:01
 * @Desc:
 */
interface OnErrorListener {
    fun onError()
}

interface OnSuccessListener {
    fun onSuccess(context: Context, uri: URI)
}

interface Interceptor {
    fun interceptor(
        context: Context,
        uri: URI,
        onSuccessListener: OnSuccessListener? = null,
        onErrorListener: OnErrorListener? = null
    )
}

class AlertInterceptor : Interceptor {
    override fun interceptor(
        context: Context,
        uri: URI,
        onSuccessListener: OnSuccessListener?,
        onErrorListener: OnErrorListener?
    ) {
        AlertDialog.Builder(context).apply {
            setTitle("拦截提示")
            setMessage("是否跳转SecondActivity")
            setPositiveButton(
                "确定"
            ) { dialog, _ ->
                onSuccessListener?.onSuccess(context, uri)
                dialog.dismiss()
            }
            setNegativeButton(
                "取消"
            ) { dialog, _ ->
                onErrorListener?.onError()
                dialog.dismiss()
            }
        }.create().show()
    }
}
