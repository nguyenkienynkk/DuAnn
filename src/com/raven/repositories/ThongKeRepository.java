/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raven.repositories;

import com.raven.viewmodels.ThongKeDayInMonth;
import com.raven.viewmodels.ThongKeDayToDay;
import com.raven.viewmodels.ThongKeGetYear;
import com.raven.viewmodels.ThongKeMonthInYear;
import com.raven.viewmodels.ThongKeReponse;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface ThongKeRepository {

    int getSoLuongLaptop();

    int getSoLuongKhachHang();

    int getSoLuongNhanVien();

    float getTongDoanhThu();

    List<ThongKeReponse> getThongKeTop8();

    List<ThongKeGetYear> loadComBoBoxYear();

    List<ThongKeGetYear> getThongKeYear();

    List<ThongKeMonthInYear> getThongKeMonthInYear();

    List<ThongKeDayInMonth> getThongKeDayInMonth();

    List<ThongKeDayToDay> getThongKeDayToDay();

    List<ThongKeGetYear> getThongKeByYear(int year);

    List<ThongKeMonthInYear> getThongKeByMothInYear(int month, int year);

    List<ThongKeDayToDay> getThongKeByDayToDay(Date ngayBatDau, Date ngayKetThuc);

    List<ThongKeMonthInYear> getThongKeByMonthInYear(int month, int year);

    List<ThongKeDayInMonth> getThongKeByDayToMonth(int day, int month, int year);

    List<ThongKeMonthInYear> FinMonthByYear(int year);

    List<ThongKeDayInMonth> getThongKeDayInMonthByMonth(int month);

}
