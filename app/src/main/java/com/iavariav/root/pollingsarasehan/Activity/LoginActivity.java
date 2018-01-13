package com.iavariav.root.pollingsarasehan.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.iavariav.root.pollingsarasehan.Activity.Camera.CameraPreview;
import com.iavariav.root.pollingsarasehan.Helper.Config;
import com.iavariav.root.pollingsarasehan.Model.Result;
import com.iavariav.root.pollingsarasehan.Model.UserModel;
import com.iavariav.root.pollingsarasehan.R;
import com.iavariav.root.pollingsarasehan.permission.INternetConnection;
import com.iavariav.root.pollingsarasehan.permission.PermissinCheker;
import com.iavariav.root.pollingsarasehan.permission.permissionActivity;
import com.iavariav.root.pollingsarasehan.rest.ApiService;
import com.iavariav.root.pollingsarasehan.rest.Client;
import com.iavariav.root.pollingsarasehan.rest.restImaget.APIService;
import com.iavariav.root.pollingsarasehan.rest.restImaget.RetroClient;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText edtusername, edtpassword, edtHwid, edtKelas;
    private Button btnLogin;
    private Button btnUpload;
    ArrayList<UserModel> UserModels;
    private String email, password;
    private TextView tvHwid;
    private ProgressDialog loading;
    private LinearLayout div;

    private Random r = new Random();
    private int i = r.nextInt(1000);
    private String id;

    private AdView mAdView;

    // Camera
    private Camera mCamera;
    private CameraPreview mPreview;
    private FrameLayout preview;
    private static Context con;
    private String TAG;
    private final static int CAMERA_PIC_REQUEST1 = 0;



    private static final String[] PERMISSION_READ_STORAGE = new String[] {Manifest.permission.READ_EXTERNAL_STORAGE};

    Context context;

    View parentView;
    ImageView imageVi;
    TextView te;

    String imagePath;

    PermissinCheker permissinCheker;
    private String h;
    private int MY_PERMISSIONS_REQUEST_READ_EXTERNAL = 1;
    // Camera


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        edtusername = (EditText) findViewById(R.id.edtUsername);
        edtpassword = (EditText) findViewById(R.id.edtPassword);
        edtKelas = (EditText) findViewById(R.id.edtKelas);
        edtHwid = (EditText) findViewById(R.id.edtHwid);
        tvHwid = (TextView) findViewById(R.id.tvHwid);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        preview = (FrameLayout) findViewById(R.id.camera_preview);
        UserModels = new ArrayList<>();
        div = (LinearLayout)findViewById(R.id.div);
// Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        context = getApplicationContext();

        parentView = findViewById(R.id.vvvv);

        permissinCheker = new PermissinCheker(this);

        te = (TextView)findViewById(R.id.textView);
        imageVi= (ImageView) findViewById(R.id.imageView);


//        getdummy();
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
//        isInternetOn();
        con = getApplicationContext();
//        try {
//            mCamera = getCameraInstance();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        mPreview = new CameraPreview(this, mCamera);
//
//        preview.addView(mPreview);

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
                }
                if (edtpassword.getText().toString().trim().isEmpty()) {
                    edtpassword.setError("Isi Nama");
                }
                if (edtKelas.getText().toString().trim().isEmpty()){
                    edtKelas.setError("Isi Kelas");
                }
                if (TextUtils.isEmpty(h)){
                    Toast.makeText(LoginActivity.this, "Pilih Foto Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                }
                else {
                    posData();
                }
//                mCamera.takePicture(null, null, mPicture);



//                uploadImage();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(imagePath)) {

                    if (INternetConnection.checkConnection(context)) {

                    } else {
                        Snackbar.make(parentView, "warning notice SnackBar", Snackbar.LENGTH_SHORT).show();
                    }
                }else {
                    Snackbar.make(parentView, "lampirkan file untuk di unggah", Snackbar.LENGTH_SHORT).show();
                }
//                h = new File(imagePath).getName();
//                if (h == null){
//                    Toast.makeText(LoginActivity.this, "Result " + h, Toast.LENGTH_SHORT).show();
//                }else {
//                    uploadImage();
//                }



            }
        });
    }


    // Code Camera
