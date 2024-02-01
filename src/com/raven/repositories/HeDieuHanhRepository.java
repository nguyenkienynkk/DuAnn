/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raven.repositories;

import com.raven.domainmodels.HeDieuHanh;
import com.raven.viewmodels.HeDieuHanhResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public interface HeDieuHanhRepository {

    List<HeDieuHanhResponse> getAll();

    Boolean add(HeDieuHanhResponse hdh);

    Boolean update(HeDieuHanhResponse hdh, int id);

    Boolean delete(int id);

    List<HeDieuHanhResponse> getOne(int id);
    
    List<HeDieuHanhResponse> getTenHeDiuDieuHanh();
}
