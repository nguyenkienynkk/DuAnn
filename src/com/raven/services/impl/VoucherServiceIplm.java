package com.raven.services.impl;

import com.raven.domainmodels.ModelVoucher;
import com.raven.repositories.impl.VoucherRepositoryIplm;
import com.raven.services.VoucherService;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class VoucherServiceIplm implements VoucherService {
    
    VoucherRepositoryIplm repo = new VoucherRepositoryIplm();
    
    @Override
    public List<ModelVoucher> getAllVoucher() {
        return repo.getAllVoucher();
    }
    
    @Override
    public List<ModelVoucher> FindVoucherByKeyword(String keyword, String trangThai) {
        return repo.FindVoucherByKeyword(keyword, trangThai);
    }
    
    @Override
    public Integer addVoucher(ModelVoucher vch) {
        return repo.addVoucher(vch);
    }
    
    @Override
    public Integer updateVoucher(ModelVoucher vch) {
        return repo.updateVoucher(vch);
        
    }
    
    @Override
    public Integer deleteVoucher(int ma_voucher) {
        return repo.deleteVoucher(ma_voucher);
    }
    
    @Override
    public List<ModelVoucher> getVoucherByTrangThai(String trangThai) {
        return repo.getVoucherByTrangThai(trangThai);
    }
    
    @Override
    public List<ModelVoucher> findVoucherByAll(String tenVoucher, String trangThai, java.util.Date dateStart, java.util.Date dateEnd, float giaTriStart, float giaTriEnd) {
        return repo.findVoucherByAll(tenVoucher, trangThai, dateStart, dateEnd, giaTriStart, giaTriEnd);
    }
    
    @Override
    public int CountVoucher() {
        return repo.CountVoucher();
    }
    
    @Override
    public List<ModelVoucher> getDataByPage(int soTrang) {
        return repo.getDataByPage(soTrang);
    }
    
    @Override
    public List<ModelVoucher> phanTrang(int page, int limit) {
        return repo.phanTrang(page, limit);
    }
    
    @Override
    public int getMaxPages(int itemsPerPage) {
        return repo.getMaxPages(itemsPerPage);
    }
    
    @Override
    public Integer UpdateStatusByClick(ModelVoucher voucher) {
        return repo.UpdateStatusByClick(voucher);
    }
    
    @Override
    public Integer AutoUpdateStatus() {
        return repo.AutoUpdateStatus();
    }
    
    @Override
    public List<ModelVoucher> getVoucherByName(String name) {
        return repo.FindVoucherByKeyword(name, name);
    }
    
    @Override
    public List<ModelVoucher> getVoucherByDate(java.util.Date ngayBatDau, java.util.Date ngayKetThuc) {
        return repo.getVoucherByDate(ngayBatDau, ngayKetThuc);
    }
    
    @Override
    public List<ModelVoucher> getVoucherByPrice(float priceStart, float priceEnd) {
        return repo.getVoucherByPrice(priceStart, priceEnd);
    }
    
    @Override
    public int getIdVoucher(String maVoucher) {
        return repo.getIdVoucher(maVoucher);
    }
    
    @Override
    public int CountVoucherDeleted() {
        return repo.CountVoucherDeleted();
    }
    
    @Override
    public List<ModelVoucher> phanTrangDeleted(int page, int limit) {
        return repo.phanTrangDeleted(page, limit);
    }
    
    @Override
    public int getMaxPagesDeleted(int itemsPerPage) {
        return repo.getMaxPagesDeleted(itemsPerPage);
    }
    
    @Override
    public Boolean restoreVoucher(int id_voucher) {
        return repo.restoreVoucher(id_voucher);
    }
    
}
