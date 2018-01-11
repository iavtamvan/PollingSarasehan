package com.iavariav.root.pollingsarasehan.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.iavariav.root.pollingsarasehan.Helper.Config;
import com.iavariav.root.pollingsarasehan.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp = getSharedPreferences(Config.SHARED_TITTLE, Context.MODE_PRIVATE);
                String email = sp.getString(Config.SHARED_NPM,"");
                String rulename = sp.getString(Config.SHARED_NAMA,"");
                String hwid = sp.getString(Config.SHARED_HWID,"");
                if(rulename.equalsIgnoreCase("") || TextUtils.isEmpty(rulename)) {
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 4000);
    }
}
