/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.model;

import java.util.Date;

/**
 *
 * @author vinhd
 */
public class ModelBill {

    private int id;
    private int tenNhanVien;
    private String tenKhachHang;
    private String maVoucher;
    private String trangThai;
    private String diaChi;
    private String tenNguoiNhan;
    private String soDienThoai;
    private String loaiHoaDon;
    private double tienShip;
    private double tongTien;
    private double tienMat;
    private double tienChuyenKhoan;
    private Date ngayTao;
    private boolean deleted;

    public ModelBill() {
    }

    public ModelBill(int id, int tenNhanVien, String tenKhachHang, String maVoucher, String trangThai, String diaChi, String tenNguoiNhan, String soDienThoai, String loaiHoaDon, double tienShip, double tongTien, double tienMat, double tienChuyenKhoan, Date ngayTao, boolean deleted) {
        this.id = id;
        this.tenNhanVien = tenNhanVien;
        this.tenKhachHang = tenKhachHang;
        this.maVoucher = maVoucher;
        this.trangThai = trangThai;
        this.diaChi = diaChi;
        this.tenNguoiNhan = tenNguoiNhan;
        this.soDienThoai = soDienThoai;
        this.loaiHoaDon = loaiHoaDon;
        this.tienShip = tienShip;
        this.tongTien = tongTien;
        this.tienMat = tienMat;
        this.tienChuyenKhoan = tienChuyenKhoan;
        this.ngayTao = ngayTao;
        this.deleted = deleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(int tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getMaVoucher() {
        return maVoucher;
    }

    public void setMaVoucher(String maVoucher) {
        this.maVoucher = maVoucher;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getTenNguoiNhan() {
        return tenNguoiNhan;
    }

    public void setTenNguoiNhan(String tenNguoiNhan) {
        this.tenNguoiNhan = tenNguoiNhan;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getLoaiHoaDon() {
        return loaiHoaDon;
    }

    public void setLoaiHoaDon(String loaiHoaDon) {
        this.loaiHoaDon = loaiHoaDon;
    }

    public double getTienShip() {
        return tienShip;
    }

    public void setTienShip(double tienShip) {
        this.tienShip = tienShip;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public double getTienMat() {
        return tienMat;
    }

    public void setTienMat(double tienMat) {
        this.tienMat = tienMat;
    }

    public double getTienChuyenKhoan() {
        return tienChuyenKhoan;
    }

    public void setTienChuyenKhoan(double tienChuyenKhoan) {
        this.tienChuyenKhoan = tienChuyenKhoan;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    

}
