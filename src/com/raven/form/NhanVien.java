package com.raven.form;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.raven.form.frameViewCtsp.QrStaff;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.raven.services.NhanVienService;
import com.raven.services.impl.NhanVienServiceImpl;
import com.raven.viewmodels.NhanVienResponse;
import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.jfr.consumer.EventStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NhanVien extends javax.swing.JPanel {

    NhanVienService service = new NhanVienServiceImpl();
    private DefaultTableModel dtm = new DefaultTableModel();
    private int index = -1;
    private List<NhanVienResponse> list = new ArrayList<>();
    DefaultTableModel model = new DefaultTableModel();
    private WebcamPanel panel = null;
    private Webcam webcam = null;
    private DefaultTableModel dtmsp = new DefaultTableModel();
    private SanPham product = new SanPham();
    public int row;
    public static String imeiiii;

    public NhanVien() {
        initComponents();
        list = service.getAll();
        showData(service.getAll());
    }

    public void showData(List<NhanVienResponse> list) {
        dtm = (DefaultTableModel) tblNhanVien.getModel();
        dtm.setRowCount(0);
        int a = 1;
        for (NhanVienResponse nv : list) {
            dtm.addRow(new Object[]{
                a++,
                nv.getMaNhanVien(),
                nv.getTenNhanVien(),
                nv.getNgaySinh(),
                nv.getDiaChi(),
                nv.isGioiTinh() ? "Nam" : "Nữ",
                nv.getEmail(),
                nv.getSoDienThoai(),
                nv.getSoCccd(),
                nv.isVaiTro() ? "Nhân viên" : "Quản lý"
            });
        }
    }

    void show(int index) {
        NhanVienResponse nv = service.getAll().get(index);
        txtMaNhanVien.setText(nv.getMaNhanVien().trim().replaceAll("\\s+", ""));
        txtTenNhanVien.setText(nv.getTenNhanVien().replaceAll("\\s+", " "));
        dateNgaySinh.setDate(nv.getNgaySinh());
        txtDiaChi.setText(nv.getDiaChi().trim().replaceAll("\\s+", " "));
        if (nv.isGioiTinh()) {
            rdoNam.setSelected(true);
            rdoNu.setSelected(false);
        } else {
            rdoNam.setSelected(false);
            rdoNu.setSelected(true);
        }
        DecimalFormat dcmfm = new DecimalFormat();
        txtLuong.setText(dcmfm.format(nv.getLuong()).replaceAll(",", "").trim().replaceAll("\\s+", ""));
        txtEmail.setText(nv.getEmail().trim().replaceAll("\\s+", " "));
        txtSDT.setText(String.valueOf(nv.getSoDienThoai()).trim().replaceAll("\\s+", ""));
        txtSoCCCD.setText(nv.getSoCccd().trim().replaceAll("\\s+", ""));
        String matkhau = service.getAll().get(index).getMatKhau();
        txtMatKhau.setText(matkhau);
        if (nv.isVaiTro()) {
            rdoNhanVien.setSelected(true);
            rdoQuanLy.setSelected(false);
        } else {
            rdoNhanVien.setSelected(false);
            rdoQuanLy.setSelected(true);
        }
    }

    public boolean testData() {

        boolean gt = false;
        if (rdoNhanVien.isSelected()) {
            gt = rdoNhanVien.isSelected();
        } else {
            rdoQuanLy.isSelected();
        }

        if (txtMaNhanVien.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên không được để trống !");
            return false;
        }
        if (txtTenNhanVien.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ tên nhân viên không được để trống !");
            return false;
        }
        if (txtTenNhanVien.getText().length() <= 5) {
            JOptionPane.showMessageDialog(this, "Họ tên phải nhiều hơn 5 kí tự !");

            return false;
        }
        if (txtTenNhanVien.getText().length() > 50) {
            JOptionPane.showMessageDialog(this, "Họ tên không được quá 50 kí tự");
            return false;
        }

        if (dateNgaySinh.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Ngày sinh nhân viên không được để trống !");
            return false;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date ngaySinhDate;

        try {
            ngaySinhDate = format.parse(format.format(dateNgaySinh.getDate())); // Get the date from the date picker directly

            // Check if the date is in the future
            Date currentDate = new Date();
            if (ngaySinhDate.after(currentDate)) {
                JOptionPane.showMessageDialog(this, "Ngày sinh không thể lớn hơn ngày hiện tại!");
                dateNgaySinh.requestFocus();
                return false;
            }

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không đúng định dạng yyyy-MM-dd !");
            dateNgaySinh.requestFocus();
            return false;
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Lỗi trong việc đặt định dạng ngày!");
            dateNgaySinh.requestFocus();
            return false;
        }

        if (txtDiaChi.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Địa chỉ không được để trống !");
            return false;
        }
        if (txtLuong.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lương không được để trống !");
            return false;
        }

        if (txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email nhân viên không được để trống !");
            return false;
        }

        if (txtEmail.getText().length() <= 10) {
            JOptionPane.showMessageDialog(this, "Email phải nhiều hơn 10 kí tự !");
            return false;
        }
        if (txtEmail.getText().length() > 50) {
            JOptionPane.showMessageDialog(this, "Email không được quá 50 kí tự");
            return false;
        }
        if (txtSoCCCD.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số CCCD không được để trống !");
            return false;
        }

        if (txtSoCCCD.getText().length() <= 5) {
            JOptionPane.showMessageDialog(this, "Số CCCD phải nhiều hơn 5 số !");
            return false;
        }
        if (txtSoCCCD.getText().length() > 24) {
            JOptionPane.showMessageDialog(this, "Số CCCD  không được quá 24 số !");
            return false;
        }
        String password = new String(txtMatKhau.getPassword());
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống !");
            return false;
        }
        if (password.length() < 5 || password.length() > 50) {
            JOptionPane.showMessageDialog(this, "Mật khẩu chỉ được nằm trong khoảng 5 đến 50 kí tự");
            return false;
        }
        String rex = "^[0-9a-zA-Z]+$";
        if (!password.matches(rex)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu chỉ được chứa số và chữ không được chứa kí tự đặc biệt");
            return false;
        }
        return true;

    }

    void resetForm() {
        txtMaNhanVien.setText("");
        txtTenNhanVien.setText("");
        dateNgaySinh.setDate(null);
        txtDiaChi.setText("");
        rdoNam.setSelected(true);
        rdoNu.setSelected(false);
        txtLuong.setText("");
        txtEmail.setText("");
        txtSDT.setText("");
        txtSoCCCD.setText("");
        rdoNhanVien.setSelected(false);
        rdoQuanLy.setSelected(true);
        txtMatKhau.setText("");

    }

    NhanVienResponse getForm() {
        String maNhanVien = txtMaNhanVien.getText().trim().replaceAll("\\s+", "");
        String tenNhanVien = txtTenNhanVien.getText().trim().replaceAll("\\s+", " ");
        String soDienThoai = txtSDT.getText().trim().replaceAll("\\s+", "");
        boolean gioiTinh;
        boolean vaiTro;
        String diaChi = txtDiaChi.getText().trim().replaceAll("\\s+", " ");
        String luong = txtLuong.getText().trim().replaceAll("\\s+", " ");
        float LuongFL;
        String email = txtEmail.getText().trim().replaceAll("\\s+", " ");
        Date ngaySinh = dateNgaySinh.getDate();
        String CCCD = txtSoCCCD.getText().trim().replaceAll("\\s+", "");
        String password = new String(txtMatKhau.getPassword());
        if (maNhanVien.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên không được để trống ");
            txtMaNhanVien.requestFocus();
            return null;
        }

        if (tenNhanVien.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên nhân viên không được để trống ");
            txtTenNhanVien.requestFocus();
            return null;
        }
        if (soDienThoai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số điện thoại nhân viên không được để trống ");
            txtSDT.requestFocus();
            return null;
        }
        for (NhanVienResponse nv : service.getAll()) {
            if (soDienThoai.equals(nv.getSoDienThoai())) {
                JOptionPane.showMessageDialog(this, "Số điện thoại đăng ký này đã được sử dụng");
                return null;
            }
        }
        if (soDienThoai.length() != 10) {
            JOptionPane.showMessageDialog(this, "Số điện thoại nhân viên phải là 10 số ");
            txtSDT.requestFocus();
            return null;
        }
        String regexsdt = "^0\\d+$";
        if (!soDienThoai.matches(regexsdt)) {
            JOptionPane.showMessageDialog(this, "Dữ liệu được nhập vào phải là số");
            return null;
        }
        if (rdoNam.isSelected()) {
            gioiTinh = true;
        } else {
            gioiTinh = false;
        }
        if (rdoNhanVien.isSelected()) {
            vaiTro = true;
        } else {
            vaiTro = false;
        }
        if (diaChi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Địa chỉ không được để trống");
            txtDiaChi.requestFocus();
            return null;
        }
        if (luong.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lương không được để trống");
            txtLuong.requestFocus();
            return null;
        }
        try {
            LuongFL = Float.parseFloat(luong);
            if (LuongFL <= 0) {
                JOptionPane.showMessageDialog(this, "Lương phải lớn hơn 0");
                txtLuong.requestFocus();
                return null;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lương phải là số");
            txtLuong.requestFocus();
            return null;
        }
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email không được để trống");
            txtEmail.requestFocus();
            return null;

        }
        if (ngaySinh == null) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không được để trống");
            dateNgaySinh.requestFocus();
            return null;
        }
        Date ngaySinhDate = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String datePar = format.format(ngaySinh);
            ngaySinhDate = format.parse(datePar);
            Date dateNow = new Date();
            if (ngaySinhDate.after(dateNow)) {
                JOptionPane.showMessageDialog(this, "Ngày sinh không được lớn hơn ngày hiện tại");
                dateNgaySinh.requestFocus();
                return null;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ngày sinh sai định dạng");
            dateNgaySinh.requestFocus();
            return null;
        }
        if (CCCD.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Căn cước công dân không được để trống");
            txtSoCCCD.requestFocus();
            return null;
        }
        for (NhanVienResponse nv : service.getAll()) {
            if (CCCD.equals(nv.getSoCccd())) {
                JOptionPane.showMessageDialog(this, "Số căn cước đã được đăng ký");
                return null;
            }
        }
        try {
            String regex = "^\\d{12}+$";
            if (!CCCD.matches(regex)) {
                JOptionPane.showMessageDialog(this, "Căn cước không đủ 12 số hoặc không phải là số");
                return null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Căn cước công dân phải là số nguyên");
            txtSoCCCD.requestFocus();
            return null;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống !");
            return null;
        }
        if (password.length() < 5 || password.length() > 50) {
            JOptionPane.showMessageDialog(this, "Mật khẩu chỉ được nằm trong khoảng 5 đến 50 kí tự");
            return null;
        }

        NhanVienResponse rp = new NhanVienResponse(maNhanVien, tenNhanVien, ngaySinhDate, diaChi, gioiTinh, LuongFL, email, soDienThoai, password, CCCD, vaiTro);
        return rp;

    }

    NhanVienResponse getFormUpdate() {
        String maNhanVien = txtMaNhanVien.getText().trim().replaceAll("\\s+", "");
        String tenNhanVien = txtTenNhanVien.getText().trim().replaceAll("\\s+", " ");
        String soDienThoai = txtSDT.getText().trim().replaceAll("\\s+", "");
        boolean gioiTinh;
        boolean vaiTro;
        String diaChi = txtDiaChi.getText().trim().replaceAll("\\s+", " ");
        String luong = txtLuong.getText().trim().replaceAll(",", "").replaceAll("\\s+", "");
        float LuongFL;
        String email = txtEmail.getText().trim().replaceAll("\\s+", "");
        Date ngaySinh = dateNgaySinh.getDate();
        String CCCD = txtSoCCCD.getText().trim().replaceAll("\\s+", "");
        String password = new String(txtMatKhau.getPassword());
        if (maNhanVien.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên không được để trống ");
            txtMaNhanVien.requestFocus();
            return null;
        }

        if (tenNhanVien.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên nhân viên không được để trống ");
            txtTenNhanVien.requestFocus();
            return null;
        }
        if (soDienThoai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số điện thoại nhân viên không được để trống ");
            txtSDT.requestFocus();
            return null;
        }

        if (soDienThoai.length() != 10) {
            JOptionPane.showMessageDialog(this, "Số điện thoại nhân viên phải là 10 số ");
            txtSDT.requestFocus();
            return null;
        }
        String regexsdt = "^0\\d+$";
        if (!soDienThoai.matches(regexsdt)) {
            JOptionPane.showMessageDialog(this, "Dữ liệu được nhập vào phải là số và số đầu tiên phải là 0");
            return null;
        }
        if (rdoNam.isSelected()) {
            gioiTinh = true;
        } else {
            gioiTinh = false;
        }
        if (rdoNhanVien.isSelected()) {
            vaiTro = true;
        } else {
            vaiTro = false;
        }
        if (diaChi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Địa chỉ không được để trống");
            txtDiaChi.requestFocus();
            return null;
        }
        if (luong.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lương không được để trống");
            txtLuong.requestFocus();
            return null;
        }
        try {
            LuongFL = Float.parseFloat(luong);
            if (LuongFL <= 0) {
                JOptionPane.showMessageDialog(this, "Lương phải lớn hơn 0");
                txtLuong.requestFocus();
                return null;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lương phải là số");
            txtLuong.requestFocus();
            return null;
        }
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email không được để trống");
            txtEmail.requestFocus();
            return null;

        }
        if (ngaySinh == null) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không được để trống");
            dateNgaySinh.requestFocus();
            return null;
        }
        Date ngaySinhDate = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String datePar = format.format(ngaySinh);
            ngaySinhDate = format.parse(datePar);
            Date dateNow = new Date();
            if (ngaySinhDate.after(dateNow)) {
                JOptionPane.showMessageDialog(this, "Ngày sinh không được lớn hơn ngày hiện tại");
                dateNgaySinh.requestFocus();
                return null;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ngày sinh sai định dạng");
            dateNgaySinh.requestFocus();
            return null;
        }
        if (CCCD.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Căn cước công dân không được để trống");
            txtSoCCCD.requestFocus();
            return null;
        }
        try {
            String regex = "^\\d{12}+$";
            if (!CCCD.matches(regex)) {
                JOptionPane.showMessageDialog(this, "Căn cước không đủ 12 số hoặc không phải là số");
                return null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Căn cước công dân phải là số nguyên");
            txtSoCCCD.requestFocus();
            return null;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống !");
            return null;
        }
        if (password.length() < 5 || password.length() > 50) {
            JOptionPane.showMessageDialog(this, "Mật khẩu chỉ được nằm trong khoảng 5 đến 50 kí tự");
            return null;
        }
        String rex = "^[0-9a-zA-Z]+$";
        if (!password.matches(rex)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu chỉ được chứa số và chữ không được chứa kí tự đặc biệt");
            return null;
        }

        NhanVienResponse rp = new NhanVienResponse(maNhanVien, tenNhanVien, ngaySinhDate, diaChi, gioiTinh, LuongFL, email, soDienThoai, password, CCCD, vaiTro);
        return rp;

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        txtMaNhanVien = new javax.swing.JTextField();
        txtTenNhanVien = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtLuong = new javax.swing.JTextField();
        txtSoCCCD = new javax.swing.JTextField();
        rdoNhanVien = new javax.swing.JRadioButton();
        rdoQuanLy = new javax.swing.JRadioButton();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();
        btnXuat = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnQuetQRCCCD = new javax.swing.JButton();
        dateNgaySinh = new com.toedter.calendar.JDateChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        jLabel14 = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1058, 770));
        setMinimumSize(new java.awt.Dimension(1058, 770));
        setPreferredSize(new java.awt.Dimension(1058, 770));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 0, 0)));

        txtLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLuongActionPerformed(evt);
            }
        });

        buttonGroup3.add(rdoNhanVien);
        rdoNhanVien.setSelected(true);
        rdoNhanVien.setText("Nhân viên");

        buttonGroup3.add(rdoQuanLy);
        rdoQuanLy.setText("Quản lý");

        buttonGroup1.add(rdoNam);
        rdoNam.setSelected(true);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        jLabel1.setText("Mã nhân viên");

        jLabel2.setText("Tên nhân viên");

        jLabel6.setText("Vai trò");

        jLabel7.setText("Giới tính");

        jLabel8.setText("Số điện thoại");

        jLabel9.setText("Địa chỉ");

        jLabel10.setText("Lương");

        jLabel5.setText("Email");

        jLabel11.setText("Ngày sinh");

        jLabel12.setText("CCCD");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Thông tin nhân viên");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 0, 0)));

        btnThem.setBackground(new java.awt.Color(255, 0, 0));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm nhân viên");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(255, 0, 0));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(255, 0, 0));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnThoat.setBackground(new java.awt.Color(255, 0, 0));
        btnThoat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThoat.setForeground(new java.awt.Color(255, 255, 255));
        btnThoat.setText("Thoát");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        btnXuat.setBackground(new java.awt.Color(255, 0, 0));
        btnXuat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXuat.setForeground(new java.awt.Color(255, 255, 255));
        btnXuat.setText("Excel");
        btnXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(255, 0, 0));
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnReset.setForeground(new java.awt.Color(255, 255, 255));
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Các chức năng");

        btnQuetQRCCCD.setBackground(new java.awt.Color(255, 0, 0));
        btnQuetQRCCCD.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnQuetQRCCCD.setForeground(new java.awt.Color(255, 255, 255));
        btnQuetQRCCCD.setText("Quét CCCD");
        btnQuetQRCCCD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuetQRCCCDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 13, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                                    .addComponent(btnThoat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnXoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnXuat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnQuetQRCCCD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(19, 19, 19))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnXuat, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(btnThoat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnQuetQRCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        dateNgaySinh.setDateFormatString("yyyy-MM-dd");

        txtDiaChi.setColumns(20);
        txtDiaChi.setRows(5);
        jScrollPane2.setViewportView(txtDiaChi);

        jLabel14.setText("Mật khẩu :");

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/icons8-return-30.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(37, 37, 37)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtMaNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                                            .addComponent(txtTenNhanVien)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(34, 34, 34)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(rdoNam)
                                                        .addComponent(rdoNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGap(57, 57, 57)
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(rdoQuanLy)
                                                        .addComponent(rdoNu)))
                                                .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSoCCCD)
                                    .addComponent(dateNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtEmail)
                                    .addComponent(txtLuong)
                                    .addComponent(txtMatKhau))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(117, 117, 117)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(txtTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rdoNam, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(rdoNu))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rdoNhanVien)
                                    .addComponent(rdoQuanLy))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(dateNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel12))
                    .addComponent(txtSoCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMatKhau)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 0, 0)));

        txtTimKiem.setForeground(new java.awt.Color(153, 153, 153));
        txtTimKiem.setText("Mã nhân viên - Tên nhân viên - Số điện thoại");
        txtTimKiem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTimKiemFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTimKiemFocusLost(evt);
            }
        });
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        jLabel3.setText("Tìm kiếm :");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(4, 4, 4))
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
                .addContainerGap())
        );

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "MaNV", "TenNV", "Ngày sinh", "Địa chỉ", "Giới tính", "Email", "Số điện thoại", "Số CCCD", "Vai trò"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1048, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                .addGap(1044, 1044, 1044))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(250, 250, 250)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        index = tblNhanVien.getSelectedRow();
        show(index);
