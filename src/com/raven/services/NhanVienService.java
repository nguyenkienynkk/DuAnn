package com.raven.services;

import com.raven.viewmodels.NhanVienResponse;
import java.util.List;

public interface NhanVienService {

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
    
    public NhanVienResponse getOneByID(int id);

    public NhanVienResponse getOne(String SDT);

}
