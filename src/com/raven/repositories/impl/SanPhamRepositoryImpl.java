/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.repositories.impl;

import com.raven.repositories.SanPhamRepository;
import com.raven.untilities.DBConect;
import com.raven.viewmodels.RamResponse;
import com.raven.viewmodels.SanPhamResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nguye
 */
public class SanPhamRepositoryImpl implements SanPhamRepository {

    @Override
    public List<SanPhamResponse> getAll() {
        List<SanPhamResponse> list = new ArrayList<>();
        String sql = "select id,ten_san_pham,loai_san_pham from san_pham order by id desc";
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamResponse sp = new SanPhamResponse(rs.getInt(1), rs.getString(2), rs.getString(3));
                list.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Boolean add(SanPhamResponse sp) {
        int check = 0;
        String sql = """
     INSERT INTO [dbo].[san_pham]
                            ([ten_san_pham])
                      VALUES
                            (?)
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, sp.getTenSanPham());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    @Override
    public Boolean update(SanPhamResponse sp, int id) {
        int check = 0;
        String sql = """
     UPDATE [dbo].[san_pham]
        SET [ten_san_pham] = ?
           ,[loai_san_pham] = ?
           ,[nguoi_tao] = ?
           ,[ngay_tao] = ?
           ,[nguoi_sua] = ?
           ,[ngay_sua] = ?
           ,[deleted] = ?
      WHERE id =?
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, sp.getTenSanPham());
            ps.setObject(2, sp.getLoaiSanPham());
            ps.setObject(3, sp.getNguoiTao());
            ps.setObject(4, sp.getNgayTao());
            ps.setObject(5, sp.getNguoiSua());
            ps.setObject(6, sp.getNgaySua());
            ps.setObject(7, sp.getDeleted());
            ps.setObject(8, id);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    @Override
    public List<SanPhamResponse> getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<SanPhamResponse> getTenSanPham() {
        List<SanPhamResponse> list = new ArrayList<>();
        String sql = """
                     SELECT sp.id,
                            sp.ten_san_pham,
                            sp.loai_san_pham,
                            sp.nguoi_tao,
                            sp.ngay_tao,
                            sp.nguoi_sua,
                            sp.ngay_sua,
                            sp.deleted,
                            COALESCE(soluong.soluong, 0) AS soluong
                     FROM san_pham sp
                     LEFT JOIN (
                         SELECT ctsp.san_pham, COUNT(*) as soluong
                         FROM chi_tiet_san_pham ctsp 
                         LEFT JOIN nha_san_xuat nsx ON ctsp.nha_san_xuat = nsx.id 
                         LEFT JOIN dung_luong dl ON ctsp.dung_luong = dl.id 
                         LEFT JOIN mau_sac ms ON ctsp.mau_sac = ms.id 
                         LEFT JOIN he_dieu_hanh hdh ON ctsp.he_dieu_hanh = hdh.id 
                         LEFT JOIN gpu gu ON ctsp.gpu = gu.id 
                         LEFT JOIN ram r ON ctsp.ram = r.id 
                         LEFT JOIN cpu c ON ctsp.cpu = c.id 
                         LEFT JOIN man_hinh mh ON ctsp.man_hinh = mh.id 
                         LEFT JOIN chat_lieu cl ON ctsp.chat_lieu = cl.id 
                         GROUP BY ctsp.san_pham
                     ) AS soluong ON sp.id = soluong.san_pham
                     ORDER BY sp.id DESC;
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamResponse sp = new SanPhamResponse();
                sp.setIdSanPham(rs.getInt("id"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setLoaiSanPham(rs.getString("loai_san_pham"));
                sp.setNguoiTao(rs.getString("nguoi_tao"));
                sp.setNgayTao(rs.getDate("ngay_tao"));
                sp.setNguoiSua(rs.getString("nguoi_sua"));
                sp.setNgaySua(rs.getDate("ngay_sua"));
                sp.setDeleted(rs.getInt("deleted"));
                sp.setSoLuong(rs.getInt("soluong"));
                list.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<SanPhamResponse> showSPTheoTrangThai(int trangThai) {

        List<SanPhamResponse> list = new ArrayList<>();
        String sql = """
                  SELECT sp.id, sp.ten_san_pham, sp.loai_san_pham, sp.nguoi_tao,
                  sp.ngay_tao, sp.nguoi_sua, sp.ngay_sua, sp.deleted, 
                  COALESCE(soluong.soluong, 0) AS soluong FROM san_pham sp LEFT
                  JOIN (SELECT ctsp.san_pham, COUNT(*) as soluong FROM
                  chi_tiet_san_pham ctsp GROUP BY ctsp.san_pham) AS soluong ON 
                  sp.id = soluong.san_pham WHERE sp.deleted = ? ORDER BY sp.id DESC
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, trangThai);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamResponse sp = new SanPhamResponse();
                sp.setIdSanPham(rs.getInt("id"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setLoaiSanPham(rs.getString("loai_san_pham"));
                sp.setNguoiTao(rs.getString("nguoi_tao"));
                sp.setNgayTao(rs.getDate("ngay_tao"));
                sp.setNguoiSua(rs.getString("nguoi_sua"));
                sp.setNgaySua(rs.getDate("ngay_sua"));
                sp.setDeleted(rs.getInt("deleted"));
                sp.setSoLuong(rs.getInt("soluong"));
                list.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean updateStatus(int ttctsp, int ttsp) {
        int check = 0;
        String sql = "UPDATE chi_tiet_san_pham "
                + "SET deleted = ? "
                + "FROM chi_tiet_san_pham ctsp "
                + "INNER JOIN san_pham sp ON ctsp.san_pham = sp.id "
                + "WHERE sp.deleted = ?";

        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ttctsp);
            ps.setInt(2, ttsp);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return check > 0;
    }

    @Override
    public Boolean toggleStatus(int id) {
        int check = 0;
        String sql = """
                UPDATE san_pham SET deleted = CASE WHEN deleted = 0 THEN 1 ELSE 0 END
                WHERE id = ?
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
    public Boolean delete(int id) {
        int check = 0;
        String sql = """
               	 update san_pham set deleted = 0 where id = ?
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
    public Boolean restore(int id) {
        int check = 0;
        String sql = """
               	 update san_pham set deleted = 1 where id = ?
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
    public List<SanPhamResponse> keyPressed(String key) {
        List<SanPhamResponse> list = new ArrayList<>();
        String sql = """
                    SELECT 
                         sp.id,
                         sp.ten_san_pham,
                         sp.loai_san_pham,
                         sp.nguoi_tao,
                         sp.ngay_tao,
                         sp.nguoi_sua,
                         sp.ngay_sua,
                         sp.deleted,
                         ISNULL(COUNT(ctsp.san_pham), 0) AS soluong
                     FROM 
                         san_pham sp
                     LEFT JOIN chi_tiet_san_pham ctsp ON sp.id = ctsp.san_pham
                     LEFT JOIN nha_san_xuat nsx ON ctsp.nha_san_xuat = nsx.id 
                     LEFT JOIN dung_luong dl ON ctsp.dung_luong = dl.id 
                     LEFT JOIN mau_sac ms ON ctsp.mau_sac = ms.id 
                     LEFT JOIN he_dieu_hanh hdh ON ctsp.he_dieu_hanh = hdh.id 
                     LEFT JOIN gpu gu ON ctsp.gpu = gu.id 
                     LEFT JOIN ram r ON ctsp.ram = r.id 
                     LEFT JOIN cpu c ON ctsp.cpu = c.id 
                     LEFT JOIN man_hinh mh ON ctsp.man_hinh = mh.id 
                     LEFT JOIN chat_lieu cl ON ctsp.chat_lieu = cl.id 
                     GROUP BY sp.id, sp.ten_san_pham, sp.loai_san_pham, sp.nguoi_tao, sp.ngay_tao, sp.nguoi_sua, sp.ngay_sua, sp.deleted
                     HAVING
                         (
                             sp.id LIKE ? OR
                             sp.ten_san_pham LIKE ? OR 
                             sp.loai_san_pham LIKE ? OR 
                             sp.deleted LIKE ? OR
                             ISNULL(COUNT(ctsp.san_pham), 0) LIKE ?
                         )
                     ORDER BY sp.id DESC;
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 1; i <= 5; i++) {
                ps.setObject(i, "%" + key + "%");
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamResponse sp = new SanPhamResponse();
                sp.setIdSanPham(rs.getInt("id"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setLoaiSanPham(rs.getString("loai_san_pham"));
                sp.setNguoiTao(rs.getString("nguoi_tao"));
                sp.setNgayTao(rs.getDate("ngay_tao"));
                sp.setNguoiSua(rs.getString("nguoi_sua"));
                sp.setNgaySua(rs.getDate("ngay_sua"));
                sp.setDeleted(rs.getInt("deleted"));
                sp.setSoLuong(rs.getInt("soluong"));
                list.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
