/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.services.impl;

import com.raven.domainmodels.NhaSanXuat;
import com.raven.repositories.impl.NhaSanXuatRepositoryImpl;
import com.raven.services.NhaSanXuatService;
import com.raven.viewmodels.NhaSanXuatResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public class NhaSanXuatServiceImpl implements NhaSanXuatService {

    private NhaSanXuatRepositoryImpl nsxsi = new NhaSanXuatRepositoryImpl();

    @Override
    public List<NhaSanXuatResponse> getAll() {
        return nsxsi.getAll();
    }

    @Override
    public Boolean add(NhaSanXuatResponse nsx) {
        return nsxsi.add(nsx);
    }

    @Override
    public Boolean update(NhaSanXuatResponse nsx, int id) {
        return nsxsi.update(nsx, id);
    }

    @Override
    public List<NhaSanXuatResponse> getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<NhaSanXuatResponse> getTenNhaSanXuat() {
        return nsxsi.getTenNhaSanXuat();
    }

    @Override
    public Boolean delete(int id) {
        return nsxsi.delete(id);
    }

}
