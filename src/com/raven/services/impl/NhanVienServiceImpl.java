/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raven.services.impl;

import com.raven.repositories.NhanVienRepository;
import com.raven.repositories.impl.NhanVienRepositoryImpl;
import com.raven.services.NhanVienService;
import com.raven.viewmodels.NhanVienResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public class NhanVienServiceImpl implements NhanVienService {

    private NhanVienRepositoryImpl repo = new NhanVienRepositoryImpl();

    @Override
    public List<NhanVienResponse> getAll() {
        return repo.getAll();
    }

    @Override
    public Boolean add(NhanVienResponse kh) {
        return repo.add(kh);
    }

    @Override
    public Boolean update(NhanVienResponse kh, int id) {
        return repo.update(kh, id);
    }

    @Override
    public Boolean delete(int ma) {
        return repo.delete(ma);
    }

    @Override
    public int getIDBySDT(String sdt) {
        return repo.getIDBySDT(sdt);
    }

    @Override
    public boolean isPhoneNumberInUse(int currentEmployeeId) {
        return repo.isPhoneNumberInUse(currentEmployeeId);
    }

    @Override
    public int getIDByMaNV(String ma) {
        return repo.getIDByMaNV(ma);
    }

    @Override
    public int getIDByCCCD(String cccd) {
        return repo.getIDByCCCD(cccd);
    }

    @Override
    public boolean isCodeStaffInUse(String codeStaff, int currentEmployeeId) {
        return repo.isCodeStaffInUse(codeStaff, currentEmployeeId);
    }

    @Override
    public boolean isCCCDInUse(String cCCD, int currentEmployeeId) {
        return repo.isCCCDInUse(cCCD, currentEmployeeId);
    }

    @Override
    public List<NhanVienResponse> searchAllField(String keyword) {
        return repo.searchAllField(keyword);
    }
    
    @Override
    public NhanVienResponse getOneByID(int id) {
        return repo.getOneByID(id);
    }

    @Override
    public NhanVienResponse getOne(String SDT) {

        return repo.getOne(SDT);
    }

}
