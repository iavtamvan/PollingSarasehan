package com.iavariav.root.pollingsarasehan.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.widget.Toast;

import com.iavariav.root.pollingsarasehan.Adapter.UserAdapter;
import com.iavariav.root.pollingsarasehan.Model.UserModel;
import com.iavariav.root.pollingsarasehan.R;
import com.iavariav.root.pollingsarasehan.rest.ApiService;
import com.iavariav.root.pollingsarasehan.rest.Client;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataMahasiswaActivity extends AppCompatActivity {
    ArrayList<UserModel> mahasiswaModelsList;
    private UserAdapter mahasiswaAdapter;
    private SearchView searchview;
    private RecyclerView rv;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_mahasiswa);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();

        mahasiswaModelsList = new ArrayList<>();
        mahasiswaAdapter = new UserAdapter(mahasiswaModelsList, DataMahasiswaActivity.this);
        rv.setAdapter(mahasiswaAdapter);

        getdata();

        searchview.setIconifiedByDefault(true);
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mahasiswaAdapter.getFilter().filter(s);
                return false;
            }
        });
    }
    private void getdata() {
        loading = ProgressDialog.show(DataMahasiswaActivity.this, "", "Mengambil Data...", false, false);
        ApiService apiService = Client.getInstanceRetrofit();
        Call<ArrayList<UserModel>> call = apiService.getUser();
        call.enqueue(new Callback<ArrayList<UserModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                ArrayList<UserModel> list = response.body();
                mahasiswaModelsList.clear();
                UserModel mahasiswa = null;
                for (int i = 0; i < list.size(); i++) {
                    mahasiswa = new UserModel();
                    String nama = list.get(i).getNama();
                    String npm = list.get(i).getNpm();
                    mahasiswa.setNama(nama);
                    mahasiswa.setNpm(npm);


                    mahasiswaModelsList.add(mahasiswa);

                }
                loading.dismiss();

                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(DataMahasiswaActivity.this, 3);
                rv.setLayoutManager(layoutManager);
                rv.setItemAnimator(new DefaultItemAnimator());
                rv.setAdapter(mahasiswaAdapter);
                mahasiswaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(DataMahasiswaActivity.this, "Gagal Load", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initView() {
        searchview = (SearchView) findViewById(R.id.searchview);
        rv = (RecyclerView) findViewById(R.id.rv);
    }
    
    
}
