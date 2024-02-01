/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.repositories.impl;

import com.raven.repositories.NhaSanXuatRepository;
import com.raven.untilities.DBConect;
import com.raven.viewmodels.MauSacResponse;
import com.raven.viewmodels.NhaSanXuatResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nguye
 */
public class NhaSanXuatRepositoryImpl implements NhaSanXuatRepository {

    @Override
    public List<NhaSanXuatResponse> getAll() {
        List<NhaSanXuatResponse> list = new ArrayList<>();
        String sql = "select id,ten_nha_san_xuat from nha_san_xuat where deleted = 1 order by id desc";
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhaSanXuatResponse nsx = new NhaSanXuatResponse(rs.getInt(1), rs.getString(2));
                list.add(nsx);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Boolean add(NhaSanXuatResponse nsx) {
        int check = 0;
        String sql = """
                  INSERT INTO [dbo].[nha_san_xuat]
                                                    ([ten_nha_san_xuat])
                                              VALUES
                                                    (?)
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, nsx.getTenNhaSanXuat());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    @Override
    public Boolean update(NhaSanXuatResponse nsx, int id) {
        int check = 0;
        String sql = """
      UPDATE [dbo].[nha_san_xuat]
         SET [ten_nha_san_xuat] = ?
            ,[nguoi_tao] = ?
            ,[ngay_tao] =?
            ,[nguoi_sua] = ?
            ,[ngay_sua] = ?
       WHERE id = ?
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, nsx.getTenNhaSanXuat());
            ps.setObject(2, nsx.getNguoiTao());
            ps.setObject(3, nsx.getNgayTao());
            ps.setObject(4, nsx.getNguoiSua());
            ps.setObject(5, nsx.getNgaySua());
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
                       update nha_san_xuat set deleted = 0 where id = ?
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
    public List<NhaSanXuatResponse> getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<NhaSanXuatResponse> getTenNhaSanXuat() {
        List<NhaSanXuatResponse> list = new ArrayList<>();
        String sql = "SELECT id, ten_nha_san_xuat FROM nha_san_xuat where deleted = 1 order by id desc";
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhaSanXuatResponse nsx = new NhaSanXuatResponse();
                nsx.setId(rs.getInt("id"));
                nsx.setTenNhaSanXuat(rs.getString("ten_nha_san_xuat"));
                list.add(nsx);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
