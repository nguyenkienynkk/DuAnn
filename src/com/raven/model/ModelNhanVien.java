package com.raven.model;

import java.util.Date;

public class ModelNhanVien {

    private String ma_nhan_vien;
    private String ten_nhan_vien;
    private Date ngay_sinh;
    private String dia_chi;
    private boolean gioi_tinh;
    private float luong;
    private String email;
    private String so_dien_thoai;
    private String mat_khau;
    private String so_cccd;
    private boolean vai_tro;

    public ModelNhanVien() {
    }

    public ModelNhanVien(String ma_nhan_vien, String ten_nhan_vien, Date ngay_sinh, String dia_chi, boolean gioi_tinh, float luong, String email, String so_dien_thoai, String mat_khau, String so_cccd, boolean vai_tro) {
        this.ma_nhan_vien = ma_nhan_vien;
        this.ten_nhan_vien = ten_nhan_vien;
        this.ngay_sinh = ngay_sinh;
        this.dia_chi = dia_chi;
        this.gioi_tinh = gioi_tinh;
        this.luong = luong;
        this.email = email;
        this.so_dien_thoai = so_dien_thoai;
        this.mat_khau = mat_khau;
        this.so_cccd = so_cccd;
        this.vai_tro = vai_tro;
    }

    public String getMa_nhan_vien() {
        return ma_nhan_vien;
    }

    public void setMa_nhan_vien(String ma_nhan_vien) {
        this.ma_nhan_vien = ma_nhan_vien;
    }

    public String getTen_nhan_vien() {
        return ten_nhan_vien;
    }

    public void setTen_nhan_vien(String ten_nhan_vien) {
        this.ten_nhan_vien = ten_nhan_vien;
    }

    public Date getNgay_sinh() {
        return ngay_sinh;
    }

    public void setNgay_sinh(Date ngay_sinh) {
        this.ngay_sinh = ngay_sinh;
    }

    public String getDia_chi() {
        return dia_chi;
    }

    public void setDia_chi(String dia_chi) {
        this.dia_chi = dia_chi;
    }

    public boolean isGioi_tinh() {
        return gioi_tinh;
    }

    public void setGioi_tinh(boolean gioi_tinh) {
        this.gioi_tinh = gioi_tinh;
    }

    public float getLuong() {
        return luong;
    }

    public void setLuong(float luong) {
        this.luong = luong;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSo_dien_thoai() {
        return so_dien_thoai;
    }

    public void setSo_dien_thoai(String so_dien_thoai) {
        this.so_dien_thoai = so_dien_thoai;
    }

    public String getMat_khau() {
        return mat_khau;
    }

    public void setMat_khau(String mat_khau) {
        this.mat_khau = mat_khau;
    }

    public String getSo_cccd() {
        return so_cccd;
    }

    public void setSo_cccd(String so_cccd) {
        this.so_cccd = so_cccd;
    }

    public boolean isVai_tro() {
        return vai_tro;
    }

    public void setVai_tro(boolean vai_tro) {
        this.vai_tro = vai_tro;
    }

    @Override
    public String toString() {
        return "ModelNhanVien{" + "ma_nhan_vien=" + ma_nhan_vien + ", ten_nhan_vien=" + ten_nhan_vien + ", ngay_sinh=" + ngay_sinh + ", dia_chi=" + dia_chi + ", gioi_tinh=" + gioi_tinh + ", luong=" + luong + ", email=" + email + ", so_dien_thoai=" + so_dien_thoai + ", mat_khau=" + mat_khau + ", so_cccd=" + so_cccd + ", vai_tro=" + vai_tro + '}';
    }

    
    public Object[] toDataRow(int stt) {
        return new Object[]{
            stt, this.ma_nhan_vien, this.ten_nhan_vien, this.ngay_sinh, this.dia_chi, this.gioi_tinh, this.luong, this.email, this.so_dien_thoai, this.mat_khau, this.so_cccd, this.vai_tro
        };
    }

}
