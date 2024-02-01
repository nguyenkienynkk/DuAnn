/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.services;

import com.raven.untilities.DBConect;
import com.raven.viewmodels.GioHangModel;
import com.raven.viewmodels.HoaDoninBanHang;
import com.raven.viewmodels.VoucherInBanHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vinhd
 */
public class BanHangService {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<HoaDoninBanHang> fillHoaDon() {
        sql = "select hoa_don.id,CONVERT(int, count(hoa_don_chi_tiet.id)), hoa_don.ngay_tao, hoa_don.ten_nhan_vien, trang_thai.ten_trang_thai\n"
                + "                from hoa_don left join nhan_vien on hoa_don.ten_nhan_vien = nhan_vien.id\n"
                + "                left join khach_hang on hoa_don.ten_khach_hang = khach_hang.id\n"
                + "                left join trang_thai on hoa_don.trang_thai = trang_thai.id\n"
                + "				left join hoa_don_chi_tiet on hoa_don.id = hoa_don_chi_tiet.id_hoa_don\n"
                + "				where trang_thai.ten_trang_thai = N'Chờ thanh toán' or trang_thai.ten_trang_thai = N'Chờ giao hàng' "
                + "or trang_thai.ten_trang_thai = N'Đang giao'\n"
                + "				group by hoa_don.id, hoa_don.ngay_tao, hoa_don.ten_nhan_vien, trang_thai.ten_trang_thai\n"
                + "                order by hoa_don.id desc";
        List<HoaDoninBanHang> list = new ArrayList<>();
        try {
            con = DBConect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDoninBanHang sv = new HoaDoninBanHang(rs.getInt(1),
                        rs.getInt(2), rs.getDate(3), rs.getInt(4), rs.getString(5));
                list.add(sv);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getIDctsp(String ma_imei) {
        sql = "select chi_tiet_san_pham.id\n"
                + " from chi_tiet_san_pham left join imei on chi_tiet_san_pham.imei = imei.id\n"
                + " where ma_imei = ?";
        try {
            con = DBConect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ma_imei);
            rs = ps.executeQuery();

            // Thêm dòng này để di chuyển con trỏ kết quả đến hàng đầu tiên
            if (rs.next()) {
                return rs.getInt("id"); // Sử dụng tên cột thay vì câu truy vấn
            } else {
                return 0; // Trả về 0 nếu không có kết quả
            }

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            // Đóng các đối tượng kết nối, câu lệnh và kết quả
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public int addHDCT(int maHD, int idCTSP) {
        sql = "insert into hoa_don_chi_tiet(id_hoa_don, id_chi_tiet_san_pham)\n"
                + "values (?,?)";

        try {
            con = DBConect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, maHD);
            ps.setObject(2, idCTSP);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<GioHangModel> getAllHDCT(int idHD) {
        sql = "select san_pham.ten_san_pham, nha_san_xuat.ten_nha_san_xuat, chi_tiet_san_pham.gia, count(*) as 'SoLuong', sum(chi_tiet_san_pham.gia)\n"
                + "from hoa_don_chi_tiet left join chi_tiet_san_pham on hoa_don_chi_tiet.id_chi_tiet_san_pham = chi_tiet_san_pham.id\n"
                + "	left join san_pham on chi_tiet_san_pham.san_pham = san_pham.id\n"
                + "	left join nha_san_xuat on chi_tiet_san_pham.nha_san_xuat = nha_san_xuat.id\n"
                + "where hoa_don_chi_tiet.id_hoa_don = ? and hoa_don_chi_tiet.deleted = 1 \n"
                + "group by san_pham.ten_san_pham, nha_san_xuat.ten_nha_san_xuat, chi_tiet_san_pham.gia";
        List<GioHangModel> list = new ArrayList<>();
        try {
            con = DBConect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, idHD);
            rs = ps.executeQuery();
            while (rs.next()) {
                GioHangModel a = new GioHangModel(rs.getString(1),
                        rs.getString(2), rs.getDouble(3), rs.getInt(4), rs.getDouble(5));
                list.add(a);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int updateHoaDonTQ(int maNV, int tenKH, String loaiHD, String tenNguoiNhan, String soDienThoai, double tienMat, double tienCK, double tongTien, int maHD, int phuong_thuc_tt, String maGiaoDich) {
        sql = "update hoa_don set ten_nhan_vien = ?, ten_khach_hang = ?, loai_hoa_don = ?, ten_nguoi_nhan = ?,"
                + " so_dien_thoai = ?, tien_ship = 0, tien_mat = ?, tien_chuyen_khoan = ?,\n"
                + "	tong_tien= ?, trang_thai = 1, phuong_thuc_tt = ?, ma_giao_dich = ? where id = ?";
        try {
            con = DBConect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, maNV);
            ps.setObject(2, tenKH);
            ps.setObject(3, loaiHD);
            ps.setObject(4, tenNguoiNhan);
            ps.setObject(5, soDienThoai);
            ps.setObject(6, tienMat);
            ps.setObject(7, tienCK);
            ps.setObject(8, tongTien);
            ps.setObject(9, phuong_thuc_tt);
            ps.setObject(10, maGiaoDich);
            ps.setObject(11, maHD);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateHoaDonTQ2(int maNV, String loaiHD, String tenNguoiNhan, double tienMat, double tienCK, double tongTien, int maHD, int phuong_thuc_tt, String maGiaoDich) {
        sql = "update hoa_don set ten_nhan_vien = ?, loai_hoa_don = ?, ten_nguoi_nhan = ?,"
                + "  tien_ship = 0, tien_mat = ?, tien_chuyen_khoan = ?,\n"
                + "	tong_tien= ?, trang_thai = 1, phuong_thuc_tt = ?, ma_giao_dich = ? where id = ?";
        try {
            con = DBConect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, maNV);
            ps.setObject(2, loaiHD);
            ps.setObject(3, tenNguoiNhan);
            ps.setObject(4, tienMat);
            ps.setObject(5, tienCK);
            ps.setObject(6, tongTien);
            ps.setObject(7, phuong_thuc_tt);
            ps.setObject(8, maGiaoDich);
            ps.setObject(9, maHD);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getIDKhachHang(String soDienThoai) {
        sql = "SELECT id FROM khach_hang WHERE so_dien_thoai = ? ";
        try {
            con = DBConect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, soDienThoai);
            // Add this line to set the tenKH parameter
            rs = ps.executeQuery(); // Khởi tạo ResultSet
            if (rs.next()) {
                return rs.getInt("id"); // Sử dụng tên cột thay vì câu truy vấn
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0; // Move return statement outside of the try-catch block
    }

    public List<VoucherInBanHang> getAllVoucher() {
        sql = "select ten_voucher, loai_giam_gia, gia_tri, giam_toi_da from voucher where trang_thai = N'Đang áp dụng'";
        List<VoucherInBanHang> list = new ArrayList<>();
        try {
            con = DBConect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                VoucherInBanHang voucher = new VoucherInBanHang(rs.getString(1),
                        rs.getBoolean(2), rs.getDouble(3), rs.getDouble(4));
                list.add(voucher);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int updateHoaDonDH(int maNV, int tenKH, String loaiHD, String tenNguoiNhan, String soDienThoai, double tienShip, int trangThai,
            double tienMat, double tienCK, double tongTien, int maHD, int phuong_thuc_tt, String diaChi, String maGiaoDich) {
        sql = "update hoa_don set ten_nhan_vien = ?, ten_khach_hang = ?, loai_hoa_don = ?, ten_nguoi_nhan = ?,"
                + " so_dien_thoai = ?, tien_ship = ?, tien_mat = ?, tien_chuyen_khoan = ?,\n"
                + "	tong_tien= ?, trang_thai = ?, phuong_thuc_tt = ?, dia_chi_nguoi_nhan = ?, ma_giao_dich = ? where id = ?";
        try {
            con = DBConect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, maNV);
            ps.setObject(2, tenKH);
            ps.setObject(3, loaiHD);
            ps.setObject(4, tenNguoiNhan);
            ps.setObject(5, soDienThoai);
            ps.setObject(6, tienShip);
            ps.setObject(7, tienMat);
            ps.setObject(8, tienCK);
            ps.setObject(9, tongTien);
            ps.setObject(10, trangThai);
            ps.setObject(11, phuong_thuc_tt);
            ps.setObject(12, diaChi);
            ps.setObject(13, maGiaoDich);
            ps.setObject(14, maHD);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateHoaDonDH2(int maNV, String loaiHD, String tenNguoiNhan, double tienShip, String soDienThoai, String maGiaoDich, int trangThai,
            double tienMat, double tienCK, double tongTien, int maHD, int phuong_thuc_tt, String diaChi) {
        sql = "update hoa_don set ten_nhan_vien = ?, loai_hoa_don = ?, ten_khach_hang = ?, so_dien_thoai = ?, "
                + "  tien_ship = ?, tien_mat = ?, tien_chuyen_khoan = ?,\n"
                + "	tong_tien= ?, trang_thai = ?, phuong_thuc_tt = ?, dia_chi_nguoi_nhan = ?, ma_giao_dich = ? where id = ?";
        try {
            con = DBConect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, maNV);
            ps.setObject(2, loaiHD);
            ps.setObject(3, tenNguoiNhan);
            ps.setObject(4, soDienThoai);
            ps.setObject(5, tienShip);
            ps.setObject(6, tienMat);
            ps.setObject(7, tienCK);
            ps.setObject(8, tongTien);
            ps.setObject(9, trangThai);
            ps.setObject(10, phuong_thuc_tt);
            ps.setObject(11, diaChi);
            ps.setObject(12, maGiaoDich);
            ps.setObject(13, maHD);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateTrangThaiHD(int trangThai, int maHD, String soDienThoai, String diaChi, double phiShip, int tenKH, String tenNguoiNhan) {
        sql = "update hoa_don set  dia_chi_nguoi_nhan = ?, tien_ship = ?, so_dien_thoai = ?, "
                + "	trang_thai = ?, ten_khach_hang = ?, ten_nguoi_nhan =? where id = ?";
        try {
            con = DBConect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, diaChi);
            ps.setObject(2, phiShip);
            ps.setObject(3, soDienThoai);
            ps.setObject(4, trangThai);
            ps.setObject(5, tenKH);
            ps.setObject(6, tenNguoiNhan);
            ps.setObject(7, maHD);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int insertLichSu(int maHD, String hanhDong, int maNV) {
        sql = "insert into lich_su_mua_hang(id_hoa_don, hanh_dong, nguoi_tao) values \n"
                + "( ?, ?, ?)";
        try {
            con = DBConect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, maHD);
            ps.setObject(2, hanhDong);
            ps.setObject(3, maNV);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int deleteHDCT(int maHD, double gia) {
        sql = """
              delete hoa_don_chi_tiet 
              from hoa_don_chi_tiet join chi_tiet_san_pham on hoa_don_chi_tiet.id_chi_tiet_san_pham = chi_tiet_san_pham.id
              where hoa_don_chi_tiet.id_hoa_don = ? and chi_tiet_san_pham.gia =?
              """;
        try {
            con = DBConect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, maHD);
            ps.setObject(2, gia);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public int updateHD(int maHD) {
        sql = "update hoa_don set   "
                + "	trang_thai = 3 where id = ?";
        try {
            con = DBConect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, maHD);

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
