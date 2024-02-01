/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.viewmodels;

/**
 *
 * @author vinhd
 */
public class GioHangModel {

    private String tenSP;
    private String hang;
    private double donGia;
    private int soLuong;
    private double thanhTien;
    

    public GioHangModel() {
    }

    public GioHangModel(String tenSP, String hang, double donGia, int soLuong, double thanhTien) {
        this.tenSP = tenSP;
        this.hang = hang;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getHang() {
        return hang;
    }

    public void setHang(String hang) {
        this.hang = hang;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    

    public Object[] toDataRow(int a) {
        return new Object[]{
            a, this.tenSP, this.hang, String.format("%,.0f", this.donGia), this.soLuong, String.format("%,.0f", this.thanhTien)};
    }
}
