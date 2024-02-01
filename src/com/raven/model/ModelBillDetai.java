/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.model;

/**
 *
 * @author vinhd
 */
public class ModelBillDetai {

    private int maHDCT;
    private int idHoaDon;
    private int idChiTietSanPham;
    private double gia;

    public ModelBillDetai() {
    }

    public ModelBillDetai(int maHDCT, int idHoaDon, int idChiTietSanPham, double gia) {
        this.maHDCT = maHDCT;
        this.idHoaDon = idHoaDon;
        this.idChiTietSanPham = idChiTietSanPham;
        this.gia = gia;
    }

    public int getMaHDCT() {
        return maHDCT;
    }

    public void setMaHDCT(int maHDCT) {
        this.maHDCT = maHDCT;
    }

    public int getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(int idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public int getIdChiTietSanPham() {
        return idChiTietSanPham;
    }

    public void setIdChiTietSanPham(int idChiTietSanPham) {
        this.idChiTietSanPham = idChiTietSanPham;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public Object[] toDataRow() {
        return new Object[]{
            this.maHDCT, this.idHoaDon, this.idChiTietSanPham, this.gia
        };
    }
}
