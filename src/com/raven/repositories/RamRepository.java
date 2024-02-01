/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raven.repositories;

import com.raven.viewmodels.RamResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public interface RamRepository {
    List<RamResponse> getAll();

    Boolean add(RamResponse ram);

    Boolean update(RamResponse ram, int id);

    Boolean delete(int id);

    List<RamResponse> getOne(int id);
    
    List<RamResponse> getDungLuongRam();
}
