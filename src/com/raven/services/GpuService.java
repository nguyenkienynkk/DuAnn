/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raven.services;

import com.raven.viewmodels.GpuResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public interface GpuService {

     List<GpuResponse> getAll();

    Boolean add(GpuResponse gu);

    Boolean update(GpuResponse gu, int id);

    List<GpuResponse> getOne(int id);
    
    List<GpuResponse> getGpu();
    
    Boolean delete(int id);
}
