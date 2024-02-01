/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.repositories.impl;

import com.raven.repositories.ImeiRepository;
import com.raven.untilities.DBConect;
import com.raven.viewmodels.ImeiResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nguye
 */
public class ImeiRepositoryImpl implements ImeiRepository {

    @Override
    public List<ImeiResponse> getAll() {
        List<ImeiResponse> list = new ArrayList<>();
        String sql = "select id,ma_imei from imei order by id desc";
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new ImeiResponse(rs.getInt(1), rs.getString(2)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Boolean add(ImeiResponse imei) {
        int check = 0;
        String sql = """
                    INSERT INTO [dbo].[imei]
                                          ([ma_imei])
                                    VALUES
                                          (?)
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, imei.getMaImei());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    @Override
    public Boolean update(ImeiResponse imei, int id) {
        int check = 0;
        String sql = """
        UPDATE [dbo].[imei]
           SET [ma_imei] = ?
              ,[nguoi_tao] = ?
              ,[ngay_tao] = ?
              ,[nguoi_sua] = ?
              ,[ngay_sua] = ?
              ,[deleted] = ?
         WHERE id = ?
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, imei.getMaImei());
            ps.setObject(2, imei.getNguoiTao());
            ps.setObject(3, imei.getNgayTao());
            ps.setObject(4, imei.getNguoiSua());
            ps.setObject(5, imei.getNgaySua());
            ps.setObject(6, imei.getDeleted());
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
                    delete from imei where id = ?
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
    public List<ImeiResponse> getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ImeiResponse> getMaImei() {
        List<ImeiResponse> list = new ArrayList<>();
        String sql = "SELECT id,ma_imei FROM imei order by id desc";
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ImeiResponse im = new ImeiResponse();
                im.setId(rs.getInt("id"));
                im.setMaImei(rs.getString("ma_imei"));
                list.add(im);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int iiii(String maIm) {
        int id = 0;
        String sql = """
                       select id from imei where ma_imei = ?
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maIm);
            ImeiResponse i = null;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public ImeiResponse imeiiii(String im) {

        String sql = """
                        select id from imei where ma_imei =?
            """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, im);
            ImeiResponse ime = null;
            ResultSet rs = ps.executeQuery();
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

}
