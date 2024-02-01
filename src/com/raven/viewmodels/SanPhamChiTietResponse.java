/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.viewmodels;

import com.raven.domainmodels.ChatLieu;
import com.raven.domainmodels.Cpu;
import com.raven.domainmodels.DungLuong;
import com.raven.domainmodels.Gpu;
import com.raven.domainmodels.HeDieuHanh;
import com.raven.domainmodels.Imei;
import com.raven.domainmodels.ManHinh;
import com.raven.domainmodels.MauSac;
import com.raven.domainmodels.NhaSanXuat;
import com.raven.domainmodels.Ram;
import com.raven.domainmodels.SanPham;
import com.raven.untilities.DBConect;
import java.util.Date;
import java.sql.*;

/**
 *
 * @author nguye
 */
public class SanPhamChiTietResponse {

    private int id;
    private SanPhamResponse sanPham;
    private RamResponse ram;
    private CpuResponse cpu;
    private ManHinhResponse manHinh;
    private GpuResponse gpu;
    private ImeiResponse imei;
    private DungLuongResponse dungLuong;
    private MauSacResponse mauSac;
    private HeDieuHanhResponse heDieuHanh;
    private NhaSanXuatResponse nhaSanXuat;
    private ChatLieuResponse chatLieu;
    private int soLuong;
    private float trongLuong;
    private float gia;
    private Date ngayTao, ngaySua;
    private String nguoiTao, nguoiSua;
    private int deleted;

    public SanPhamChiTietResponse() {
    }

