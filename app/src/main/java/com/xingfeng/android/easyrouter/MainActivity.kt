package com.xingfeng.android.easyrouter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val secondActivityUrl = "easyrouter://demo/secondActivity"
    val paramsActivityUrl = "easyrouter://demo/paramsActivity"
    val noPageUrl = "easyrouter://demo/noPages"
    val externalUrl = "http://www.baidu.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnGo2SecondActivity.setOnClickListener {
            goToPages(this, secondActivityUrl)
        }

        btnLoadUrl.setOnClickListener {
            goToPages(this, externalUrl)
        }

        btnWithParams.setOnClickListener {
            goToPages(this, "$paramsActivityUrl?firstname=wangli&secondname=li")
        }

        btnWithoutParams.setOnClickListener {
            goToPages(this, paramsActivityUrl)
        }

        btnDegradeNoPages.setOnClickListener {
            goToPages(this, noPageUrl)
        }

        btnDegradeException.setOnClickListener {
            goToPages(this, "$paramsActivityUrl?firstname=wangli")
        }
    }
}
