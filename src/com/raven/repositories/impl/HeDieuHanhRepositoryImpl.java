/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.repositories.impl;

import com.raven.repositories.HeDieuHanhRepository;
import com.raven.untilities.DBConect;
import com.raven.viewmodels.GpuResponse;
import com.raven.viewmodels.HeDieuHanhResponse;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author nguye
 */
public class HeDieuHanhRepositoryImpl implements HeDieuHanhRepository {

    @Override
    public List<HeDieuHanhResponse> getAll() {
        List<HeDieuHanhResponse> list = new ArrayList<>();
        String sql = "select id,ten_he_dieu_hanh from he_dieu_hanh where deleted = 1 order by id desc";
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HeDieuHanhResponse dl = new HeDieuHanhResponse(rs.getInt(1), rs.getString(2));
                list.add(dl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Boolean add(HeDieuHanhResponse hdh) {
        int check = 0;
        String sql = """
                    INSERT INTO [dbo].[he_dieu_hanh]
                               ([ten_he_dieu_hanh])
                         VALUES
                               (?)
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, hdh.getHeDieuHanh());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    @Override
    public Boolean update(HeDieuHanhResponse hdh, int id) {
        int check = 0;
        String sql = """
        UPDATE [dbo].[he_dieu_hanh]
           SET [ten_he_dieu_hanh] = ?
              ,[nguoi_tao] =?
              ,[ngay_tao] =?
              ,[nguoi_sua] =?
              ,[ngay_sua] = ?
         WHERE id = ?
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, hdh.getHeDieuHanh());
            ps.setObject(2, hdh.getNguoiTao());
            ps.setObject(3, hdh.getNgayTao());
            ps.setObject(4, hdh.getNguoiSua());
            ps.setObject(5, hdh.getNgaySua());
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
                     update he_dieu_hanh set deleted = 0 where id = ?
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
    public List<HeDieuHanhResponse> getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<HeDieuHanhResponse> getTenHeDiuDieuHanh() {
        List<HeDieuHanhResponse> list = new ArrayList<>();
        String sql = "SELECT id, ten_he_dieu_hanh FROM he_dieu_hanh where deleted = 1 order by id desc";
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HeDieuHanhResponse hdh = new HeDieuHanhResponse();
                hdh.setId(rs.getInt("id"));
                hdh.setHeDieuHanh(rs.getString("ten_he_dieu_hanh"));
                list.add(hdh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
