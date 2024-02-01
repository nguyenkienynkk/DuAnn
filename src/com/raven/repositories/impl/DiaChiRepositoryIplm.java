/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.repositories.impl;

import com.raven.domainmodels.ModelDiaChi;
import com.raven.repositories.DiaChiRepository;
import com.raven.untilities.DBConect;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class DiaChiRepositoryIplm implements DiaChiRepository {

    Connection cn = null;
    String sql = "";
    PreparedStatement ps = null;
    ResultSet rs = null;

    @Override
    public List<ModelDiaChi> getAllDiaChi() {
        List<ModelDiaChi> listDC = new ArrayList<>();
        sql = "select id, dia_chi_cu_the from dia_chi ";
        cn = DBConect.getConnection();
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ModelDiaChi dc = new ModelDiaChi();
                dc.setID(rs.getInt(1));
                dc.setDiaChiCuThe(rs.getString(2));
                listDC.add(dc);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDC;

    }

    @Override
    public int addDiaChi(String diaChiMacDinh) {
        sql = "insert into dia_chi(dia_chi_cu_the) values (?)";
        cn = DBConect.getConnection();
        int idDiaChi = -1;
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, diaChiMacDinh);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idDiaChi = rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return idDiaChi;
    }

    @Override
    public Integer updateDiaChi(ModelDiaChi diaChi) {
        sql = "update dia_chi set dia_chi_cu_the=? where id=?";
        cn = DBConect.getConnection();
        Integer r = null;
        try {
            ps = cn.prepareStatement(sql);
            ps.setObject(1, diaChi.getDiaChiCuThe());
            ps.setObject(2, diaChi.getID());
            r = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public Integer deleteDiaChi(int id) {
        sql = "delete from dia_chi where id=?";
        cn = DBConect.getConnection();
        Integer r = null;
        try {
            ps = cn.prepareStatement(sql);
            ps.setObject(1, id);

            r = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;

    }

    @Override
    public Integer addDiaChiInteger(ModelDiaChi diaChi) {
        sql = "INSERT INTO dia_chi(dia_chi_cu_the) VALUES (?)";
        cn = DBConect.getConnection();
        Integer generatedId = null;
        try {
            ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, diaChi.getDiaChiCuThe());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return generatedId;

    }

}
