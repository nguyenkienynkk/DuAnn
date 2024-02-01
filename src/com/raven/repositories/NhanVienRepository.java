/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raven.repositories;

import com.raven.viewmodels.NhanVienResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public interface NhanVienRepository {

    List<NhanVienResponse> getAll();

    Boolean add(NhanVienResponse kh);

    Boolean update(NhanVienResponse kh, int id);

    Boolean delete(int ma);

    int getIDBySDT(String sdt);

    int getIDByMaNV(String ma);

    int getIDByCCCD(String cccd);

    boolean isPhoneNumberInUse(int currentEmployeeId);

    boolean isCodeStaffInUse(String codeStaff, int currentEmployeeId);

    boolean isCCCDInUse(String cCCD, int currentEmployeeId);

    List<NhanVienResponse> searchAllField(String keyword);
    
    public NhanVienResponse getOne(String SDT);

    public NhanVienResponse getOneByID(int id);

}
