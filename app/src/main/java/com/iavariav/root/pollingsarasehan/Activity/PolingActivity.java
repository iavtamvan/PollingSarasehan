package com.iavariav.root.pollingsarasehan.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.iavariav.root.pollingsarasehan.Helper.Config;
import com.iavariav.root.pollingsarasehan.Model.KategoriDosenModel;
import com.iavariav.root.pollingsarasehan.Model.UserModel;
import com.iavariav.root.pollingsarasehan.R;
import com.iavariav.root.pollingsarasehan.rest.ApiService;
import com.iavariav.root.pollingsarasehan.rest.Client;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PolingActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private Toolbar toolbar;
    private RadioGroup rgDosen;
    private RadioButton rbDosen1;
    private RadioButton rbDosen2;
    private RadioButton rbDosen3;
    private RadioButton rbDosen4;
    private RadioButton rbDosen5;
    private RadioButton rbDosen6;
    private RadioButton rbDosen7;
    private RadioButton rbDosen8;
    private Button btnSimpan;
    private FloatingActionButton fab;
    private ScrollView svList;

    boolean checked;
    private String namadosen, jabatandosen;
    private ProgressDialog loading;
    private LinearLayout divRb;
    private FloatingActionButton fbSukses;
    private LinearLayout divContainer;
    private LinearLayout divContainerAll;
    private LinearLayout divKoneksi;
    private TextView tvKoneksi;
    private TextView tvStatusLogin;
    private TextView tvNamaNpm;
    private TextView tvCek;

    private SharedPreferences sp;
    private String ID_DOS;
    private String NPM;

    private ArrayList<KategoriDosenModel> kategoriDosenModels;
    private ArrayList<UserModel> userModels;
    private SwipeRefreshLayout sr;

    private AlertDialog.Builder builder;
    private LayoutInflater inflater;
    private String varSaran, varKritik;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poling);
//        getSupportActionBar().setHomeButtonEnabled(true);
        initView();
        sp = getSharedPreferences(Config.SHARED_TITTLE, Context.MODE_PRIVATE);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        sr.setOnRefreshListener(this);

        isInternetOn();

//        NPM = bundle.getString("npm");
        NPM = sp.getString(Config.SHARED_NPM, "");
//        Toast.makeText(this, "Npm Bundle : " + NPM, Toast.LENGTH_SHORT).show();


        tvStatusLogin.setText(" Login " + sp.getString(Config.SHARED_NAMA, ""));
        tvNamaNpm.setText("NPM " + NPM);


        kategoriDosenModels = new ArrayList<>();
        userModels = new ArrayList<>();


