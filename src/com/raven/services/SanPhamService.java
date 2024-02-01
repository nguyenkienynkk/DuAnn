/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raven.services;

import com.raven.viewmodels.SanPhamResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public interface SanPhamService {

    List<SanPhamResponse> getAll();

    Boolean add(SanPhamResponse sp);

    Boolean update(SanPhamResponse sp, int id);

    List<SanPhamResponse> getOne(int id);

    List<SanPhamResponse> getTenSanPham();

    List<SanPhamResponse> showSPTheoTrangThai(int trangThai);

    boolean updateStatus(int ttctsp, int ttsp);

    Boolean toggleStatus(int id);

    Boolean delete(int id);

    Boolean restore(int id);
    
     List<SanPhamResponse> keyPressed(String key);

}
