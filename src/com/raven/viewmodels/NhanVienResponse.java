package com.raven.viewmodels;

import java.util.Date;

/**
 *
 * @author HoaiPhuong
 */
public class NhanVienResponse {

    private int id;
    private String maNhanVien;
    private String tenNhanVien;
    private Date ngaySinh;
    private String diaChi;
    private boolean gioiTinh;
    private float luong;
    private String email;
    private String soDienThoai;
    private String matKhau;
    private String soCccd;
    private boolean vaiTro;
    private boolean deleted;

    public NhanVienResponse() {
    }

    public NhanVienResponse(String maNhanVien, String tenNhanVien, Date ngaySinh, String diaChi, boolean gioiTinh, float luong, String email, String soDienThoai, String soCccd, boolean vaiTro) {
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.luong = luong;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.soCccd = soCccd;
        this.vaiTro = vaiTro;
    }

    public NhanVienResponse(String maNhanVien, String tenNhanVien, Date ngaySinh, String diaChi, boolean gioiTinh, float luong, String email, String soDienThoai, String matKhau, String soCccd, boolean vaiTro) {
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.luong = luong;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.matKhau = matKhau;
        this.soCccd = soCccd;
        this.vaiTro = vaiTro;
    }

    public NhanVienResponse(int id, String maNhanVien, String tenNhanVien, Date ngaySinh, String diaChi, boolean gioiTinh, float luong, String email, String soDienThoai, String matKhau, String soCccd, boolean vaiTro, boolean deleted) {
        this.id = id;
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.luong = luong;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.matKhau = matKhau;
        this.soCccd = soCccd;
        this.vaiTro = vaiTro;
        this.deleted = deleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
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

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getSoCccd() {
        return soCccd;
    }

    public void setSoCccd(String soCccd) {
        this.soCccd = soCccd;
    }

    public boolean isVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(boolean vaiTro) {
        this.vaiTro = vaiTro;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
