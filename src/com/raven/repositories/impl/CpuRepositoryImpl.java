/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.repositories.impl;

import com.raven.repositories.CpuRepository;
import com.raven.untilities.DBConect;
import com.raven.viewmodels.CpuResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nguye
 */
public class CpuRepositoryImpl implements CpuRepository {

    @Override
    public List<CpuResponse> getAll() {
        List<CpuResponse> list = new ArrayList<>();
        String sql = "SELECT id,nha_cung_cap,loai_cpu FROM cpu where deleted = 1 order by id desc";
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CpuResponse cpu = new CpuResponse(rs.getInt(1), rs.getString(2), rs.getString(3));
                list.add(cpu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Boolean add(CpuResponse cpu) {
        int check = 0;
        String sql = """
                     INSERT INTO [dbo].[cpu]
                                           ([loai_cpu])
                                     VALUES
                                           (?)
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, cpu.getLoaiCPU());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    @Override
    public Boolean update(CpuResponse cpu, int id) {
        int check = 0;
        String sql = """
                    UPDATE [dbo].[cpu]
                        SET [nha_cung_cap] = ?
                           ,[loai_cpu] = ?
                           ,[nguoi_tao] = ?
                           ,[ngay_tao] = ?
                           ,[nguoi_sua] = ?
                           ,[ngay_sua] = ?
                      WHERE id = ?
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, cpu.getNhaCungCap());
            ps.setObject(2, cpu.getLoaiCPU());
            ps.setObject(3, cpu.getNguoiTao());
            ps.setObject(4, cpu.getNgayTao());
            ps.setObject(5, cpu.getNguoiSua());
            ps.setObject(6, cpu.getNgaySua());
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
                      update cpu set deleted = 0 where id = ?
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
    public List<CpuResponse> getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<CpuResponse> getLoaiCpu() {
        List<CpuResponse> list = new ArrayList<>();
        String sql = "SELECT id, loai_cpu FROM cpu where deleted = 1 order by id desc";
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CpuResponse cpu = new CpuResponse();
                cpu.setId(rs.getInt("id"));
                cpu.setLoaiCPU(rs.getString("loai_cpu"));
                list.add(cpu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
