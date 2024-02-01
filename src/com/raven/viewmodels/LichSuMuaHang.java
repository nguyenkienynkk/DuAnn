/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.viewmodels;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class LichSuMuaHang {

    private int idHoaDon;
    private Date ngayTao;
    private float tongTien;

    public LichSuMuaHang() {
    }

    public LichSuMuaHang(int idHoaDon, Date ngayTao, float tongTien) {
        this.idHoaDon = idHoaDon;
        this.ngayTao = ngayTao;
        this.tongTien = tongTien;
    }

    public int getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(int idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public float getTongTien() {
        return tongTien;
    }

    public void setTongTien(float tongTien) {
        this.tongTien = tongTien;
    }
    
    

}
