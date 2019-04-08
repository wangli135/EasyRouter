package com.xingfeng.android.easyrouter

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.xingfeng.android.api.EasyRouter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val secondActivityUrl = "easyrouter://demo/secondActivity"
    val paramsActivityUrl = "easyrouter://demo/paramsActivity"
    val noPageUrl = "easyrouter://demo/noPages"
    val errorUrl = "otherrouter://demo/noPages"
    val externalUrl = "http://www.baidu.com"
    val dynamicUrl = "/dynamicActivity"
    val absoluteUrl = "/absoluteUrlActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(EasyRouter.getInstance()) {
            setRouterListener(object : EasyRouter.RouterListener {
                override fun onIntercept(url: String?): Boolean {
                    if (url != null && url.startsWith("http")) {
                        startActivity(Intent(this@MainActivity, WebViewActivity::class.java).apply {
                            putExtra("external_url", url!!)
                        })
                        return true
                    }
                    return false
                }

                override fun onLost(url: String?) {
                    startActivity(Intent(this@MainActivity, DegradeActivity::class.java).apply {
                        putExtra("error_msg", "没找到该${url!!}对应的页面")
                    })
                }

                override fun onFound(url: String?) {
                    Toast.makeText(this@MainActivity, "找到了", Toast.LENGTH_SHORT).show()
                }
            })
            addUrl(secondActivityUrl, SecondActivity::class.java)
            addUrl(paramsActivityUrl, WithParamsActivity::class.java)
        }

        btnGo2SecondActivity.setOnClickListener {
            EasyRouter.getInstance().goToPages(this, secondActivityUrl)
        }

        btnLoadUrl.setOnClickListener {
            EasyRouter.getInstance().goToPages(this, externalUrl)
        }

        btnWithParams.setOnClickListener {
            EasyRouter.getInstance().goToPages(this, "$paramsActivityUrl?firstname=wangli&secondname=li")
        }

        btnWithoutParams.setOnClickListener {
            EasyRouter.getInstance().goToPages(this, paramsActivityUrl)
        }

        btnDegradeNoPages.setOnClickListener {
            EasyRouter.getInstance().goToPages(this, noPageUrl)
        }

        btnErrorSchemeException.setOnClickListener {
            EasyRouter.getInstance().goToPages(this, errorUrl)
        }

        btnDynamicActivity.setOnClickListener {
            EasyRouter.getInstance().goToPages(this, dynamicUrl)
        }

        btnAbsoluteActivity.setOnClickListener {
            EasyRouter.getInstance().goToPages(this, absoluteUrl);
        }

    }
}
