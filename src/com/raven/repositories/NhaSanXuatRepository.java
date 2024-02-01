/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raven.repositories;

import com.raven.viewmodels.NhaSanXuatResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public interface NhaSanXuatRepository {
    List<NhaSanXuatResponse> getAll();

    Boolean add(NhaSanXuatResponse nsx);

    Boolean update(NhaSanXuatResponse nsx, int id);

    Boolean delete(int id);

    List<NhaSanXuatResponse> getOne(int id);
    
    List<NhaSanXuatResponse> getTenNhaSanXuat();
}
