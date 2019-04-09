package com.xingfeng.android.easyrouter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.xingfeng.android.annotation.Path

@Path("/secondActivity")
class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }
}
