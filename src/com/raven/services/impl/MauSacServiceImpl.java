/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.services.impl;

import com.raven.domainmodels.MauSac;
import com.raven.repositories.impl.MauSacRepositoryImpl;
import com.raven.services.MauSacService;
import com.raven.viewmodels.MauSacResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public class MauSacServiceImpl implements MauSacService {

    private MauSacRepositoryImpl mssi = new MauSacRepositoryImpl();

    @Override
    public List<MauSacResponse> getAll() {
        return mssi.getAll();
    }

    @Override
    public Boolean add(MauSacResponse ms) {
        return mssi.add(ms);
    }

    @Override
    public Boolean update(MauSacResponse ms, int id) {
        return mssi.update(ms, id);
    }

    @Override
    public List<MauSacResponse> getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<MauSacResponse> getTenMauSac() {
        return mssi.getTenMauSac();
    }

    @Override
    public Boolean delete(int id) {
        return mssi.delete(id);
    }

}
