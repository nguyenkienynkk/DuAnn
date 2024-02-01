/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.services.impl;

import com.raven.domainmodels.DungLuong;
import com.raven.repositories.impl.DungLuongRepositoryImpl;
import com.raven.services.DungLuongService;
import com.raven.viewmodels.DungLuongResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public class DungLuongServiceImpl implements DungLuongService {

    private DungLuongRepositoryImpl dlsi = new DungLuongRepositoryImpl();

    @Override
    public List<DungLuongResponse> getAll() {
        return dlsi.getAll();
    }

    @Override
    public Boolean add(DungLuongResponse dl) {
        return dlsi.add(dl);
    }

    @Override
    public Boolean update(DungLuongResponse dl, int id) {
        return dlsi.update(dl, id);
    }

    @Override
    public List<DungLuongResponse> getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<DungLuongResponse> getDungLuongS() {
        return dlsi.getDungLuongS();
    }

    @Override
    public Boolean delete(int id) {
        return dlsi.delete(id);
    }

}
