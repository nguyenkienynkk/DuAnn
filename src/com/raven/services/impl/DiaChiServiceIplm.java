/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.services.impl;

import com.raven.domainmodels.ModelDiaChi;
import com.raven.repositories.impl.DiaChiRepositoryIplm;
import com.raven.services.DiaChiService;
import java.util.List;

/**
 *
 * @author Admin
 */
public class DiaChiServiceIplm implements DiaChiService {

    DiaChiRepositoryIplm repo = new DiaChiRepositoryIplm();

    @Override
    public List<ModelDiaChi> getAllDiaChi() {
        return repo.getAllDiaChi();
    }

    @Override
    public int addDiaChi(String diaChiMacDinh) {
        return repo.addDiaChi(diaChiMacDinh);
    }

    @Override
    public Integer updateDiaChi(ModelDiaChi diaChi) {
        return repo.updateDiaChi(diaChi);
    }

    @Override
    public Integer deleteDiaChi(int id) {
        return repo.deleteDiaChi(id);
    }

    @Override
    public Integer addDiaChiInteger(ModelDiaChi diaChi) {
        return repo.addDiaChiInteger(diaChi);
    }

}
