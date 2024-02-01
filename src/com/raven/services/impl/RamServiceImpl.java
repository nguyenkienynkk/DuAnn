/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.services.impl;

import com.raven.domainmodels.Ram;
import com.raven.repositories.impl.RamRepositoryImpl;
import com.raven.services.RamService;
import com.raven.viewmodels.RamResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public class RamServiceImpl implements RamService {

    private RamRepositoryImpl rsi = new RamRepositoryImpl();

    @Override
    public List<RamResponse> getAll() {
        return rsi.getAll();
    }

    @Override
    public Boolean add(RamResponse ram) {
        return rsi.add(ram);
    }

    @Override
    public Boolean update(RamResponse ram, int id) {
        return rsi.update(ram, id);
    }

    @Override
    public List<RamResponse> getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<RamResponse> getDungLuongRam() {
        return rsi.getDungLuongRam();
    }

    @Override
    public Boolean delete(int id) {
        return rsi.delete(id);
    }

}
