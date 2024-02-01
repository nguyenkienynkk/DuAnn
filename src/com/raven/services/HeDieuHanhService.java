/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raven.services;

import com.raven.domainmodels.HeDieuHanh;
import com.raven.viewmodels.HeDieuHanhResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public interface HeDieuHanhService {
    List<HeDieuHanhResponse> getAll();

    Boolean add(HeDieuHanhResponse hdh);

    Boolean update(HeDieuHanhResponse hdh, int id);

    List<HeDieuHanhResponse> getOne(int id);
    
    List<HeDieuHanhResponse> getTenHeDieuHanh();
    
    Boolean delete(int id);
}
