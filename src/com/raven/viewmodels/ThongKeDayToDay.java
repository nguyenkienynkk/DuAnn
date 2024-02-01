package com.raven.viewmodels;

import java.util.Date;

public class ThongKeDayToDay {

    private Date ngayTao;
    private float doanhThu;

    public ThongKeDayToDay() {
    }

    public ThongKeDayToDay(Date ngayTao, float doanhThu) {
        this.ngayTao = ngayTao;
        this.doanhThu = doanhThu;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public float getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(float doanhThu) {
        this.doanhThu = doanhThu;
    }
    
    

}
