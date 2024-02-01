/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.viewmodels;

import java.util.Date;

/**
 *
 * @author nguye
 */
public class SanPhamResponse {

    private int idSanPham;
    private String tenSanPham;
    private String loaiSanPham;
    private Date ngayTao, ngaySua;
    private String nguoiTao, nguoiSua;
    private int deleted, soLuong;

    public SanPhamResponse() {
    }

    public SanPhamResponse(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public SanPhamResponse(int idSanPham, String tenSanPham) {
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
    }

    public SanPhamResponse(int idSanPham, String tenSanPham, String loaiSanPham) {
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.loaiSanPham = loaiSanPham;
    }

    public SanPhamResponse(int idSanPham, String tenSanPham, String loaiSanPham, Date ngayTao, Date ngaySua, String nguoiTao, String nguoiSua, int deleted) {
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.loaiSanPham = loaiSanPham;
        this.ngayTao = ngayTao;
        this.ngaySua = ngaySua;
        this.nguoiTao = nguoiTao;
        this.nguoiSua = nguoiSua;
        this.deleted = deleted;
    }

    public SanPhamResponse(int idSanPham, String tenSanPham, String loaiSanPham, Date ngayTao, Date ngaySua, String nguoiTao, String nguoiSua, int deleted, int soLuong) {
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.loaiSanPham = loaiSanPham;
        this.ngayTao = ngayTao;
        this.ngaySua = ngaySua;
        this.nguoiTao = nguoiTao;
        this.nguoiSua = nguoiSua;
        this.deleted = deleted;
        this.soLuong = soLuong;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(int idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getLoaiSanPham() {
        return loaiSanPham;
    }

    public void setLoaiSanPham(String loaiSanPham) {
        this.loaiSanPham = loaiSanPham;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Date getNgaySua() {
        return ngaySua;
    }

    public void setNgaySua(Date ngaySua) {
        this.ngaySua = ngaySua;
    }

    public String getNguoiTao() {
        return nguoiTao;
    }

    public void setNguoiTao(String nguoiTao) {
        this.nguoiTao = nguoiTao;
    }

    public String getNguoiSua() {
        return nguoiSua;
    }

    public void setNguoiSua(String nguoiSua) {
        this.nguoiSua = nguoiSua;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return tenSanPham;
    }

    public String trangThai() {
        String trangThai;
        if (this.deleted == 1) {
            trangThai = "Đang bán";
        } else if (this.deleted == 0 || this.soLuong == 0) {
            trangThai = "Ngừng bán";
        } else {
            trangThai = "Đã bán";
        }
        return trangThai;
    }

}
