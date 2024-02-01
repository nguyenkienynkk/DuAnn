/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raven.repositories;

import com.raven.domainmodels.ModelDiaChi;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface DiaChiRepository {

    List<ModelDiaChi> getAllDiaChi();

    int addDiaChi(String diaChiMacDinh);

    Integer addDiaChiInteger(ModelDiaChi dc);

    Integer updateDiaChi(ModelDiaChi diaChi);

    Integer deleteDiaChi(int id);
    
    

}
