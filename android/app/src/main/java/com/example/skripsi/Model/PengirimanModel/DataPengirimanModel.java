package com.example.skripsi.Model.PengirimanModel;

public class DataPengirimanModel {
    private int id;
    private String nama, alamat;
    private Double latitude, longitude;
    private boolean is_selected;

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

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public boolean getIsSelected() {
        return is_selected;
    }

    public void setIsSelected(boolean isSelected) {
        this.is_selected = isSelected;
    }
}
