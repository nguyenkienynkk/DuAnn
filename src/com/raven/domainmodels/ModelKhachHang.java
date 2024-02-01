package com.raven.domainmodels;

import java.util.Date;

public class ModelKhachHang {

    private int ID;
    private String tenKhachHang;
    private int gioiTinh;
    private Date ngaySinh;
    private String SDT;
    private String email;
    private ModelDiaChi diaChiMacDinh;

    public ModelKhachHang() {
    }

    public ModelKhachHang(String tenKhachHang, int gioiTinh, Date ngaySinh, String SDT, String email) {
        this.tenKhachHang = tenKhachHang;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.SDT = SDT;
        this.email = email;
    }

    public ModelKhachHang(String tenKhachHang, int gioiTinh, Date ngaySinh, String SDT, String email, ModelDiaChi diaChiMacDinh) {
        this.tenKhachHang = tenKhachHang;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.SDT = SDT;
        this.email = email;
        this.diaChiMacDinh = diaChiMacDinh;
    }

    public ModelKhachHang(int ID, String tenKhachHang, int gioiTinh, Date ngaySinh, String SDT, String email, ModelDiaChi diaChiMacDinh) {
        this.ID = ID;
        this.tenKhachHang = tenKhachHang;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.SDT = SDT;
        this.email = email;
        this.diaChiMacDinh = diaChiMacDinh;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public int getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ModelDiaChi getDiaChiMacDinh() {
        return diaChiMacDinh;
    }

    public void setDiaChiMacDinh(ModelDiaChi diaChiMacDinh) {
        this.diaChiMacDinh = diaChiMacDinh;
    }

}
