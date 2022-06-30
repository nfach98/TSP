package com.example.skripsi.Model.HitungModel;

import com.example.skripsi.Model.PengirimanModel.DataPengirimanModel;

import java.util.List;

public class ResponHitungModel {
    private int kode, total;
    private String pesan;
    private List<DataHitungModel> data;

    public int getKode() {
        return kode;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public List<DataHitungModel> getData() {
        return data;
    }

    public void setData(List<DataHitungModel> data) {
        this.data = data;
    }
}
