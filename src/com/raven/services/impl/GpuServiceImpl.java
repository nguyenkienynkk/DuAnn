/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.services.impl;

import com.raven.domainmodels.Gpu;
import com.raven.repositories.impl.GpuRepositoryImpl;
import com.raven.services.GpuService;
import com.raven.viewmodels.GpuResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public class GpuServiceImpl implements GpuService {

    private GpuRepositoryImpl gpusi = new GpuRepositoryImpl();

    @Override
    public List<GpuResponse> getAll() {
        return gpusi.getAll();
    }

    @Override
    public Boolean add(GpuResponse gu) {
        return gpusi.add(gu);
    }

    @Override
    public Boolean update(GpuResponse gu, int id) {
        return gpusi.update(gu, id);
    }

    @Override
    public List<GpuResponse> getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<GpuResponse> getGpu() {
        return gpusi.getNhaCungCapGpu();
    }

    @Override
    public Boolean delete(int id) {
        return gpusi.delete(id);
    }

}
