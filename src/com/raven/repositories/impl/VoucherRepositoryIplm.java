package com.raven.repositories.impl;

import com.raven.domainmodels.ModelVoucher;
import com.raven.repositories.VoucherRepository;
import com.raven.untilities.DBConect;
import java.util.ArrayList;
import java.sql.*;
import java.util.List;

public class VoucherRepositoryIplm implements VoucherRepository {

    @Override
    public List<ModelVoucher> getAllVoucher() {

        ArrayList<ModelVoucher> listVCH = new ArrayList<>();
        Connection cn = DBConect.getConnection();
        String sql = "SELECT ten_voucher, ma_voucher, loai_giam_gia,gia_tri, so_luong, ngay_bat_dau, ngay_ket_thuc,trang_thai FROM voucher where deleted = 1";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ModelVoucher modelV = new ModelVoucher();

                modelV.setTenVoucher(rs.getString(1));
                modelV.setMaVoucher(rs.getString(2));
                modelV.setLoaiGiamGia(rs.getBoolean(3));
                modelV.setGiaTri(rs.getFloat(4));
                modelV.setSoLuong(rs.getInt(5));
                modelV.setNgayBatDau(rs.getDate(6));
                modelV.setNgayKetThuc(rs.getDate(7));
                modelV.setTrangThai(rs.getString(8));
                listVCH.add(modelV);
            }
            cn.close();
            ps.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listVCH;

    }

    @Override
    public List<ModelVoucher> FindVoucherByKeyword(String keyword, String trangThai) {
        ArrayList<ModelVoucher> listVoucher = new ArrayList<>();
        Connection cn = DBConect.getConnection();
        String sql = """
                    SELECT ten_voucher, ma_voucher, loai_giam_gia, gia_tri, so_luong, ngay_bat_dau, ngay_ket_thuc, trang_thai 
                    FROM voucher 
                    WHERE deleted = 1 AND
                    (ten_voucher LIKE ? OR ma_voucher LIKE ?) AND
                    trang_thai LIKE ?
                """;
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setObject(1, "%" + keyword + "%");
            ps.setObject(2, "%" + keyword + "%");
            ps.setObject(3, "%" + trangThai + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ModelVoucher modelV = new ModelVoucher();
                modelV.setTenVoucher(rs.getString(1));
                modelV.setMaVoucher(rs.getString(2));
                modelV.setLoaiGiamGia(rs.getBoolean(3));
                modelV.setGiaTri(rs.getFloat(4));
                modelV.setSoLuong(rs.getInt(5));
                modelV.setNgayBatDau(rs.getDate(6));
                modelV.setNgayKetThuc(rs.getDate(7));
                modelV.setTrangThai(rs.getString(8));

                listVoucher.add(modelV);
            }
            cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listVoucher;
    }

    @Override
    public Integer addVoucher(ModelVoucher vch) {
        Integer r = null;
        try {
            Connection cn = DBConect.getConnection();
            String sql = "INSERT INTO voucher (ten_voucher, ma_voucher, loai_giam_gia,gia_tri, so_luong, ngay_bat_dau, ngay_ket_thuc) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setObject(1, vch.getTenVoucher());
            ps.setObject(2, vch.getMaVoucher());
            ps.setObject(3, vch.isLoaiGiamGia());
            ps.setObject(4, vch.getGiaTri());
            ps.setObject(5, vch.getSoLuong());
            ps.setObject(6, vch.getNgayBatDau());
            ps.setObject(7, vch.getNgayKetThuc());

            r = ps.executeUpdate();

        } catch (Exception e) {
//            e.printStackTrace();
        }
        return r;
    }

    @Override
    public Integer updateVoucher(ModelVoucher vch) {
        Integer r = null;
        try {
            Connection cn = DBConect.getConnection();
            String sql = "UPDATE voucher SET ten_voucher =?,ma_voucher=?, loai_giam_gia =?, gia_tri =?, so_luong =?, ngay_bat_dau =?, ngay_ket_thuc =? WHERE id =?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setObject(1, vch.getTenVoucher());
            ps.setObject(2, vch.getMaVoucher());
            ps.setObject(3, vch.isLoaiGiamGia());
            ps.setObject(4, vch.getGiaTri());
            ps.setObject(5, vch.getSoLuong());
            ps.setObject(6, vch.getNgayBatDau());
            ps.setObject(7, vch.getNgayKetThuc());
            ps.setInt(8, vch.getId());

            r = ps.executeUpdate();

        } catch (Exception e) {
//            e.printStackTrace();
        }
        return r;
    }

    @Override
    public Integer deleteVoucher(int id_voucher) {
        Integer r = null;
        try {
            Connection cn = DBConect.getConnection();
            String sql = "update voucher set deleted =0 where id=?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setObject(1, id_voucher);
            r = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public List<ModelVoucher> getVoucherByTrangThai(String trangThai) {
        ArrayList<ModelVoucher> listVoucher = new ArrayList<>();
        Connection cn = DBConect.getConnection();
        String sql = "SELECT ten_voucher, ma_voucher, loai_giam_gia,gia_tri, so_luong, ngay_bat_dau, ngay_ket_thuc, trang_thai FROM voucher WHERE deleted =1 and trang_thai like ?";

        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, trangThai);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ModelVoucher modelV = new ModelVoucher();
                modelV.setTenVoucher(rs.getString(1));
                modelV.setMaVoucher(rs.getString(2));
                modelV.setLoaiGiamGia(rs.getBoolean(3));
                modelV.setGiaTri(rs.getFloat(4));
                modelV.setSoLuong(rs.getInt(5));
                modelV.setNgayBatDau(rs.getDate(6));
                modelV.setNgayKetThuc(rs.getDate(7));
                modelV.setTrangThai(rs.getString(8));

                listVoucher.add(modelV);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listVoucher;
    }

    @Override
    public List<ModelVoucher> findVoucherByAll(String tenVoucher, String trangThai, java.util.Date dateStart, java.util.Date dateEnd, float giaTriStart, float giaTriEnd) {
        ArrayList<ModelVoucher> listVoucher = new ArrayList<>();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = DBConect.getConnection();
            String sql = "SELECT ten_voucher, ma_voucher, loai_giam_gia, gia_tri, so_luong, ngay_bat_dau, ngay_ket_thuc, trang_thai "
                    + "FROM voucher "
                    + "WHERE ten_voucher LIKE ? AND trang_thai = ? AND ngay_bat_dau >= ? AND ngay_ket_thuc <= ? AND gia_tri BETWEEN ? AND ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, "%" + tenVoucher + "%");
            ps.setString(2, "%" + trangThai + "%");
            ps.setDate(3, new java.sql.Date(dateStart.getTime()));
            ps.setDate(4, new java.sql.Date(dateEnd.getTime()));
            ps.setFloat(5, giaTriStart);
            ps.setFloat(6, giaTriEnd);

            rs = ps.executeQuery();
            while (rs.next()) {

                ModelVoucher modelV = new ModelVoucher();
                modelV.setTenVoucher(rs.getString(1));
                modelV.setMaVoucher(rs.getString(2));
                modelV.setLoaiGiamGia(rs.getBoolean(3));
                modelV.setGiaTri(rs.getFloat(4));
                modelV.setSoLuong(rs.getInt(5));
                modelV.setNgayBatDau(rs.getDate(6));
                modelV.setNgayKetThuc(rs.getDate(7));
                modelV.setTrangThai(rs.getString(8));
                listVoucher.add(modelV);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listVoucher;
    }

    @Override
    public int CountVoucher() {
        Connection cn = DBConect.getConnection();
        String sql = "select count(*) from voucher where deleted = 1";
        int count = 0;
        try {

            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
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
    public List<ModelVoucher> getDataByPage(int soTrang) {
        List<ModelVoucher> listVCH = new ArrayList<>();
        Connection cn = DBConect.getConnection();
        String sql = "SELECT top 5 ten_voucher, ma_voucher, loai_giam_gia,gia_tri, so_luong, ngay_bat_dau, ngay_ket_thuc,trang_thai "
                + "FROM voucher"
                + " where voucher.id not in (select top " + (soTrang * 5 - 5) + " voucher.id from voucher)";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ModelVoucher modelV = new ModelVoucher();
                modelV.setTenVoucher(rs.getString(1));
                modelV.setMaVoucher(rs.getString(2));
                modelV.setLoaiGiamGia(rs.getBoolean(3));
                modelV.setGiaTri(rs.getFloat(4));
                modelV.setSoLuong(rs.getInt(5));
                modelV.setNgayBatDau(rs.getDate(6));
                modelV.setNgayKetThuc(rs.getDate(7));
                modelV.setTrangThai(rs.getString(8));

                listVCH.add(modelV);
            }
            cn.close();
            ps.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listVCH;
    }

    @Override
    public List<ModelVoucher> phanTrang(int page, int limit) {
        List<ModelVoucher> listVch = new ArrayList<>();
        Connection cn = DBConect.getConnection();
        String sql = """
                     
                      SELECT  id,ten_voucher, ma_voucher, loai_giam_gia, gia_tri, so_luong, ngay_bat_dau, ngay_ket_thuc, trang_thai
                      FROM (
                          SELECT *,
                          ROW_NUMBER() OVER (ORDER BY id) AS RowNum
                          FROM voucher
                          WHERE deleted = 1
                      ) AS SubQuery
                      ORDER BY id desc
                      OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;
                    """;
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, (page - 1) * limit);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ModelVoucher modelV = new ModelVoucher();
                modelV.setId(rs.getInt(1));
                modelV.setTenVoucher(rs.getString(2));
                modelV.setMaVoucher(rs.getString(3));
                modelV.setLoaiGiamGia(rs.getBoolean(4));
                modelV.setGiaTri(rs.getFloat(5));
                modelV.setSoLuong(rs.getInt(6));
                modelV.setNgayBatDau(rs.getDate(7));
                modelV.setNgayKetThuc(rs.getDate(8));
                modelV.setTrangThai(rs.getString(9));
                listVch.add(modelV);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listVch;

    }

    @Override
    public int getMaxPages(int itemsPerPage) {
        int totalCount = CountVoucher();
        int maxPages = totalCount / itemsPerPage;
        if (totalCount % itemsPerPage != 0) {
            maxPages++;
        }
        return maxPages;
    }

    @Override
    public Integer UpdateStatusByClick(ModelVoucher voucher) {
        Integer r = null;
        Connection cn = DBConect.getConnection();
        String sql = "update  voucher set  ngay_bat_dau=?,ngay_ket_thuc=?, trang_thai =  ?  where id = ?";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setObject(1, voucher.getNgayBatDau());
            ps.setObject(2, voucher.getNgayKetThuc());
            ps.setObject(3, voucher.getTrangThai());
            ps.setObject(4, voucher.getId());
            r = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return r;
    }

    @Override
    public Integer AutoUpdateStatus() {
        Integer r = null;
        Connection cn = DBConect.getConnection();
        String sql = "UPDATE voucher "
                + "SET trang_thai = "
                + "CASE "
                + "WHEN GETDATE() < ngay_bat_dau THEN N'Sắp áp dụng' "
                + "WHEN GETDATE() >= ngay_bat_dau AND GETDATE() <= ngay_ket_thuc THEN N'Đang áp dụng' "
                + "ELSE N'Hết hạn'"
                + "END;";

        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            r = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return r;
    }

    @Override
    public List<ModelVoucher> getVoucherByName(String name) {
        ArrayList<ModelVoucher> listVoucher = new ArrayList<>();
        Connection cn = DBConect.getConnection();
        String sql = "SELECT id, ten_voucher, ma_voucher, loai_giam_gia,gia_tri, so_luong, ngay_bat_dau, ngay_ket_thuc, trang_thai FROM voucher WHERE ten_voucher like ?";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ModelVoucher modelV = new ModelVoucher();
                modelV.setId(rs.getInt(1));
                modelV.setTenVoucher(rs.getString(2));
                modelV.setMaVoucher(rs.getString(3));
                modelV.setLoaiGiamGia(rs.getBoolean(4));
                modelV.setGiaTri(rs.getFloat(5));
                modelV.setSoLuong(rs.getInt(6));
                modelV.setNgayBatDau(rs.getDate(7));
                modelV.setNgayKetThuc(rs.getDate(8));
                modelV.setTrangThai(rs.getString(9));

                listVoucher.add(modelV);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listVoucher;
    }

    @Override
    public List<ModelVoucher> getVoucherByDate(java.util.Date ngayBatDau, java.util.Date ngayKetThuc) {
        ArrayList<ModelVoucher> listVoucher = new ArrayList<>();
        Connection cn = DBConect.getConnection();
        String sql = "SELECT id, ten_voucher, ma_voucher, loai_giam_gia, gia_tri, so_luong, ngay_bat_dau, ngay_ket_thuc, trang_thai FROM voucher WHERE ngay_bat_dau >= ? AND ngay_ket_thuc <= ?";

        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setTimestamp(1, new java.sql.Timestamp(ngayBatDau.getTime()));
            ps.setTimestamp(2, new java.sql.Timestamp(ngayKetThuc.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ModelVoucher modelV = new ModelVoucher();
                modelV.setId(rs.getInt(1));
                modelV.setTenVoucher(rs.getString(2));
                modelV.setMaVoucher(rs.getString(3));
                modelV.setLoaiGiamGia(rs.getBoolean(4));
                modelV.setGiaTri(rs.getFloat(5));
                modelV.setSoLuong(rs.getInt(6));
                modelV.setNgayBatDau(rs.getDate(7));
                modelV.setNgayKetThuc(rs.getDate(8));
                modelV.setTrangThai(rs.getString(9));

                listVoucher.add(modelV);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listVoucher;
    }

    @Override
    public List<ModelVoucher> getVoucherByPrice(float priceStart, float priceEnd) {
        ArrayList<ModelVoucher> listVoucher = new ArrayList<>();
        Connection cn = DBConect.getConnection();
        String sql = "SELECT id, ten_voucher, ma_voucher, loai_giam_gia,gia_tri, so_luong, ngay_bat_dau, ngay_ket_thuc FROM voucher where gia_tri BETWEEN ? AND ?";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setFloat(1, priceStart);
            ps.setFloat(2, priceEnd);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ModelVoucher modelV = new ModelVoucher();
                modelV.setId(rs.getInt(1));
                modelV.setTenVoucher(rs.getString(2));
                modelV.setMaVoucher(rs.getString(3));
                modelV.setLoaiGiamGia(rs.getBoolean(4));
                modelV.setGiaTri(rs.getFloat(5));
                modelV.setSoLuong(rs.getInt(6));
                modelV.setNgayBatDau(rs.getDate(7));
                modelV.setNgayKetThuc(rs.getDate(8));

                listVoucher.add(modelV);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listVoucher;
    }

    @Override
    public int getIdVoucher(String maVoucher) {
        Connection cn = DBConect.getConnection();
        String sql = "select id from voucher where ma_voucher like ?";
        int id = 0;

        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setObject(1, maVoucher);
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
    public List<ModelVoucher> phanTrangDeleted(int page, int limit) {
        List<ModelVoucher> listVch = new ArrayList<>();
        Connection cn = DBConect.getConnection();
        String sql = """
                     SELECT id, ten_voucher, ma_voucher, loai_giam_gia, gia_tri, so_luong, ngay_bat_dau, ngay_ket_thuc, trang_thai
                     FROM voucher
                     WHERE deleted = 0
                     ORDER BY id desc
                     OFFSET ?  ROWS FETCH NEXT ? ROWS ONLY;
                     """;
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, (page - 1) * limit);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ModelVoucher modelV = new ModelVoucher();
                modelV.setId(rs.getInt(1));
                modelV.setTenVoucher(rs.getString(2));
                modelV.setMaVoucher(rs.getString(3));
                modelV.setLoaiGiamGia(rs.getBoolean(4));
                modelV.setGiaTri(rs.getFloat(5));
                modelV.setSoLuong(rs.getInt(6));
                modelV.setNgayBatDau(rs.getDate(7));
                modelV.setNgayKetThuc(rs.getDate(8));
                modelV.setTrangThai(rs.getString(9));
                listVch.add(modelV);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listVch;
    }

    @Override
    public int CountVoucherDeleted() {
        Connection cn = DBConect.getConnection();
        String sql = "select count(*) from voucher where deleted = 0";
        int count = 0;
        try {

            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
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
    public int getMaxPagesDeleted(int itemsPerPage) {
        int totalCount = CountVoucherDeleted();
        int maxPages = totalCount / itemsPerPage;
        if (totalCount % itemsPerPage != 0) {
            maxPages++;
        }

        return maxPages;
    }

    @Override
    public Boolean restoreVoucher(int id_voucher) {
        Integer r = null;
        int check = 0;
        try {
            Connection cn = DBConect.getConnection();
            String sql = "update voucher set deleted =1 where id=?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setObject(1, id_voucher);
            check = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

}
