/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raven.services;

import com.raven.domainmodels.ModelDiaChi;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface DiaChiService {

    public List<ModelDiaChi> getAllDiaChi();

    int addDiaChi(String diaChiMacDinh) ;

    Integer updateDiaChi(ModelDiaChi diaChi);

    Integer deleteDiaChi(int id);
    
     Integer addDiaChiInteger(ModelDiaChi diaChi);

}
