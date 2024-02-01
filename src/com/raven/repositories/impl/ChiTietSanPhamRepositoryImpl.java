/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.repositories.impl;

import com.raven.repositories.ChiTietSanPhamRepository;
import com.raven.untilities.DBConect;
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
import java.sql.Connection;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nguye
 */
public class ChiTietSanPhamRepositoryImpl implements ChiTietSanPhamRepository {

    @Override
    public List<SanPhamChiTietResponse> getAll() {
        List<SanPhamChiTietResponse> list = new ArrayList<>();
        String sql = """
SELECT ctsp.id, sp.ten_san_pham, 
                                            nsx.ten_nha_san_xuat, 
                                            dl.dung_luong,
                                            i.ma_imei,								
                                            ms.ten_mau_sac,
                                            hdh.ten_he_dieu_hanh,
                                            gu.loai_gpu, 
                                            r.dung_luong_ram, 
                                            c.loai_cpu,
                                            mh.loai_man_hinh, 
                                            cl.ten_chat_lieu ,
                                            count(*) as nhom  ,
                                            ctsp.trong_luong,
                                            ctsp.gia,
                                            ctsp.deleted
                                            FROM chi_tiet_san_pham ctsp 
                                            LEFT JOIN san_pham sp ON ctsp.san_pham = sp.id 
                                            LEFT JOIN nha_san_xuat nsx ON ctsp.nha_san_xuat = nsx.id 
                                            LEFT JOIN dung_luong dl ON ctsp.dung_luong = dl.id 
                                            LEFT JOIN mau_sac ms ON ctsp.mau_sac = ms.id 
                                            LEFT JOIN he_dieu_hanh hdh ON ctsp.he_dieu_hanh = hdh.id 
                                            LEFT JOIN gpu gu ON ctsp.gpu = gu.id 
                                            LEFT JOIN ram r ON ctsp.ram = r.id 
                                            LEFT JOIN cpu c ON ctsp.cpu = c.id 
                                            LEFT JOIN imei i ON ctsp.imei = i.id				
                                            LEFT JOIN man_hinh mh ON ctsp.man_hinh = mh.id 
                                            LEFT JOIN chat_lieu cl ON ctsp.chat_lieu = cl.id 
                                            where ctsp.deleted = 1
                                            group by ctsp.id,sp.ten_san_pham, 
                                            nsx.ten_nha_san_xuat, 
                                            dl.dung_luong, 
                                            ms.ten_mau_sac,
                                            hdh.ten_he_dieu_hanh,
                                            gu.loai_gpu, 
                                            r.dung_luong_ram,
                                            i.ma_imei,
                                            c.loai_cpu,
                                            mh.loai_man_hinh, 
                                            cl.ten_chat_lieu,
                                            ctsp.trong_luong,
                                            ctsp.gia,
                                            ctsp.deleted
                                            order by ctsp.id desc                                            
                    """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamChiTietResponse ctspr = new SanPhamChiTietResponse();
                SanPhamResponse sp = new SanPhamResponse();
                NhaSanXuatResponse nsx = new NhaSanXuatResponse();
                DungLuongResponse dl = new DungLuongResponse();
                MauSacResponse ms = new MauSacResponse();
                HeDieuHanhResponse hdh = new HeDieuHanhResponse();
                GpuResponse gpu = new GpuResponse();
                RamResponse ram = new RamResponse();
                CpuResponse cpu = new CpuResponse();
                ManHinhResponse mh = new ManHinhResponse();
                ChatLieuResponse cl = new ChatLieuResponse();
                ImeiResponse im = new ImeiResponse();
                ctspr.setId(rs.getInt("id"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                nsx.setTenNhaSanXuat(rs.getString("ten_nha_san_xuat"));
                dl.setDungLuong(rs.getString("dung_luong"));
                ms.setTenMauSac(rs.getString("ten_mau_sac"));
                hdh.setHeDieuHanh(rs.getString("ten_he_dieu_hanh"));
                gpu.setLoaiGPU(rs.getString("loai_gpu"));
                ram.setDungLuongRam(rs.getString("dung_luong_ram"));
                cpu.setLoaiCPU(rs.getString("loai_cpu"));
                mh.setLoaiManHinh(rs.getString("loai_man_hinh"));
                cl.setTenChatLieu(rs.getString("ten_chat_lieu"));
                im.setMaImei(rs.getString("ma_imei"));
                ctspr.setSoLuong(rs.getInt("nhom"));
                ctspr.setGia(rs.getFloat("gia"));
                ctspr.setTrongLuong(rs.getFloat("trong_luong"));
                ctspr.setSanPham(sp);
                ctspr.setNhaSanXuat(nsx);
                ctspr.setDungLuong(dl);
                ctspr.setMauSac(ms);
                ctspr.setHeDieuHanh(hdh);
                ctspr.setImei(im);
                ctspr.setGpu(gpu);
                ctspr.setRam(ram);
                ctspr.setCpu(cpu);
                ctspr.setManHinh(mh);
                ctspr.setChatLieu(cl);
                list.add(ctspr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Boolean add(SanPhamChiTietResponse ctsp) {
        String sql = """
               			
                           INSERT INTO [dbo].[chi_tiet_san_pham]
                                                      ([san_pham]
                                                      ,[ram]
                                                      ,[cpu]
                                                      ,[man_hinh]
                                                      ,[gpu]
                                                      ,[he_dieu_hanh]
                                                      ,[mau_sac]
                                                      ,[chat_lieu]
                                                      ,[dung_luong]
                                                      ,[imei]
                                                      ,[nha_san_xuat]
                                                      ,[trong_luong]
                                                      ,[gia])
                                                VALUES
                                                      (?
                                                      ,?
                                                      ,?
                                                      ,?
                                                      ,?
                                                      ,?
                                                      ,?
                                                      ,?
                                                      ,?
                                                      ,?
                                                      ,?
                                                      ,?
                                                      ,?)
            """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, ctsp.getSanPham().getIdSanPham());
            ps.setObject(2, ctsp.getRam().getId());
            ps.setObject(3, ctsp.getCpu().getId());
            ps.setObject(4, ctsp.getManHinh().getId());
            ps.setObject(5, ctsp.getGpu().getId());
            ps.setObject(6, ctsp.getHeDieuHanh().getId());
            ps.setObject(7, ctsp.getMauSac().getId());
            ps.setObject(8, ctsp.getChatLieu().getId());
            ps.setObject(9, ctsp.getDungLuong().getId());
            ps.setObject(10, ctsp.getImei().getId());
            ps.setObject(11, ctsp.getNhaSanXuat().getId());
            ps.setObject(12, ctsp.getTrongLuong());
            ps.setObject(13, ctsp.getGia());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Thêm mới thành công. Số bản ghi đã thêm: " + rowsAffected);
            } else {
                System.out.println("Thêm mới không thành công. Không có bản ghi nào được thêm.");
            }

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean update(SanPhamChiTietResponse ctsp, int id) {
        String chiTietSanPhamUpdate = """
        UPDATE chi_tiet_san_pham
        SET
        san_pham = ?,
        ram = ?,
        cpu = ?,
        man_hinh = ?,
        gpu = ?,
        he_dieu_hanh = ?,
        mau_sac = ?,
        chat_lieu = ?,
        dung_luong = ?,
        nha_san_xuat = ?,
        imei = ?,
        trong_luong = ?,
        gia = ?
        WHERE id = ?;
    """;

        String imeiUpdate = """
        UPDATE imei
        SET ma_imei = ?
        WHERE id = ?;
    """;

        try (Connection conn = DBConect.getConnection(); PreparedStatement psChiTietSanPham = conn.prepareStatement(chiTietSanPhamUpdate); PreparedStatement psImei = conn.prepareStatement(imeiUpdate)) {

            conn.setAutoCommit(false); // Start the transaction

            try {
                // Update chi_tiet_san_pham
                psChiTietSanPham.setObject(1, ctsp.getSanPham().getIdSanPham());
                psChiTietSanPham.setObject(2, ctsp.getRam().getId());
                psChiTietSanPham.setObject(3, ctsp.getCpu().getId());
                psChiTietSanPham.setObject(4, ctsp.getManHinh().getId());
                psChiTietSanPham.setObject(5, ctsp.getGpu().getId());
                psChiTietSanPham.setObject(6, ctsp.getHeDieuHanh().getId());
                psChiTietSanPham.setObject(7, ctsp.getMauSac().getId());
                psChiTietSanPham.setObject(8, ctsp.getChatLieu().getId());
                psChiTietSanPham.setObject(9, ctsp.getDungLuong().getId());
                psChiTietSanPham.setObject(10, ctsp.getNhaSanXuat().getId());
                psChiTietSanPham.setObject(11, ctsp.getImei().getId());
                psChiTietSanPham.setObject(12, ctsp.getTrongLuong());
                psChiTietSanPham.setObject(13, ctsp.getGia());
                psChiTietSanPham.setObject(14, id);
                psChiTietSanPham.executeUpdate();

                // Update imei only if it is not null
                if (ctsp.getImei().getMaImei() != null) {
                    psImei.setObject(1, ctsp.getImei().getMaImei());
                    psImei.setObject(2, ctsp.getImei().getId());
                    psImei.executeUpdate();
                }

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean delete(int id) {
        int check = 0;
        String sql = """
                update chi_tiet_san_pham set deleted = 0 where id = ?
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    @Override
    public List<SanPhamChiTietResponse> getOne(int id) {
        List<SanPhamChiTietResponse> list = new ArrayList<>();
        String sql = """
                            SELECT sp.ten_san_pham, 
                                                    nsx.ten_nha_san_xuat, 
                                                    dl.dung_luong, 
                                                    ms.ten_mau_sac,
                                                    hdh.ten_he_dieu_hanh,
                                                    gu.loai_gpu, 
                                                    r.dung_luong_ram, 
                                                    c.loai_cpu,
                                                    mh.loai_man_hinh, 
                                                    cl.ten_chat_lieu ,
                                                    count(*) as nhom  ,
                                                   ctsp.trong_luong,
                                                    ctsp.gia
                                                    FROM chi_tiet_san_pham ctsp 
                                                   LEFT JOIN san_pham sp ON ctsp.san_pham = sp.id 
                                                   LEFT JOIN nha_san_xuat nsx ON ctsp.nha_san_xuat = nsx.id 
                                                    LEFT JOIN dung_luong dl ON ctsp.dung_luong = dl.id 
                                                    LEFT JOIN mau_sac ms ON ctsp.mau_sac = ms.id 
                                                    LEFT JOIN he_dieu_hanh hdh ON ctsp.he_dieu_hanh = hdh.id 
                                                    LEFT JOIN gpu gu ON ctsp.gpu = gu.id 
                                                    LEFT JOIN ram r ON ctsp.ram = r.id 
                                                    LEFT JOIN cpu c ON ctsp.cpu = c.id 
                                                    LEFT JOIN man_hinh mh ON ctsp.man_hinh = mh.id 
                                                    LEFT JOIN chat_lieu cl ON ctsp.chat_lieu = cl.id 
                                                    group by  sp.ten_san_pham, 
                                                    nsx.ten_nha_san_xuat, 
                                                    dl.dung_luong, 
                                                    ms.ten_mau_sac,
                                                    hdh.ten_he_dieu_hanh,
                                                    gu.loai_gpu, 
                                                    r.dung_luong_ram, 
                                                    c.loai_cpu,
                                                    mh.loai_man_hinh, 
                                                    cl.ten_chat_lieu,
                                                    ctsp.trong_luong,
                                                    ctsp.gia
                            having ctsp.id =?
            """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            ps.setObject(1, id);
            while (rs.next()) {
                while (rs.next()) {
                    SanPhamChiTietResponse ctspr = new SanPhamChiTietResponse();
                    SanPhamResponse sp = new SanPhamResponse();
                    NhaSanXuatResponse nsx = new NhaSanXuatResponse();
                    DungLuongResponse dl = new DungLuongResponse();
                    MauSacResponse ms = new MauSacResponse();
                    HeDieuHanhResponse hdh = new HeDieuHanhResponse();
                    GpuResponse gpu = new GpuResponse();
                    RamResponse ram = new RamResponse();
                    CpuResponse cpu = new CpuResponse();
                    ManHinhResponse mh = new ManHinhResponse();
                    ChatLieuResponse cl = new ChatLieuResponse();
                    ctspr.setId(rs.getInt("id"));
                    sp.setTenSanPham(rs.getString("ten_san_pham"));
                    nsx.setTenNhaSanXuat(rs.getString("ten_nha_san_xuat"));
                    dl.setDungLuong(rs.getString("dung_luong"));
                    ms.setTenMauSac(rs.getString("ten_mau_sac"));
                    hdh.setHeDieuHanh(rs.getString("ten_he_dieu_hanh"));
                    gpu.setLoaiGPU(rs.getString("loai_gpu"));
                    ram.setDungLuongRam(rs.getString("dung_luong_ram"));
                    cpu.setLoaiCPU(rs.getString("loai_cpu"));
                    mh.setLoaiManHinh(rs.getString("loai_man_hinh"));
                    cl.setTenChatLieu(rs.getString("ten_chat_lieu"));
                    ctspr.setSoLuong(rs.getInt("nhom"));
                    ctspr.setGia(rs.getFloat("gia"));
                    ctspr.setTrongLuong(rs.getFloat("trong_luong"));
                    ctspr.setSanPham(sp);
                    ctspr.setNhaSanXuat(nsx);
                    ctspr.setDungLuong(dl);
                    ctspr.setMauSac(ms);
                    ctspr.setHeDieuHanh(hdh);
                    ctspr.setGpu(gpu);
                    ctspr.setRam(ram);
                    ctspr.setCpu(cpu);
                    ctspr.setManHinh(mh);
                    ctspr.setChatLieu(cl);
                    list.add(ctspr);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<SanPhamChiTietResponse> searchKey(String keyword) {
        List<SanPhamChiTietResponse> list = new ArrayList<>();
        String sql = """
                  SELECT 
                         ctsp.id,
                         sp.ten_san_pham, 
                         nsx.ten_nha_san_xuat, 
                         dl.dung_luong, 
                         ms.ten_mau_sac,
                         hdh.ten_he_dieu_hanh,
                         gu.loai_gpu, 
                         r.dung_luong_ram, 
                         c.loai_cpu,
                         mh.loai_man_hinh, 
                         cl.ten_chat_lieu,
                         COUNT(*) as nhom,
                         ctsp.trong_luong,
                         ctsp.gia
                     FROM 
                         chi_tiet_san_pham ctsp 
                     LEFT JOIN 
                         san_pham sp ON ctsp.san_pham = sp.id 
                     LEFT JOIN 
                         nha_san_xuat nsx ON ctsp.nha_san_xuat = nsx.id 
                     LEFT JOIN 
                         dung_luong dl ON ctsp.dung_luong = dl.id 
                     LEFT JOIN 
                         mau_sac ms ON ctsp.mau_sac = ms.id 
                     LEFT JOIN 
                         he_dieu_hanh hdh ON ctsp.he_dieu_hanh = hdh.id 
                     LEFT JOIN 
                         gpu gu ON ctsp.gpu = gu.id 
                     LEFT JOIN 
                         ram r ON ctsp.ram = r.id 
                     LEFT JOIN 
                         cpu c ON ctsp.cpu = c.id 
                     LEFT JOIN 
                         man_hinh mh ON ctsp.man_hinh = mh.id 
                     LEFT JOIN 
                         chat_lieu cl ON ctsp.chat_lieu = cl.id 
                     WHERE
                         ctsp.deleted = 1 AND
                         (sp.ten_san_pham LIKE ? OR 
                         nsx.ten_nha_san_xuat LIKE ? OR 
                         dl.dung_luong LIKE ? OR 
                         ms.ten_mau_sac LIKE ? OR 
                         hdh.ten_he_dieu_hanh LIKE ? OR 
                         gu.loai_gpu LIKE ? OR 
                         r.dung_luong_ram LIKE ? OR 
                         c.loai_cpu LIKE ? OR 
                         mh.loai_man_hinh LIKE ? OR 
                         cl.ten_chat_lieu LIKE ?)
                     GROUP BY
                         ctsp.id,
                         sp.ten_san_pham, 
                         nsx.ten_nha_san_xuat, 
                         dl.dung_luong, 
                         ms.ten_mau_sac,
                         hdh.ten_he_dieu_hanh,
                         gu.loai_gpu, 
                         r.dung_luong_ram, 
                         c.loai_cpu,
                         mh.loai_man_hinh, 
                         cl.ten_chat_lieu,
                         ctsp.trong_luong,
                         ctsp.gia;
                 """;

        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 1; i <= 10; i++) {
                ps.setObject(i, "%" + keyword + "%");
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamChiTietResponse ctspr = new SanPhamChiTietResponse();
                SanPhamResponse sp = new SanPhamResponse();
                NhaSanXuatResponse nsx = new NhaSanXuatResponse();
                DungLuongResponse dl = new DungLuongResponse();
                MauSacResponse ms = new MauSacResponse();
                HeDieuHanhResponse hdh = new HeDieuHanhResponse();
                GpuResponse gpu = new GpuResponse();
                RamResponse ram = new RamResponse();
                CpuResponse cpu = new CpuResponse();
                ManHinhResponse mh = new ManHinhResponse();
                ChatLieuResponse cl = new ChatLieuResponse();
                ctspr.setId(rs.getInt("id"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                nsx.setTenNhaSanXuat(rs.getString("ten_nha_san_xuat"));
                dl.setDungLuong(rs.getString("dung_luong"));
                ms.setTenMauSac(rs.getString("ten_mau_sac"));
                hdh.setHeDieuHanh(rs.getString("ten_he_dieu_hanh"));
                gpu.setLoaiGPU(rs.getString("loai_gpu"));
                ram.setDungLuongRam(rs.getString("dung_luong_ram"));
                cpu.setLoaiCPU(rs.getString("loai_cpu"));
                mh.setLoaiManHinh(rs.getString("loai_man_hinh"));
                cl.setTenChatLieu(rs.getString("ten_chat_lieu"));
                ctspr.setSoLuong(rs.getInt("nhom"));
                ctspr.setGia(rs.getFloat("gia"));
                ctspr.setTrongLuong(rs.getFloat("trong_luong"));
                ctspr.setSanPham(sp);
                ctspr.setNhaSanXuat(nsx);
                ctspr.setDungLuong(dl);
                ctspr.setMauSac(ms);
                ctspr.setHeDieuHanh(hdh);
                ctspr.setGpu(gpu);
                ctspr.setRam(ram);
                ctspr.setCpu(cpu);
                ctspr.setManHinh(mh);
                ctspr.setChatLieu(cl);
                list.add(ctspr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<SanPhamChiTietResponse> sliderGia(int gia) {
        List<SanPhamChiTietResponse> listResult = new ArrayList<>();
        String sql = """
                       SELECT ctsp.id, sp.ten_san_pham, 
                                                                                                                           nsx.ten_nha_san_xuat, 
                                                                                                                           dl.dung_luong,
                                                                                                                           im.ma_imei,									  im.ma_imei,
                                                                                                                           ms.ten_mau_sac,
                                                                                                                           hdh.ten_he_dieu_hanh,
                                                                                                                           gu.loai_gpu, 
                                                                                                                           r.dung_luong_ram, 
                                                                                                                           c.loai_cpu,
                                                                                                                           mh.loai_man_hinh, 
                                                                                                                           cl.ten_chat_lieu ,
                                                                                                                           count(*) as nhom  ,
                                                                                                                           ctsp.trong_luong,
                                                                                                                           ctsp.gia,
                                                                                                       		       ctsp.deleted
                                                                                                                           FROM chi_tiet_san_pham ctsp 
                                                                                                                          LEFT JOIN san_pham sp ON ctsp.san_pham = sp.id 
                                                                                                                          LEFT JOIN nha_san_xuat nsx ON ctsp.nha_san_xuat = nsx.id 
                                                                                                                           LEFT JOIN dung_luong dl ON ctsp.dung_luong = dl.id 
                                                                                                                           LEFT JOIN mau_sac ms ON ctsp.mau_sac = ms.id 
                                                                                                                           LEFT JOIN he_dieu_hanh hdh ON ctsp.he_dieu_hanh = hdh.id 
                                                                                                                           LEFT JOIN gpu gu ON ctsp.gpu = gu.id 
                                                                                                                           LEFT JOIN ram r ON ctsp.ram = r.id 
                                                                                                                           LEFT JOIN cpu c ON ctsp.cpu = c.id 
                                             										   LEFT JOIN imei i ON ctsp.imei = i.id								  LEFT JOIN imei im on ctsp.imei = im.id
                                                                                                                           LEFT JOIN man_hinh mh ON ctsp.man_hinh = mh.id 
                                                                                                                           LEFT JOIN chat_lieu cl ON ctsp.chat_lieu = cl.id 
                                                                                                                           where ctsp.deleted = 1
                                                                                                                           group by ctsp.id,sp.ten_san_pham, 
                                                                                                                           nsx.ten_nha_san_xuat, 
                                                                                                                           dl.dung_luong, 
                                                                                                                           ms.ten_mau_sac,
                                                                                                                           hdh.ten_he_dieu_hanh,
                                                                                                                           gu.loai_gpu, 
                                                                                                                           r.dung_luong_ram,
                                                                                                                           im.ma_imei,
                                                                                                                           c.loai_cpu,
                                                                                                                           mh.loai_man_hinh, 
                                                                                                                           cl.ten_chat_lieu,
                                                                                                                           ctsp.trong_luong,
                                                                                                                           ctsp.gia,
                                                                                                       		       ctsp.deleted
                          HAVING 
                              ctsp.gia < ?;
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, gia);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamChiTietResponse ctspr = new SanPhamChiTietResponse();
                SanPhamResponse sp = new SanPhamResponse();
                NhaSanXuatResponse nsx = new NhaSanXuatResponse();
                DungLuongResponse dl = new DungLuongResponse();
                MauSacResponse ms = new MauSacResponse();
                HeDieuHanhResponse hdh = new HeDieuHanhResponse();
                GpuResponse gpu = new GpuResponse();
                RamResponse ram = new RamResponse();
                CpuResponse cpu = new CpuResponse();
                ManHinhResponse mh = new ManHinhResponse();
                ChatLieuResponse cl = new ChatLieuResponse();
                ImeiResponse im = new ImeiResponse();
                ctspr.setId(rs.getInt("id"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                nsx.setTenNhaSanXuat(rs.getString("ten_nha_san_xuat"));
                dl.setDungLuong(rs.getString("dung_luong"));
                ms.setTenMauSac(rs.getString("ten_mau_sac"));
                hdh.setHeDieuHanh(rs.getString("ten_he_dieu_hanh"));
                gpu.setLoaiGPU(rs.getString("loai_gpu"));
                ram.setDungLuongRam(rs.getString("dung_luong_ram"));
                cpu.setLoaiCPU(rs.getString("loai_cpu"));
                mh.setLoaiManHinh(rs.getString("loai_man_hinh"));
                cl.setTenChatLieu(rs.getString("ten_chat_lieu"));
                im.setMaImei(rs.getString("ma_imei"));
                ctspr.setSoLuong(rs.getInt("nhom"));
                ctspr.setGia(rs.getFloat("gia"));
                ctspr.setTrongLuong(rs.getFloat("trong_luong"));
                ctspr.setSanPham(sp);
                ctspr.setNhaSanXuat(nsx);
                ctspr.setDungLuong(dl);
                ctspr.setMauSac(ms);
                ctspr.setHeDieuHanh(hdh);
                ctspr.setImei(im);
                ctspr.setGpu(gpu);
                ctspr.setRam(ram);
                ctspr.setCpu(cpu);
                ctspr.setManHinh(mh);
                ctspr.setChatLieu(cl);
                listResult.add(ctspr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listResult;
    }

    @Override
    public NhaSanXuatResponse nsx(String key) {
        String sql = "select id from nha_san_xuat where ten_nha_san_xuat = ?";
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            // Đặt giá trị cho tham số
            ps.setString(1, key);

            NhaSanXuatResponse nsx = null;
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                nsx = new NhaSanXuatResponse();
                nsx.setId(rs.getInt(1));
            }
            return nsx;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ChatLieuResponse cl(String key) {
        String sql = """
                        select id from chat_lieu where ten_chat_lieu =?
            """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, key);
            ChatLieuResponse cl = null;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cl = new ChatLieuResponse();
                cl.setId(rs.getInt(1));
            }
            return cl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CpuResponse cpu(String key) {
        String sql = """
                        select id from cpu where loai_cpu =?
            """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            // Đặt giá trị cho tham số
            ps.setString(1, key);

            CpuResponse cu = null;
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                cu = new CpuResponse();
                cu.setId(rs.getInt(1));
            }
            return cu;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DungLuongResponse dl(String key) {
        String sql = """
                        select id from dung_luong where dung_luong =?
            """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            // Đặt giá trị cho tham số
            ps.setString(1, key);

            DungLuongResponse dl = null;
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                dl = new DungLuongResponse();
                dl.setId(rs.getInt(1));
            }
            return dl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public GpuResponse gpu(String key) {
        String sql = """
                        select id from gpu where loai_gpu =?
            """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            // Đặt giá trị cho tham số
            ps.setString(1, key);

            GpuResponse gu = null;
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                gu = new GpuResponse();
                gu.setId(rs.getInt(1));
            }
            return gu;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HeDieuHanhResponse hdh(String key) {
        String sql = """
                        select id from he_dieu_hanh where ten_he_dieu_hanh =?
            """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            // Đặt giá trị cho tham số
            ps.setString(1, key);

            HeDieuHanhResponse hdh = null;
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                hdh = new HeDieuHanhResponse();
                hdh.setId(rs.getInt(1));
            }
            return hdh;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
   public ImeiResponse imei(String key) {

        String sqlInsert = "insert into imei(ma_imei) values(?)";
        String sqlSelect = "select id from imei where ma_imei =?";
        try (Connection conn = DBConect.getConnection()) {
            // Đặt giá trị cho tham số
            PreparedStatement psInsert = conn.prepareStatement(sqlInsert);
            psInsert.setString(1, key);
            psInsert.executeUpdate(); // Thực thi lệnh insert
            PreparedStatement psSelect = conn.prepareStatement(sqlSelect);
            psSelect.setObject(1, key);
            ImeiResponse ime = null;
            ResultSet rs = psSelect.executeQuery(); // Thực thi lệnh select
            while (rs.next()) {
                ime = new ImeiResponse();
                ime.setId(rs.getInt(1));

            }
            return ime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ManHinhResponse mh(String key) {
        String sql = """
                        select id from man_hinh where loai_man_hinh =?
            """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            // Đặt giá trị cho tham số
            ps.setString(1, key);

            ManHinhResponse mh = null;
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                mh = new ManHinhResponse();
                mh.setId(rs.getInt(1));
            }
            return mh;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public MauSacResponse ms(String key) {
        String sql = """
                        select id from mau_sac where ten_mau_sac =?
            """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            // Đặt giá trị cho tham số
            ps.setString(1, key);

            MauSacResponse ms = null;
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ms = new MauSacResponse();
                ms.setId(rs.getInt(1));
            }
            return ms;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public RamResponse ram(int key) {
        String sql = """
                        select id from ram where dung_luong_ram  =?
            """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            // Đặt giá trị cho tham số
            ps.setInt(1, key);

            RamResponse rm = null;
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                rm = new RamResponse();
                rm.setId(rs.getInt(1));
            }
            return rm;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SanPhamResponse sp(String key) {
        String sql = """
                        select id from san_pham where ten_san_pham  =?
            """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            // Đặt giá trị cho tham số
            ps.setString(1, key);

            SanPhamResponse sp = null;
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                sp = new SanPhamResponse();
                sp.setIdSanPham(rs.getInt(1));
            }
            return sp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<SanPhamChiTietResponse> phanTrang(int page, int limit) {
        List<SanPhamChiTietResponse> list = new ArrayList<>();

        String sql = """
                          SELECT ctsp.id,sp.ten_san_pham, 
                                             nsx.ten_nha_san_xuat, 
                                             dl.dung_luong, 
                                             ms.ten_mau_sac,
                                             hdh.ten_he_dieu_hanh,
                                             gu.loai_gpu, 
                                             r.dung_luong_ram, 
                                             c.loai_cpu,
                                             mh.loai_man_hinh, 
                                             cl.ten_chat_lieu ,
                                             count(*) as nhom  ,
                                            ctsp.trong_luong,
                                             ctsp.gia
                                             FROM chi_tiet_san_pham ctsp 
                                            LEFT JOIN san_pham sp ON ctsp.san_pham = sp.id 
                                            LEFT JOIN nha_san_xuat nsx ON ctsp.nha_san_xuat = nsx.id 
                                             LEFT JOIN dung_luong dl ON ctsp.dung_luong = dl.id 
                                             LEFT JOIN mau_sac ms ON ctsp.mau_sac = ms.id 
                                             LEFT JOIN he_dieu_hanh hdh ON ctsp.he_dieu_hanh = hdh.id 
                                             LEFT JOIN gpu gu ON ctsp.gpu = gu.id 
                                             LEFT JOIN ram r ON ctsp.ram = r.id 
                                             LEFT JOIN cpu c ON ctsp.cpu = c.id 
                                             LEFT JOIN man_hinh mh ON ctsp.man_hinh = mh.id 
                                             LEFT JOIN chat_lieu cl ON ctsp.chat_lieu = cl.id 
                                             group by ctsp.id, sp.ten_san_pham, 
                                             nsx.ten_nha_san_xuat, 
                                             dl.dung_luong, 
                                             ms.ten_mau_sac,
                                             hdh.ten_he_dieu_hanh,
                                             gu.loai_gpu, 
                                             r.dung_luong_ram, 
                                             c.loai_cpu,
                                             mh.loai_man_hinh, 
                                             cl.ten_chat_lieu,
                                             ctsp.trong_luong,
                                             ctsp.gia ORDER BY ctsp.id
                                                    """ + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            // Đặt giá trị cho tham số
            ps.setInt(1, (page - 1) * limit);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamChiTietResponse ctspr = new SanPhamChiTietResponse();
                SanPhamResponse sp = new SanPhamResponse();
                NhaSanXuatResponse nsx = new NhaSanXuatResponse();
                DungLuongResponse dl = new DungLuongResponse();
                MauSacResponse ms = new MauSacResponse();
                HeDieuHanhResponse hdh = new HeDieuHanhResponse();
                GpuResponse gpu = new GpuResponse();
                RamResponse ram = new RamResponse();
                CpuResponse cpu = new CpuResponse();
                ManHinhResponse mh = new ManHinhResponse();
                ChatLieuResponse cl = new ChatLieuResponse();
                ctspr.setId(rs.getInt("id"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                nsx.setTenNhaSanXuat(rs.getString("ten_nha_san_xuat"));
                dl.setDungLuong(rs.getString("dung_luong"));
                ms.setTenMauSac(rs.getString("ten_mau_sac"));
                hdh.setHeDieuHanh(rs.getString("ten_he_dieu_hanh"));
                gpu.setLoaiGPU(rs.getString("loai_gpu"));
                ram.setDungLuongRam(rs.getString("dung_luong_ram"));
                cpu.setLoaiCPU(rs.getString("loai_cpu"));
                mh.setLoaiManHinh(rs.getString("loai_man_hinh"));
                cl.setTenChatLieu(rs.getString("ten_chat_lieu"));
                ctspr.setSoLuong(rs.getInt("nhom"));
                ctspr.setGia(rs.getFloat("gia"));
                ctspr.setTrongLuong(rs.getFloat("trong_luong"));
                ctspr.setSanPham(sp);
                ctspr.setNhaSanXuat(nsx);
                ctspr.setDungLuong(dl);
                ctspr.setMauSac(ms);
                ctspr.setHeDieuHanh(hdh);
                ctspr.setGpu(gpu);
                ctspr.setRam(ram);
                ctspr.setCpu(cpu);
                ctspr.setManHinh(mh);
                ctspr.setChatLieu(cl);
                list.add(ctspr);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public int CountSanPham() {
        Connection cn = DBConect.getConnection();
        String sql = """
                      select count(*) from chi_tiet_san_pham where deleted  = 1
                     """;
        int count = 0;
        try {

            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<SanPhamChiTietResponse> getAllByName(String name) {
        List<SanPhamChiTietResponse> list = new ArrayList<>();
        String sql = """
                SELECT
                                        ctsp.id,
                                        sp.ten_san_pham,
                                        nsx.ten_nha_san_xuat,
                                        dl.dung_luong,
                                        ms.ten_mau_sac,
                                        hdh.ten_he_dieu_hanh,
                                        gu.loai_gpu,
                                        r.dung_luong_ram,
                                        c.loai_cpu,
                                        mh.loai_man_hinh,
                                        cl.ten_chat_lieu,
                                        COUNT(*) AS nhom,
                                        ctsp.trong_luong,
                                        ctsp.gia
                                    FROM
                                        chi_tiet_san_pham ctsp
                                        LEFT JOIN san_pham sp ON ctsp.san_pham = sp.id
                                        LEFT JOIN nha_san_xuat nsx ON ctsp.nha_san_xuat = nsx.id
                                        LEFT JOIN dung_luong dl ON ctsp.dung_luong = dl.id
                                        LEFT JOIN mau_sac ms ON ctsp.mau_sac = ms.id
                                        LEFT JOIN he_dieu_hanh hdh ON ctsp.he_dieu_hanh = hdh.id
                                        LEFT JOIN gpu gu ON ctsp.gpu = gu.id
                                        LEFT JOIN ram r ON ctsp.ram = r.id
                                        LEFT JOIN cpu c ON ctsp.cpu = c.id
                                        LEFT JOIN man_hinh mh ON ctsp.man_hinh = mh.id
                                        LEFT JOIN chat_lieu cl ON ctsp.chat_lieu = cl.id
                                    WHERE
                                        sp.ten_san_pham = ? 
                                    GROUP BY
                                        ctsp.id,
                                        sp.ten_san_pham,
                                        nsx.ten_nha_san_xuat,
                                        dl.dung_luong,
                                        ms.ten_mau_sac,
                                        hdh.ten_he_dieu_hanh,
                                        gu.loai_gpu,
                                        r.dung_luong_ram,
                                        c.loai_cpu,
                                        mh.loai_man_hinh,
                                        cl.ten_chat_lieu,
                                        ctsp.trong_luong,
                                        ctsp.gia;
            """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, name);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SanPhamChiTietResponse ctspr = new SanPhamChiTietResponse();
                SanPhamResponse sp = new SanPhamResponse();
                NhaSanXuatResponse nsx = new NhaSanXuatResponse();
                DungLuongResponse dl = new DungLuongResponse();
                MauSacResponse ms = new MauSacResponse();
                HeDieuHanhResponse hdh = new HeDieuHanhResponse();
                GpuResponse gpu = new GpuResponse();
                RamResponse ram = new RamResponse();
                CpuResponse cpu = new CpuResponse();
                ManHinhResponse mh = new ManHinhResponse();
                ChatLieuResponse cl = new ChatLieuResponse();
                ctspr.setId(rs.getInt("id"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                nsx.setTenNhaSanXuat(rs.getString("ten_nha_san_xuat"));
                dl.setDungLuong(rs.getString("dung_luong"));
                ms.setTenMauSac(rs.getString("ten_mau_sac"));
                hdh.setHeDieuHanh(rs.getString("ten_he_dieu_hanh"));
                gpu.setLoaiGPU(rs.getString("loai_gpu"));
                ram.setDungLuongRam(rs.getString("dung_luong_ram"));
                cpu.setLoaiCPU(rs.getString("loai_cpu"));
                mh.setLoaiManHinh(rs.getString("loai_man_hinh"));
                cl.setTenChatLieu(rs.getString("ten_chat_lieu"));
                ctspr.setSoLuong(rs.getInt("nhom"));
                ctspr.setGia(rs.getFloat("gia"));
                ctspr.setTrongLuong(rs.getFloat("trong_luong"));
                ctspr.setSanPham(sp);
                ctspr.setNhaSanXuat(nsx);
                ctspr.setDungLuong(dl);
                ctspr.setMauSac(ms);
                ctspr.setHeDieuHanh(hdh);
                ctspr.setGpu(gpu);
                ctspr.setRam(ram);
                ctspr.setCpu(cpu);
                ctspr.setManHinh(mh);
                ctspr.setChatLieu(cl);
                list.add(ctspr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public SanPhamChiTietResponse ctsp(int id) {
        String sql = """
                       SELECT ctsp.id
                                                             FROM chi_tiet_san_pham ctsp 
                                                            LEFT JOIN san_pham sp ON ctsp.san_pham = sp.id 
                                                            LEFT JOIN nha_san_xuat nsx ON ctsp.nha_san_xuat = nsx.id 
                                                             LEFT JOIN dung_luong dl ON ctsp.dung_luong = dl.id 
                                                             LEFT JOIN mau_sac ms ON ctsp.mau_sac = ms.id 
                                                             LEFT JOIN he_dieu_hanh hdh ON ctsp.he_dieu_hanh = hdh.id 
                                                             LEFT JOIN gpu gu ON ctsp.gpu = gu.id 
                                                             LEFT JOIN ram r ON ctsp.ram = r.id 
                                                             LEFT JOIN cpu c ON ctsp.cpu = c.id 
                                                             LEFT JOIN man_hinh mh ON ctsp.man_hinh = mh.id 
                                                             LEFT JOIN chat_lieu cl ON ctsp.chat_lieu = cl.id 
                                                             where ctsp.deleted = 1 and ctsp.id = 9
                                                             group by ctsp.id, sp.ten_san_pham, 
                                                             nsx.ten_nha_san_xuat, 
                                                             dl.dung_luong, 
                                                             ms.ten_mau_sac,
                                                             hdh.ten_he_dieu_hanh,
                                                             gu.loai_gpu, 
                                                             r.dung_luong_ram, 
                                                             c.loai_cpu,
                                                             mh.loai_man_hinh, 
                                                             cl.ten_chat_lieu,
                                                             ctsp.trong_luong,
                                                             ctsp.gia,
                                         		       ctsp.deleted
            """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            // Đặt giá trị cho tham số
            ps.setInt(1, id);

            SanPhamChiTietResponse spr = null;
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                spr = new SanPhamChiTietResponse();
                spr.setId(rs.getInt(1));
            }
            return spr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<SanPhamChiTietResponse> getAllDelete() {
        List<SanPhamChiTietResponse> list = new ArrayList<>();
        String sql = """
                     SELECT ctsp.id, sp.ten_san_pham, 
                                                                              nsx.ten_nha_san_xuat, 
                                                                              dl.dung_luong, 
                                                                              ms.ten_mau_sac,
                                                                              hdh.ten_he_dieu_hanh,
                                                                              gu.loai_gpu, 
                                                                              r.dung_luong_ram, 
                                                                              c.loai_cpu,
                                                                              mh.loai_man_hinh, 
                                                                              cl.ten_chat_lieu ,
                                                                              count(*) as nhom  ,
                                                                              ctsp.trong_luong,
                                                                              ctsp.gia,
                                                          		       ctsp.deleted
                                                                              FROM chi_tiet_san_pham ctsp 
                                                                             LEFT JOIN san_pham sp ON ctsp.san_pham = sp.id 
                                                                             LEFT JOIN nha_san_xuat nsx ON ctsp.nha_san_xuat = nsx.id 
                                                                              LEFT JOIN dung_luong dl ON ctsp.dung_luong = dl.id 
                                                                              LEFT JOIN mau_sac ms ON ctsp.mau_sac = ms.id 
                                                                              LEFT JOIN he_dieu_hanh hdh ON ctsp.he_dieu_hanh = hdh.id 
                                                                              LEFT JOIN gpu gu ON ctsp.gpu = gu.id 
                                                                              LEFT JOIN ram r ON ctsp.ram = r.id 
                                                                              LEFT JOIN cpu c ON ctsp.cpu = c.id 
                                                                              LEFT JOIN man_hinh mh ON ctsp.man_hinh = mh.id 
                                                                              LEFT JOIN chat_lieu cl ON ctsp.chat_lieu = cl.id 
                                                                              where ctsp.deleted = 0
                                                                              group by ctsp.id,sp.ten_san_pham, 
                                                                              nsx.ten_nha_san_xuat, 
                                                                              dl.dung_luong, 
                                                                              ms.ten_mau_sac,
                                                                              hdh.ten_he_dieu_hanh,
                                                                              gu.loai_gpu, 
                                                                              r.dung_luong_ram, 
                                                                              c.loai_cpu,
                                                                              mh.loai_man_hinh, 
                                                                              cl.ten_chat_lieu,
                                                                              ctsp.trong_luong,
                                                                              ctsp.gia,
                                                          		       ctsp.deleted
                   
                                                                
                    """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamChiTietResponse ctspr = new SanPhamChiTietResponse();
                SanPhamResponse sp = new SanPhamResponse();
                NhaSanXuatResponse nsx = new NhaSanXuatResponse();
                DungLuongResponse dl = new DungLuongResponse();
                MauSacResponse ms = new MauSacResponse();
                HeDieuHanhResponse hdh = new HeDieuHanhResponse();
                GpuResponse gpu = new GpuResponse();
                RamResponse ram = new RamResponse();
                CpuResponse cpu = new CpuResponse();
                ManHinhResponse mh = new ManHinhResponse();
                ChatLieuResponse cl = new ChatLieuResponse();
                ctspr.setId(rs.getInt("id"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                nsx.setTenNhaSanXuat(rs.getString("ten_nha_san_xuat"));
                dl.setDungLuong(rs.getString("dung_luong"));
                ms.setTenMauSac(rs.getString("ten_mau_sac"));
                hdh.setHeDieuHanh(rs.getString("ten_he_dieu_hanh"));
                gpu.setLoaiGPU(rs.getString("loai_gpu"));
                ram.setDungLuongRam(rs.getString("dung_luong_ram"));
                cpu.setLoaiCPU(rs.getString("loai_cpu"));
                mh.setLoaiManHinh(rs.getString("loai_man_hinh"));
                cl.setTenChatLieu(rs.getString("ten_chat_lieu"));
                ctspr.setSoLuong(rs.getInt("nhom"));
                ctspr.setGia(rs.getFloat("gia"));
                ctspr.setTrongLuong(rs.getFloat("trong_luong"));
                ctspr.setSanPham(sp);
                ctspr.setNhaSanXuat(nsx);
                ctspr.setDungLuong(dl);
                ctspr.setMauSac(ms);
                ctspr.setHeDieuHanh(hdh);
                ctspr.setGpu(gpu);
                ctspr.setRam(ram);
                ctspr.setCpu(cpu);
                ctspr.setManHinh(mh);
                ctspr.setChatLieu(cl);
                list.add(ctspr);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    @Override
    public Boolean restore(int id) {
        int check = 0;
        String sql = """
                UPDATE [dbo].[chi_tiet_san_pham]
                   SET 
                      [deleted] = 1
                 WHERE id = ?
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    @Override
    public List<ImeiResponse> imeiget(String tensp) {
        List<ImeiResponse> list = new ArrayList<>();
        String sql = """
                 SELECT imei.ma_imei
                 FROM chi_tiet_san_pham
                 JOIN san_pham ON chi_tiet_san_pham.san_pham = san_pham.id
                 JOIN imei ON chi_tiet_san_pham.imei = imei.id
                 WHERE san_pham.ten_san_pham = ?;
        """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            // Đặt giá trị cho tham số
            ps.setString(1, tensp);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ImeiResponse im = new ImeiResponse();
                im.setMaImei(rs.getString("ma_imei"));
                list.add(im);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<SanPhamChiTietResponse> getAllSanPhamBH() {
        List<SanPhamChiTietResponse> list = new ArrayList<>();
        String sql = """  
        SELECT sp.ten_san_pham, nsx.ten_nha_san_xuat, dl.dung_luong, ms.ten_mau_sac,
        hdh.ten_he_dieu_hanh, gu.loai_gpu, r.dung_luong_ram, c.loai_cpu,
        mh.loai_man_hinh, cl.ten_chat_lieu , count(*) as nhom , ctsp.deleted 
        FROM chi_tiet_san_pham ctsp 
        JOIN san_pham sp ON ctsp.san_pham = sp.id 
        JOIN nha_san_xuat nsx ON ctsp.nha_san_xuat = nsx.id 
        JOIN dung_luong dl ON ctsp.dung_luong = dl.id 
        JOIN mau_sac ms ON ctsp.mau_sac = ms.id 
        JOIN he_dieu_hanh hdh ON ctsp.he_dieu_hanh = hdh.id 
        JOIN gpu gu ON ctsp.gpu = gu.id 
        JOIN ram r ON ctsp.ram = r.id 
        JOIN cpu c ON ctsp.cpu = c.id 
        JOIN man_hinh mh ON ctsp.man_hinh = mh.id 
        JOIN chat_lieu cl ON ctsp.chat_lieu = cl.id 
        LEFT JOIN hoa_don_chi_tiet ON ctsp.id = hoa_don_chi_tiet.id_chi_tiet_san_pham
        LEFT JOIN hoa_don ON hoa_don_chi_tiet.id_hoa_don = hoa_don.id 
        LEFT JOIN trang_thai ON hoa_don.trang_thai = trang_thai.id 
        WHERE ctsp.deleted = 1 
        AND (trang_thai.ten_trang_thai IS NULL OR trang_thai.ten_trang_thai <> N'Đã thanh toán') 
        AND ctsp.id NOT IN (SELECT hoa_don_chi_tiet.id_chi_tiet_san_pham 
        FROM hoa_don_chi_tiet 
        JOIN chi_tiet_san_pham ON hoa_don_chi_tiet.id_chi_tiet_san_pham = chi_tiet_san_pham.id)
        GROUP BY sp.ten_san_pham, nsx.ten_nha_san_xuat, dl.dung_luong, ms.ten_mau_sac,
        hdh.ten_he_dieu_hanh, gu.loai_gpu, r.dung_luong_ram, c.loai_cpu, mh.loai_man_hinh, cl.ten_chat_lieu, ctsp.deleted                                                        
        """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamChiTietResponse ctspr = new SanPhamChiTietResponse();
                SanPhamResponse sp = new SanPhamResponse();
                NhaSanXuatResponse nsx = new NhaSanXuatResponse();
                DungLuongResponse dl = new DungLuongResponse();
                MauSacResponse ms = new MauSacResponse();
                HeDieuHanhResponse hdh = new HeDieuHanhResponse();
                GpuResponse gpu = new GpuResponse();
                RamResponse ram = new RamResponse();
                CpuResponse cpu = new CpuResponse();
                ManHinhResponse mh = new ManHinhResponse();
                ChatLieuResponse cl = new ChatLieuResponse();
                ImeiResponse im = new ImeiResponse();
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                nsx.setTenNhaSanXuat(rs.getString("ten_nha_san_xuat"));
                dl.setDungLuong(rs.getString("dung_luong"));
                ms.setTenMauSac(rs.getString("ten_mau_sac"));
                hdh.setHeDieuHanh(rs.getString("ten_he_dieu_hanh"));
                gpu.setLoaiGPU(rs.getString("loai_gpu"));
                ram.setDungLuongRam(rs.getString("dung_luong_ram"));
                cpu.setLoaiCPU(rs.getString("loai_cpu"));
                mh.setLoaiManHinh(rs.getString("loai_man_hinh"));
                cl.setTenChatLieu(rs.getString("ten_chat_lieu"));
                ctspr.setSoLuong(rs.getInt("nhom"));
                ctspr.setSanPham(sp);
                ctspr.setNhaSanXuat(nsx);
                ctspr.setDungLuong(dl);
                ctspr.setMauSac(ms);
                ctspr.setHeDieuHanh(hdh);
                ctspr.setImei(im);
                ctspr.setGpu(gpu);
                ctspr.setRam(ram);
                ctspr.setCpu(cpu);
                ctspr.setManHinh(mh);
                ctspr.setChatLieu(cl);
                list.add(ctspr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<SanPhamChiTietResponse> searchQr(String imm) {
        List<SanPhamChiTietResponse> list = new ArrayList<>();
        String sql = """
                  SELECT 
                         ctsp.id,
                         i.ma_imei,
                         sp.ten_san_pham, 
                         nsx.ten_nha_san_xuat, 
                         dl.dung_luong, 
                         ms.ten_mau_sac,
                         hdh.ten_he_dieu_hanh,
                         gu.loai_gpu, 
                         r.dung_luong_ram, 
                         c.loai_cpu,
                         mh.loai_man_hinh, 
                         cl.ten_chat_lieu,
                         COUNT(*) as nhom,
                         ctsp.trong_luong,
                         ctsp.gia
                         FROM 
                         chi_tiet_san_pham ctsp 
                         LEFT JOIN 
                         san_pham sp ON ctsp.san_pham = sp.id 
                         LEFT JOIN 
                         imei i ON ctsp.imei = i.id 
                         LEFT JOIN 
                         nha_san_xuat nsx ON ctsp.nha_san_xuat = nsx.id 
                         LEFT JOIN 
                         dung_luong dl ON ctsp.dung_luong = dl.id 
                         LEFT JOIN 
                         mau_sac ms ON ctsp.mau_sac = ms.id 
                         LEFT JOIN 
                         he_dieu_hanh hdh ON ctsp.he_dieu_hanh = hdh.id 
                         LEFT JOIN 
                         gpu gu ON ctsp.gpu = gu.id 
                         LEFT JOIN 
                         ram r ON ctsp.ram = r.id 
                         LEFT JOIN 
                         cpu c ON ctsp.cpu = c.id 
                         LEFT JOIN 
                         man_hinh mh ON ctsp.man_hinh = mh.id 
                         LEFT JOIN 
                         chat_lieu cl ON ctsp.chat_lieu = cl.id 
                         WHERE
                         ctsp.deleted = 1 AND i.ma_imei = ?
                         GROUP BY
                         ctsp.id,
                         i.ma_imei,
                         sp.ten_san_pham, 
                         nsx.ten_nha_san_xuat, 
                         dl.dung_luong, 
                         ms.ten_mau_sac,
                         hdh.ten_he_dieu_hanh,
                         gu.loai_gpu, 
                         r.dung_luong_ram, 
                         c.loai_cpu,
                         mh.loai_man_hinh, 
                         cl.ten_chat_lieu,
                         ctsp.trong_luong,
                         ctsp.gia;
                 """;

        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, imm);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamChiTietResponse ctspr = new SanPhamChiTietResponse();
                SanPhamResponse sp = new SanPhamResponse();
                NhaSanXuatResponse nsx = new NhaSanXuatResponse();
                DungLuongResponse dl = new DungLuongResponse();
                MauSacResponse ms = new MauSacResponse();
                HeDieuHanhResponse hdh = new HeDieuHanhResponse();
                GpuResponse gpu = new GpuResponse();
                RamResponse ram = new RamResponse();
                CpuResponse cpu = new CpuResponse();
                ManHinhResponse mh = new ManHinhResponse();
                ChatLieuResponse cl = new ChatLieuResponse();
                ImeiResponse im = new ImeiResponse();
                ctspr.setId(rs.getInt("id"));
                im.setMaImei(rs.getString("ma_imei"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                nsx.setTenNhaSanXuat(rs.getString("ten_nha_san_xuat"));
                dl.setDungLuong(rs.getString("dung_luong"));
                ms.setTenMauSac(rs.getString("ten_mau_sac"));
                hdh.setHeDieuHanh(rs.getString("ten_he_dieu_hanh"));
                gpu.setLoaiGPU(rs.getString("loai_gpu"));
                ram.setDungLuongRam(rs.getString("dung_luong_ram"));
                cpu.setLoaiCPU(rs.getString("loai_cpu"));
                mh.setLoaiManHinh(rs.getString("loai_man_hinh"));
                cl.setTenChatLieu(rs.getString("ten_chat_lieu"));
                ctspr.setSoLuong(rs.getInt("nhom"));
                ctspr.setGia(rs.getFloat("gia"));
                ctspr.setTrongLuong(rs.getFloat("trong_luong"));
                ctspr.setSanPham(sp);
                ctspr.setImei(im);
                ctspr.setNhaSanXuat(nsx);
                ctspr.setDungLuong(dl);
                ctspr.setMauSac(ms);
                ctspr.setHeDieuHanh(hdh);
                ctspr.setGpu(gpu);
                ctspr.setRam(ram);
                ctspr.setCpu(cpu);
                ctspr.setManHinh(mh);
                ctspr.setChatLieu(cl);
                list.add(ctspr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int getMaxPage(int sumctsp) {
        int totalcount = CountSanPham();
        int maxpage = totalcount / sumctsp;
        if (totalcount % sumctsp != 0) {
            maxpage = 1;
        }
        return maxpage;
    }

    @Override
    public List<SanPhamChiTietResponse> phanTrangCtsp(int pagee, int limitt) {
        List<SanPhamChiTietResponse> list = new ArrayList<>();
        String sql = """
SELECT ctsp.id, sp.ten_san_pham, 
                                            nsx.ten_nha_san_xuat, 
                                            dl.dung_luong,
                                            i.ma_imei,								
                                            ms.ten_mau_sac,
                                            hdh.ten_he_dieu_hanh,
                                            gu.loai_gpu, 
                                            r.dung_luong_ram, 
                                            c.loai_cpu,
                                            mh.loai_man_hinh, 
                                            cl.ten_chat_lieu ,
                                            count(*) as nhom  ,
                                            ctsp.trong_luong,
                                            ctsp.gia,
                                            ctsp.deleted
                                            FROM chi_tiet_san_pham ctsp 
                                            LEFT JOIN san_pham sp ON ctsp.san_pham = sp.id 
                                            LEFT JOIN nha_san_xuat nsx ON ctsp.nha_san_xuat = nsx.id 
                                            LEFT JOIN dung_luong dl ON ctsp.dung_luong = dl.id 
                                            LEFT JOIN mau_sac ms ON ctsp.mau_sac = ms.id 
                                            LEFT JOIN he_dieu_hanh hdh ON ctsp.he_dieu_hanh = hdh.id 
                                            LEFT JOIN gpu gu ON ctsp.gpu = gu.id 
                                            LEFT JOIN ram r ON ctsp.ram = r.id 
                                            LEFT JOIN cpu c ON ctsp.cpu = c.id 
                                            LEFT JOIN imei i ON ctsp.imei = i.id				
                                            LEFT JOIN man_hinh mh ON ctsp.man_hinh = mh.id 
                                            LEFT JOIN chat_lieu cl ON ctsp.chat_lieu = cl.id 
                                            where ctsp.deleted = 1
                                            group by ctsp.id,sp.ten_san_pham, 
                                            nsx.ten_nha_san_xuat, 
                                            dl.dung_luong, 
                                            ms.ten_mau_sac,
                                            hdh.ten_he_dieu_hanh,
                                            gu.loai_gpu, 
                                            r.dung_luong_ram,
                                            i.ma_imei,
                                            c.loai_cpu,
                                            mh.loai_man_hinh, 
                                            cl.ten_chat_lieu,
                                            ctsp.trong_luong,
                                            ctsp.gia,
                                            ctsp.deleted
                                            order by ctsp.id desc offset ? rows fetch next ? only
                                                             
                                                                
                    """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, (pagee - 1) * limitt);
            ps.setObject(2, limitt);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamChiTietResponse ctspr = new SanPhamChiTietResponse();
                SanPhamResponse sp = new SanPhamResponse();
                NhaSanXuatResponse nsx = new NhaSanXuatResponse();
                DungLuongResponse dl = new DungLuongResponse();
                MauSacResponse ms = new MauSacResponse();
                HeDieuHanhResponse hdh = new HeDieuHanhResponse();
                GpuResponse gpu = new GpuResponse();
                RamResponse ram = new RamResponse();
                CpuResponse cpu = new CpuResponse();
                ManHinhResponse mh = new ManHinhResponse();
                ChatLieuResponse cl = new ChatLieuResponse();
                ImeiResponse im = new ImeiResponse();
                ctspr.setId(rs.getInt("id"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                nsx.setTenNhaSanXuat(rs.getString("ten_nha_san_xuat"));
                dl.setDungLuong(rs.getString("dung_luong"));
                ms.setTenMauSac(rs.getString("ten_mau_sac"));
                hdh.setHeDieuHanh(rs.getString("ten_he_dieu_hanh"));
                gpu.setLoaiGPU(rs.getString("loai_gpu"));
                ram.setDungLuongRam(rs.getString("dung_luong_ram"));
                cpu.setLoaiCPU(rs.getString("loai_cpu"));
                mh.setLoaiManHinh(rs.getString("loai_man_hinh"));
                cl.setTenChatLieu(rs.getString("ten_chat_lieu"));
                im.setMaImei(rs.getString("ma_imei"));
                ctspr.setSoLuong(rs.getInt("nhom"));
                ctspr.setGia(rs.getFloat("gia"));
                ctspr.setTrongLuong(rs.getFloat("trong_luong"));
                ctspr.setSanPham(sp);
                ctspr.setNhaSanXuat(nsx);
                ctspr.setDungLuong(dl);
                ctspr.setMauSac(ms);
                ctspr.setHeDieuHanh(hdh);
                ctspr.setImei(im);
                ctspr.setGpu(gpu);
                ctspr.setRam(ram);
                ctspr.setCpu(cpu);
                ctspr.setManHinh(mh);
                ctspr.setChatLieu(cl);
                list.add(ctspr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    @Override
    public Boolean updateNotImei(SanPhamChiTietResponse spctr, int id) {
        int check = 0;
        String chiTietSanPhamUpdate = """
        UPDATE chi_tiet_san_pham
        SET
        san_pham = ?,
        ram = ?,
        cpu = ?,
        man_hinh = ?,
        gpu = ?,
        he_dieu_hanh = ?,
        mau_sac = ?,
        chat_lieu = ?,
        dung_luong = ?,
        nha_san_xuat = ?,
        trong_luong = ?,
        gia = ?
        WHERE id = ?;
    """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(chiTietSanPhamUpdate)) {

            ps.setObject(1, spctr.getSanPham().getIdSanPham());
            ps.setObject(2, spctr.getRam().getId());
            ps.setObject(3, spctr.getCpu().getId());
            ps.setObject(4, spctr.getManHinh().getId());
            ps.setObject(5, spctr.getGpu().getId());
            ps.setObject(6, spctr.getHeDieuHanh().getId());
            ps.setObject(7, spctr.getMauSac().getId());
            ps.setObject(8, spctr.getChatLieu().getId());
            ps.setObject(9, spctr.getDungLuong().getId());
            ps.setObject(10, spctr.getNhaSanXuat().getId());
            ps.setObject(11, spctr.getTrongLuong());
            ps.setObject(12, spctr.getGia());
            ps.setObject(13, id);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }
    
    @Override
    public List<ImeiResponse> getImei(String sp, String nsx, String dl, String ms, String hdh, String gpu, int ram, String cpu, String mh, String cl, int maHD) {
        List<ImeiResponse> list = new ArrayList<>();
        String sql = """
            SELECT im.ma_imei FROM chi_tiet_san_pham ctsp JOIN san_pham sp ON 
                                 ctsp.san_pham = sp.id JOIN nha_san_xuat nsx ON ctsp.nha_san_xuat
                                 = nsx.id JOIN dung_luong dl ON ctsp.dung_luong = dl.id JOIN mau_sac 
                                 ms ON ctsp.mau_sac = ms.id JOIN he_dieu_hanh hdh ON ctsp.he_dieu_hanh
                                 = hdh.id JOIN gpu gu ON ctsp.gpu = gu.id JOIN ram r ON
                                 ctsp.ram = r.id JOIN cpu c ON ctsp.cpu = c.id JOIN 
                                 man_hinh mh ON ctsp.man_hinh = mh.id JOIN chat_lieu
                                 cl ON ctsp.chat_lieu = cl.id JOIN imei im ON ctsp.imei
                                 = im.id LEFT JOIN hoa_don_chi_tiet hdct ON hdct.id_chi_tiet_san_pham
                                 = ctsp.id LEFT JOIN hoa_don hd ON hd.id = hdct.id_hoa_don 
                                 WHERE ctsp.deleted = 1 AND sp.ten_san_pham = ? 
                                 AND nsx.ten_nha_san_xuat = ? AND dl.dung_luong= ?
                                 AND ms.ten_mau_sac like ? AND hdh.ten_he_dieu_hanh= ?
                                 AND gu.loai_gpu= ? AND r.dung_luong_ram= ? AND 
                                 c.loai_cpu= ? AND mh.loai_man_hinh= ? AND
                                 cl.ten_chat_lieu like ? AND (hd.id IS NULL OR hd.id <> ?)
            					 and im.ma_imei not in (select imei.ma_imei from hoa_don_chi_tiet join chi_tiet_san_pham on hoa_don_chi_tiet.id_chi_tiet_san_pham = chi_tiet_san_pham.id
            					 join imei on chi_tiet_san_pham.imei = imei.id)
             """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            // Đặt giá trị cho các tham số
            ps.setObject(1, sp);
            ps.setObject(2, nsx);
            ps.setObject(3, dl);
            ps.setObject(4, "%" + ms + "%");
            ps.setObject(5, hdh);
            ps.setObject(6, gpu);
            ps.setObject(7, ram);
            ps.setObject(8, cpu);
            ps.setObject(9, mh);
            ps.setObject(10, "%" + cl + "%");
            ps.setObject(11, maHD);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ImeiResponse im = new ImeiResponse(rs.getString(1)); // sử dụng constructor của lớp ImeiResponse
                list.add(im);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
