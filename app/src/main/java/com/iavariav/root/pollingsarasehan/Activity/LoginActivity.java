package com.iavariav.root.pollingsarasehan.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.iavariav.root.pollingsarasehan.Helper.Config;
import com.iavariav.root.pollingsarasehan.Model.UserModel;
import com.iavariav.root.pollingsarasehan.R;
import com.iavariav.root.pollingsarasehan.rest.ApiService;
import com.iavariav.root.pollingsarasehan.rest.Client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText edtusername, edtpassword, edtHwid;
    private Button btnLogin;
    ArrayList<UserModel> UserModels;
    private String email, password;
    private TextView tvHwid;
    private ProgressDialog loading;
    private LinearLayout div;

    private Random r = new Random();
    private int i = r.nextInt(1000);
    private String id;

    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        edtusername = (EditText) findViewById(R.id.edtUsername);
        edtpassword = (EditText) findViewById(R.id.edtPassword);
        edtHwid = (EditText) findViewById(R.id.edtHwid);
        tvHwid = (TextView) findViewById(R.id.tvHwid);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        UserModels = new ArrayList<>();
        div = (LinearLayout)findViewById(R.id.div);

//        getdummy();
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        isInternetOn();


//        loading = ProgressDialog.show(LoginActivity.this, "Loading", "Geeting ID...", false, false);
//        loadCrack();

//        edtHwid.setEnabled(false);
        edtHwid.setFocusable(false);

        edtHwid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                LayoutInflater inflater = LayoutInflater.from(LoginActivity.this);
                final View dialView = inflater.inflate(R.layout.dialog_box, null);


                builder.setView(dialView);
                builder.show();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtusername.getText().toString().trim().isEmpty()) {
                    edtusername.setError("Isi NPM");
                } else if (edtpassword.getText().toString().trim().isEmpty()) {
                    edtpassword.setError("Isi Nama");
                } else {
                    posData();
                }
            }
        });
    }


    private void getdummy() {
        for (int i = 0; i<100; i++){
            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View addView = layoutInflater.inflate(R.layout.list_ads, null);

            final AdView adView = (AdView) addView.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);

            div.addView(addView);

        }
    }

    private void posData() {
        loading = ProgressDialog.show(LoginActivity.this, "Loading", "Login MHS...", false, false);
        ApiService api = Client.getInstanceRetrofit();
        api.postLogin(edtusername.getText().toString().trim(),
                edtpassword.getText().toString().trim(),
                edtHwid.getText().toString().trim())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.isSuccessful()){
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                String statusLogin = jsonObject.getString("error_msg");

                                if (statusLogin.contains("Data tersimpan")){
                                    loading.dismiss();


                                    Toast.makeText(LoginActivity.this, "Login Sukses" , Toast.LENGTH_SHORT).show();
                                    //Creating a shared preference
                                    SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_TITTLE, Context.MODE_PRIVATE);

                                    //Creating editor to store values to shared preferences
                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    //Adding values to editor
                                    editor.putBoolean(Config.SHARED_TITTLE, true);
                                    editor.putString(Config.SHARED_NPM, edtusername.getText().toString().trim());
                                    editor.putString(Config.SHARED_NAMA, edtpassword.getText().toString().trim());
//                            editor.putString(Config.SHARED_HWID, edtHwid.getText().toString().trim());
                                    //Saving values to editor
                                    editor.commit();

                                    Bundle bundle = new Bundle();
                                    bundle.putString("npm", edtusername.getText().toString().trim());
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finishAffinity();
                                } else if (statusLogin.contains("Data Sudah Ada")){
                                    loading.dismiss();
                                    Toast.makeText(LoginActivity.this, statusLogin, Toast.LENGTH_SHORT).show();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }



                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        loading.dismiss();
                        Toast.makeText(LoginActivity.this, "Cek koneksi anda", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void loadCrack() {
        TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String jm = tManager.getDeviceId().toString();
        String sub = tManager.getSubscriberId().toString();
        String nm = tManager.getSimOperator().toString();
        String deviceId = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        String deviceId1 = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
//        Toast.makeText(this, "Loaded ID", Toast.LENGTH_SHORT).show();
        @SuppressLint("WifiManagerLeak") WifiManager mac = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = mac.getConnectionInfo();
        String address = info.getMacAddress();

//        tvHwid.setText(deviceId + " Iav " + deviceId1 + "k09145" + "==" + jm + sub + (deviceId + deviceId1) + (jm + deviceId1) + nm + i + (jm + sub + deviceId + sub)
//                + (sub + jm) + address);
        
        id = deviceId + "iav" + jm + sub + deviceId1 + address + nm;
        edtHwid.setText(deviceId + "iav" + jm + sub + deviceId1  + nm);
    }

    public final boolean isInternetOn() {
        loading = ProgressDialog.show(LoginActivity.this, "", "Check Connection...", false, false);

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            loading.dismiss();
//            Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
//            btnLogin.setEnabled(true);
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
            loading.dismiss();
            Toast.makeText(this, "Internet Not Connected ", Toast.LENGTH_LONG).show();
//            btnLogin.setEnabled(false);
            return false;
        }
        return false;
    }
    private void ambildata() {
        ApiService api = Client.getInstanceRetrofit();
        Call<ArrayList<UserModel>> call = api.getUser();
        call.enqueue(new Callback<ArrayList<UserModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                UserModels = response.body();
                for (UserModel s : UserModels) {
                    if (s.getNama() != null &&
                            s.getNama().contains
                                    (edtusername.getText()
                                            .toString().trim())) {
//                        npm = s.getEmail();
//                        password = s.getPassword();
//                        if (edtusername.getText().toString().equalsIgnoreCase(npm)
//                                && edtpassword.getText().toString()
//                                .equalsIgnoreCase(password)) {
//                            Toast.makeText(LoginActivity.this, "Data Ada "
//                                            + s.getNama(),
//                                    Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(LoginActivity.this, "Data kosong",
//                                    Toast.LENGTH_SHORT).show();
//                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Internet anda belum bayar", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    @Override
//    protected void onPause() {
//        isInternetOn();
//        super.onPause();
//    }

    @Override
    protected void onStart() {
        isInternetOn();
        super.onStart();
    }

    @Override
    protected void onResume() {
        isInternetOn();
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        isInternetOn();
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestart() {
        isInternetOn();
        super.onRestart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        isInternetOn();
        super.onSaveInstanceState(outState, outPersistentState);

    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Tutup Aplikasi")
                .setMessage("Apakah anda ingin menutup aplikasi ?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(startMain);
                    }

                })
                .setNegativeButton("Tidak", null)
                .show();
    }
}
