/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.services;

import com.raven.model.ModelBill;
import com.raven.viewmodels.ModelBillTable;
import com.raven.repositories.BillRepositoty;
import com.raven.untilities.DBConect;
import com.raven.viewmodels.ModelBillDetailTable;
import com.raven.viewmodels.ModelHistoryTable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vinhd
 */
public class BillService implements BillRepositoty {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    @Override
    public List<ModelBillTable> fillHoaDon(int trang) {
        sql = "select top 8 hoa_don.id, hoa_don.ten_nhan_vien, khach_hang.ten_khach_hang,\n"
                + "hoa_don.loai_hoa_don, hoa_don.ngay_tao, trang_thai.ten_trang_thai, phuong_thuc_thanh_toan.ten_phuong_thuc, hoa_don.ma_giao_dich\n"
                + " from hoa_don left join nhan_vien on hoa_don.ten_nhan_vien = nhan_vien.id\n"
                + "left join khach_hang on hoa_don.ten_khach_hang = khach_hang.id\n"
                + "left join trang_thai on hoa_don.trang_thai = trang_thai.id "
                + "left join phuong_thuc_thanh_toan on hoa_don.phuong_thuc_tt = phuong_thuc_thanh_toan.id \n"
                + "where hoa_don.id not in (select top " + (trang * 8 - 8) + " hoa_don.id from hoa_don order by hoa_don.id desc) "
                + "order by hoa_don.id desc";
        List<ModelBillTable> listBillTable = new ArrayList<>();
        try {
            con = DBConect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ModelBillTable modelBillTable = new ModelBillTable(rs.getInt(1),
                        rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getDate(5), rs.getString(6),
                        rs.getString(7), rs.getString(8));
                listBillTable.add(modelBillTable);
            }
            return listBillTable;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ModelBill selectHoaDonById(int id) {
        sql = "select hoa_don.id, hoa_don.ten_nhan_vien, khach_hang.ten_khach_hang,\n"
                + "                voucher.ma_voucher, trang_thai.ten_trang_thai, hoa_don.dia_chi_nguoi_nhan,\n"
                + "                ten_nguoi_nhan, hoa_don.so_dien_thoai,loai_hoa_don, tien_ship, sum(chi_tiet_san_pham.gia) + tien_ship ,tien_mat,\n"
                + "                tien_chuyen_khoan, hoa_don.ngay_tao, hoa_don.deleted \n"
                + "                from hoa_don left join nhan_vien  on hoa_don.ten_nhan_vien = nhan_vien.id\n"
                + "                left join trang_thai on hoa_don.trang_thai = trang_thai.id\n"
                + "                left join voucher on hoa_don.ma_voucher = voucher.id\n"
                + "                left join khach_hang on hoa_don.ten_khach_hang = khach_hang.id \n"
                + "				left join hoa_don_chi_tiet on hoa_don.id = hoa_don_chi_tiet.id_hoa_don\n"
                + "				left join chi_tiet_san_pham on chi_tiet_san_pham.id = hoa_don_chi_tiet.id_chi_tiet_san_pham\n"
                + "				where hoa_don.id = ?\n"
                + "				group by hoa_don.id, hoa_don.ten_nhan_vien, khach_hang.ten_khach_hang,\n"
                + "                voucher.ma_voucher, trang_thai.ten_trang_thai, hoa_don.dia_chi_nguoi_nhan,\n"
                + "                ten_nguoi_nhan, hoa_don.so_dien_thoai,loai_hoa_don, tien_ship,tien_mat,\n"
                + "                tien_chuyen_khoan, hoa_don.ngay_tao, hoa_don.deleted";
        try {
            ModelBill modelBill = null;
            con = DBConect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                modelBill = new ModelBill(rs.getInt(1), rs.getInt(2),
                        rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getString(8), rs.getString(9),
                        rs.getDouble(10), rs.getDouble(11),
                        rs.getDouble(12), rs.getDouble(13),
                        rs.getDate(14), rs.getBoolean(15));
            }
            return modelBill;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int add(String loaiHD, int trangThai, int idnv) {
        sql = "insert into hoa_don(trang_thai, loai_hoa_don, ten_nhan_vien) values(?, ?, ?)";
        try {
            con = DBConect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, trangThai);
            ps.setObject(2, loaiHD);
            ps.setObject(3, idnv);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleted() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ModelBill> selectAllHoaDon() {
        sql = "select hoa_don.id, hoa_don.ten_nhan_vien, khach_hang.ten_khach_hang,\n"
                + "voucher.ma_voucher, trang_thai.ten_trang_thai, hoa_don.dia_chi_nguoi_nhan,\n"
                + "ten_nguoi_nhan, hoa_don.so_dien_thoai,loai_hoa_don, tien_ship, tong_tien,tien_mat,\n"
                + "tien_chuyen_khoan, hoa_don.ngay_tao, hoa_don.deleted \n"
                + "from hoa_don left join nhan_vien  on hoa_don.ten_nhan_vien = nhan_vien.id\n"
                + "left join trang_thai on hoa_don.trang_thai = trang_thai.id\n"
                + "left join voucher on hoa_don.ma_voucher = voucher.id\n"
                + "left join khach_hang on hoa_don.ten_khach_hang = khach_hang.id";
        List<ModelBill> listBill = new ArrayList<>();
        try {
            con = DBConect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ModelBill modelBill = new ModelBill(rs.getInt(1), rs.getInt(2),
                        rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getString(8), rs.getString(9),
                        rs.getDouble(10), rs.getDouble(11),
                        rs.getDouble(12), rs.getDouble(13),
                        rs.getDate(14), rs.getBoolean(15));
                listBill.add(modelBill);
            }
            return listBill;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ModelBillDetailTable> fillHoaDonChiTiet(int idHoaDon, int soTrangHDCT) {
        sql = "select top 8 san_pham.ten_san_pham, imei.ma_imei, chi_tiet_san_pham.gia\n"
                + "from hoa_don_chi_tiet left join chi_tiet_san_pham on hoa_don_chi_tiet.id_chi_tiet_san_pham = chi_tiet_san_pham.id\n"
                + "left join hoa_don on hoa_don_chi_tiet.id_hoa_don = hoa_don.id\n"
                + "left join imei on chi_tiet_san_pham.imei = imei.id\n"
                + "left join san_pham on chi_tiet_san_pham.san_pham = san_pham.id\n"
                + "where hoa_don_chi_tiet.id_hoa_don =  ? and hoa_don_chi_tiet.id not "
                + "in (select top " + (soTrangHDCT * 8 - 8) + " hoa_don_chi_tiet.id from hoa_don_chi_tiet)";
        List<ModelBillDetailTable> listBillDetailTable = new ArrayList<>();
        try {
            con = DBConect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, idHoaDon);
            rs = ps.executeQuery();
            while (rs.next()) {
                ModelBillDetailTable modelBillDetail = new ModelBillDetailTable(rs.getString(1), rs.getString(2), rs.getDouble(3));
                listBillDetailTable.add(modelBillDetail);
            }
            return listBillDetailTable;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ModelBillTable> countHoaDon(String timKiem, String loaiHoaDon, String trangThai, String start, String end) {
        sql = "SELECT  hoa_don.id, hoa_don.ten_nhan_vien , khach_hang.ten_khach_hang, \n"
                + "hoa_don.loai_hoa_don, hoa_don.ngay_tao, trang_thai.ten_trang_thai, phuong_thuc_thanh_toan.ten_phuong_thuc, hoa_don.ma_giao_dich \n"
                + "FROM hoa_don LEFT JOIN nhan_vien ON hoa_don.ten_nhan_vien = nhan_vien.id \n"
                + "LEFT JOIN khach_hang ON hoa_don.ten_khach_hang = khach_hang.id \n"
                + "LEFT JOIN trang_thai ON hoa_don.trang_thai = trang_thai.id "
                + "left join phuong_thuc_thanh_toan on hoa_don.phuong_thuc_tt = phuong_thuc_thanh_toan.id \n"
                + "WHERE  \n"
                + " (hoa_don.ten_nhan_vien = TRY_CAST(? as int) \n"
                + "OR khach_hang.ten_khach_hang LIKE ? \n"
                + "OR hoa_don.id = TRY_CAST(? as int)) \n"
                + "AND hoa_don.loai_hoa_don like ? \n"
                + "AND trang_thai.ten_trang_thai like ? \n"
                + "AND hoa_don.ngay_tao between try_cast(? as date) and try_cast(? as date)";
        List<ModelBillTable> listBillTable = new ArrayList<>();
        try {
            con = DBConect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1,  timKiem );
            ps.setObject(2, "%" + timKiem + "%");
            ps.setObject(3, timKiem);
            ps.setObject(4, "%" + loaiHoaDon + "%");
            ps.setObject(5, "%" + trangThai + "%");
            ps.setObject(6, start);
            ps.setObject(7, end);
            rs = ps.executeQuery();
            while (rs.next()) {
                ModelBillTable modelBillTable = new ModelBillTable(rs.getInt(1),
                        rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getDate(5), rs.getString(6),
                        rs.getString(7), rs.getString(8));
                listBillTable.add(modelBillTable);
            }
            return listBillTable;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int countHDCT() {
        sql = "select count(*) from hoa_don_chi_tiet";
        int count = 0;
        try {
            con = DBConect.getConnection();
            ps = con.prepareStatement(sql);
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

    public int countLichSu() {
        sql = "select count(*) from hoa_don lich_su_mua_hang";
        int count = 0;
        try {
            con = DBConect.getConnection();
            ps = con.prepareStatement(sql);
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
    public int updateStatus(String trangThai, int id) {
        sql = "update hoa_don set trang_thai = " + trangThai + " where id like " + id + "";
        try {
            con = DBConect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, trangThai);
            ps.setObject(2, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<ModelBillTable> timKiem(int trang, String timKiem, String loaiHoaDon, String trangThai, String start, String end) {
        sql = "SELECT TOP 8 hoa_don.id, hoa_don.ten_nhan_vien, khach_hang.ten_khach_hang,\n"
                + "hoa_don.loai_hoa_don, hoa_don.ngay_tao, trang_thai.ten_trang_thai, phuong_thuc_thanh_toan.ten_phuong_thuc, hoa_don.ma_giao_dich\n"
                + "FROM hoa_don\n"
                + " LEFT JOIN nhan_vien ON hoa_don.ten_nhan_vien = nhan_vien.id\n"
                + " LEFT JOIN khach_hang ON hoa_don.ten_khach_hang = khach_hang.id\n"
                + " LEFT JOIN trang_thai ON hoa_don.trang_thai = trang_thai.id\n"
                + " LEFT JOIN phuong_thuc_thanh_toan ON hoa_don.phuong_thuc_tt = phuong_thuc_thanh_toan.id\n"
                + "WHERE hoa_don.id NOT IN (\n"
                + "    SELECT TOP " + (trang * 8 - 8) + " hoa_don.id\n"
                + "    FROM hoa_don\n"
                + "     LEFT JOIN nhan_vien ON hoa_don.ten_nhan_vien = nhan_vien.id\n"
                + "     LEFT JOIN khach_hang ON hoa_don.ten_khach_hang = khach_hang.id\n"
                + "     LEFT JOIN trang_thai ON hoa_don.trang_thai = trang_thai.id\n"
                + "     LEFT JOIN phuong_thuc_thanh_toan ON hoa_don.phuong_thuc_tt = phuong_thuc_thanh_toan.id\n"
                + "    WHERE (hoa_don.ten_nhan_vien = TRY_CAST(? as int)\n"
                + "    OR khach_hang.ten_khach_hang LIKE ?\n"
                + "    OR hoa_don.id = TRY_CAST(? as int))\n"
                + "    AND hoa_don.loai_hoa_don like ?\n"
                + "    AND trang_thai.ten_trang_thai like ?\n"
                + "    AND hoa_don.ngay_tao between try_cast(? as date) and try_cast(? as date)\n"
                + "    ORDER BY hoa_don.id desc )\n"
                + "AND (hoa_don.ten_nhan_vien = TRY_CAST(? as int)\n"
                + "OR khach_hang.ten_khach_hang LIKE ?\n"
                + "OR hoa_don.id = TRY_CAST(? as int))\n"
                + "AND hoa_don.loai_hoa_don like ?\n"
                + "AND trang_thai.ten_trang_thai like ?\n"
                + "AND hoa_don.ngay_tao between try_cast(? as date) and try_cast(? as date)\n"
                + "ORDER BY hoa_don.id desc";
        List<ModelBillTable> listBillTable = new ArrayList<>();
        try {
            con = DBConect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, timKiem);
            ps.setObject(2, "%" + timKiem + "%");
            ps.setObject(3, timKiem);
            ps.setObject(4, "%" + loaiHoaDon + "%");
            ps.setObject(5, "%" + trangThai + "%");
            ps.setObject(6, start);
            ps.setObject(7, end);
            ps.setObject(8, timKiem);
            ps.setObject(9, "%" + timKiem + "%");
            ps.setObject(10, timKiem);
            ps.setObject(11, "%" + loaiHoaDon + "%");
            ps.setObject(12, "%" + trangThai + "%");
            ps.setObject(13, start);
            ps.setObject(14, end);
            rs = ps.executeQuery();
            while (rs.next()) {
                ModelBillTable modelBillTable = new ModelBillTable(rs.getInt(1),
                        rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getDate(5), rs.getString(6),
                        rs.getString(7), rs.getString(8));
                listBillTable.add(modelBillTable);
            }
            return listBillTable;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ModelHistoryTable> fillLichSu(int idHoaDon, int soTrangLichSu) {
        sql = "select thoi_gian, lich_su_mua_hang.ngay_tao, hanh_dong, nhan_vien.ten_nhan_vien \n"
                + "from lich_su_mua_hang left join hoa_don on lich_su_mua_hang.id_hoa_don = hoa_don.id "
                + "left join nhan_vien on lich_su_mua_hang.nguoi_tao = nhan_vien.id \n"
                + "where lich_su_mua_hang.id_hoa_don = ?";
        List<ModelHistoryTable> listHistory = new ArrayList<>();
        try {
            con = DBConect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, idHoaDon);
            rs = ps.executeQuery();
            while (rs.next()) {
                ModelHistoryTable history = new ModelHistoryTable(rs.getTime(1),
                        rs.getDate(2), rs.getString(3), rs.getString(4));
                listHistory.add(history);
            }
            return listHistory;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
