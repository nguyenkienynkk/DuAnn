/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.repositories.impl;

import com.raven.repositories.GpuRepository;
import com.raven.untilities.DBConect;
import com.raven.viewmodels.DungLuongResponse;
import com.raven.viewmodels.GpuResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nguye
 */
public class GpuRepositoryImpl implements GpuRepository {

    @Override
    public List<GpuResponse> getAll() {
        List<GpuResponse> list = new ArrayList<>();
        String sql = "select id,nha_cung_cap,loai_gpu from gpu where deleted = 1 order by id desc";
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                GpuResponse gpu = new GpuResponse(rs.getInt(1), rs.getString(2), rs.getString(3));
                list.add(gpu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Boolean add(GpuResponse gpu) {
        int check = 0;
        String sql = """
                     INSERT INTO [dbo].[gpu]
                                ([loai_gpu])
                          VALUES
                                (?)
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, gpu.getLoaiGPU());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    @Override
    public Boolean update(GpuResponse gpu, int id) {
        int check = 0;
        String sql = """
        UPDATE [dbo].[gpu]
           SET [nha_cung_cap] = ?
              ,[loai_gpu] =?
              ,[nguoi_tao] =?
              ,[ngay_tao] = ?
              ,[nguoi_sua] =?
              ,[ngay_sua] =?
         WHERE id = ?
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, gpu.getNhaCungCap());
            ps.setObject(2, gpu.getLoaiGPU());
            ps.setObject(3, gpu.getNguoiTao());
            ps.setObject(4, gpu.getNgayTao());
            ps.setObject(5, gpu.getNguoiSua());
            ps.setObject(6, gpu.getNgaySua());
            ps.setObject(7, id);

            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    @Override
    public Boolean delete(int id) {
        int check = 0;
        String sql = """
                        update gpu set deleted = 0 where id = ?
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
    public List<GpuResponse> getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<GpuResponse> getNhaCungCapGpu() {
        List<GpuResponse> list = new ArrayList<>();
        String sql = "select id, loai_gpu from gpu where deleted = 1 order by id desc";
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                GpuResponse gpu = new GpuResponse();
                gpu.setId(rs.getInt("id"));
                gpu.setLoaiGPU(rs.getString("loai_gpu"));
                list.add(gpu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
