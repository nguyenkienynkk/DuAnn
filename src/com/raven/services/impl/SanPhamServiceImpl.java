/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.services.impl;

import com.raven.repositories.impl.SanPhamRepositoryImpl;
import com.raven.services.SanPhamService;
import com.raven.viewmodels.SanPhamResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public class SanPhamServiceImpl implements SanPhamService {

    private SanPhamRepositoryImpl spsi = new SanPhamRepositoryImpl();

    @Override
    public List<SanPhamResponse> getAll() {
        return spsi.getAll();
    }

    @Override
    public Boolean add(SanPhamResponse sp) {
        return spsi.add(sp);
    }

    @Override
    public Boolean update(SanPhamResponse sp, int id) {
        return spsi.update(sp, id);
    }

    @Override
    public List<SanPhamResponse> getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<SanPhamResponse> getTenSanPham() {
        return spsi.getTenSanPham();
    }

    @Override
    public List<SanPhamResponse> showSPTheoTrangThai(int trangThai) {
        return spsi.showSPTheoTrangThai(trangThai);
    }

    @Override
    public boolean updateStatus(int ttctsp, int ttsp) {
        return spsi.updateStatus(ttctsp, ttsp);
    }

    @Override
    public Boolean toggleStatus(int id) {
        return spsi.toggleStatus(id);
    }

    @Override
    public Boolean delete(int id) {
        return spsi.delete(id);
    }

    @Override
    public Boolean restore(int id) {
        return spsi.restore(id);
    }

    @Override
    public List<SanPhamResponse> keyPressed(String key) {
        return spsi.keyPressed(key);
    }
}
