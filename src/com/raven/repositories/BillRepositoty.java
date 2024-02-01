/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raven.repositories;

import com.raven.model.ModelBill;
import com.raven.viewmodels.ModelBillDetailTable;
import com.raven.viewmodels.ModelBillTable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author vinhd
 */
public interface BillRepositoty {

    public List<ModelBillTable> fillHoaDon(int trang);

    public List<ModelBill> selectAllHoaDon();

    public List<ModelBillDetailTable> fillHoaDonChiTiet(int idHoaDon, int soTrangHDCT);

    public ModelBill selectHoaDonById(int id);

    public int updateStatus(String trangThai, int id);

    public int add(String loaiHD, int trangThai, int idnv);

    public int deleted();

    public List<ModelBillTable> timKiem(int trang, String timKiem, String loaiHoaDon, String trangThai, String start, String end);
}
