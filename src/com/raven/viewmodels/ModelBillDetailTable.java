/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.viewmodels;

/**
 *
 * @author vinhd
 */
public class ModelBillDetailTable {

    private String tenSanPham;
    private String imei;
    private double gia;

    public ModelBillDetailTable() {
    }

    public ModelBillDetailTable( String tenSanPham, String imei, double gia) {
        this.tenSanPham = tenSanPham;
        this.imei = imei;
        this.gia = gia;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public Object[] toDataRow(int stt) {
        return new Object[]{
            stt, this.tenSanPham, this.imei, String.format("%,.0f", this.gia)
        };
    }
}
