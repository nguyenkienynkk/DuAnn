/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raven.services;

import com.raven.viewmodels.NhaSanXuatResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public interface NhaSanXuatService {

    List<NhaSanXuatResponse> getAll();

    Boolean add(NhaSanXuatResponse nsx);

    Boolean update(NhaSanXuatResponse nsx, int id);

    List<NhaSanXuatResponse> getOne(int id);
    
    List<NhaSanXuatResponse> getTenNhaSanXuat();
    
    Boolean delete(int id);
}
