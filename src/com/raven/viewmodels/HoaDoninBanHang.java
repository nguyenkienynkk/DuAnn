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
public class HoaDoninBanHang {

    private int maHD;
    private int soLuong;
    private Date ngayTao;
    private int nhanVien;
    private String trangThai;

    public HoaDoninBanHang() {
    }

    public HoaDoninBanHang(int maHD, int soLuong, Date ngayTao, int nhanVien, String trangThai) {
        this.maHD = maHD;
        this.soLuong = soLuong;
        this.ngayTao = ngayTao;
        this.nhanVien = nhanVien;
        this.trangThai = trangThai;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public int getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(int nhanVien) {
        this.nhanVien = nhanVien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    

    

    public Object[] toDataRow(int stt) {
        return new Object[]{
            stt, this.maHD, this.ngayTao,"NV0"+ this.nhanVien, this.soLuong, this.trangThai
        };
    }
}
