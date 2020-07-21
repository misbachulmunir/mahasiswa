package com.kubangkangkung.mahasiswa.Model;

import java.util.List;

public class ResponseMhs {
    private int kode;
    private String pesan;
    private List<ModelMhs> data;

    public int getKode() {
        return kode;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public List<ModelMhs> getData() {
        return data;
    }

    public void setData(List<ModelMhs> data) {
        this.data = data;
    }
}
