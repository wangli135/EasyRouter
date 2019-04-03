package com.xingfeng.android.easyrouter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val secondActivityUrl = "easyrouter://demo/secondActivity"
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

    }
}
