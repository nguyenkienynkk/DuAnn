/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raven.repositories.impl;

import com.raven.repositories.NhanVienRepository;
import com.raven.untilities.DBConect;
import com.raven.viewmodels.NhanVienResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nguye
 */
public class NhanVienRepositoryImpl implements NhanVienRepository {

    @Override
    public List<NhanVienResponse> getAll() {
        List<NhanVienResponse> list = new ArrayList<>();
        String sql = """
                     select id,ma_nhan_vien,ten_nhan_vien,ngay_sinh,dia_chi,gioi_tinh,luong,email,so_dien_thoai,mat_khau,so_cccd,vai_tro,deleted from nhan_vien where deleted = 1
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new NhanVienResponse(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5), rs.getBoolean(6), rs.getFloat(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getBoolean(12), rs.getBoolean(13)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Boolean add(NhanVienResponse kh) {
        int check = 0;
        String sql = """
                     INSERT INTO [dbo].[nhan_vien]
                                           ([ma_nhan_vien]
                                           ,[ten_nhan_vien]
                                           ,[ngay_sinh]
                                           ,[dia_chi]
                                           ,[gioi_tinh]
                                           ,[luong]
                                           ,[email]
                                           ,[so_dien_thoai]
                                           ,[mat_khau]
                                           ,[so_cccd]
                                           ,[vai_tro])
                                     VALUES
                                           (?
                                           ,?
                                           ,?
                                           ,?
                                           ,?
                                           ,?
                                           ,?
                                           ,?
                                           ,?
                                           ,?
                                           ,?)
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, kh.getMaNhanVien());
            ps.setObject(2, kh.getTenNhanVien());
            ps.setObject(3, kh.getNgaySinh());
            ps.setObject(4, kh.getDiaChi());
            ps.setObject(5, kh.isGioiTinh());
            ps.setObject(6, kh.getLuong());
            ps.setObject(7, kh.getEmail());
            ps.setObject(8, kh.getSoDienThoai());
            ps.setObject(9, kh.getMatKhau());
            ps.setObject(10, kh.getSoCccd());
            ps.setObject(11, kh.isVaiTro());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    @Override
    public Boolean update(NhanVienResponse kh, int id) {
        int check = 0;
        String sql = """
                   UPDATE [dbo].[nhan_vien]
                        SET [ma_nhan_vien] =?
                           ,[ten_nhan_vien] = ?
                           ,[ngay_sinh] =?
                           ,[dia_chi] = ?
                           ,[gioi_tinh] = ?
                           ,[luong] =?
                           ,[email] =?
                           ,[so_dien_thoai] =?
                           ,[mat_khau] = ?
                           ,[so_cccd] =?
                           ,[vai_tro] = ?
                      WHERE id = ?
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, kh.getMaNhanVien());
            ps.setObject(2, kh.getTenNhanVien());
            ps.setObject(3, kh.getNgaySinh());
            ps.setObject(4, kh.getDiaChi());
            ps.setObject(5, kh.isGioiTinh());
            ps.setObject(6, kh.getLuong());
            ps.setObject(7, kh.getEmail());
            ps.setObject(8, kh.getSoDienThoai());
            ps.setObject(9, kh.getMatKhau());
            ps.setObject(10, kh.getSoCccd());
            ps.setObject(11, kh.isVaiTro());
            ps.setObject(12, id);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    @Override
    public Boolean delete(int ma) {
        int check = 0;
        String sql = """
                    update nhan_vien set deleted = 0 where id = ?
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, ma);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    @Override
    public int getIDBySDT(String sdt) {
        String sql = "select id from nhan_vien where so_dien_thoai=?";
        int id = 0;
        try (Connection cn = DBConect.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setObject(1, sdt);
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
    public boolean isPhoneNumberInUse(int currentEmployeeId) {
        String sql = "SELECT id FROM nhan_vien WHERE id != ?";
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, currentEmployeeId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getIDByMaNV(String ma) {
        String sql = "select id from nhan_vien where ma_nhan_vien = ?";
        int id = 0;
        try (Connection cn = DBConect.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setObject(1, ma);
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
    public int getIDByCCCD(String cccd) {
        String sql = "select id from nhan_vien where so_cccd = ?";
        int id = 0;
        try (Connection cn = DBConect.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setObject(1, cccd);
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
    public boolean isCodeStaffInUse(String codeStaff, int currentEmployeeId) {
        String sql = "SELECT COUNT(*) FROM nhan_vien WHERE ma_nhan_vien = ? AND id != ?";

        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codeStaff);
            ps.setInt(2, currentEmployeeId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean isCCCDInUse(String cCCD, int currentEmployeeId) {
        String sql = "SELECT COUNT(*) FROM nhan_vien WHERE so_cccd = ? AND id != ?";

        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cCCD);
            ps.setInt(2, currentEmployeeId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<NhanVienResponse> searchAllField(String keyword) {
        List<NhanVienResponse> list = new ArrayList<>();
        String sql = """
                     select id,
                     ma_nhan_vien,
                     ten_nhan_vien,
                     ngay_sinh,
                     dia_chi,
                     gioi_tinh,
                     luong,
                     email,
                     so_dien_thoai,
                     mat_khau,
                     so_cccd,
                     deleted,
                     vai_tro from nhan_vien where deleted = 1 AND
                     (ma_nhan_vien LIKE ? OR 
                     ten_nhan_vien LIKE ? OR 
                     ngay_sinh LIKE ? OR 
                     dia_chi LIKE ? OR 
                     gioi_tinh LIKE ? OR 
                     luong LIKE ? OR 
                     email LIKE ? OR 
                     so_dien_thoai LIKE ? OR 
                     so_cccd LIKE ?)
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 1; i <= 9; i++) {
                ps.setObject(i, "%" + keyword + "%");
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new NhanVienResponse(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5), rs.getBoolean(6), rs.getFloat(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getBoolean(12), rs.getBoolean(13)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public NhanVienResponse getOne(String SDT) {

        NhanVienResponse nv = null;
        String sql = """
                     select id,ma_nhan_vien,ten_nhan_vien,ngay_sinh,dia_chi,gioi_tinh,
                     luong,email,so_dien_thoai,mat_khau,so_cccd,vai_tro,deleted from nhan_vien where so_dien_thoai = ?
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, SDT);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                nv = new NhanVienResponse(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5), rs.getBoolean(6), rs.getFloat(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getBoolean(12), rs.getBoolean(13));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nv;

    }

    @Override
    public NhanVienResponse getOneByID(int id) {
        NhanVienResponse nv = null;
        String sql = """
                     select id,ma_nhan_vien,ten_nhan_vien,ngay_sinh,dia_chi,gioi_tinh,
                     luong,email,so_dien_thoai,mat_khau,so_cccd,vai_tro,deleted from nhan_vien where id = ?
                     """;
        try (Connection conn = DBConect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                nv = new NhanVienResponse(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5), rs.getBoolean(6), rs.getFloat(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getBoolean(12), rs.getBoolean(13));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nv;
    }

}
