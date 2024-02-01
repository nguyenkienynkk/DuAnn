/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raven.services;

import com.raven.domainmodels.Imei;
import com.raven.viewmodels.ImeiResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public interface ImeiService {

    List<ImeiResponse> getAll();

    Boolean add(ImeiResponse imei);

    Boolean update(ImeiResponse imei, int id);

    List<ImeiResponse> getOne(int id);

    List<ImeiResponse> getMaImei();

    Boolean delete(int id);

    public int iiii(String ma);

    public ImeiResponse imei(String im);

}
