package com.iavariav.root.pollingsarasehan.Helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.iavariav.root.pollingsarasehan.Activity.LoginActivity;

/**
 * Created by root on 12/20/17.
 */

public final class Config {

    public static final String BASE_URL_IMG = "http://suci.can.web.id/images/img/";
    public static final String SHARED_TITTLE = "POOLING";
    public static final String SHARED_NPM = "shared npm";
    public static final String SHARED_NPM1 = "shared npm1";
    public static final String SHARED_NAMA = "shared nama";
    public static final String SHARED_HWID = "shared_hwid";

    public static final String ERROR_MSG_MODEL_NULL = "Data Kosong";

    public static final String BUNDLE_NAMA_DOSEN = "nama_dosen";



    public static void forceLogoutternak(Context context) {
        //Getting out shared preferences
        SharedPreferences preferences = context.getSharedPreferences(Config.SHARED_TITTLE, Context.MODE_PRIVATE);
        //Getting editor
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Config.SHARED_NPM, "");
        editor.putString(Config.SHARED_NPM1, "");
        editor.putString(Config.SHARED_NAMA,"");
        editor.putString(Config.SHARED_HWID, "");

        //Saving the sharedpreferences
        editor.commit();

        //Starting login activity
        Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class);
        context.startActivity(intent);

    }

}