//        isInternetOn();
//        getdata(true);
//        divContainerAll.setVisibility(View.VISIBLE);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Logged", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Config.forceLogoutternak(PolingActivity.this);
//                isInternetOn();
                // Akan menampilkan poling
            }
        });
        tvKoneksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isInternetOn();
            }
        });
    }

    private void getdata(boolean rm) {
        if (rm) {
            if (divContainer.getChildCount() > 0) divContainer.removeAllViews();
        }

        loading = ProgressDialog.show(PolingActivity.this, "", "Mengambil Data...", false, false);
        ApiService api = Client.getInstanceRetrofit();
        Call<ArrayList<KategoriDosenModel>> call = api.getDataKategoriDosen();
        call.enqueue(new Callback<ArrayList<KategoriDosenModel>>() {
            @Override
            public void onResponse(Call<ArrayList<KategoriDosenModel>> call, Response<ArrayList<KategoriDosenModel>> response) {
                kategoriDosenModels = response.body();
                for (int i = 0; i < kategoriDosenModels.size(); i++) {
                    LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View addView = layoutInflater.inflate(R.layout.list_rb_dosen, null);

                    final TextView tvRbIDdosen = (TextView) addView.findViewById(R.id.tvRbIDdosen);
                    tvRbIDdosen.setText(kategoriDosenModels.get(i).getID_DOS());
//                    Toast.makeText(PolingActivity.this, "TV" + tvRbIDdosen.getText().toString().trim(), Toast.LENGTH_SHORT).show();


                    final CircleImageView ciRbFotoDosen = (CircleImageView) addView.findViewById(R.id.ciRbFotoDosen);
                    Glide.with(getApplicationContext()).load(kategoriDosenModels.get(i).getFOTODOSEN())
                            .thumbnail(1f)
                            .crossFade()
                            .error(R.drawable.ic_status)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(ciRbFotoDosen);

//                    Toast.makeText(PolingActivity.this, "Responses " + kategoriDosenModels.get(i).getFOTODOSEN(), Toast.LENGTH_SHORT).show();
                    final RadioButton rbDosen = (RadioButton) addView.findViewById(R.id.rbNamaDosen);
                    rbDosen.setText(kategoriDosenModels.get(i).getNAMADOSEN());

                    final TextView tvJabatanDosen = (TextView) addView.findViewById(R.id.tvRbJabatanDosen);
                    tvJabatanDosen.setText(kategoriDosenModels.get(i).getJABATANDOSEN());

                    rbDosen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            divContainer.setVisibility(View.GONE);
                            namadosen = rbDosen.getText().toString().trim();
                            jabatandosen = tvJabatanDosen.getText().toString().trim();
                            ID_DOS = tvRbIDdosen.getText().toString().trim();

                            builder = new AlertDialog.Builder(PolingActivity.this);
                            inflater = LayoutInflater.from(PolingActivity.this);
                            final View dialView = inflater.inflate(R.layout.dialog_komentar, null);
                            final TextView tvKomentarAndaMemilihDosen = (TextView) dialView.findViewById(R.id.tvKomentarAndaMemilihDosen);
                            final EditText edtKomentar = (EditText) dialView.findViewById(R.id.edtKomentarSaran);
                            final EditText edtKomentarKritik = (EditText) dialView.findViewById(R.id.edtKomentarKritik);
                            final Button btnDialogKomentarTidak = (Button) dialView.findViewById(R.id.btnDialogKomentarTidak);
                            final Button btnDialogKomentarYa = (Button) dialView.findViewById(R.id.btnDialogKomentarYa);

                            tvKomentarAndaMemilihDosen.setText("Anda memilih dosen "+ namadosen + " apakah anda yakin ?");

                            btnDialogKomentarYa.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    varSaran = edtKomentar.getText().toString().trim();
                                    varKritik = edtKomentarKritik.getText().toString().trim();
                                    postData();
                                }
                            });

                            btnDialogKomentarTidak.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    finish();
                                    startActivity(new Intent(getBaseContext(), PolingActivity.class));
                                }
                            });

                            builder.setView(dialView);
                            builder.show();

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


                        }
                    });


                    divContainer.addView(addView);
                    loading.dismiss();
                    sr.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<KategoriDosenModel>> call, Throwable t) {
                loading.dismiss();
                sr.setRefreshing(false);
                Toast.makeText(PolingActivity.this, "Cek koneksi anda", Toast.LENGTH_SHORT).show();
                divKoneksi.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getdataonline() {
        ApiService apiService = Client.getInstanceRetrofit();
        Call<ArrayList<UserModel>> call = apiService.getUser();
        call.enqueue(new Callback<ArrayList<UserModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                userModels = response.body();
                Toast.makeText(PolingActivity.this, "Res : " + userModels, Toast.LENGTH_SHORT).show();
//                if (userModels == null){
//                    Toast.makeText(PolingActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                }
//                else {
                for (UserModel s : userModels) {
//                    LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    View view = layoutInflater.inflate(R.layout.list_transaksi_masuk, null);
                    if (s.getNama() != null && s.getNama().contains(sp.getString(Config.SHARED_NAMA, ""))) {
                        Toast.makeText(PolingActivity.this, "heh :" + sp.getString(Config.SHARED_NAMA, "") + " -- " + s.getNama(), Toast.LENGTH_SHORT).show();
                        if (sp.getString(Config.SHARED_NAMA, "").equalsIgnoreCase(s.getNama())) {
                            Toast.makeText(PolingActivity.this, "uhh", Toast.LENGTH_SHORT).show();
                            divContainer.setVisibility(View.GONE);
                            divKoneksi.setVisibility(View.VISIBLE);
                            tvCek.setText("Anda Sudah Terdaftar");
                            tvKoneksi.setText("Cek data");
                            tvKoneksi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // do something
                                }
                            });
                        } else {
                            getdata(true);
                            divKoneksi.setVisibility(View.GONE);
                            divContainer.setVisibility(View.VISIBLE);
                        }
                    }
                }
