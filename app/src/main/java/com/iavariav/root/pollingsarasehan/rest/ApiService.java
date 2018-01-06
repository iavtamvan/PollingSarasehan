package com.iavariav.root.pollingsarasehan.rest;

import com.iavariav.root.pollingsarasehan.Model.KategoriDosenModel;
import com.iavariav.root.pollingsarasehan.Model.SurveyModel;
import com.iavariav.root.pollingsarasehan.Model.UserModel;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by root on 12/16/17.
 */

public interface ApiService {
    @GET("userget.php")
    Call<ArrayList<UserModel>> getUser();
    @GET("kategoridosenget.php")
    Call<ArrayList<KategoriDosenModel>> getDataKategoriDosen();
    @GET("surveycoba.php")
    Call<ArrayList<SurveyModel>> getDataSurveyDosen();

    @FormUrlEncoded
    @POST("polingdosen.php")
    Call<ResponseBody> postDosenPoling  (@Field("NAMA_DOSEN") String namadosen,
                                         @Field("JABATAN_DOSEN") String jabatandosen,
                                         @Field("POLING") String poling,
                                         @Field("NPM") String npm,
                                         @Field("ID_DOS") String ID_DOS);
    @FormUrlEncoded
    @POST("user.php")
    Call<ResponseBody> postLogin  (@Field("npm") String emaillogin,
                                   @Field("nama") String passwordLogin,
                                   @Field("hwid") String hwid);


}
