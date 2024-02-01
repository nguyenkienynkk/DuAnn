package com.raven.repositories.impl;

import com.raven.domainmodels.ModelDiaChi;
import com.raven.domainmodels.ModelKhachHang;
import com.raven.repositories.KhachHangRepository;
import com.raven.untilities.DBConect;
import com.raven.viewmodels.LichSuMuaHang;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class KhachHangRepositoryIplm implements KhachHangRepository {

    Connection cn = null;
    String sql = "";
    PreparedStatement ps = null;
    ResultSet rs = null;

    @Override
    public List<ModelKhachHang> getKhachHangByPage(int page, int limit) {
        List<ModelKhachHang> listKH = new ArrayList<>();
        sql = """
              SELECT
                  kh.id,
                  kh.ten_khach_hang,
                  kh.gioi_tinh,
                  kh.ngay_sinh,
                  kh.so_dien_thoai,
                  kh.email,
                  dc.dia_chi_cu_the
              FROM
                  khach_hang kh
              JOIN
                  dia_chi dc ON dc.id = kh.dia_chi
              WHERE
                  kh.deleted = 1
              ORDER BY
                  kh.id desc
              OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
            """;
        cn = DBConect.getConnection();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, (page - 1) * limit);
            ps.setInt(2, limit);
            rs = ps.executeQuery();
            while (rs.next()) {
                ModelKhachHang kh = new ModelKhachHang();
                kh.setID(rs.getInt(1));
                kh.setTenKhachHang(rs.getString(2));
                kh.setGioiTinh(rs.getInt(3));
                kh.setNgaySinh(rs.getDate(4));
                kh.setSDT(rs.getString(5));
                kh.setEmail(rs.getString(6));
                ModelDiaChi dc = new ModelDiaChi();
                dc.setDiaChiCuThe(rs.getString(7));
                kh.setDiaChiMacDinh(dc);
                listKH.add(kh);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listKH;
    }

    @Override
    public int CountKhachHang() {
        sql = """
                select count(*) from  khach_hang 
                JOIN  dia_chi dc ON dc.id = khach_hang.dia_chi
                where khach_hang.deleted = 1
              """;
        cn = DBConect.getConnection();
        int count = 0;
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);

            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getMaxPage(int countKH) {
        int totalcount = CountKhachHang();
        int maxPage = totalcount / countKH;
        if (totalcount % countKH != 0) {
            maxPage++;
        }
        return maxPage;
    }

    @Override
    public Integer addKhachHang(ModelKhachHang kh) {
        sql = "INSERT INTO khach_hang(ten_khach_hang, gioi_tinh, ngay_sinh, so_dien_thoai, email,dia_chi)VALUES (?,?,?,?,?,?)";
        cn = DBConect.getConnection();
        Integer r = null;
        try {
            ps = cn.prepareStatement(sql);
            ps.setObject(1, kh.getTenKhachHang());
            ps.setObject(2, kh.getGioiTinh());
            ps.setObject(3, kh.getNgaySinh());
            ps.setObject(4, kh.getSDT());
            ps.setObject(5, kh.getEmail());
            ps.setObject(6, kh.getDiaChiMacDinh().getID());

            r = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public Integer updateKhachHang(ModelKhachHang kh) {
        sql = "update khach_hang set ten_khach_hang=?, gioi_tinh=?, ngay_sinh=? , so_dien_thoai=?, email=? where id=?";
        cn = DBConect.getConnection();
        Integer r = null;
        try {
            ps = cn.prepareStatement(sql);
            ps.setObject(1, kh.getTenKhachHang());
            ps.setObject(2, kh.getGioiTinh());
            ps.setObject(3, kh.getNgaySinh());
            ps.setObject(4, kh.getSDT());
            ps.setObject(5, kh.getEmail());
            ps.setObject(6, kh.getID());
            r = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public int getIdBySoDienThoai(String sdt) {
        int idKhachHang = 0;
        sql = " select id from khach_hang where so_dien_thoai= ?";
        cn = DBConect.getConnection();
        try {
            ps = cn.prepareStatement(sql);
            ps.setObject(1, sdt);
            rs = ps.executeQuery();
            while (rs.next()) {
                idKhachHang = rs.getInt(1);
            }
            return idKhachHang;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getIDDiaChi(int idKhachHang) {
        int idDiaChi = 0;
        sql = """
           SELECT dia_chi.id
           FROM dia_chi
           JOIN khach_hang ON khach_hang.dia_chi = dia_chi.id
           WHERE khach_hang.id = ?
           """;
        cn = DBConect.getConnection();
        try {
            ps = cn.prepareStatement(sql);
            ps.setObject(1, idKhachHang);
            rs = ps.executeQuery();
            while (rs.next()) {
                idDiaChi = rs.getInt(1);
            }
            return idDiaChi;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Integer deleteKhachHang(int id) {
        Integer r = null;
        sql = "update khach_hang set deleted = 0 where id = ?";
        cn = DBConect.getConnection();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, id);

            r = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public List<LichSuMuaHang> getLichSuMuaHangByIDKhachHang(int idKH) {
        List<LichSuMuaHang> listLS = new ArrayList<>();
        sql = """
              select hoa_don.id ,hoa_don.ngay_tao ,  tong_tien from hoa_don
              join khach_hang on khach_hang.id = hoa_don.ten_khach_hang
              where khach_hang.id= ?
              order by hoa_don.id desc
             """;
        cn = DBConect.getConnection();
        try {
            ps = cn.prepareStatement(sql);
            ps.setObject(1, idKH);
            rs = ps.executeQuery();
            while (rs.next()) {
                LichSuMuaHang ls = new LichSuMuaHang();
                ls.setIdHoaDon(rs.getInt(1));
                ls.setNgayTao(rs.getDate(2));
                ls.setTongTien(rs.getFloat(3));

                listLS.add(ls);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listLS;
    }

    @Override
    public List<ModelKhachHang> getKhachHangDeletedByPage(int page, int limit) {
        List<ModelKhachHang> listKH = new ArrayList<>();
        sql = """
              SELECT
                  kh.id,
                  kh.ten_khach_hang,
                  kh.gioi_tinh,
                  kh.ngay_sinh,
                  kh.so_dien_thoai,
                  kh.email,
                  dc.dia_chi_cu_the
              FROM
                  khach_hang kh
              JOIN
                  dia_chi dc ON dc.id = kh.dia_chi
              WHERE
                  kh.deleted = 0
              ORDER BY
                  kh.id desc
              OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
            """;
        cn = DBConect.getConnection();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, (page - 1) * limit);
            ps.setInt(2, limit);
            rs = ps.executeQuery();
            while (rs.next()) {
                ModelKhachHang kh = new ModelKhachHang();
                kh.setID(rs.getInt(1));
                kh.setTenKhachHang(rs.getString(2));
                kh.setGioiTinh(rs.getInt(3));
                kh.setNgaySinh(rs.getDate(4));
                kh.setSDT(rs.getString(5));
                kh.setEmail(rs.getString(6));
                ModelDiaChi dc = new ModelDiaChi();
                dc.setDiaChiCuThe(rs.getString(7));
                kh.setDiaChiMacDinh(dc);
                listKH.add(kh);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listKH;
    }

    @Override
    public int CountKhachHangDeletd() {
        sql = """
                select count(*) from  khach_hang 
                JOIN  dia_chi dc ON dc.id = khach_hang.dia_chi
                where khach_hang.deleted = 0
              """;
        cn = DBConect.getConnection();
        int count = 0;
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);

            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getMaxPageDeleted(int countKH) {
        int total = CountKhachHangDeletd();
        int maxPage = total / countKH;
        if (total % countKH != 0) {
            maxPage++;
        }
        return maxPage;
    }

    @Override
    public Integer restoreKhachHang(int idKhachHang) {
        Integer r = null;
        sql = "update khach_hang set deleted = 1 where id = ?";
        cn = DBConect.getConnection();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, idKhachHang);

            r = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public List<ModelKhachHang> FindByAll(String keyword, int gioiTinh) {
        List<ModelKhachHang> listKH = new ArrayList<>();
        sql = """
                select ten_khach_hang,gioi_tinh,ngay_sinh,so_dien_thoai,email,dia_chi.dia_chi_cu_the from khach_hang
                                join dia_chi on dia_chi.id = khach_hang.dia_chi 
                                where khach_hang.deleted = 1 and(
                                ten_khach_hang like ? or
                                so_dien_thoai like ? or
                                email like ? or
                                dia_chi_nguoi_nhan like ?) and
                                gioi_tinh = ?
            """;
        cn = DBConect.getConnection();
        try {
            ps = cn.prepareStatement(sql);
            for (int i = 1; i <= 4; i++) {
                ps.setObject(i, "%" + keyword + "%");

            }
            ps.setObject(5, gioiTinh);
            rs = ps.executeQuery();
            while (rs.next()) {
                ModelKhachHang kh = new ModelKhachHang();
                kh.setTenKhachHang(rs.getString(1));
                kh.setGioiTinh(rs.getInt(2));
                kh.setNgaySinh(rs.getDate(3));
                kh.setSDT(rs.getString(4));
                kh.setEmail(rs.getString(5));

                ModelDiaChi dc = new ModelDiaChi();
                dc.setDiaChiCuThe(rs.getString(6));
                kh.setDiaChiMacDinh(dc);
                listKH.add(kh);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listKH;
    }

    @Override
    public List<ModelKhachHang> FindByGioiTinh(int gioiTinh) {
        List<ModelKhachHang> listKH = new ArrayList<>();
        sql = """
            	select ten_khach_hang,gioi_tinh,ngay_sinh,so_dien_thoai,email,dia_chi.dia_chi_cu_the from khach_hang
            	join dia_chi on dia_chi.id = khach_hang.dia_chi 
            	where khach_hang.deleted = 1 and gioi_tinh =?
            """;
        cn = DBConect.getConnection();
        try {
            ps = cn.prepareStatement(sql);
            ps.setObject(1, gioiTinh);
            rs = ps.executeQuery();
            while (rs.next()) {
                ModelKhachHang kh = new ModelKhachHang();
                kh.setTenKhachHang(rs.getString(1));
                kh.setGioiTinh(rs.getInt(2));
                kh.setNgaySinh(rs.getDate(3));
                kh.setSDT(rs.getString(4));
                kh.setEmail(rs.getString(5));

                ModelDiaChi dc = new ModelDiaChi();
                dc.setDiaChiCuThe(rs.getString(6));
                kh.setDiaChiMacDinh(dc);

                listKH.add(kh);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listKH;
    }

    @Override
    public int getIDDiaChiByDiaChi(String diaChi) {
        sql = "select id from dia_chi where dia_chi_cu_the=?";
        int id = 0;
        cn = DBConect.getConnection();
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, diaChi);
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);

            }
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
