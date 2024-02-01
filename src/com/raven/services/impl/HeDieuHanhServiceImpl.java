/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.services.impl;

import com.raven.repositories.impl.HeDieuHanhRepositoryImpl;
import com.raven.services.HeDieuHanhService;
import com.raven.viewmodels.HeDieuHanhResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public class HeDieuHanhServiceImpl implements HeDieuHanhService {

    private HeDieuHanhRepositoryImpl hdhsi = new HeDieuHanhRepositoryImpl();

    @Override
    public List<HeDieuHanhResponse> getAll() {
        return hdhsi.getAll();
    }

    @Override
    public Boolean add(HeDieuHanhResponse hdh) {
        return hdhsi.add(hdh);
    }

    @Override
    public Boolean update(HeDieuHanhResponse hdh, int id) {
        return hdhsi.update(hdh, id);
    }

    @Override
    public List<HeDieuHanhResponse> getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<HeDieuHanhResponse> getTenHeDieuHanh() {
        return hdhsi.getTenHeDiuDieuHanh();
    }

    @Override
    public Boolean delete(int id) {
        return hdhsi.delete(id);
    }

}
