/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raven.services;

import com.raven.domainmodels.DungLuong;
import com.raven.viewmodels.DungLuongResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public interface DungLuongService {

    List<DungLuongResponse> getAll();

    Boolean add(DungLuongResponse dl);

    Boolean update(DungLuongResponse dl, int id);

    List<DungLuongResponse> getOne(int id);
    
    List<DungLuongResponse> getDungLuongS();
    
    Boolean delete(int id);
}
