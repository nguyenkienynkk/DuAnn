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
public class Cpu {

    private int id;
    private String nhaCungCap;
    private String loaiCPU;
    private Date ngayTao,ngaySua ;
    private String nguoiTao, nguoiSua;

    public Cpu() {
    }

    public Cpu(int id, String nhaCungCap, String loaiCPU, Date ngayTao, Date ngaySua, String nguoiTao, String nguoiSua) {
        this.id = id;
        this.nhaCungCap = nhaCungCap;
        this.loaiCPU = loaiCPU;
        this.ngayTao = ngayTao;
        this.ngaySua = ngaySua;
        this.nguoiTao = nguoiTao;
        this.nguoiSua = nguoiSua;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(String nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public String getLoaiCPU() {
        return loaiCPU;
    }

    public void setLoaiCPU(String loaiCPU) {
        this.loaiCPU = loaiCPU;
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
        return  nhaCungCap ;
    }

   
}
