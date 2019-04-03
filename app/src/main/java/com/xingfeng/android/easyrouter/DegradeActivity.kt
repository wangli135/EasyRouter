package com.xingfeng.android.easyrouter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_degrade.*

class DegradeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_degrade)

        tvErrorMsg.text = intent?.getStringExtra("error_msg")

    }
}
