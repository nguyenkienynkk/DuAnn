/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.services.impl;

import com.raven.domainmodels.Cpu;
import com.raven.repositories.impl.CpuRepositoryImpl;
import com.raven.services.CpuService;
import com.raven.viewmodels.CpuResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public class CpuServiceImpl implements CpuService{
    private CpuRepositoryImpl cpuri = new CpuRepositoryImpl();

    @Override
    public List<CpuResponse> getAll() {
        return cpuri.getAll();
    }

    @Override
    public Boolean add(CpuResponse cpu) {
        return cpuri.add(cpu);
    }

    @Override
    public Boolean update(CpuResponse cpu, int id) {
        return cpuri.update(cpu, id);
    }

    @Override
    public List<CpuResponse> getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<CpuResponse> getLoaiCpu() {
       return cpuri.getLoaiCpu();
    }

    @Override
    public Boolean delete(int id) {
        return cpuri.delete(id);
    }
    
}
