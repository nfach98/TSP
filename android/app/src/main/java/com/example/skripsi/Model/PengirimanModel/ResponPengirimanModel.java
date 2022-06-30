package com.example.skripsi.Model.PengirimanModel;

import java.util.List;

public class ResponPengirimanModel {
    private int kode;
    private String pesan;
    private List<DataPengirimanModel> data;

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

    public List<DataPengirimanModel> getData() {
        return data;
    }

    public void setData(List<DataPengirimanModel> data) {
        this.data = data;
    }
}
