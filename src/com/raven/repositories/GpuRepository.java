/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raven.repositories;

import com.raven.viewmodels.GpuResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public interface GpuRepository {

    List<GpuResponse> getAll();

    Boolean add(GpuResponse gpu);

    Boolean update(GpuResponse gpu, int id);

    Boolean delete(int id);

    List<GpuResponse> getOne(int id);
    
    List<GpuResponse> getNhaCungCapGpu();
}
