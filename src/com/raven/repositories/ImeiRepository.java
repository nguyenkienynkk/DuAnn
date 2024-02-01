/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raven.repositories;

import com.raven.viewmodels.ImeiResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public interface ImeiRepository {

    List<ImeiResponse> getAll();

    Boolean add(ImeiResponse imei);

    Boolean update(ImeiResponse imei, int id);

    Boolean delete(int id);

    List<ImeiResponse> getOne(int id);

    List<ImeiResponse> getMaImei();

    int iiii(String maIm);

    public ImeiResponse imeiiii(String im);

}
