/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.domainmodels;

import java.util.Date;

/**
 *
 * @author nguye
 */
public class ChiTietSanPham {
    private int id ;
    private SanPham sanPham;
    private Ram ram ;
    private Cpu cpu;
    private ManHinh manHinh;
    private Gpu gpu;
    private Imei imei;
    private DungLuong dungLuong;
    private MauSac mauSac;
    private HeDieuHanh heDieuHanh;
    private NhaSanXuat nhaSanXuat;
     private ChatLieu chatLieu;
    private float trongLuong;
    private float gia;
    private Date ngayTao,ngaySua ;
    private String nguoiTao,nguoiSua ;
    private int deleted ;

    public ChiTietSanPham() {
    }

    public ChiTietSanPham(SanPham sanPham, Ram ram, Cpu cpu, ManHinh manHinh, Gpu gpu, Imei imei, DungLuong dungLuong, MauSac mauSac, HeDieuHanh heDieuHanh, NhaSanXuat nhaSanXuat, ChatLieu chatLieu, float trongLuong, float gia) {
        this.sanPham = sanPham;
        this.ram = ram;
        this.cpu = cpu;
        this.manHinh = manHinh;
        this.gpu = gpu;
        this.imei = imei;
        this.dungLuong = dungLuong;
        this.mauSac = mauSac;
        this.heDieuHanh = heDieuHanh;
        this.nhaSanXuat = nhaSanXuat;
        this.chatLieu = chatLieu;
        this.trongLuong = trongLuong;
        this.gia = gia;
    }

    public ChiTietSanPham(int id, SanPham sanPham, Ram ram, Cpu cpu, ManHinh manHinh, Gpu gpu, Imei imei, DungLuong dungLuong, MauSac mauSac, HeDieuHanh heDieuHanh, NhaSanXuat nhaSanXuat, ChatLieu chatLieu, float trongLuong, float gia, Date ngayTao, Date ngaySua, String nguoiTao, String nguoiSua, int deleted) {
        this.id = id;
        this.sanPham = sanPham;
        this.ram = ram;
        this.cpu = cpu;
        this.manHinh = manHinh;
        this.gpu = gpu;
        this.imei = imei;
        this.dungLuong = dungLuong;
        this.mauSac = mauSac;
        this.heDieuHanh = heDieuHanh;
        this.nhaSanXuat = nhaSanXuat;
        this.chatLieu = chatLieu;
        this.trongLuong = trongLuong;
        this.gia = gia;
        this.ngayTao = ngayTao;
        this.ngaySua = ngaySua;
        this.nguoiTao = nguoiTao;
        this.nguoiSua = nguoiSua;
        this.deleted = deleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public Ram getRam() {
        return ram;
    }

    public void setRam(Ram ram) {
        this.ram = ram;
    }

    public Cpu getCpu() {
        return cpu;
    }

    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
    }

    public ManHinh getManHinh() {
        return manHinh;
    }

    public void setManHinh(ManHinh manHinh) {
        this.manHinh = manHinh;
    }

    public Gpu getGpu() {
        return gpu;
    }

    public void setGpu(Gpu gpu) {
        this.gpu = gpu;
    }

    public Imei getImei() {
        return imei;
    }

    public void setImei(Imei imei) {
        this.imei = imei;
    }

    public DungLuong getDungLuong() {
        return dungLuong;
    }

    public void setDungLuong(DungLuong dungLuong) {
        this.dungLuong = dungLuong;
    }

    public MauSac getMauSac() {
        return mauSac;
    }

    public void setMauSac(MauSac mauSac) {
        this.mauSac = mauSac;
    }

    public HeDieuHanh getHeDieuHanh() {
        return heDieuHanh;
    }

    public void setHeDieuHanh(HeDieuHanh heDieuHanh) {
        this.heDieuHanh = heDieuHanh;
    }

    public NhaSanXuat getNhaSanXuat() {
        return nhaSanXuat;
    }

    public void setNhaSanXuat(NhaSanXuat nhaSanXuat) {
        this.nhaSanXuat = nhaSanXuat;
    }

    public ChatLieu getChatLieu() {
        return chatLieu;
    }

    public void setChatLieu(ChatLieu chatLieu) {
        this.chatLieu = chatLieu;
    }

    public float getTrongLuong() {
        return trongLuong;
    }

    public void setTrongLuong(float trongLuong) {
        this.trongLuong = trongLuong;
    }

    public float getGia() {
        return gia;
    }

    public void setGia(float gia) {
        this.gia = gia;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Date getNgaySua() {
        return ngaySua;
    }

    public void setNgaySua(Date ngaySua) {
        this.ngaySua = ngaySua;
    }

    public String getNguoiTao() {
        return nguoiTao;
    }

    public void setNguoiTao(String nguoiTao) {
        this.nguoiTao = nguoiTao;
    }

    public String getNguoiSua() {
        return nguoiSua;
    }

    public void setNguoiSua(String nguoiSua) {
        this.nguoiSua = nguoiSua;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

}
