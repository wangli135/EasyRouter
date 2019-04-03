package com.xingfeng.android.other_module;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.xingfeng.android.annotation.Path;

@Path("/otherMoudleActivity")
public class OtherModuleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_module);
    }
}