//    Bitmap bitmap;
//    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
//
//        @Override
//        public void onPictureTaken(byte[] data, Camera camera) {
//
//            System.gc();
//            bitmap = null;
//            BitmapWorkerTask task = new BitmapWorkerTask(data);
//            task.execute(0);
//        }
//    };
//
//    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
//        private final WeakReference<byte[]> dataf;
//        private int data = 0;
//
//        public BitmapWorkerTask(byte[] imgdata) {
//            // Use a WeakReference to ensure the ImageView can be garbage
//            // collected
//            dataf = new WeakReference<byte[]>(imgdata);
//        }
//
//        // Decode image in background.
//        @Override
//        protected Bitmap doInBackground(Integer... params) {
//            data = params[0];
//            ResultActivity(dataf.get());
//            return mainbitmap;
//        }
//
//        // Once complete, see if ImageView is still around and set bitmap.
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            saving();
////            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmddhhmmss");
////            File pictureDir = getDir();
////            String date = simpleDateFormat.format(new Date());
////            String savingPoto = "pict_" + date + ".png";
////            imagePath = pictureDir.getPath() + File.separator + savingPoto;
////            File file = new File(imagePath);
////            try{
////                FileOutputStream fileOutputStream = new FileOutputStream(file);
////                fileOutputStream.write(data);
////                fileOutputStream.close();
////                Toast.makeText(LoginActivity.this, "Berhasil Save + " + imagePath, Toast.LENGTH_SHORT).show();
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////            saving();
////            if (mainbitmap != null) {
////
////                Intent i = new Intent();
////                i.putExtra("BitmapImage", mainbitmap);
////                setResult(-1, i);
////                // Here I am Setting the Requestcode 1, you can put according to
////                // your requirement
//////                finish(); // ini buat ngelempar ke activity sebelumnya
////
////            }
//        }
//    }
//    int requestCode;
//    private void saving() {
//        Toast.makeText(LoginActivity.this, "Konnect", Toast.LENGTH_SHORT).show();
//        if (requestCode == CAMERA_PIC_REQUEST1){
//            ivResult.setImageBitmap(mainbitmap);
//        }
//        else {
//            Toast.makeText(LoginActivity.this, "Poto Galat", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    Bitmap mainbitmap;
//
//    public void ResultActivity(byte[] data) {
//        mainbitmap = null;
//        mainbitmap = decodeSampledBitmapFromResource(data, 1000, 1000);
//        mainbitmap=RotateBitmap(mainbitmap,270);
//        mainbitmap=flip(mainbitmap);
//    }
//
//    public static Bitmap decodeSampledBitmapFromResource(byte[] data,
//                                                         int reqWidth, int reqHeight) {
//
//        // First decode with inJustDecodeBounds=true to check dimensions
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        // BitmapFactory.decodeResource(res, resId, options);
//        BitmapFactory.decodeByteArray(data, 0, data.length, options);
//
//        // Calculate inSampleSize
//        options.inSampleSize = calculateInSampleSize(options, reqWidth,
//                reqHeight);
//
//        // Decode bitmap with inSampleSize set
//        options.inJustDecodeBounds = false;
//        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
//    }
//
//    public static int calculateInSampleSize(BitmapFactory.Options options,
//                                            int reqWidth, int reqHeight) {
//        // Raw height and width of image
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//        int inSampleSize = 1;
//
//        if (height > reqHeight || width > reqWidth) {
//            if (width > height) {
//                inSampleSize = Math.round((float) height / (float) reqHeight);
//            } else {
//                inSampleSize = Math.round((float) width / (float) reqWidth);
//            }
//        }
//        return inSampleSize;
//    }
//
//    /** A safe way to get an instance of the Camera object. */
//    public static Camera getCameraInstance() {
//        Camera c = null;
//        Log.d("No of cameras", Camera.getNumberOfCameras() + "");
//        for (int camNo = 0; camNo < Camera.getNumberOfCameras(); camNo++) {
//            Camera.CameraInfo camInfo = new Camera.CameraInfo();
//            Camera.getCameraInfo(camNo, camInfo);
//
//            if (camInfo.facing == (Camera.CameraInfo.CAMERA_FACING_FRONT)) {
//                c = Camera.open(camNo);
//                c.setDisplayOrientation(90);
//            }
//        }
//        return c; // returns null if camera is unavailable
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        releaseCamera(); // release the camera immediately on pause event
//    }
//
//    private void releaseCamera() {
//        if (mCamera != null) {
//            mCamera.release(); // release the camera for other applications
//            mCamera = null;
//        }
//    }
//
//    // rotate the bitmap to portrait
//    public static Bitmap RotateBitmap(Bitmap source, float angle) {
//        Matrix matrix = new Matrix();
//        matrix.postRotate(angle);
//        return Bitmap.createBitmap(source, 0, 0, source.getWidth(),
//                source.getHeight(), matrix, true);
//    }
//
//    //the front camera displays the mirror image, we should flip it to its original
//    Bitmap flip(Bitmap d)
//    {
//        Matrix m = new Matrix();
//        m.preScale(-1, 1);
//        Bitmap src = d;
//        Bitmap dst = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, false);
//        dst.setDensity(DisplayMetrics.DENSITY_DEFAULT);
//        return dst;
//    }
    // Code Camera

    private void uploadImage() {

//        final ProgressDialog p  ;
//        p = new ProgressDialog(this);
//        p.setMessage("Upload Foto");
//        p.show();

        APIService s = RetroClient.getService();

        File f = new File(imagePath);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);

        MultipartBody.Part part = MultipartBody.Part.createFormData("uploaded_file", f.getName(), requestFile);
        Call<Result> resultCAll = s.postIMmage(part);
        resultCAll.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

