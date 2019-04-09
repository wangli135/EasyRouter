package com.xingfeng.android.easyrouter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.xingfeng.android.annotation.Path
import kotlinx.android.synthetic.main.activity_with_params.*

@Path("/paramsActivity")
class WithParamsActivity : AppCompatActivity() {

    val FIRST_NAME_KEY = "firstname"
    val SECOND_NAME_KEY = "secondname"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_params)

        intent.extras?.apply {
            tvFirstName.text = getString(FIRST_NAME_KEY)
            tvSecondName.text = getString(SECOND_NAME_KEY)
        }

    }
}
