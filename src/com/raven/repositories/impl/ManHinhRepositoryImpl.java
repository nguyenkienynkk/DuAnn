/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.repositories.impl;

import com.raven.repositories.ManHinhRepository;
import com.raven.untilities.DBConect;
import com.raven.viewmodels.HeDieuHanhResponse;
import com.raven.viewmodels.ManHinhResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nguye
 */
public class ManHinhRepositoryImpl implements ManHinhRepository {

    @Override
    public List<ManHinhResponse> getAll() {
        List<ManHinhResponse> list = new ArrayList<>();
        String sql = "select id,loai_man_hinh,do_phan_giai from man_hinh where deleted = 1 order by id desc";
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ManHinhResponse mh = new ManHinhResponse(rs.getInt(1), rs.getString(2), rs.getString(3));
                list.add(mh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Boolean add(ManHinhResponse mh) {
        int check = 0;
        String sql = """
                   INSERT INTO [dbo].[man_hinh]
                              ([loai_man_hinh])
                        VALUES
                              (?)
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, mh.getLoaiManHinh());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    @Override
    public Boolean update(ManHinhResponse mh, int id) {
        int check = 0;
        String sql = """
        UPDATE [dbo].[man_hinh]
           SET [loai_man_hinh] = ?
              ,[do_phan_giai] = ?
              ,[nguoi_tao] = ?
              ,[ngay_tao] = ?
              ,[nguoi_sua] = ?
              ,[ngay_sua] = ?
         WHERE id = ?
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, mh.getLoaiManHinh());
            ps.setObject(2, mh.getDoPhanGiai());
            ps.setObject(3, mh.getNguoiTao());
            ps.setObject(4, mh.getNgayTao());
            ps.setObject(5, mh.getNguoiSua());
            ps.setObject(6, mh.getNgaySua());
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
                       update man_hinh set deleted = 0 where id = ?
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
    public List<ManHinhResponse> getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ManHinhResponse> getTenManHinh() {
        List<ManHinhResponse> list = new ArrayList<>();
        String sql = "SELECT id, loai_man_hinh FROM man_hinh where deleted = 1 order by id desc";
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ManHinhResponse mh = new ManHinhResponse();
                mh.setId(rs.getInt("id"));
                mh.setLoaiManHinh(rs.getString("loai_man_hinh"));
                list.add(mh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
