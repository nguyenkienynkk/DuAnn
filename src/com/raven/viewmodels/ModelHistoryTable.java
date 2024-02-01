/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.viewmodels;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author vinhd
 */
public class ModelHistoryTable {

    private java.sql.Time thoiGian;
    private Date ngay;
    private String hanhDong;
    private String nguoiXacNhan;

    public ModelHistoryTable() {
    }

    public ModelHistoryTable(Time thoiGian, Date ngay, String hanhDong, String nguoiXacNhan) {
        this.thoiGian = thoiGian;
        this.ngay = ngay;
        this.hanhDong = hanhDong;
        this.nguoiXacNhan = nguoiXacNhan;
    }

    public Time getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Time thoiGian) {
        this.thoiGian = thoiGian;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public String getHanhDong() {
        return hanhDong;
    }

    public void setHanhDong(String hanhDong) {
        this.hanhDong = hanhDong;
    }

    public String getNguoiXacNhan() {
        return nguoiXacNhan;
    }

    public void setNguoiXacNhan(String nguoiXacNhan) {
        this.nguoiXacNhan = nguoiXacNhan;
    }

    public Object[] toDataRow(int stt) {

        return new Object[]{
            stt, this.thoiGian, this.ngay, this.hanhDong, this.nguoiXacNhan
        };
    }
}
