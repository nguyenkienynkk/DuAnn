/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.repositories.impl;

import com.raven.repositories.ThongKeRepository;
import com.raven.untilities.DBConect;
import com.raven.viewmodels.ThongKeDayInMonth;
import com.raven.viewmodels.ThongKeDayToDay;
import com.raven.viewmodels.ThongKeGetYear;
import com.raven.viewmodels.ThongKeMonthInYear;
import com.raven.viewmodels.ThongKeReponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ThongKeRepositoryIplm implements ThongKeRepository {

    String sql = "";
    Connection cn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    @Override
    public int getSoLuongLaptop() {
        cn = DBConect.getConnection();
        sql = "select count(id) from chi_tiet_san_pham";
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
    public int getSoLuongKhachHang() {
        cn = DBConect.getConnection();
        sql = "select count(id) from khach_hang";
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
    public int getSoLuongNhanVien() {
        cn = DBConect.getConnection();
        sql = "select count(id) from nhan_vien";
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
    public float getTongDoanhThu() {
        cn = DBConect.getConnection();
        sql = "select sum(tong_tien) from hoa_don   JOIN trang_thai ON trang_thai.id = hoa_don.trang_thai where hoa_don.trang_thai=1 ";
        float sum = 0;
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                sum = rs.getFloat(1);

            }
            return sum;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<ThongKeReponse> getThongKeTop8() {
        List<ThongKeReponse> listThongKe = new ArrayList<>();
        cn = DBConect.getConnection();
        sql = """
              SELECT top 8 ngay_tao AS Ngay, SUM(tong_tien) AS TongTien_Theo_Ngay
              FROM hoa_don
              WHERE deleted = 1 
              GROUP BY ngay_tao
              ORDER BY ngay_tao DESC
              """;
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ThongKeReponse thongKe = new ThongKeReponse();
                thongKe.setNgay(rs.getDate(1));
                thongKe.setTongTien(rs.getFloat(2));
                listThongKe.add(thongKe);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listThongKe;
    }

    @Override
    public List<ThongKeGetYear> loadComBoBoxYear() {
        List<ThongKeGetYear> list = new ArrayList<>();
        cn = DBConect.getConnection();
        sql = """
                     SELECT Year( hoa_don.ngay_tao) from hoa_don 
                     JOIN trang_thai ON trang_thai.id = hoa_don.trang_thai
                     WHERE hoa_don.trang_thai = 1
                     order by Year( hoa_don.ngay_tao) desc 
              """;
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ThongKeGetYear tk = new ThongKeGetYear();
                tk.setYear(rs.getInt(1));
                list.add(tk);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    @Override
    public List<ThongKeGetYear> getThongKeYear() {
        List<ThongKeGetYear> listThongKe = new ArrayList<>();
        cn = DBConect.getConnection();
        sql = """
              SELECT Year( hoa_don.ngay_tao), SUM(hoa_don.tong_tien)
              FROM hoa_don
              JOIN trang_thai ON trang_thai.id = hoa_don.trang_thai
              WHERE hoa_don.trang_thai = 1 
                Group by  Year( hoa_don.ngay_tao)
              """;
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ThongKeGetYear tk = new ThongKeGetYear();
                tk.setYear(rs.getInt(1));
                tk.setDoanhThu(rs.getFloat(2));
                listThongKe.add(tk);

            }
        } catch (Exception e) {
        }
        return listThongKe;
    }

    @Override
    public List<ThongKeMonthInYear> getThongKeMonthInYear() {
        List<ThongKeMonthInYear> listTK = new ArrayList<>();
        sql = """
              SELECT MONTH(hoa_don.ngay_tao) , YEAR(hoa_don.ngay_tao), SUM(hoa_don.tong_tien)
                              FROM hoa_don
                              JOIN trang_thai ON trang_thai.id = hoa_don.trang_thai
                              WHERE hoa_don.trang_thai = 1 
                              GROUP BY MONTH(hoa_don.ngay_tao) , YEAR(hoa_don.ngay_tao)
                               	ORDER BY  MONTH(hoa_don.ngay_tao) asc  , YEAR(hoa_don.ngay_tao) asc
              """;
        cn = DBConect.getConnection();
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ThongKeMonthInYear tk = new ThongKeMonthInYear();
                tk.setMonth(rs.getInt(1));
                tk.setYear(rs.getInt(2));
                tk.setDoanhThu(rs.getFloat(3));
                listTK.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listTK;
    }

    @Override
    public List<ThongKeDayInMonth> getThongKeDayInMonth() {
        List<ThongKeDayInMonth> listTK = new ArrayList<>();
        sql = """
               SELECT DAY(hoa_don.ngay_tao) AS NgayTrongThang , MONTH(hoa_don.ngay_tao), YEar(hoa_don.ngay_tao),SUM(hoa_don.tong_tien) AS TongTien
                            FROM hoa_don
                            JOIN trang_thai ON trang_thai.id = hoa_don.trang_thai
                            WHERE hoa_don.trang_thai = 1   
                            GROUP BY MONTH(hoa_don.ngay_tao), DAY(hoa_don.ngay_tao),  YEar(hoa_don.ngay_tao)
                            ORDER BY MONTH(hoa_don.ngay_tao) DESC, DAY(hoa_don.ngay_tao) DESC
              """;
        cn = DBConect.getConnection();
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ThongKeDayInMonth tk = new ThongKeDayInMonth();
                tk.setDay(rs.getInt(1));
                tk.setMonth(rs.getInt(2));
                tk.setYear(rs.getInt(3));
                tk.setDoanhThu(rs.getFloat(4));
                listTK.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listTK;
    }

    @Override
    public List<ThongKeDayToDay> getThongKeDayToDay() {
        List<ThongKeDayToDay> listTK = new ArrayList<>();
        sql = """
              SELECT hoa_don.ngay_tao, SUM(hoa_don.tong_tien) AS TongTien
              FROM hoa_don
              JOIN trang_thai ON trang_thai.id = hoa_don.trang_thai
              WHERE hoa_don.trang_thai = 1
              GROUP BY hoa_don.ngay_tao
              ORDER BY hoa_don.ngay_tao DESC
              """;
        cn = DBConect.getConnection();
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ThongKeDayToDay tk = new ThongKeDayToDay();
                tk.setNgayTao(rs.getDate(1));
                tk.setDoanhThu(rs.getFloat(2));
                listTK.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listTK;
    }

    @Override
    public List<ThongKeGetYear> getThongKeByYear(int year) {
        List<ThongKeGetYear> listThongKe = new ArrayList<>();
        cn = DBConect.getConnection();
        sql = """
             SELECT Year( hoa_don.ngay_tao), SUM(hoa_don.tong_tien) from hoa_don 
            JOIN trang_thai ON trang_thai.id = hoa_don.trang_thai
            WHERE hoa_don.trang_thai = 1 and  Year( hoa_don.ngay_tao) = ?
            Group by Year( hoa_don.ngay_tao)
            order by Year( hoa_don.ngay_tao) desc
              """;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, year);
            rs = ps.executeQuery();
            while (rs.next()) {
                ThongKeGetYear tk = new ThongKeGetYear();
                tk.setYear(rs.getInt(1));
                tk.setDoanhThu(rs.getFloat(2));
                listThongKe.add(tk);

            }
        } catch (Exception e) {
        }
        return listThongKe;

    }

    @Override
    public List<ThongKeMonthInYear> getThongKeByMothInYear(int month, int year) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ThongKeDayToDay> getThongKeByDayToDay(java.util.Date ngayBatDau, java.util.Date ngayKetThuc) {
        List<ThongKeDayToDay> listTK = new ArrayList<>();
        sql = """
             SELECT hoa_don.ngay_tao, SUM(hoa_don.tong_tien) AS TongTien
              FROM hoa_don
              JOIN trang_thai ON trang_thai.id = hoa_don.trang_thai
              WHERE hoa_don.trang_thai = 1 and hoa_don.ngay_tao >= ? and hoa_don.ngay_tao <= ?
              GROUP BY hoa_don.ngay_tao
              ORDER BY hoa_don.ngay_tao DESC
              """;
        cn = DBConect.getConnection();
        try {
            ps = cn.prepareStatement(sql);
            ps.setTimestamp(1, new java.sql.Timestamp(ngayBatDau.getTime()));
            ps.setTimestamp(2, new java.sql.Timestamp(ngayKetThuc.getTime()));
            rs = ps.executeQuery();
            while (rs.next()) {
                ThongKeDayToDay tk = new ThongKeDayToDay();
                tk.setNgayTao(rs.getDate(1));
                tk.setDoanhThu(rs.getFloat(2));
                listTK.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listTK;
    }

    @Override
    public List<ThongKeMonthInYear> getThongKeByMonthInYear(int month, int year) {
        List<ThongKeMonthInYear> listTK = new ArrayList<>();
        sql = """
              SELECT MONTH(hoa_don.ngay_tao) AS Thang, YEAR(hoa_don.ngay_tao) AS Nam, SUM(hoa_don.tong_tien) AS TongTien
              FROM hoa_don
              JOIN trang_thai ON trang_thai.id = hoa_don.trang_thai
              WHERE hoa_don.trang_thai = 1 and  MONTH(hoa_don.ngay_tao) =? and YEAR(hoa_don.ngay_tao)=?
              GROUP BY YEAR(hoa_don.ngay_tao), MONTH(hoa_don.ngay_tao)  
              ORDER BY YEAR(hoa_don.ngay_tao) DESC, MONTH(hoa_don.ngay_tao) DESC
              """;
        cn = DBConect.getConnection();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, month);
            ps.setInt(2, year);
            rs = ps.executeQuery();
            while (rs.next()) {
                ThongKeMonthInYear tk = new ThongKeMonthInYear();
                tk.setMonth(rs.getInt(1));
                tk.setYear(rs.getInt(2));
                tk.setDoanhThu(rs.getFloat(3));
                listTK.add(tk);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listTK;
    }

    @Override
    public List<ThongKeDayInMonth> getThongKeByDayToMonth(int day, int month, int year) {
        List<ThongKeDayInMonth> listTK = new ArrayList<>();
        sql = """
              SELECT DAY(hoa_don.ngay_tao) AS NgayTrongThang , MONTH(hoa_don.ngay_tao), YEar(hoa_don.ngay_tao),SUM(hoa_don.tong_tien) AS TongTien
                            FROM hoa_don
                            JOIN trang_thai ON trang_thai.id = hoa_don.trang_thai
                            WHERE hoa_don.trang_thai = 1  and  DAY(hoa_don.ngay_tao) = ? and MONTH(hoa_don.ngay_tao) = ? and YEar(hoa_don.ngay_tao) =?
                            GROUP BY MONTH(hoa_don.ngay_tao), DAY(hoa_don.ngay_tao),  YEar(hoa_don.ngay_tao)
                            ORDER BY MONTH(hoa_don.ngay_tao) DESC, DAY(hoa_don.ngay_tao) DESC
              """;
        cn = DBConect.getConnection();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, day);
            ps.setInt(2, month);
            ps.setInt(3, year);
            rs = ps.executeQuery();
            while (rs.next()) {
                ThongKeDayInMonth tk = new ThongKeDayInMonth();
                tk.setDay(rs.getInt(1));
                tk.setMonth(rs.getInt(2));
                tk.setYear(rs.getInt(3));
                tk.setDoanhThu(rs.getFloat(4));

                listTK.add(tk);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listTK;
    }

    @Override
    public List<ThongKeMonthInYear> FinMonthByYear(int year) {
        List<ThongKeMonthInYear> list = new ArrayList<>();
        sql = """
              	SELECT MONTH(hoa_don.ngay_tao) AS Thang ,SUM(hoa_don.tong_tien) AS TongTien
              	FROM hoa_don
              	JOIN trang_thai ON trang_thai.id = hoa_don.trang_thai
              	WHERE hoa_don.trang_thai = 1 and  YEAR(hoa_don.ngay_tao) = ?
              	GROUP BY YEAR(hoa_don.ngay_tao), MONTH(hoa_don.ngay_tao)  
              	ORDER BY YEAR(hoa_don.ngay_tao) DESC, MONTH(hoa_don.ngay_tao) DESC
              """;
        cn = DBConect.getConnection();
        try {
            ps = cn.prepareStatement(sql);
            ps.setObject(1, year);
            rs = ps.executeQuery();
            while (rs.next()) {
                ThongKeMonthInYear tk = new ThongKeMonthInYear();
                tk.setMonth(rs.getInt(1));
                tk.setDoanhThu(rs.getFloat(2));

                list.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<ThongKeDayInMonth> getThongKeDayInMonthByMonth(int month) {
        List<ThongKeDayInMonth> list = new ArrayList<>();
        sql = """
               SELECT DAY(hoa_don.ngay_tao) AS NgayTrongThang , MONTH(hoa_don.ngay_tao), YEar(hoa_don.ngay_tao),SUM(hoa_don.tong_tien) AS TongTien
                                          FROM hoa_don
                                          JOIN trang_thai ON trang_thai.id = hoa_don.trang_thai
                                          WHERE hoa_don.trang_thai = 1  and MONTH(hoa_don.ngay_tao)=?
                                          GROUP BY MONTH(hoa_don.ngay_tao), DAY(hoa_don.ngay_tao),  YEar(hoa_don.ngay_tao)
                                          ORDER BY MONTH(hoa_don.ngay_tao) DESC, DAY(hoa_don.ngay_tao) DESC
              """;
        cn = DBConect.getConnection();
        try {
            ps = cn.prepareStatement(sql);
            ps.setObject(1, month);
            rs = ps.executeQuery();
            while (rs.next()) {
                ThongKeDayInMonth tk = new ThongKeDayInMonth();
                tk.setDay(rs.getInt(1));
                tk.setMonth(rs.getInt(2));
                tk.setYear(rs.getInt(3));
                tk.setDoanhThu(rs.getFloat(4));

                list.add(tk);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
