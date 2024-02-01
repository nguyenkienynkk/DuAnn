package com.raven.domainmodels;

public class ModelDiaChi {

    private int ID;
    private String diaChiCuThe;

    public ModelDiaChi() {
    }

    public ModelDiaChi(String diaChiCuThe) {
        this.diaChiCuThe = diaChiCuThe;
    }

    public ModelDiaChi(int ID, String diaChiCuThe) {
        this.ID = ID;
        this.diaChiCuThe = diaChiCuThe;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDiaChiCuThe() {
        return diaChiCuThe;
    }

    public void setDiaChiCuThe(String diaChiCuThe) {
        this.diaChiCuThe = diaChiCuThe;
    }

    @Override
    public String toString() {
        return diaChiCuThe;
    }

}
