/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.services.impl;

import com.raven.domainmodels.Imei;
import com.raven.repositories.impl.ImeiRepositoryImpl;
import com.raven.services.ImeiService;
import com.raven.viewmodels.ImeiResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public class ImeiServiceImpl implements ImeiService {

    private ImeiRepositoryImpl iri = new ImeiRepositoryImpl();

    @Override
    public List<ImeiResponse> getAll() {
        return iri.getAll();
    }

    @Override
    public Boolean add(ImeiResponse imei) {
        return iri.add(imei);
    }

    @Override
    public Boolean update(ImeiResponse imei, int id) {
        return iri.update(imei, id);
    }

    @Override
    public List<ImeiResponse> getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ImeiResponse> getMaImei() {
        return iri.getMaImei();
    }

    @Override
    public Boolean delete(int id) {
        return iri.delete(id);
    }

    @Override
    public int iiii(String ma) {
        return iri.iiii(ma);
    }

    @Override
    public ImeiResponse imei(String im) {
        return iri.imeiiii(im);
    }

}
