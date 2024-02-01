/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.repositories.impl;

import com.raven.repositories.UserRepository;
import com.raven.untilities.DBConect;
import com.raven.viewmodels.User;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class UserRepositoryImpl implements UserRepository {

    String sql = "";
    Connection cn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    @Override
    public List<User> getAllUser() {
        List<User> listUser = new ArrayList<>();
        sql = "select so_dien_thoai,mat_khau,vai_tro from nhan_vien";
        cn = DBConect.getConnection();
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUsername(rs.getString(1));
                u.setPaswword(rs.getString(2));
                u.setRole(rs.getInt(3));
                listUser.add(u);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listUser;
    }

    @Override
    public User getUserByID(int id) {
        sql = "select so_dien_thoai,mat_khau from nhan_vien where id=?";
        cn = DBConect.getConnection();
        try {
            ps = cn.prepareStatement(sql);
            ps.setObject(1, id);
            while (rs.next()) {
                User u = new User();
                u.setUsername(rs.getString(1));
                u.setPaswword(rs.getString(2));
                return u;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer addUser(User u) {
        Integer r = null;
        sql = "	insert into nhan_vien (so_dien_thoai, mat_khau) values(?,?)";
        cn = DBConect.getConnection();
        try {
            ps = cn.prepareStatement(sql);
            ps.setObject(1, u.getUsername());
            ps.setObject(2, u.getPaswword());

            r = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public int getIDBySDT(String sdt) {
        sql = "	select id from nhan_vien where so_dien_thoai = ?";
        cn = DBConect.getConnection();
        int id = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setObject(1, sdt);
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return id = 0;
        }

    }

    @Override
    public Integer updateUser(User u) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getRoleByUseAndPass(String user, String pass) {
        sql = " select vai_tro from nhan_vien where so_dien_thoai = ? and mat_khau=?";
        cn = DBConect.getConnection();
        int role = 0;
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                role = rs.getInt(1);
            }
            return role;

        } catch (Exception e) {
            e.printStackTrace();
            return role = 0;
        }
    }

}