//                p.dismiss();
                if (response.isSuccessful()){
                    if (response.body().getResult().equals("success"))
                        Snackbar.make(parentView, "upload Success", Snackbar.LENGTH_INDEFINITE).show();
                    else
                        Snackbar.make(parentView, "Gagal Upload Image", Snackbar.LENGTH_INDEFINITE).show();
                }else {
                    Snackbar.make(parentView, "Upload Image Gagal", Snackbar.LENGTH_INDEFINITE).show();
                }

                imagePath = "";
                te.setVisibility(View.VISIBLE);
//                imageVi.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

//                p.dismiss();



            }
        });
    }
    public void showImagePopup(View v){
        if (permissinCheker.lacksPermissions(PERMISSION_READ_STORAGE)){
            startPermissionActivity(PERMISSION_READ_STORAGE);

        }else {

            Intent qq = new Intent(Intent.ACTION_PICK);
            qq.setType("image/*");
            startActivityForResult(Intent.createChooser(qq, "Pilih Foto"), 100);



            //   final Intent galleryIntent = new Intent();
            //   galleryIntent.setType("image/*");
            //   galleryIntent.setAction(Intent.ACTION_PICK);

            //   final Intent pilihanIntent = Intent.createChooser(galleryIntent, "pilih foto");
            //   startActivityForResult(pilihanIntent, 1010);
        }
    }

    private void startPermissionActivity(String[] permissionReadStorage) {
        permissionActivity.startActivityForResult(this, 0, permissionReadStorage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode ==100 && resultCode == Activity.RESULT_OK){
            if (data == null){
                Snackbar.make(parentView, "unable to pick Image", Snackbar.LENGTH_INDEFINITE).show();
                return;

            }else {
                Snackbar.make(parentView, "image dapat", Snackbar.LENGTH_SHORT).show();
            }
            Uri selectImageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor c =getContentResolver().query(selectImageUri, filePathColumn, null, null, null);
            if (c !=null){
                c.moveToFirst();

                int columnIndex = c.getColumnIndex(filePathColumn[0]);
                imagePath = c.getString(columnIndex);

                Picasso.with(context).load(new File(imagePath)).into(imageVi);
                h = new File(imagePath).getName();
                Snackbar.make(parentView, "Gambar terpilih", Snackbar.LENGTH_SHORT).show();
                c.close();

                te.setVisibility(View.GONE);
                imageVi.setVisibility(View.VISIBLE);
            }else {
                te.setVisibility(View.VISIBLE);
                imageVi.setVisibility(View.VISIBLE);
                Snackbar.make(parentView, "Pilih gambar lagi", Snackbar.LENGTH_INDEFINITE).show();
            }
        }
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
                "Informatika/ " +edtKelas.getText().toString().trim(), "null poto")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.isSuccessful()){
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                String statusLogin = jsonObject.getString("error_msg");

                                if (statusLogin.contains("Data tersimpan")){
                                    loading.dismiss();
                                    uploadImage();


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
//        if (mCamera == null) {
//            setContentView(R.layout.activity_login);
//            btnLogin = (Button) findViewById(R.id.btnLogin);
//            FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
//
//            // Create an instance of Camera
//            con = getApplicationContext();
//            try {
//                mCamera = getCameraInstance();
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            // Create our Preview view and set it as the content of our
//            // activity.
//            mPreview = new CameraPreview(this, mCamera);
//
//            preview.addView(mPreview);
//        }
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
