package com.raven.repositories;

import com.raven.domainmodels.ModelVoucher;
import java.util.ArrayList;
import java.util.Date;
import java.sql.*;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface VoucherRepository {

    public List<ModelVoucher> getAllVoucher();

    public List<ModelVoucher> getDataByPage(int soTrang);

    public int getIdVoucher(String maVoucher);

    public List<ModelVoucher> FindVoucherByKeyword(String keyword, String trangThai);

    public Integer addVoucher(ModelVoucher vch);

    public Integer updateVoucher(ModelVoucher vch);

    public Integer deleteVoucher(int ma_voucher);

    public Boolean restoreVoucher(int _voucher);

    public List<ModelVoucher> getVoucherByTrangThai(String trangThai);

    public List<ModelVoucher> getVoucherByName(String name);

    public List<ModelVoucher> getVoucherByDate(Date ngayBatDau, Date ngayKetThuc);

    public List<ModelVoucher> getVoucherByPrice(float priceStart, float priceEnd);

    public List<ModelVoucher> findVoucherByAll(String tenVoucher, String trangThai, java.util.Date dateStart, java.util.Date dateEnd, float giaTriStart, float giaTriEnd);

    public int CountVoucher();

    public int CountVoucherDeleted();

    public Integer AutoUpdateStatus();

    public List<ModelVoucher> phanTrang(int page, int limit);

    public List<ModelVoucher> phanTrangDeleted(int page, int limit);

    public int getMaxPages(int itemsPerPage);

    public int getMaxPagesDeleted(int itemsPerPage);

    public Integer UpdateStatusByClick(ModelVoucher voucher);

}