//                }


            }

            @Override
            public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {
                Toast.makeText(PolingActivity.this, "Gagal Load", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onclickradio(View view) {
        checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rbDosen1:
                if (checked) {
                    namadosen = rbDosen1.getText().toString().trim();
                    Toast.makeText(this, "Hasil " + namadosen, Toast.LENGTH_SHORT).show();
                    builder = new AlertDialog.Builder(PolingActivity.this);
                    inflater = LayoutInflater.from(PolingActivity.this);
                    final View dialView = inflater.inflate(R.layout.dialog_komentar, null);
                    final EditText edtKomentar = (EditText) dialView.findViewById(R.id.edtKomentarSaran);
                    final Button btnDialogKomentarTidak = (Button) dialView.findViewById(R.id.btnDialogKomentarTidak);
                    final Button btnDialogKomentarYa = (Button) dialView.findViewById(R.id.btnDialogKomentarYa);

                    btnDialogKomentarYa.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            varSaran = edtKomentar.getText().toString().trim();
                            postData();
                        }
                    });

                    btnDialogKomentarTidak.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    });

                    builder.setView(dialView);
                    builder.show();

                    break;
                }
            case R.id.rbDosen2:
                if (checked) {
                    namadosen = rbDosen2.getText().toString().trim();
                    Toast.makeText(this, "Hasil " + namadosen, Toast.LENGTH_SHORT).show();
                    postData();
                    break;
                }
            case R.id.rbDosen3:
                if (checked) {
                    namadosen = rbDosen3.getText().toString().trim();
                    Toast.makeText(this, "Hasil " + namadosen, Toast.LENGTH_SHORT).show();
                    postData();
                    break;
                }
            case R.id.rbDosen4:
                if (checked) {
                    namadosen = rbDosen4.getText().toString().trim();
                    Toast.makeText(this, "Hasil " + namadosen, Toast.LENGTH_SHORT).show();
                    postData();
                    break;
                }
            case R.id.rbDosen5:
                if (checked) {
                    namadosen = rbDosen5.getText().toString().trim();
                    Toast.makeText(this, "Hasil " + namadosen, Toast.LENGTH_SHORT).show();
                    postData();
                    break;
                }
            case R.id.rbDosen6:
                if (checked) {
                    namadosen = rbDosen6.getText().toString().trim();
                    Toast.makeText(this, "Hasil " + namadosen, Toast.LENGTH_SHORT).show();
                    postData();
                    break;
                }
            case R.id.rbDosen7:
                if (checked) {
                    namadosen = rbDosen7.getText().toString().trim();
                    Toast.makeText(this, "Hasil " + namadosen, Toast.LENGTH_SHORT).show();
                    postData();
                    break;
                }
            case R.id.rbDosen8:
                if (checked) {
                    namadosen = rbDosen8.getText().toString().trim();
                    Toast.makeText(this, "Hasil " + namadosen, Toast.LENGTH_SHORT).show();
                    postData();
                    break;
                }
        }
    }

    private void postData() {
        loading = ProgressDialog.show(PolingActivity.this, "", "Mengirim Data...", false, false);
        ApiService api = Client.getInstanceRetrofit();
        api.postDosenPoling(namadosen, jabatandosen, "1", NPM, varSaran, varKritik, ID_DOS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
//                            Toast.makeText(PolingActivity.this, "Res 1" + response, Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                            divContainer.setVisibility(View.INVISIBLE); // kudune urip
                            SharedPreferences sharedPreferences = PolingActivity.this.getSharedPreferences(Config.SHARED_TITTLE, Context.MODE_PRIVATE);

                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adding values to editor
//                            editor.putBoolean(Config.SHARED_TITTLE, true);
                            editor.putString(Config.SHARED_NPM1, NPM);
                            editor.commit();

                            Toast.makeText(PolingActivity.this, "Sukses Survey", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            finishAffinity();
//                            try {
//                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
//                                Toast.makeText(PolingActivity.this, "Json res " + jsonRESULTS.getString("error_msg"), Toast.LENGTH_SHORT).show();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//
//
//                            try {
//                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
//                                Toast.makeText(PolingActivity.this, "Result " + jsonRESULTS, Toast.LENGTH_SHORT).show();
//                                if (jsonRESULTS.getString("error_msg").equalsIgnoreCase("Data tersimpan")){
//                                    String responsesNPM = jsonRESULTS.getString("NPM");
//                                    divContainer.setVisibility(View.INVISIBLE); // kudune urip
//                                    Toast.makeText(PolingActivity.this, "Result NPM " + responsesNPM, Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(PolingActivity.this, "Sukses Tambah", Toast.LENGTH_SHORT).show();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            divContainerAll.setVisibility(View.INVISIBLE);

                        } else {
                            loading.dismiss();
//                            divContainerAll.setVisibility(View.VISIBLE);
                            Toast.makeText(PolingActivity.this, "Gagal Tambah", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        loading.dismiss();
                        divKoneksi.setVisibility(View.VISIBLE);
                        Toast.makeText(PolingActivity.this, "Cek koneksi anda", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initView() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rgDosen = (RadioGroup) findViewById(R.id.rgDosen);
        rbDosen1 = (RadioButton) findViewById(R.id.rbDosen1);
        rbDosen2 = (RadioButton) findViewById(R.id.rbDosen2);
        rbDosen3 = (RadioButton) findViewById(R.id.rbDosen3);
        rbDosen4 = (RadioButton) findViewById(R.id.rbDosen4);
        rbDosen5 = (RadioButton) findViewById(R.id.rbDosen5);
        rbDosen6 = (RadioButton) findViewById(R.id.rbDosen6);
        rbDosen7 = (RadioButton) findViewById(R.id.rbDosen7);
        rbDosen8 = (RadioButton) findViewById(R.id.rbDosen8);
        svList = (ScrollView) findViewById(R.id.svList);
        btnSimpan = (Button) findViewById(R.id.btnSimpan);
        fab = (FloatingActionButton) findViewById(R.id.fbSukses);
        divRb = (LinearLayout) findViewById(R.id.divRb);
        fbSukses = (FloatingActionButton) findViewById(R.id.fbSukses);
        divContainer = (LinearLayout) findViewById(R.id.divContainer);
        divContainerAll = (LinearLayout) findViewById(R.id.divContainerAll);
        divKoneksi = (LinearLayout) findViewById(R.id.divKoneksi);
        tvKoneksi = (TextView) findViewById(R.id.tvKoneksi);
        tvStatusLogin = (TextView) findViewById(R.id.tvStatusLogin);
        tvNamaNpm = (TextView) findViewById(R.id.tvNamaNpm);
        tvCek = (TextView) findViewById(R.id.tvCek);


        sr = (SwipeRefreshLayout) findViewById(R.id.sr);
    }

    public final boolean isInternetOn() {
        loading = ProgressDialog.show(PolingActivity.this, "", "Check Connection...", false, false);

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
            loading.dismiss();
            divKoneksi.setVisibility(View.GONE);
            divContainer.setVisibility(View.VISIBLE);
//            divContainer.setVisibility(View.VISIBLE);
            getdata(true);
//            getdataonline();
//            Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {
            loading.dismiss();
            divContainer.setVisibility(View.GONE);
            divKoneksi.setVisibility(View.VISIBLE);
//            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

//    @Override
//    protected void onResume() {
//
//
//        super.onResume();
//    }

    @Override
    public void onRefresh() {
        isInternetOn();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        divContainer.setVisibility(View.VISIBLE);
//        getdata(true);
//    }
    //    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        isInternetOn();
//        super.onSaveInstanceState(outState, outPersistentState);
//    }
//
//    @Override
//    protected void onRestart() {
//        isInternetOn();
//        super.onRestart();
//    }

//    @Override
//    protected void onPause() {
//        isInternetOn();
//        super.onPause();
//    }
}
