/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.viewmodels;

/**
 *
 * @author Admin
 */
public class ThongKeDayInMonth {

    private int day;
    private int month;
    private int year;
    private float doanhThu;

    public ThongKeDayInMonth() {
    }

    public ThongKeDayInMonth(int day, int month, int year, float doanhThu) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.doanhThu = doanhThu;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(float doanhThu) {
        this.doanhThu = doanhThu;
    }

}
