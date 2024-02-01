/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.services.impl;

import com.raven.repositories.impl.ChiTietSanPhamRepositoryImpl;
import com.raven.repositories.impl.CpuRepositoryImpl;
import com.raven.repositories.impl.DungLuongRepositoryImpl;
import com.raven.repositories.impl.GpuRepositoryImpl;
import com.raven.repositories.impl.HeDieuHanhRepositoryImpl;
import com.raven.repositories.impl.ImeiRepositoryImpl;
import com.raven.repositories.impl.ManHinhRepositoryImpl;
import com.raven.repositories.impl.MauSacRepositoryImpl;
import com.raven.repositories.impl.NhaSanXuatRepositoryImpl;
import com.raven.repositories.impl.RamRepositoryImpl;
import com.raven.repositories.impl.SanPhamRepositoryImpl;
import com.raven.services.ChiTietSanPhamService;
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
public class ChiTietSanPhamServiceImpl implements ChiTietSanPhamService {

    private final CpuRepositoryImpl cpuri = new CpuRepositoryImpl();
    private final ChiTietSanPhamRepositoryImpl ctspri = new ChiTietSanPhamRepositoryImpl();
    private final DungLuongRepositoryImpl dlri = new DungLuongRepositoryImpl();
    private final GpuRepositoryImpl gpuri = new GpuRepositoryImpl();
    private final HeDieuHanhRepositoryImpl hdhri = new HeDieuHanhRepositoryImpl();
    private final ImeiRepositoryImpl imeiri = new ImeiRepositoryImpl();
    private final ManHinhRepositoryImpl mhri = new ManHinhRepositoryImpl();
    private final MauSacRepositoryImpl msri = new MauSacRepositoryImpl();
    private final NhaSanXuatRepositoryImpl nsxri = new NhaSanXuatRepositoryImpl();
    private final RamRepositoryImpl ramri = new RamRepositoryImpl();
    private final SanPhamRepositoryImpl spri = new SanPhamRepositoryImpl();

    public ChiTietSanPhamServiceImpl() {
    }

    @Override
    public List<SanPhamChiTietResponse> getAll() {
        return ctspri.getAll();
    }

    @Override
    public Boolean add(SanPhamChiTietResponse ctsp) {
        return ctspri.add(ctsp);
    }

    @Override
    public Boolean update(SanPhamChiTietResponse ctsp, int id) {
        return ctspri.update(ctsp, id);
    }

    @Override
    public List<SanPhamChiTietResponse> getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<SanPhamChiTietResponse> keyPressed(String key) {
        return ctspri.searchKey(key);
    }

    @Override
    public List<SanPhamChiTietResponse> sliderGia(int gia) {
        return ctspri.sliderGia(gia);
    }

    @Override
    public NhaSanXuatResponse nsx(String key) {
        return ctspri.nsx(key);
    }

    @Override
    public ChatLieuResponse cl(String key) {
        return ctspri.cl(key);
    }

    @Override
    public CpuResponse cpu(String key) {
        return ctspri.cpu(key);
    }

    @Override
    public DungLuongResponse dl(String key) {
        return ctspri.dl(key);
    }

    @Override
    public GpuResponse gpu(String key) {
        return ctspri.gpu(key);
    }

    @Override
    public HeDieuHanhResponse hdh(String key) {
        return ctspri.hdh(key);
    }

    @Override
    public ImeiResponse imei(String key) {
        return ctspri.imei(key);
    }

    @Override
    public ManHinhResponse mh(String key) {
        return ctspri.mh(key);
    }

    @Override
    public MauSacResponse ms(String key) {
        return ctspri.ms(key);
    }

    @Override
    public RamResponse ram(int key) {
        return ctspri.ram(key);
    }

    @Override
    public SanPhamResponse sp(String key) {
        return ctspri.sp(key);
    }

    @Override
    public List<SanPhamChiTietResponse> phanTrang(int page, int limit) {
        return ctspri.phanTrang(page, limit);
    }

    @Override
    public int CountSanPham() {
        return ctspri.CountSanPham();
    }

    @Override
    public List<SanPhamChiTietResponse> getAllByName(String name) {
        return ctspri.getAllByName(name);
    }

    @Override
    public Boolean delete(int id) {
        return ctspri.delete(id);
    }

    @Override
    public SanPhamChiTietResponse ctsp(int id) {
        return ctspri.ctsp(id);
    }

    @Override
    public List<SanPhamChiTietResponse> getAllDelete() {
        return ctspri.getAllDelete();
    }

    @Override
    public Boolean restore(int id) {
        return ctspri.restore(id);
    }

    @Override
    public List<ImeiResponse> imeiget(String tensp) {
        return ctspri.imeiget(tensp);
    }

    @Override
    public List<SanPhamChiTietResponse> getAllSanPhamBH() {
        return ctspri.getAllSanPhamBH();
    }

    @Override
    public List<SanPhamChiTietResponse> searchQr(String imm) {
        return ctspri.searchQr(imm);
    }

    @Override
    public int getMaxPage(int sumctsp) {
        return ctspri.getMaxPage(sumctsp);
    }

    @Override
    public List<SanPhamChiTietResponse> phanTrangCtsp(int pagee, int limitt) {
        return ctspri.phanTrangCtsp(pagee, limitt);
    }

    @Override
    public Boolean updateNotImei(SanPhamChiTietResponse spctr, int id) {
        return ctspri.updateNotImei(spctr, id);
    }

    @Override
    public List<ImeiResponse> getImei(String sp, String nsx, String dl, String ms, String hdh, String gpu, int ram, String cpu, String mh, String cl, int maHD) {
        return ctspri.getImei(sp, nsx, dl, ms, hdh, gpu, ram, cpu, mh, cl, maHD);
    }

}
