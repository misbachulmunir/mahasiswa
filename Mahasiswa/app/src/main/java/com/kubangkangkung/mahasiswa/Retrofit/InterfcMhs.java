package com.kubangkangkung.mahasiswa.Retrofit;

import com.kubangkangkung.mahasiswa.Model.ResponseMhs;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface InterfcMhs {
    @GET("retrieve.php")
    Call<ResponseMhs> RetrieveDataMhs();

    //untuk post data
    @FormUrlEncoded
    @POST("create.php")
    Call<ResponseMhs> CreateDataMhs(
            @Field("nama")String nama,
            @Field("tgl_lahir")String tgl_lahir,
            @Field("jenis_kelamin")String jenis_kelamin,
            @Field("jurusan")String jurusan,
            @Field("alamat")String alamat
    );

    //utuk hapus data methodnya post
    @FormUrlEncoded
    @POST("delete.php")
    Call<ResponseMhs> HapusDataMhs(
            @Field("id") int id

    );

    //untuk update
    @FormUrlEncoded
    @POST("update.php")
    Call<ResponseMhs> ardUpdateData(
            @Field("id")int id,
            @Field("nama")String nama,
            @Field("tgl_lahir")String tgl_lahir,
            @Field("jenis_kelamin")String jenis_kelamin,
            @Field("jurusan")String jurusan,
            @Field("alamat")String alamat
    );

}
