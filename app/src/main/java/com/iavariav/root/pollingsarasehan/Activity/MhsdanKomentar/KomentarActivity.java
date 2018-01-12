package com.iavariav.root.pollingsarasehan.Activity.MhsdanKomentar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iavariav.root.pollingsarasehan.Activity.PolingActivity;
import com.iavariav.root.pollingsarasehan.Helper.Config;
import com.iavariav.root.pollingsarasehan.Model.PolingSuaraModel;
import com.iavariav.root.pollingsarasehan.R;
import com.iavariav.root.pollingsarasehan.rest.ApiService;
import com.iavariav.root.pollingsarasehan.rest.Client;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KomentarActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    // String Bundle
    private String namaDosen;
    // String Bundle

    private ArrayList<PolingSuaraModel> polingSuaraModels;
    private LinearLayout div;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komentar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        polingSuaraModels = new ArrayList<>();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        namaDosen = bundle.getString(Config.BUNDLE_NAMA_DOSEN);
        Toast.makeText(this, "Hasil : " + namaDosen, Toast.LENGTH_SHORT).show();

        getdatKomentarDariMhs(true);

    }

    private void getdatKomentarDariMhs(boolean r) {
        loading = ProgressDialog.show(KomentarActivity.this, "", "Mengambil Data...", false, false);
        if(r)    {
            if(div.getChildCount() > 0)    div.removeAllViews();
        }
        ApiService apiService = Client.getInstanceRetrofit();
        Call<ArrayList<PolingSuaraModel>> call = apiService.getDataKomentarMhs();
        call.enqueue(new Callback<ArrayList<PolingSuaraModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PolingSuaraModel>> call, Response<ArrayList<PolingSuaraModel>> response) {
                polingSuaraModels = response.body();
                if (polingSuaraModels == null) {
                    loading.dismiss();
                    Toast.makeText(KomentarActivity.this, Config.ERROR_MSG_MODEL_NULL, Toast.LENGTH_SHORT).show();
                } else {
                    for (PolingSuaraModel s : polingSuaraModels) {
                        if (s.getNAMADOSEN() != null && s.getNAMADOSEN().contains(namaDosen)) {
                            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View addView = layoutInflater.inflate(R.layout.list_komentar_mhs, null);

                            Toast.makeText(KomentarActivity.this, "Hasil : " + s.getSARAN(), Toast.LENGTH_SHORT).show();

                            final TextView tvListKomentarDari = (TextView) addView.findViewById(R.id.tvListKomentarDari);
                            tvListKomentarDari.setText("Dari : " +s.getNPM());

                            final TextView tvKomentarSaran = (TextView) addView.findViewById(R.id.tvKomentarSaran);
                            tvKomentarSaran.setText(s.getSARAN());

                            final TextView tvKomentarKRITIK = (TextView) addView.findViewById(R.id.tvKomentarKRITIK);
                            tvKomentarKRITIK.setText(s.getKRITIK());

                            final TextView tvListKomentarUntuk = (TextView) addView.findViewById(R.id.tvListKomentarUntuk);
                            tvListKomentarUntuk.setText("Untuk : " + namaDosen);


                            div.addView(addView);
                        }
                    }
                    loading.dismiss();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<PolingSuaraModel>> call, Throwable t) {

            }
        });
    }

    private void initView() {
        div = (LinearLayout) findViewById(R.id.div);
    }

    @Override
    public void onRefresh() {
        getdatKomentarDariMhs(true);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