//        txtMaNhanVien.setEnabled(false);
        int id = service.getAll().get(index).getId();
        String password = service.getAll().get(index).getMatKhau();
        System.out.println("Mat khau" + password);
        System.out.println("iD nhan vien:" + id);

    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        int check = JOptionPane.showConfirmDialog(this, "Bạn muốn thoát?");
        if (check == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_btnThoatActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        int row = tblNhanVien.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bạn phải chọn 1 dòng");
            return;
        }
        if (true) {

        }
        int a = JOptionPane.showConfirmDialog(this, "Bạn muốn xóa?");
        if (a == 0) {
            int id = service.getAll().get(index).getId();
            System.out.println(id);
            if (service.delete(id)) {
                JOptionPane.showMessageDialog(this, "Xóa dữ liệu thành công");
                showData(service.getAll());
                this.resetForm();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa dữ liệu thất bại");

            }
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm không ?", "add", JOptionPane.YES_NO_OPTION);
        if (check != JOptionPane.YES_NO_OPTION) {
            return;
        }
        NhanVienResponse nv = getForm();
        try {
            if (service.add(nv)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                showData(service.getAll());
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void txtLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLuongActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        resetForm();
        txtMaNhanVien.setEnabled(true);
    }//GEN-LAST:event_btnResetActionPerformed

    private void txtTimKiemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusGained
        // TODO add your handling code here:
        if (txtTimKiem.getText().equals("Mã nhân viên - Tên nhân viên - Số điện thoại")) {
            txtTimKiem.setText("");
            setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_txtTimKiemFocusGained

    private void txtTimKiemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusLost
        // TODO add your handling code here:
        if (txtTimKiem.getText().equals("")) {
            txtTimKiem.setText("Mã nhân viên - Tên nhân viên - Số điện thoại");
            setForeground(new Color(53, 153, 153));
        }
    }//GEN-LAST:event_txtTimKiemFocusLost

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed

    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void btnXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatActionPerformed
        try {
            String path = "D:\\admin\\GIT_FINAL_DA1 - Donate_AGift_ForKien\\Exel";
            JFileChooser jFileChooser = new JFileChooser(path);
            jFileChooser.showSaveDialog(this);
            File saveFile = jFileChooser.getSelectedFile();

            if (saveFile != null) {
                saveFile = new File(saveFile.toString() + ".xlsx");
                Workbook wb = new XSSFWorkbook();
                Sheet sheet = (Sheet) wb.createSheet("Account");

                Row rowCol = sheet.createRow(0);
                for (int i = 0; i < tblNhanVien.getColumnCount(); i++) {
                    Cell cell = rowCol.createCell(i);
                    cell.setCellValue(tblNhanVien.getColumnName(i));
                }
                for (int j = 0; j < tblNhanVien.getRowCount(); j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < tblNhanVien.getColumnCount(); k++) {
                        Cell cell = row.createCell(k);
                        if (tblNhanVien.getValueAt(j, k) != null) {
                            cell.setCellValue(tblNhanVien.getValueAt(j, k).toString());
                        }
                    }
                }
                FileOutputStream out = new FileOutputStream(new File(saveFile.toString()));
                wb.write(out);
                wb.close();
                out.close();
                EventStream.openFile(saveFile.toPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnXuatActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        int roww = tblNhanVien.getSelectedRow();
        if (roww == -1) {
            JOptionPane.showMessageDialog(this, "Bạn phải chọn 1 dòng");
            return;
        }
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa không ?", "add", JOptionPane.YES_NO_OPTION);
        if (check != JOptionPane.YES_NO_OPTION) {
            return;
        }

        String maNV = tblNhanVien.getValueAt(roww, 1).toString();
        String cccd = tblNhanVien.getValueAt(roww, 8).toString();
        String sdt = tblNhanVien.getValueAt(roww, 7).toString();
        for (NhanVienResponse nv : list) {
            if (nv.getMaNhanVien().equals(txtMaNhanVien.getText()) && !nv.getMaNhanVien().equals(maNV)) {
                System.out.println(maNV);
                System.out.println(nv.getMaNhanVien());
                JOptionPane.showMessageDialog(this, "Đã trùng mã nhân viên");
                return;
            }
        }
        for (NhanVienResponse nv : list) {
            if (nv.getSoDienThoai().equals(txtSDT.getText()) && !nv.getSoDienThoai().equals(sdt)) {
                JOptionPane.showMessageDialog(this, "Đã trùng số điện thoại");
                return;
            }
        }

        for (NhanVienResponse nv : list) {
            if (nv.getSoCccd().equals(txtSoCCCD.getText()) && !nv.getSoCccd().equals(cccd)) {
                JOptionPane.showMessageDialog(this, "Đã trùng căn cước");
                return;
            }
        }
//        String password = service.getAll().get(index).getMatKhau();
        NhanVienResponse nvr = getFormUpdate();
        int id = service.getAll().get(roww).getId();
        if (service.update(nvr, id)) {
            JOptionPane.showMessageDialog(this, "Update thành công");
            showData(service.getAll());
        } else {
            JOptionPane.showMessageDialog(this, "Update thất bại");
        }


    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnQuetQRCCCDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuetQRCCCDActionPerformed
        // TODO add your handling code here:
        QrStaff qr = new QrStaff(null, true);
        qr.setVisible(true);
        qr.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("window closing");
                txtSoCCCD.setText(QrStaff.socccd);
                txtDiaChi.setText(QrStaff.nvaddress);
                txtTenNhanVien.setText(QrStaff.tennv);
                SimpleDateFormat sdfInput = new SimpleDateFormat("ddMMyyyy");
                SimpleDateFormat sdfOutput = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date date = sdfInput.parse(QrStaff.nvbirth);
                    String formattedDate = sdfOutput.format(date);
                    dateNgaySinh.setDate(sdfOutput.parse(formattedDate));
                    System.out.println("Formatted Date: " + formattedDate);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                qr.dispose();
            }
        });

    }//GEN-LAST:event_btnQuetQRCCCDActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
        String timKiem = txtTimKiem.getText().trim();
        if (timKiem.isEmpty()) {
            showData(service.getAll());
        } else {
            showData(service.searchAllField(timKiem));
        }
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String randomCode = generateRandomCode(9);
        txtMatKhau.setText(randomCode);
    }//GEN-LAST:event_jButton1ActionPerformed

    private String generateRandomCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        // Tạo đối tượng Random
        Random random = new Random();
        StringBuilder randomCode = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            randomCode.append(randomChar);
        }
        return randomCode.toString();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnQuetQRCCCD;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuat;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup3;
    private com.toedter.calendar.JDateChooser dateNgaySinh;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNhanVien;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JRadioButton rdoQuanLy;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtLuong;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSoCCCD;
    private javax.swing.JTextField txtTenNhanVien;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables

}
