/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.services.impl;

import com.raven.domainmodels.ManHinh;
import com.raven.repositories.impl.HeDieuHanhRepositoryImpl;
import com.raven.repositories.impl.ManHinhRepositoryImpl;
import com.raven.services.ManHinhService;
import com.raven.viewmodels.ManHinhResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public class ManHinhServiceImpl implements ManHinhService {

    private ManHinhRepositoryImpl mhsi = new ManHinhRepositoryImpl();

    @Override
    public List<ManHinhResponse> getAll() {
        return mhsi.getTenManHinh();
    }

    @Override
    public Boolean add(ManHinhResponse mh) {
        return mhsi.add(mh);
    }

    @Override
    public Boolean update(ManHinhResponse mh, int id) {
       return mhsi.update(mh, id);
    }

    @Override
    public List<ManHinhResponse> getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ManHinhResponse> getLoaiManHinh() {
        return mhsi.getTenManHinh();
    }

    @Override
    public Boolean delete(int id) {
       return mhsi.delete(id);
    }

}
