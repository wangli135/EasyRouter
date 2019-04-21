package com.xingfeng.android.easyrouter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    public static boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.btnLogin).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isLogin = true;
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                }
        );
    }
}
