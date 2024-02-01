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
public class ManHinhResponse {
    private int id;
    private String loaiManHinh;
    private String doPhanGiai;
    private Date ngayTao, ngaySua;
    private String nguoiTao, nguoiSua; 
    private int deleted;

    public ManHinhResponse() {
    }

    public ManHinhResponse(String loaiManHinh) {
        this.loaiManHinh = loaiManHinh;
    }

    public ManHinhResponse(int id, String loaiManHinh) {
        this.id = id;
        this.loaiManHinh = loaiManHinh;
    }

    public ManHinhResponse(int id, String loaiManHinh, String doPhanGiai) {
        this.id = id;
        this.loaiManHinh = loaiManHinh;
        this.doPhanGiai = doPhanGiai;
    }

    public ManHinhResponse(int id, String loaiManHinh, String doPhanGiai, Date ngayTao, Date ngaySua, String nguoiTao, String nguoiSua, int deleted) {
        this.id = id;
        this.loaiManHinh = loaiManHinh;
        this.doPhanGiai = doPhanGiai;
        this.ngayTao = ngayTao;
        this.ngaySua = ngaySua;
        this.nguoiTao = nguoiTao;
        this.nguoiSua = nguoiSua;
        this.deleted = deleted;
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

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

   
    @Override
    public String toString() {
        return loaiManHinh ;
    }
    
}
