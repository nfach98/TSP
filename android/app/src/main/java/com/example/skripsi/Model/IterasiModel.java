package com.example.skripsi.Model;

public class IterasiModel {
    private String arc1;
    private String arc2;
    private String inserted;

    public String getArc1() {
        return arc1;
    }

    public void setArc1(int arc1) {
        this.arc1 = arc1 == 0 ? "s" : "d" + arc1;
    }

    public String getArc2() {
        return arc2;
    }

    public void setArc2(int arc2) {
        this.arc2 = arc2 == 0 ? "s" : "d" + arc2;
    }

    public String getInserted() {
        return inserted;
    }

    public void setInserted(String inserted) {
        this.inserted = inserted;
    }
}
