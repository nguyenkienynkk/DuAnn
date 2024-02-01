/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.viewmodels;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author vinhd
 */
public class ToanCuc {

    public static String sdt;
    public static String tenKH = "Khách lẻ";
    public static String diaChi;

    public ToanCuc() {
    }

    public static String getSdt() {
        return sdt;
    }

    public static void setSdt(String sdt) {
        ToanCuc.sdt = sdt;
    }

    public static String getTenKH() {
        return tenKH;
    }

    public static void setTenKH(String tenKH) {
        ToanCuc.tenKH = tenKH;
    }

    public static String getDiaChi() {
        return diaChi;
    }

    public static void setDiaChi(String diaChi) {
        ToanCuc.diaChi = diaChi;
    }

    

}
