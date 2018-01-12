package com.iavariav.root.pollingsarasehan.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.iavariav.root.pollingsarasehan.Activity.MhsdanKomentar.SurveyActivity;
import com.iavariav.root.pollingsarasehan.Helper.Config;
import com.iavariav.root.pollingsarasehan.R;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {
    private SliderLayout mSliderSlider;
    private PagerIndicator customIndicator;
    private PagerIndicator customIndicator2;
    private ViewPager viewPager;

    private LinearLayout divPoling;
    private LinearLayout divCekSuara;


    SharedPreferences sp;
    String npm;
    String nama;
    String hwid;

    private CircleImageView ciPoling;
    private CircleImageView ciCekSuara;
    private TextView tvNamadanNPM;
    private SliderLayout sliderSlider;
    private CardView cvPoling;
    private CardView cvSuara;
    private String NPM;
    private FloatingActionButton fbSukses;
    private CardView cvCekMahasiswa;
    private LinearLayout divCekMahasiswa;
    private CircleImageView ciCekMahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        getSupportActionBar().hide();
        initView();

        sp = getSharedPreferences(Config.SHARED_TITTLE, Context.MODE_PRIVATE);
        npm = sp.getString(Config.SHARED_NPM, "");
        NPM = sp.getString(Config.SHARED_NPM1, "");
        nama = sp.getString(Config.SHARED_NAMA, "");
        hwid = sp.getString(Config.SHARED_HWID, "");

//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//
//        NPM = bundle.getString("npm");


        if (NPM.equalsIgnoreCase("") || TextUtils.isEmpty(NPM)) {
//            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
//            startActivity(intent);
            cvPoling.setVisibility(View.VISIBLE);
        } else {
            cvPoling.setVisibility(View.GONE);
            cvSuara.setVisibility(View.VISIBLE);
        }

        tvNamadanNPM.setText(nama + " " + npm);

        HashMap<String, String> url_maps = new HashMap<String, String>();
        // * Get internet
        url_maps.put("Hannibal", "http://sdk.semarangkota.go.id/komunitas/logosdk.png");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://sdk.semarangkota.go.id/komunitas/logosdk.png");
        // * Get internet

        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Himpunan Mahasiswa Informatika", R.drawable.hm);
        file_maps.put("Semicolon", R.drawable.semicolon);
//        file_maps.put("Himpunan Mahasiswa Informatika", R.drawable.hm);
//        file_maps.put("Himpunan Mahasiswa Informatika", R.drawable.semicolon);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.FitCenterCrop)
                    .setOnSliderClickListener(this);
            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mSliderSlider.addSlider(textSliderView);
        }
        mSliderSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSliderSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSliderSlider.setCustomAnimation(new DescriptionAnimation());
        mSliderSlider.setDuration(4000);
        mSliderSlider.addOnPageChangeListener(this);


        cvPoling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(HomeActivity.this, "AAAAAAAAAAAA", Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("npm", npm);
                Intent intent = new Intent(getApplicationContext(), PolingActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        cvSuara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SurveyActivity.class));
            }
        });

        cvCekMahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DataMahasiswaActivity.class));
            }
        });

        fbSukses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Log Out", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Config.forceLogoutternak(HomeActivity.this);
//                isInternetOn();
                // Akan menampilkan poling
            }
        });
    }

    private void initView() {
        divPoling = (LinearLayout) findViewById(R.id.divPoling);
        divCekSuara = (LinearLayout) findViewById(R.id.divCekSuara);
        mSliderSlider = (SliderLayout) findViewById(R.id.sliderSlider);
        ciPoling = (CircleImageView) findViewById(R.id.ciPoling);
        ciCekSuara = (CircleImageView) findViewById(R.id.ciCekSuara);
        tvNamadanNPM = (TextView) findViewById(R.id.tvNamadanNPM);
        sliderSlider = (SliderLayout) findViewById(R.id.sliderSlider);
        cvPoling = (CardView) findViewById(R.id.cvPoling);
        cvSuara = (CardView) findViewById(R.id.cvSuara);
        fbSukses = (FloatingActionButton) findViewById(R.id.fbSukses);
        cvCekMahasiswa = (CardView) findViewById(R.id.cvCekMahasiswa);
        divCekMahasiswa = (LinearLayout) findViewById(R.id.divCekMahasiswa);
        ciCekMahasiswa = (CircleImageView) findViewById(R.id.ciCekMahasiswa);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

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
