/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raven.services;

import com.raven.viewmodels.ChatLieuResponse;
import com.raven.viewmodels.CpuResponse;
import com.raven.viewmodels.DungLuongResponse;
import com.raven.viewmodels.GpuResponse;
import com.raven.viewmodels.HeDieuHanhResponse;
import com.raven.viewmodels.ImeiResponse;
import com.raven.viewmodels.ManHinhResponse;
import com.raven.viewmodels.MauSacResponse;
import com.raven.viewmodels.NhaSanXuatResponse;
import com.raven.viewmodels.RamResponse;
import com.raven.viewmodels.SanPhamChiTietResponse;
import com.raven.viewmodels.SanPhamResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public interface ChiTietSanPhamService {

    List<SanPhamChiTietResponse> getAll();

    List<SanPhamChiTietResponse> getAllSanPhamBH();

    List<SanPhamChiTietResponse> getAllDelete();

    Boolean add(SanPhamChiTietResponse ctsp);

    Boolean delete(int id);

    Boolean update(SanPhamChiTietResponse ctsp, int id);

    List<SanPhamChiTietResponse> getOne(int id);

    List<SanPhamChiTietResponse> keyPressed(String key);

    List<SanPhamChiTietResponse> searchQr(String imm);

    List<SanPhamChiTietResponse> sliderGia(int gia);

    NhaSanXuatResponse nsx(String key);

    ChatLieuResponse cl(String key);

    CpuResponse cpu(String key);

    DungLuongResponse dl(String key);

    GpuResponse gpu(String key);

    HeDieuHanhResponse hdh(String key);

    ImeiResponse imei(String key);

    ManHinhResponse mh(String key);

    MauSacResponse ms(String key);

    RamResponse ram(int key);

    SanPhamResponse sp(String key);

    List<SanPhamChiTietResponse> phanTrang(int page, int limit);

    int CountSanPham();

    List<SanPhamChiTietResponse> getAllByName(String name);

    SanPhamChiTietResponse ctsp(int id);

    Boolean restore(int id);

    public List<ImeiResponse> imeiget(String tensp);

    int getMaxPage(int sumctsp);

    List<SanPhamChiTietResponse> phanTrangCtsp(int pagee, int limitt);

    Boolean updateNotImei(SanPhamChiTietResponse spctr, int id);
    
        List<ImeiResponse> getImei(String sp, String nsx, String dl, String ms, String hdh, String gpu, int ram, String cpu, String mh, String cl, int maHD);


}
