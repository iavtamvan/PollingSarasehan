package com.iavariav.root.pollingsarasehan.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.iavariav.root.pollingsarasehan.Model.KategoriDosenModel;
import com.iavariav.root.pollingsarasehan.Model.SurveyModel;
import com.iavariav.root.pollingsarasehan.R;
import com.iavariav.root.pollingsarasehan.rest.ApiService;
import com.iavariav.root.pollingsarasehan.rest.Client;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveyActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private LinearLayout div;
    private ArrayList<SurveyModel> surveyModels;
    private ArrayList<KategoriDosenModel> kategoriDosenModels;


    private String febrian, ning, beng, aris, ifah, noora, mega, roby, nugroho;
    private ProgressDialog loading;
    private SwipeRefreshLayout sr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        surveyModels = new ArrayList<>();
        kategoriDosenModels = new ArrayList<>();
        getdataSurvey(true);

        sr.setOnRefreshListener(this);



    }

    private void getdata(boolean rm) {

        if (rm) {
            if (div.getChildCount() > 0) div.removeAllViews();
        }

        loading = ProgressDialog.show(SurveyActivity.this, "", "Mengambil Data...", false, false);
        ApiService api = Client.getInstanceRetrofit();
        Call<ArrayList<KategoriDosenModel>> call = api.getDataKategoriDosen();
        call.enqueue(new Callback<ArrayList<KategoriDosenModel>>() {
            @Override
            public void onResponse(Call<ArrayList<KategoriDosenModel>> call, Response<ArrayList<KategoriDosenModel>> response) {
                kategoriDosenModels = response.body();
                for (int i = 0; i < kategoriDosenModels.size(); i++) {
                    LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View addView = layoutInflater.inflate(R.layout.list_survey_dosen, null);

                    final CircleImageView ciSurveyDosen = (CircleImageView) addView.findViewById(R.id.ciSurveyDosen);
                    Glide.with(getApplicationContext()).load(kategoriDosenModels.get(i).getFOTODOSEN())
                            .thumbnail(1f)
                            .crossFade()
                            .error(R.drawable.errorman)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(ciSurveyDosen);


                    final TextView tvSurveyNamaDosen = (TextView) addView.findViewById(R.id.tvSurveyNamaDosen);
                    tvSurveyNamaDosen.setText(kategoriDosenModels.get(i).getNAMADOSEN());


                    final TextView tvRSurveySuaraDosen = (TextView) addView.findViewById(R.id.tvRSurveySuaraDosen);


                    if (surveyModels == null) {
                        loading.dismiss();
                        Toast.makeText(SurveyActivity.this, "Cek Koneksi Anda", Toast.LENGTH_SHORT).show();
                        return;
                    } else {

                        if (surveyModels == null) {
                            getdataSurvey(true);
                            return;
                        } else {
                            if (tvSurveyNamaDosen.getText().toString()
                                    .contains("F.M. DEWANTO S.E, M.KOM.")) {
                                tvRSurveySuaraDosen.setText(surveyModels.get(i).getFMDEWANTOSEMKOM() + " Suara");
                            }
                            if (tvSurveyNamaDosen.getText().toString()
                                    .contains("SETYONINGSIH WIBOWO, S.T., M.KOM.")) {
                                tvRSurveySuaraDosen.setText(surveyModels.get(i).getSETYONINGSIHWIBOWOSTMKOM() + " Suara");
                            }
                            if (tvSurveyNamaDosen.getText().toString()
                                    .contains("B.A. HERLAMBANG")) {
                                tvRSurveySuaraDosen.setText(surveyModels.get(i).getBAHERLAMBANG() + " Suara");
                            }
                            if (tvSurveyNamaDosen.getText().toString()
                                    .contains("ARIS TRI J.H., S.KOM.,M.KOM.")) {
                                tvRSurveySuaraDosen.setText(surveyModels.get(i).getARISTRIJHSKOMMKOM() + " Suara");
                            }
                            if (tvSurveyNamaDosen.getText().toString()
                                    .contains("KHOIRIYA LATIFAH, S.KOM., M.KOM.")) {
                                tvRSurveySuaraDosen.setText(surveyModels.get(i).getKHOIRIYALATIFAHSKOMMKOM() + " Suara");
                            }
                            if (tvSurveyNamaDosen.getText().toString()
                                    .contains("NOORA Q.N., S.T., M.ENG.")) {
                                tvRSurveySuaraDosen.setText(surveyModels.get(i).getNOORAQNSTMENG() + " Suara");
                            }
                            if (tvSurveyNamaDosen.getText().toString()
                                    .contains("Mega Novita S.Si.,M.Si.,M.Nat.Sc.,P.hd")) {
                                tvRSurveySuaraDosen.setText(surveyModels.get(i).getMegaNovitaSSiMSiMNatScPHd() + " Suara");
                            }
                            if (tvSurveyNamaDosen.getText().toString()
                                    .contains("Rahmat Robi Waliyansyah")) {
                                tvRSurveySuaraDosen.setText(surveyModels.get(i).getRahmatRobiWaliyansyah() + " Suara");
                            }
                            if (tvSurveyNamaDosen.getText().toString()
                                    .contains("Nugroho D.S")) {
                                tvRSurveySuaraDosen.setText(surveyModels.get(i).getNugrohoDS() + " Suara");
                            }
                        }
                    }


//                            final AlertDialog.Builder builder = new AlertDialog.Builder(PolingActivity.this);
//
//                            builder.setTitle("Apakah anda yakin ?");
//                            builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    postData();
//                                }
//                            });
//
//                            builder.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    if (rbDosen.isChecked()){
//                                        finishAffinity();
//                                    } else {
//
//                                    }
//                                }
//                            });


                    div.addView(addView);
                    loading.dismiss();
                    sr.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<KategoriDosenModel>> call, Throwable t) {
                loading.dismiss();
                sr.setRefreshing(false);
                Toast.makeText(SurveyActivity.this, "Cek koneksi anda", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getdataSurvey(boolean rm) {
        if (rm) {
            if (div.getChildCount() > 0) div.removeAllViews();
        }
        loading = ProgressDialog.show(SurveyActivity.this, "", "Mengambil Data Survey...", false, false);
        ApiService apiService = Client.getInstanceRetrofit();
        Call<ArrayList<SurveyModel>> call = apiService.getDataSurveyDosen();
        call.enqueue(new Callback<ArrayList<SurveyModel>>() {
            @Override
            public void onResponse(Call<ArrayList<SurveyModel>> call, Response<ArrayList<SurveyModel>> response) {
                surveyModels = response.body();
                for (int i = 0; i < surveyModels.size(); i++) {

                    febrian = surveyModels.get(i).getFMDEWANTOSEMKOM();
                    ning = surveyModels.get(i).getSETYONINGSIHWIBOWOSTMKOM();
                    beng = surveyModels.get(i).getBAHERLAMBANG();
                    ifah = surveyModels.get(i).getKHOIRIYALATIFAHSKOMMKOM();
                    noora = surveyModels.get(i).getNOORAQNSTMENG();
                    mega = surveyModels.get(i).getMegaNovitaSSiMSiMNatScPHd();
                    roby = surveyModels.get(i).getRahmatRobiWaliyansyah();
                    nugroho = surveyModels.get(i).getNugrohoDS();

                }
                loading.dismiss();
                getdata(true);
            }

            @Override
            public void onFailure(Call<ArrayList<SurveyModel>> call, Throwable t) {
                loading.dismiss();
                sr.setRefreshing(false);
            }
        });
    }

    private void initView() {
        div = (LinearLayout) findViewById(R.id.div);
        sr = (SwipeRefreshLayout) findViewById(R.id.sr);
    }

    @Override
    public void onRefresh() {
        getdataSurvey(true);
//        getdata(true);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
