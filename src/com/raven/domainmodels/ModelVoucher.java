package com.raven.domainmodels;

import java.util.Date;

public class ModelVoucher {

    private int id;
    private String tenVoucher;
    private String maVoucher;
    private boolean loaiGiamGia;
    private float giaTri;
    private int soLuong;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private String trangThai;

    public ModelVoucher() {
    }

    public ModelVoucher(String trangThai) {
        this.trangThai = trangThai;
    }

    public ModelVoucher(Date ngayBatDau, Date ngayKetThuc, String trangThai) {
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
    }

    public ModelVoucher(String tenVoucher, String maVoucher, boolean loaiGiamGia, float giaTri, int soLuong, Date ngayBatDau, Date ngayKetThuc) {
        this.tenVoucher = tenVoucher;
        this.maVoucher = maVoucher;
        this.loaiGiamGia = loaiGiamGia;
        this.giaTri = giaTri;
        this.soLuong = soLuong;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }

    public ModelVoucher(int id, String tenVoucher, String maVoucher, boolean loaiGiamGia, float giaTri, int soLuong, Date ngayBatDau, Date ngayKetThuc, String trangThai) {
        this.id = id;
        this.tenVoucher = tenVoucher;
        this.maVoucher = maVoucher;
        this.loaiGiamGia = loaiGiamGia;
        this.giaTri = giaTri;
        this.soLuong = soLuong;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenVoucher() {
        return tenVoucher;
    }

    public void setTenVoucher(String tenVoucher) {
        this.tenVoucher = tenVoucher;
    }

    public String getMaVoucher() {
        return maVoucher;
    }

    public void setMaVoucher(String maVoucher) {
        this.maVoucher = maVoucher;
    }

    public boolean isLoaiGiamGia() {
        return loaiGiamGia;
    }

    public void setLoaiGiamGia(boolean loaiGiamGia) {
        this.loaiGiamGia = loaiGiamGia;
    }

    public float getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(float giaTri) {
        this.giaTri = giaTri;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return String.valueOf(loaiGiamGia);
    }

    public String getTrangThaiByDate() {
        Date currentDate = new Date();
        if (currentDate.before(ngayBatDau)) {
            return "Sắp áp dụng";
        } else if (currentDate.equals(ngayBatDau) || (currentDate.after(ngayBatDau) && currentDate.before(ngayKetThuc))) {
            return "Đang áp dụng";
        } else {
            return "Hết hạn";
        }
    }
}
