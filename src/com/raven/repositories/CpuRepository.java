/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raven.repositories;

import com.raven.viewmodels.CpuResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public interface CpuRepository {

    List<CpuResponse> getAll();

    Boolean add(CpuResponse cpu);

    Boolean update(CpuResponse cpu, int id);

    Boolean delete(int id);

    List<CpuResponse> getOne(int id);
    
    List<CpuResponse> getLoaiCpu();
}
