/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.viewmodels;

import java.util.Date;

/**
 *
 * @author vinhd
 */
public class ModelBillTable {

    private int maHoaDon;
    private String tenNhanVien;
    private String tenKhachHang;
    private String loaiHoaDon;
    private Date ngayTao;
    private String trangThai;
    private String hinhThucTT;
    private String maGiaoDich;

    public ModelBillTable() {
    }

    public ModelBillTable(int maHoaDon, String tenNhanVien, String tenKhachHang, String loaiHoaDon, Date ngayTao, String trangThai, String hinhThucTT, String maGiaoDich) {
        this.maHoaDon = maHoaDon;
        this.tenNhanVien = tenNhanVien;
        this.tenKhachHang = tenKhachHang;
        this.loaiHoaDon = loaiHoaDon;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
        this.hinhThucTT = hinhThucTT;
        this.maGiaoDich = maGiaoDich;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getLoaiHoaDon() {
        return loaiHoaDon;
    }

    public void setLoaiHoaDon(String loaiHoaDon) {
        this.loaiHoaDon = loaiHoaDon;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getHinhThucTT() {
        return hinhThucTT;
    }

    public void setHinhThucTT(String hinhThucTT) {
        this.hinhThucTT = hinhThucTT;
    }

    public String getMaGiaoDich() {
        return maGiaoDich;
    }

    public void setMaGiaoDich(String maGiaoDich) {
        this.maGiaoDich = maGiaoDich;
    }

    public Object[] toDataRow(int stt) {
        if (this.tenKhachHang == null) {
            this.tenKhachHang = "Khách lẻ";
        }
        return new Object[]{
            stt, this.maHoaDon, this.tenNhanVien, this.tenKhachHang, this.loaiHoaDon, this.hinhThucTT, this.maGiaoDich, this.ngayTao, this.trangThai
        };
    }
}
