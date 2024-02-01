/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.repositories.impl;

import com.raven.repositories.DungLuongRepository;
import com.raven.untilities.DBConect;
import com.raven.viewmodels.CpuResponse;
import com.raven.viewmodels.DungLuongResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nguye
 */
public class DungLuongRepositoryImpl implements DungLuongRepository {

    @Override
    public List<DungLuongResponse> getAll() {
        List<DungLuongResponse> list = new ArrayList<>();
        String sql = "select id,dung_luong from dung_luong where deleted = 1 order by id desc";
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DungLuongResponse dl = new DungLuongResponse(rs.getInt(1), rs.getString(2));
                list.add(dl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Boolean add(DungLuongResponse dl) {
        int check = 0;
        String sql = """
                     INSERT INTO [dbo].[dung_luong]
                                           ([dung_luong])
                                     VALUES
                                           (?)
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, dl.getDungLuong());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    @Override
    public Boolean update(DungLuongResponse dl, int id) {
        int check = 0;
        String sql = """
                   UPDATE [dbo].[dung_luong]
                         SET [dung_luong] = ?
                            ,[nguoi_tao] = ?
                            ,[ngay_tao] = ?
                            ,[nguoi_sua] = ?
                            ,[ngay_sua] =?
                       WHERE id = ?
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, dl.getDungLuong());
            ps.setObject(2, dl.getNguoiTao());
            ps.setObject(3, dl.getNgayTao());
            ps.setObject(4, dl.getNguoiSua());
            ps.setObject(5, dl.getNgaySua());
            ps.setObject(6, id);

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
                        update dung_luong set deleted = 0 where id = ?
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
    public List<DungLuongResponse> getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<DungLuongResponse> getDungLuongS() {
        List<DungLuongResponse> list = new ArrayList<>();
        String sql = "SELECT id,dung_luong FROM dung_luong where deleted = 1 order by id desc";
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DungLuongResponse dl = new DungLuongResponse();
                dl.setId(rs.getInt("id"));
                dl.setDungLuong(rs.getString("dung_luong"));
                list.add(dl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
