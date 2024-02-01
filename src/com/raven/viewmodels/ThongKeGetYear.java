package com.raven.viewmodels;

public class ThongKeGetYear {

    private int year;

    private float doanhThu;

    public ThongKeGetYear() {
    }

    public ThongKeGetYear(int year, float doanhThu) {
        this.year = year;
        this.doanhThu = doanhThu;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(float doanhThu) {
        this.doanhThu = doanhThu;
    }
    

    @Override
    public String toString() {
        return String.valueOf(year);
    }

}
