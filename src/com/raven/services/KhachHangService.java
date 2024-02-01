/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raven.services;

import com.raven.domainmodels.ModelKhachHang;
import com.raven.viewmodels.LichSuMuaHang;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface KhachHangService {

    List<ModelKhachHang> getKhachHangByPage(int page, int limit);

    List<ModelKhachHang> getKhachHangDeletedByPage(int page, int limit);

    int CountKhachHang();

    int CountKhachHangDeleted();

    int getMaxPage(int countKH);

    int getMaxPageDeleted(int countKH);

    Integer addKhachHang(ModelKhachHang kh);

    Integer updateKhachHang(ModelKhachHang kh);

    Integer deleteKhachHang(int id);

    int getIdBySoDienThoai(String sdt);

    int getIDDiaChi(int idKhachHang);

    List<LichSuMuaHang> getLichSuMuaHangByIDKhachHang(int idKH);

    Integer restoreKhachHang(int idKhachHang);

    List<ModelKhachHang> FindByAll(String keyword, int gioiTinh);

    List<ModelKhachHang> FindByGioiTinh(int gioiTinh);

    int getIDDiaChiByDiaChi(String diaChi);
}
