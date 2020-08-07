package com.kubangkangkung.mahasiswa.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResponseList {
 //   @SerializedName("id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
  //  @SerializedName("nama")
    private String nama;
  //  @SerializedName("tgl_lahir")
    private String tgl_lahir;
  //  @SerializedName("jenis_kelamin")
    private String jenis_kelamin;
  //  @SerializedName("jurusan")
    private String jurusan;
 //   @SerializedName("alamat")
    private String alamat;

//    public ResponseList(int id, String nama, String tgl_lahir, String jenis_kelamin, String jurusan, String alamat) {
//        this.id = id;
//        this.nama = nama;
//        this.tgl_lahir = tgl_lahir;
//        this.jenis_kelamin = jenis_kelamin;
//        this.jurusan = jurusan;
//        this.alamat = alamat;
//    }
}
