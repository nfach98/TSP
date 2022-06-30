package com.example.skripsi.API;

import com.example.skripsi.Model.DataKurirModel.ResponModel;
import com.example.skripsi.Model.HitungModel.ResponHitungModel;
import com.example.skripsi.Model.LoginModel.ResponUserModel;
import com.example.skripsi.Model.PengirimanModel.ResponPengirimanModel;
import com.mapbox.geojson.FeatureCollection;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIRequestData {

    @GET("retrieve.php")
    Call<ResponModel> ardRetrieveData();

    @FormUrlEncoded
    @POST("createdatakurir.php")
    Call<ResponModel> ardCreateData(
            @Field("id") String id,
            @Field("name") String nama,
            @Field("address") String alamat,
            @Field("notelp") String telp
    );

    @FormUrlEncoded
    @POST("deletedatakurir.php")
    Call<ResponModel> ardDeleteData(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("getdatakurir.php")
    Call<ResponModel> ardGetData(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("updatedatakurir.php")
    Call<ResponModel> ardUpdateData(
            @Field("id") String id,
            @Field("name") String nama,
            @Field("address") String alamat,
            @Field("notelp") String telp
    );

    @FormUrlEncoded
    @POST("login3.php")
    Call<ResponUserModel> ardLogin(
            //@Field("Id") int Id,
            @Field("Username") String Username,
            @Field("Password") String Password,
            @Field("stts") String stts
    );

    @GET("dataPengiriman/retrivePengiriman.php")
    Call<ResponPengirimanModel> ardRetrievePengiriman();

    @FormUrlEncoded
    @POST("dataPengiriman/createDataPengiriman.php")
    Call<ResponPengirimanModel> ardCreatePengiriman(
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude
    );

    @FormUrlEncoded
    @POST("dataPengiriman/deleteDataPengiriman.php")
    Call<ResponPengirimanModel> ardDeletePengiriman(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("dataPengiriman/getDataPengiriman.php")
    Call<ResponPengirimanModel> ardGetPengiriman(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("dataPengiriman/updateDataPengiriman.php")
    Call<ResponPengirimanModel> ardUpdatePengiriman(
            @Field("id") int id,
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("latitude") double latitude,
            @Field("longitude") double longitude
    );

    @GET("ambilLatLong.php")
    Call<ResponHitungModel> ardAmbilLatLong();

    @GET("mapbox.places/{search}.json?country=id")
    Call<ResponseBody> searchLocation(
            @Path("search") String search,
            @Query("country") String country,
            @Query("types") String types,
            @Query("access_token") String accessToken,
            @Query("limit") int limit,
            @Query("autocomplete") boolean autocomplete
    );

    @GET("mapbox.places/{longitude},{latitude}.json")
    Call<ResponseBody> reverseLocation(
            @Path("longitude") String longitude,
            @Path("latitude") String latitude,
            @Query("types") String types,
            @Query("limit") int limit,
            @Query("access_token") String accessToken
    );
}
