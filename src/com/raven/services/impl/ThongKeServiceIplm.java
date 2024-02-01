package com.raven.services.impl;

import com.raven.repositories.impl.ThongKeRepositoryIplm;
import com.raven.services.ThongKeService;
import com.raven.viewmodels.ThongKeDayInMonth;
import com.raven.viewmodels.ThongKeDayToDay;
import com.raven.viewmodels.ThongKeGetYear;
import com.raven.viewmodels.ThongKeMonthInYear;
import com.raven.viewmodels.ThongKeReponse;
import java.util.List;

public class ThongKeServiceIplm implements ThongKeService {

    ThongKeRepositoryIplm repo = new ThongKeRepositoryIplm();

    @Override
    public int getSoLuongLaptop() {
        return repo.getSoLuongLaptop();
    }

    @Override
    public int getSoLuongKhachHang() {
        return repo.getSoLuongKhachHang();
    }

    @Override
    public int getSoLuongNhanVien() {
        return repo.getSoLuongNhanVien();
    }

    @Override
    public float getTongDoanhThu() {
        return repo.getTongDoanhThu();
    }

    @Override
    public List<ThongKeReponse> getThongKeTop8() {
        return repo.getThongKeTop8();
    }

    @Override
    public List<ThongKeGetYear> loadComBoBoxYear() {
        return repo.loadComBoBoxYear();
    }

    @Override
    public List<ThongKeGetYear> getThongKeYear() {
        return repo.getThongKeYear();
    }

    @Override
    public List<ThongKeMonthInYear> getThongKeMonthInYear() {
        return repo.getThongKeMonthInYear();
    }

    @Override
    public List<ThongKeDayInMonth> getThongKeDayInMonth() {
        return repo.getThongKeDayInMonth();
    }

    @Override
    public List<ThongKeDayToDay> getThongKeDayToDay() {
        return repo.getThongKeDayToDay();
    }

    @Override
    public List<ThongKeGetYear> getThongKeByYear(int year) {
        return repo.getThongKeByYear(year);
    }

    @Override
    public List<ThongKeDayToDay> getThongKeByDayToDay(java.util.Date ngayBatDau, java.util.Date ngayKetThuc) {
        return repo.getThongKeByDayToDay(ngayBatDau, ngayKetThuc);
    }

    @Override
    public List<ThongKeMonthInYear> getThongKeByMonthInYear(int month, int year) {
        return repo.getThongKeByMonthInYear(month, year);
    }

    @Override
    public List<ThongKeDayInMonth> getThongKeByDayToMonth(int day, int month, int year) {
        return repo.getThongKeByDayToMonth(day, month, year);
    }

    @Override
    public List<ThongKeMonthInYear> FinMonthByYear(int year) {
        return repo.FinMonthByYear(year);
    }

    @Override
    public List<ThongKeDayInMonth> getThongKeDayInMonthByMonth(int month) {
        return repo.getThongKeDayInMonthByMonth(month);
    }

}
