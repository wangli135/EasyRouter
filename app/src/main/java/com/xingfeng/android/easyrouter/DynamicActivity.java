package com.xingfeng.android.easyrouter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.xingfeng.android.annotation.Path;

@Path("/dynamicActivity")
public class DynamicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic2);
    }
}
