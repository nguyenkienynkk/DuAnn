/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.viewmodels;

/**
 *
 * @author vinhd
 */
public class VoucherInBanHang {

    private String tenVoucher;
    private boolean loaiGiamGia;
    private double giaTri;
    private double giamToiDa;

    public VoucherInBanHang() {
    }

    public VoucherInBanHang(String tenVoucher, boolean loaiGiamGia, double giaTri, double giamToiDa) {
        this.tenVoucher = tenVoucher;
        this.loaiGiamGia = loaiGiamGia;
        this.giaTri = giaTri;
        this.giamToiDa = giamToiDa;
    }

    public String getTenVoucher() {
        return tenVoucher;
    }

    public void setTenVoucher(String tenVoucher) {
        this.tenVoucher = tenVoucher;
    }

    public boolean isLoaiGiamGia() {
        return loaiGiamGia;
    }

    public void setLoaiGiamGia(boolean loaiGiamGia) {
        this.loaiGiamGia = loaiGiamGia;
    }

    public double getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(double giaTri) {
        this.giaTri = giaTri;
    }

    public double getGiamToiDa() {
        return giamToiDa;
    }

    public void setGiamToiDa(double giamToiDa) {
        this.giamToiDa = giamToiDa;
    }

}
