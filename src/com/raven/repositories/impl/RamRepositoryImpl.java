/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.repositories.impl;

import com.raven.repositories.RamRepository;
import com.raven.untilities.DBConect;
import com.raven.viewmodels.RamResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nguye
 */
public class RamRepositoryImpl implements RamRepository {

    @Override
    public List<RamResponse> getAll() {
        List<RamResponse> list = new ArrayList<>();
        String sql = "  select id,dung_luong_ram, loai_ram from ram where deleted = 1 order by id desc";
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RamResponse ram = new RamResponse(rs.getInt(1), rs.getString(2), rs.getString(3));
                list.add(ram);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Boolean add(RamResponse ram) {
        int check = 0;
        String sql = """
      INSERT INTO [dbo].[ram]
                 ([dung_luong_ram])
           VALUES
                 (?)
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, ram.getDungLuongRam());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    @Override
    public Boolean update(RamResponse ram, int id) {
        int check = 0;
        String sql = """
      UPDATE [dbo].[ram]
         SET [dung_luong_ram] =?
            ,[loai_ram] = ?
            ,[nguoi_tao] = ?
            ,[ngay_tao] = ?
            ,[nguoi_sua] = ?
            ,[ngay_sua] = ?
       WHERE id = ?
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, ram.getDungLuongRam());
            ps.setObject(2, ram.getLoaiRam());
            ps.setObject(3, ram.getNguoiTao());
            ps.setObject(4, ram.getNgayTao());
            ps.setObject(5, ram.getNguoiSua());
            ps.setObject(6, ram.getNgaySua());
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
                       update ram set deleted = 0 where id = ?
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
    public List<RamResponse> getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<RamResponse> getDungLuongRam() {
        List<RamResponse> list = new ArrayList<>();
        String sql = "SELECT id, dung_luong_ram FROM ram where deleted = 1 order by id desc";
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RamResponse ram = new RamResponse();
                ram.setId(rs.getInt("id"));
                ram.setDungLuongRam(rs.getString("dung_luong_ram"));
                list.add(ram);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
