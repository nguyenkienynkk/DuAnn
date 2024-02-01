/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.services;

import com.raven.viewmodels.CpuResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public interface CpuService {

    List<CpuResponse> getAll();

    Boolean add(CpuResponse cpu);

    Boolean update(CpuResponse cpu, int id);

    List<CpuResponse> getOne(int id);

    List<CpuResponse> getLoaiCpu();
    
    Boolean delete(int id);
}
