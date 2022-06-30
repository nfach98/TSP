package com.example.skripsi.Model.LoginModel;

import java.util.List;

public class ResponUserModel {
    private int kode;
    private String pesan;
    private List<DataUserModel> data;

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

    public List<DataUserModel> getData() {
        return data;
    }

    public void setData(List<DataUserModel> data) {
        this.data = data;
    }
}
