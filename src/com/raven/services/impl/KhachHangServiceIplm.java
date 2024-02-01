/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.services.impl;

import com.raven.domainmodels.ModelKhachHang;
import com.raven.repositories.impl.KhachHangRepositoryIplm;
import com.raven.services.KhachHangService;
import com.raven.viewmodels.LichSuMuaHang;
import java.util.List;

/**
 *
 * @author Admin
 */
public class KhachHangServiceIplm implements KhachHangService {

    KhachHangRepositoryIplm repo = new KhachHangRepositoryIplm();

    @Override
    public List<ModelKhachHang> getKhachHangByPage(int page, int limit) {
        return repo.getKhachHangByPage(page, limit);
    }

    @Override
    public int CountKhachHang() {
        return repo.CountKhachHang();
    }

    @Override
    public Integer addKhachHang(ModelKhachHang kh) {
        return repo.addKhachHang(kh);
    }

    @Override
    public Integer updateKhachHang(ModelKhachHang kh) {
        return repo.updateKhachHang(kh);
    }

    @Override
    public int getIdBySoDienThoai(String sdt) {
        return repo.getIdBySoDienThoai(sdt);
    }

    @Override
    public int getIDDiaChi(int idKhachHang) {
        return repo.getIDDiaChi(idKhachHang);
    }

    @Override
    public Integer deleteKhachHang(int id) {
        return repo.deleteKhachHang(id);
    }

    @Override
    public List<LichSuMuaHang> getLichSuMuaHangByIDKhachHang(int idKH) {
        return repo.getLichSuMuaHangByIDKhachHang(idKH);
    }

    @Override
    public List<ModelKhachHang> getKhachHangDeletedByPage(int page, int limit) {
        return repo.getKhachHangDeletedByPage(page, limit);
    }

    @Override
    public int CountKhachHangDeleted() {
        return repo.CountKhachHangDeletd();
    }

    @Override
    public int getMaxPageDeleted(int countKH) {
        return repo.getMaxPageDeleted(countKH);
    }

    @Override
    public int getMaxPage(int countKH) {
        return repo.getMaxPage(countKH);
    }

    @Override
    public Integer restoreKhachHang(int idKhachHang) {
        return repo.restoreKhachHang(idKhachHang);
    }

    @Override
    public List<ModelKhachHang> FindByAll(String keyword, int gioiTinh) {
        return repo.FindByAll(keyword, gioiTinh);
    }

    @Override
    public List<ModelKhachHang> FindByGioiTinh(int gioiTinh) {
        return repo.FindByGioiTinh(gioiTinh);
    }

    @Override
    public int getIDDiaChiByDiaChi(String diaChi) {
        return repo.getIDDiaChiByDiaChi(diaChi);
    }

}
