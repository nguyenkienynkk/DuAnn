/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raven.services;

import com.raven.domainmodels.Ram;
import com.raven.viewmodels.RamResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public interface RamService {

    List<RamResponse> getAll();

    Boolean add(RamResponse ram);

    Boolean update(RamResponse ram, int id);

    List<RamResponse> getOne(int id);

    List<RamResponse> getDungLuongRam();

     Boolean delete(int id);
}
