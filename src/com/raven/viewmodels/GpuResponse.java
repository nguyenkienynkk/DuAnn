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
public class GpuResponse {
    private int id;
    private String nhaCungCap;
    private String loaiGPU;
    private Date ngayTao, ngaySua;
    private String nguoiTao, nguoiSua; 
    private int deleted;

    public GpuResponse() {
    }

    public GpuResponse(String loaiGPU) {
        this.loaiGPU = loaiGPU;
    }


    public GpuResponse(int id, String nhaCungCap, String loaiGPU) {
        this.id = id;
        this.nhaCungCap = nhaCungCap;
        this.loaiGPU = loaiGPU;
    }

    public GpuResponse(int id, String nhaCungCap, String loaiGPU, Date ngayTao, Date ngaySua, String nguoiTao, String nguoiSua, int deleted) {
        this.id = id;
        this.nhaCungCap = nhaCungCap;
        this.loaiGPU = loaiGPU;
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

    public String getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(String nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public String getLoaiGPU() {
        return loaiGPU;
    }

    public void setLoaiGPU(String loaiGPU) {
        this.loaiGPU = loaiGPU;
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
        return  loaiGPU;
    }
    
}
