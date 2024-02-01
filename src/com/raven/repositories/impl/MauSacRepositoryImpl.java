/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.repositories.impl;

import com.raven.repositories.MauSacRepository;
import com.raven.untilities.DBConect;
import com.raven.viewmodels.ManHinhResponse;
import com.raven.viewmodels.MauSacResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nguye
 */
public class MauSacRepositoryImpl implements MauSacRepository {

    @Override
    public List<MauSacResponse> getAll() {
        List<MauSacResponse> list = new ArrayList<>();
        String sql = "select id,ten_mau_sac from mau_sac where deleted = 1 order by id desc";
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MauSacResponse ms = new MauSacResponse(rs.getInt(1), rs.getString(2));
                list.add(ms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Boolean add(MauSacResponse ms) {
        int check = 0;
        String sql = """
                   INSERT INTO [dbo].[mau_sac]
                                         ([ten_mau_sac])
                                   VALUES
                                         (?)
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, ms.getTenMauSac());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    @Override
    public Boolean update(MauSacResponse ms, int id) {
        int check = 0;
        String sql = """
        UPDATE [dbo].[mau_sac]
            SET [ten_mau_sac] = ?
               ,[nguoi_tao] = ?
               ,[ngay_tao] = ?
               ,[nguoi_sua] = ?
               ,[ngay_sua] = ?
          WHERE id = ?
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, ms.getTenMauSac());
            ps.setObject(2, ms.getNguoiTao());
            ps.setObject(3, ms.getNgayTao());
            ps.setObject(4, ms.getNguoiSua());
            ps.setObject(5, ms.getNgaySua());
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
                       update mau_sac set deleted = 0 where id = ?
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
    public List<MauSacResponse> getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<MauSacResponse> getTenMauSac() {
        List<MauSacResponse> list = new ArrayList<>();
        String sql = "SELECT id, ten_mau_sac FROM mau_sac where deleted = 1 order by id desc";
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MauSacResponse ms = new MauSacResponse();
                ms.setId(rs.getInt("id"));
                ms.setTenMauSac(rs.getString("ten_mau_sac"));

                list.add(ms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