    public SanPhamChiTietResponse(SanPhamResponse sanPham, RamResponse ram, CpuResponse cpu, ManHinhResponse manHinh, GpuResponse gpu, ImeiResponse imei, DungLuongResponse dungLuong, MauSacResponse mauSac, HeDieuHanhResponse heDieuHanh, NhaSanXuatResponse nhaSanXuat, ChatLieuResponse chatLieu, float trongLuong, float gia) {
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

    public SanPhamChiTietResponse(SanPhamResponse sanPham, RamResponse ram, CpuResponse cpu, ManHinhResponse manHinh, GpuResponse gpu, DungLuongResponse dungLuong, MauSacResponse mauSac, HeDieuHanhResponse heDieuHanh, NhaSanXuatResponse nhaSanXuat, ChatLieuResponse chatLieu, int soLuong) {
        this.sanPham = sanPham;
        this.ram = ram;
        this.cpu = cpu;
        this.manHinh = manHinh;
        this.gpu = gpu;
        this.dungLuong = dungLuong;
        this.mauSac = mauSac;
        this.heDieuHanh = heDieuHanh;
        this.nhaSanXuat = nhaSanXuat;
        this.chatLieu = chatLieu;
        this.soLuong = soLuong;
    }

    public SanPhamChiTietResponse(SanPhamResponse sanPham, RamResponse ram, CpuResponse cpu, ManHinhResponse manHinh, GpuResponse gpu, DungLuongResponse dungLuong, MauSacResponse mauSac, NhaSanXuatResponse nhaSanXuat, ChatLieuResponse chatLieu) {
        this.sanPham = sanPham;
        this.ram = ram;
        this.cpu = cpu;
        this.manHinh = manHinh;
        this.gpu = gpu;
        this.dungLuong = dungLuong;
        this.mauSac = mauSac;
        this.nhaSanXuat = nhaSanXuat;
        this.chatLieu = chatLieu;
    }

    public SanPhamChiTietResponse(SanPhamResponse sanPham, RamResponse ram, CpuResponse cpu, GpuResponse gpu, DungLuongResponse dungLuong, MauSacResponse mauSac, HeDieuHanhResponse heDieuHanh, NhaSanXuatResponse nhaSanXuat, ChatLieuResponse chatLieu) {
        this.sanPham = sanPham;
        this.ram = ram;
        this.cpu = cpu;
        this.gpu = gpu;
        this.dungLuong = dungLuong;
        this.mauSac = mauSac;
        this.heDieuHanh = heDieuHanh;
        this.nhaSanXuat = nhaSanXuat;
        this.chatLieu = chatLieu;
    }

    public SanPhamChiTietResponse(SanPhamResponse sanPham, RamResponse ram, CpuResponse cpu, ManHinhResponse manHinh, GpuResponse gpu, ImeiResponse imei, DungLuongResponse dungLuong, MauSacResponse mauSac, HeDieuHanhResponse heDieuHanh, NhaSanXuatResponse nhaSanXuat, ChatLieuResponse chatLieu, int soLuong, float trongLuong, float gia) {
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
        this.soLuong = soLuong;
        this.trongLuong = trongLuong;
        this.gia = gia;
    }

    public SanPhamChiTietResponse(int id, SanPhamResponse sanPham, RamResponse ram, CpuResponse cpu, ManHinhResponse manHinh, GpuResponse gpu, ImeiResponse imei, DungLuongResponse dungLuong, MauSacResponse mauSac, HeDieuHanhResponse heDieuHanh, NhaSanXuatResponse nhaSanXuat, ChatLieuResponse chatLieu, float trongLuong, float gia, Date ngayTao, Date ngaySua, String nguoiTao, String nguoiSua, int deleted) {
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

    public SanPhamChiTietResponse(int id, SanPhamResponse sanPham, RamResponse ram, CpuResponse cpu, ManHinhResponse manHinh, GpuResponse gpu, ImeiResponse imei, DungLuongResponse dungLuong, MauSacResponse mauSac, HeDieuHanhResponse heDieuHanh, NhaSanXuatResponse nhaSanXuat, ChatLieuResponse chatLieu, int soLuong, float trongLuong, float gia, Date ngayTao, Date ngaySua, String nguoiTao, String nguoiSua, int deleted) {
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
        this.soLuong = soLuong;
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

    public SanPhamResponse getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPhamResponse sanPham) {
        this.sanPham = sanPham;
    }

    public RamResponse getRam() {
        return ram;
    }

    public void setRam(RamResponse ram) {
        this.ram = ram;
    }

    public CpuResponse getCpu() {
        return cpu;
    }

    public void setCpu(CpuResponse cpu) {
        this.cpu = cpu;
    }

    public ManHinhResponse getManHinh() {
        return manHinh;
    }

    public void setManHinh(ManHinhResponse manHinh) {
        this.manHinh = manHinh;
    }

    public GpuResponse getGpu() {
        return gpu;
    }

    public void setGpu(GpuResponse gpu) {
        this.gpu = gpu;
    }

    public ImeiResponse getImei() {
        return imei;
    }

    public void setImei(ImeiResponse imei) {
        this.imei = imei;
    }

    public DungLuongResponse getDungLuong() {
        return dungLuong;
    }

    public void setDungLuong(DungLuongResponse dungLuong) {
        this.dungLuong = dungLuong;
    }

    public MauSacResponse getMauSac() {
        return mauSac;
    }

    public void setMauSac(MauSacResponse mauSac) {
        this.mauSac = mauSac;
    }

    public HeDieuHanhResponse getHeDieuHanh() {
        return heDieuHanh;
    }

    public void setHeDieuHanh(HeDieuHanhResponse heDieuHanh) {
        this.heDieuHanh = heDieuHanh;
    }

    public NhaSanXuatResponse getNhaSanXuat() {
        return nhaSanXuat;
    }

    public void setNhaSanXuat(NhaSanXuatResponse nhaSanXuat) {
        this.nhaSanXuat = nhaSanXuat;
    }

    public ChatLieuResponse getChatLieu() {
        return chatLieu;
    }

    public void setChatLieu(ChatLieuResponse chatLieu) {
        this.chatLieu = chatLieu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
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

    @Override
    public String toString() {
        return "SanPhamChiTietResponse{" + "id=" + id + ", sanPham=" + sanPham + ", ram=" + ram + ", cpu=" + cpu + ", manHinh=" + manHinh + ", gpu=" + gpu + ", imei=" + imei + ", dungLuong=" + dungLuong + ", mauSac=" + mauSac + ", heDieuHanh=" + heDieuHanh + ", nhaSanXuat=" + nhaSanXuat + ", chatLieu=" + chatLieu + ", soLuong=" + soLuong + ", trongLuong=" + trongLuong + ", gia=" + gia + ", ngayTao=" + ngayTao + ", ngaySua=" + ngaySua + ", nguoiTao=" + nguoiTao + ", nguoiSua=" + nguoiSua + ", deleted=" + deleted + '}';
    }

    public Object[] getObject() {
        return new Object[]{nhaSanXuat.getTenNhaSanXuat(), dungLuong.getDungLuong(), mauSac.getTenMauSac(), heDieuHanh.getHeDieuHanh(), gpu.getNhaCungCap(), ram.getDungLuongRam(),
            cpu.getNhaCungCap(), manHinh.getLoaiManHinh(), chatLieu.getTenChatLieu(), soLuong, trongLuong, gia};
    }

   

}
