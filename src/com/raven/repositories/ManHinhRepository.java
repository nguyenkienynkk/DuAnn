/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raven.repositories;

import com.raven.viewmodels.ManHinhResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public interface ManHinhRepository {
    List<ManHinhResponse> getAll();

    Boolean add(ManHinhResponse mh);

    Boolean update(ManHinhResponse mh, int id);

    Boolean delete(int id);

    List<ManHinhResponse> getOne(int id);
    
    List<ManHinhResponse> getTenManHinh();
}
