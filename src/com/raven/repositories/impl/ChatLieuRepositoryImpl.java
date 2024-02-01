/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.repositories.impl;

import com.raven.repositories.ChatLieuRepository;
import com.raven.untilities.DBConect;
import com.raven.viewmodels.ChatLieuResponse;
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
public class ChatLieuRepositoryImpl implements ChatLieuRepository {

    @Override
    public List<ChatLieuResponse> getAll() {
        List<ChatLieuResponse> list = new ArrayList<>();
        String sql = "select id,ten_chat_lieu from chat_lieu where deleted = 1 order by id desc";
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChatLieuResponse cl = new ChatLieuResponse(rs.getInt(1), rs.getString(2));
                list.add(cl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Boolean add(ChatLieuResponse cl) {
        int check = 0;
        String sql = """
                  INSERT INTO [dbo].[chat_lieu]
                      ([ten_chat_lieu])
                  VALUES
                      (?);
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, cl.getTenChatLieu());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    @Override
    public Boolean update(ChatLieuResponse cl, int id) {
        int check = 0;
        String sql = """
                    UPDATE [dbo].[chat_lieu]
                        SET [ten_chat_lieu] = ?
                           ,[nguoi_tao] = ?
                           ,[ngay_tao] = ?
                           ,[nguoi_sua] = ?
                           ,[ngay_sua] = ?
                      WHERE id = ?
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, cl.getTenChatLieu());
            ps.setObject(2, cl.getNguoiTao());
            ps.setObject(3, cl.getNgayTao());
            ps.setObject(4, cl.getNguoiSua());
            ps.setObject(5, cl.getNgaySua());
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
                   update chat_lieu set deleted = 0 where id = ?
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
    public List<ChatLieuResponse> getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ChatLieuResponse> getTenChatLieu() {
        List<ChatLieuResponse> list = new ArrayList<>();
        String sql = "SELECT id,ten_chat_lieu FROM chat_lieu where deleted = 1 order by id desc";
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChatLieuResponse cl = new ChatLieuResponse();
               cl.setId(rs.getInt("id")); 
                cl.setTenChatLieu(rs.getString("ten_chat_lieu"));
                list.add(cl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
