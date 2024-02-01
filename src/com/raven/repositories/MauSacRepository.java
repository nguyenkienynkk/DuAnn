/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raven.repositories;

import com.raven.viewmodels.MauSacResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public interface MauSacRepository {
    List<MauSacResponse> getAll();

    Boolean add(MauSacResponse ms);

    Boolean update(MauSacResponse ms, int id);

    Boolean delete(int id);

    List<MauSacResponse> getOne(int id);
    
    List<MauSacResponse> getTenMauSac();
}
