/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.domainmodels;

import java.util.Date;

/**
 *
 * @author nguye
 */
public class ManHinh {

    private int id;
    private String loaiManHinh;
    private String doPhanGiai;
    private Date ngayTao, ngaySua;
    private String nguoiTao, nguoiSua;

    public ManHinh(int id, String loaiManHinh, String doPhanGiai, Date ngayTao, Date ngaySua, String nguoiTao, String nguoiSua) {
        this.id = id;
        this.loaiManHinh = loaiManHinh;
        this.doPhanGiai = doPhanGiai;
        this.ngayTao = ngayTao;
        this.ngaySua = ngaySua;
        this.nguoiTao = nguoiTao;
        this.nguoiSua = nguoiSua;
    }

    public ManHinh() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoaiManHinh() {
        return loaiManHinh;
    }

    public void setLoaiManHinh(String loaiManHinh) {
        this.loaiManHinh = loaiManHinh;
    }

    public String getDoPhanGiai() {
        return doPhanGiai;
    }

    public void setDoPhanGiai(String doPhanGiai) {
        this.doPhanGiai = doPhanGiai;
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

    @Override
    public String toString() {
        return loaiManHinh ;
    }

}
