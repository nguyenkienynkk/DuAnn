package com.raven.form;

import com.raven.domainmodels.ModelVoucher;
import com.raven.viewmodels.TableActionCellRender;
import com.raven.services.VoucherService;
import com.raven.services.impl.VoucherServiceIplm;
import com.raven.utils.Auth;
import com.raven.viewmodels.TableActionCellEditor;
import com.raven.viewmodels.TableActionEvent;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import jdk.jfr.consumer.EventStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;

public class Voucher extends javax.swing.JPanel {

    List<ModelVoucher> listVoucher = new ArrayList<>();
    List<ModelVoucher> listVoucherDeleted = new ArrayList<>();
    DefaultTableModel modelVoucher = new DefaultTableModel();
    DefaultTableModel modelVoucherDeleted = new DefaultTableModel();
    DefaultComboBoxModel<ModelVoucher> modelCombo = new DefaultComboBoxModel<>();
    private VoucherService ser = new VoucherServiceIplm();
    DecimalFormat decimalFormat = new DecimalFormat("###0.##");
    int trangVoucher = 1;
    int trangVoucherDeleted = 1;

    public Voucher() {
        initComponents();
        setOpaque(false);
        unit();

    }

    void unit() {
        modelVoucherDeleted = (DefaultTableModel) tblVoucherDeletd.getModel();
        modelVoucher = (DefaultTableModel) tblVoucher.getModel();
        loadComboBoxToTableVoucher();
        clearFrom();
        loadTableByTrangThai();

        //Load Table Voucher
        listVoucher = ser.phanTrang(trangVoucher, 3);
        showData(listVoucher);

        //Load Table VoucherDeleted
        listVoucherDeleted = ser.phanTrangDeleted(trangVoucherDeleted, 7);
        showDataTableDeleted(listVoucherDeleted);

        if (trangVoucher == 1) {
            btnPrev.setEnabled(false);
            btnFirst.setEnabled(false);
        }
        if (trangVoucherDeleted == 1) {
            btnPrevDeleted.setEnabled(false);
            btnFirstDeleted.setEnabled(false);
        }
        int max = ser.getMaxPages(3);
        if (max == 1) {
            btnLast.setEnabled(false);
            btnNext.setEnabled(false);
        }
        lblTrang.setText("1/" + max);
        int maxByTableDeleted = ser.getMaxPagesDeleted(7);
        if (maxByTableDeleted == 1) {
            btnLastDeleted.setEnabled(false);
            btnNextDeleted.setEnabled(false);
        }
        if (maxByTableDeleted == 1) {
            btnLastDeleted.setEnabled(false);
            btnNextDeleted.setEnabled(false);
        }
        //Load Table Voucher
        listVoucher = ser.phanTrang(trangVoucher, 3);
        showData(listVoucher);

        //Load Table VoucherDeleted
        listVoucherDeleted = ser.phanTrangDeleted(trangVoucherDeleted, 7);
        showDataTableDeleted(listVoucherDeleted);
        lblTrangDeleted.setText("1/" + maxByTableDeleted);
        txtNgayBatDau.setDateFormatString("yyyy-MM-dd");
        txtNgayKetThuc.setDateFormatString("yyyy-MM-dd");
        txtSearchNgayBatDau.setDateFormatString("yyyy-MM-dd");
        txtSearchNgayKetThuc.setDateFormatString("yyyy-MM-dd");

        ActionButton();
        editByDateStart();
        editByDateEnd();
        editByPriceStart();
        editByPriceEnd();
        AutoUpdate();

    }

    void showData(List<ModelVoucher> list) {
        modelVoucher.setRowCount(0);
        int index = 1;
        for (ModelVoucher voucher : list) {
            modelVoucher.addRow(new Object[]{
                index++,
                voucher.getTenVoucher(),
                voucher.getMaVoucher(),
                voucher.isLoaiGiamGia() ? "%" : "Tiền",
                decimalFormat.format(voucher.getGiaTri()),
                voucher.getSoLuong(),
                voucher.getNgayBatDau(),
                voucher.getNgayKetThuc(),
                voucher.getTrangThai()
            });

        }
    }

    void showDataTableDeleted(List<ModelVoucher> list) {
        modelVoucherDeleted.setRowCount(0);
        int index = 1;
        for (ModelVoucher voucher : list) {
            modelVoucherDeleted.addRow(new Object[]{
                index++,
                voucher.getTenVoucher(),
                voucher.getMaVoucher(),
                voucher.isLoaiGiamGia() ? "%" : "Tiền",
                decimalFormat.format(voucher.getGiaTri()),
                voucher.getSoLuong(),
                voucher.getNgayBatDau(),
                voucher.getNgayKetThuc(),
                voucher.getTrangThai()
            });

        }
    }

    void loadToTableVoucherByAll() {
        String selectedCBO = cboSearchLoaiVoucher.getSelectedItem().toString().trim();
        String keyword = txtSearchKeyword.getText().trim();
        if (keyword.equals("Tên voucher - Mã voucher")) {
            keyword = "";
        }
        if (!keyword.isEmpty() && ("Sắp áp dụng".equals(selectedCBO) || "Đang áp dụng".equals(selectedCBO) || "Hết hạn".equals(selectedCBO))) {
            showData(ser.FindVoucherByKeyword(keyword, selectedCBO));
        } else if (keyword.isEmpty() && ("Sắp áp dụng".equals(selectedCBO) || "Đang áp dụng".equals(selectedCBO) || "Hết hạn".equals(selectedCBO))) {
            showData(ser.FindVoucherByKeyword("", selectedCBO));
        } else if (!keyword.isEmpty() && (!"Sắp áp dụng".equals(selectedCBO) || !"Đang áp dụng".equals(selectedCBO) || !"Hết hạn".equals(selectedCBO))) {
            showData(ser.FindVoucherByKeyword(keyword, ""));
        } else if ("Tất cả".equals(selectedCBO)) {
            showData(ser.FindVoucherByKeyword("", ""));
        }
    }

    void loadComboBoxToTableVoucher() {
        Set<String> setLoaiKM = new HashSet<>();
        for (ModelVoucher voucher : listVoucher) {
            setLoaiKM.add(voucher.toString());
        }
        modelCombo = (DefaultComboBoxModel) new DefaultComboBoxModel<>(setLoaiKM.toArray());
        cboLoaiKhuyenMai.setModel((DefaultComboBoxModel) modelCombo);
        cboLoaiKhuyenMai.removeAllItems();
        cboLoaiKhuyenMai.addItem("%");
        cboLoaiKhuyenMai.addItem("Tiền");
        String selectedKM = cboLoaiKhuyenMai.getSelectedItem().toString();
        if (selectedKM.equalsIgnoreCase("0")) {
            cboLoaiKhuyenMai.removeItem(selectedKM);
            cboLoaiKhuyenMai.addItem("%");
        } else if (selectedKM.equalsIgnoreCase("1")) {
            cboLoaiKhuyenMai.removeItem(selectedKM);
            cboLoaiKhuyenMai.addItem("Tiền");
        }

    }

    void clearFrom() {
        txtGiaTri.setText("");
        txtMaCode.setText("");
        txtNgayBatDau.setDate(null);
        txtNgayKetThuc.setDate(null);
        txtTenKhuyenMai.setText("");
        spfSoLuong.setValue(0);
        txtGiaTri.setText("");
        cboLoaiKhuyenMai.setSelectedIndex(0);
    }

    void showDetail() {
        int index = tblVoucher.getSelectedRow();
        txtTenKhuyenMai.setText(tblVoucher.getValueAt(index, 1).toString());
        txtMaCode.setText(tblVoucher.getValueAt(index, 2).toString());
        cboLoaiKhuyenMai.setSelectedItem(tblVoucher.getValueAt(index, 3).toString());
        txtGiaTri.setText(tblVoucher.getValueAt(index, 4).toString());
        spfSoLuong.setValue(Integer.parseInt(tblVoucher.getValueAt(index, 5).toString()));
        Object valueDateStart = tblVoucher.getValueAt(index, 6);
        Date dateStart = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateStart = dateFormat.parse(valueDateStart.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        txtNgayBatDau.setDate(dateStart);
        Object valueDateEnd = tblVoucher.getValueAt(index, 7);
        Date dateEnd = null;
        try {
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
            dateEnd = formatDate.parse(valueDateEnd.toString());

        } catch (Exception e) {
        }
        txtNgayKetThuc.setDate(dateEnd);
    }

    ModelVoucher getFrom() {
        String tenKhuyenMai = txtTenKhuyenMai.getText().trim().replaceAll("\\s+", " ");
        String maCode = txtMaCode.getText().trim().replaceAll("\\s+", "");
        String loaiKM = cboLoaiKhuyenMai.getSelectedItem().toString().trim();
        boolean loaiKMInt;
        String giaTri = txtGiaTri.getText().trim().replaceAll("\\s+", "");
        float giaTriFloat;
        int soLuong = spfSoLuong.getValue();
        Date ngayBatDau = txtNgayBatDau.getDate();
        Date ngayKetThuc = txtNgayBatDau.getDate();
        if (tenKhuyenMai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên voucher không được để trống !");
            txtTenKhuyenMai.requestFocus();
            return null;
        }
        if (tenKhuyenMai.length() > 50) {
            JOptionPane.showMessageDialog(this, "Tên voucher không được lớn 50 ký tự !");
            txtTenKhuyenMai.requestFocus();
            return null;
        }

        maCode = maCode.replaceAll("\\s+", "").trim();
        String regex = "^[a-zA-Z0-9]+$";
        if (maCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã voucher không được để trống !");
            txtMaCode.requestFocus();
            return null;
        }

        if (!maCode.matches(regex)) {
            JOptionPane.showMessageDialog(this, "Mã voucher không chứa ký tự đặc biệt !");
            txtMaCode.requestFocus();
            return null;
        }

        if (maCode.length() > 50) {
            JOptionPane.showMessageDialog(this, "Mã voucher không được lớn hơn 50 ký tự !");
            txtMaCode.requestFocus();
            return null;
        }

        if (loaiKM.equals("%")) {
            loaiKMInt = true;
        } else {
            loaiKMInt = false;
        }
        if (giaTri.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giá trị Voucher không được để trống !");
            txtGiaTri.requestFocus();
            return null;
        }

        try {
            giaTriFloat = Float.parseFloat(giaTri);
            if (giaTriFloat <= 0) {
                JOptionPane.showMessageDialog(this, "Giá trị Voucher phải lớn hơn 0 !");
                txtGiaTri.requestFocus();
                return null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Giá trị Voucher phải là số !");
            txtGiaTri.requestFocus();
            return null;
        }
        if (loaiKM.equals("%")) {
            if (giaTriFloat <= 0 || giaTriFloat > 100) {
                txtGiaTri.setText("100");
                JOptionPane.showMessageDialog(this, "Giá trị phải từ 1-100");

                txtGiaTri.requestFocus();
                return null;
            }
        }
        if (loaiKM.equals("Tiền")) {
            if (giaTriFloat <= 100000 || giaTriFloat > 5000000) {
                JOptionPane.showMessageDialog(this, "Giá trị phải từ 100.000 - 5.000.000");
                txtGiaTri.requestFocus();
                return null;
            }
        }

        if (soLuong <= 0) {
            JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0 !");
            spfSoLuong.requestFocus();
            return null;
        }
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        Date dateStart = null;
        if (ngayBatDau == null) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được để trống ");
            txtNgayBatDau.requestFocus();
            return null;
        }
        try {
            String datePar = formatDate.format(ngayBatDau);
            dateStart = formatDate.parse(datePar);
            Date dateNow = new Date();
            if (dateStart.before(dateNow)) {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được nhỏ hơn ngày hiện tại !");
                txtNgayBatDau.requestFocus();
                return null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu sai định dạng !");
            txtNgayBatDau.requestFocus();
            return null;
        }
        Date dateEnd = null;
        if (ngayKetThuc == null) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc không được để trống ");
            txtNgayKetThuc.requestFocus();
            return null;
        }
        try {
            String datePar = formatDate.format(ngayKetThuc);
            dateEnd = formatDate.parse(datePar);
            Date dateNow = new Date();
            if (dateStart.before(dateNow)) {
                JOptionPane.showMessageDialog(this, "Ngày kết thúc không được nhỏ hơn ngày hiện tại !");
                txtNgayKetThuc.requestFocus();
                return null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc sai định dạng !");
            txtNgayKetThuc.requestFocus();
            return null;
        }

        ModelVoucher voucher = new ModelVoucher(tenKhuyenMai, maCode, loaiKMInt, giaTriFloat, soLuong, ngayBatDau, ngayKetThuc);

        return voucher;
    }

    boolean checkValidateAddVoucher() {
        String tenKhuyenMai = txtTenKhuyenMai.getText().trim().replaceAll("\\s+", " ");
        String maCode = txtMaCode.getText().trim().replaceAll("\\s+", "");
        String loaiKM = cboLoaiKhuyenMai.getSelectedItem().toString();
        boolean loaiKMInt;
        String giaTri = txtGiaTri.getText().trim().replaceAll("\\s+", "");
        float giaTriFloat;
        int soLuong = spfSoLuong.getValue();
        Date ngayBatDau = txtNgayBatDau.getDate();
        Date ngayKetThuc = txtNgayBatDau.getDate();
        String trangThai;
        if (tenKhuyenMai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên voucher không được để trống !");
            txtTenKhuyenMai.requestFocus();
            return false;
        }
        if (tenKhuyenMai.length() > 50) {
            JOptionPane.showMessageDialog(this, "Tên voucher không được lớn 50 ký tự !");
            txtTenKhuyenMai.requestFocus();
            return false;
        }

        maCode = maCode.replaceAll("\\s+", "").trim();
        String regex = "^[a-zA-Z0-9]+$";
        if (maCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã voucher không được để trống !");
            txtMaCode.requestFocus();
            return false;
        }

        if (!maCode.matches(regex)) {
            JOptionPane.showMessageDialog(this, "Mã voucher không chứa ký tự đặc biệt !");
            txtMaCode.requestFocus();
            return false;
        }

        if (maCode.length() > 50) {
            JOptionPane.showMessageDialog(this, "Mã voucher không được lớn hơn 50 ký tự !");
            txtMaCode.requestFocus();
            return false;
        }

        if (loaiKM.equals("%")) {
            loaiKMInt = true;
        } else {
            loaiKMInt = false;
        }
        if (giaTri.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giá trị Voucher không được để trống !");
            txtGiaTri.requestFocus();
            return false;
        }

        try {
            giaTriFloat = Float.parseFloat(giaTri);
            if (giaTriFloat <= 0) {
                JOptionPane.showMessageDialog(this, "Giá trị Voucher phải lớn hơn 0 !");
                txtGiaTri.requestFocus();
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Giá trị Voucher phải là số !");
            txtGiaTri.requestFocus();
            return false;
        }
        if (loaiKM.equals("%")) {
            if (giaTriFloat <= 0 || giaTriFloat > 100) {
                txtGiaTri.setText("100");
                JOptionPane.showMessageDialog(this, "Giá trị phải từ 1-100");

                txtGiaTri.requestFocus();
                return false;
            }
        }
        if (loaiKM.equals("Tiền")) {
            if (giaTriFloat <= 100000 || giaTriFloat > 5000000) {
                JOptionPane.showMessageDialog(this, "Giá trị phải từ 100.000 - 5.000.000");
                txtGiaTri.requestFocus();
                return false;
            }
        }

        try {
            soLuong = (int) spfSoLuong.getValue();
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
                spfSoLuong.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số nguyên dương!");
            spfSoLuong.requestFocus();
            return false;
        }

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        Date dateStart = null;
        if (ngayBatDau == null) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được để trống ");
            txtNgayBatDau.requestFocus();
            return false;
        }
        try {
            String datePar = formatDate.format(ngayBatDau);
            dateStart = formatDate.parse(datePar);
            Date dateNow = new Date();
            if (dateStart.before(dateNow)) {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được nhỏ hơn ngày hiện tại !");
                txtNgayBatDau.requestFocus();
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu sai định dạng !");
            txtNgayBatDau.requestFocus();
            return false;
        }
        if (ngayKetThuc == null) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc không được để trống ");
            txtNgayKetThuc.requestFocus();
            return false;
        }
        Date dateEnd = null;
        try {
            String datePar = formatDate.format(ngayKetThuc);
            dateEnd = formatDate.parse(datePar);
            Date dateNow = new Date();
            if (dateStart.before(dateNow)) {
                JOptionPane.showMessageDialog(this, "Ngày kết thúc không được nhỏ hơn ngày hiện tại !");
                txtNgayKetThuc.requestFocus();
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc sai định dạng !");
            txtNgayKetThuc.requestFocus();
            return false;
        }
        return true;
    }

    ModelVoucher getFromUpdate() {
        String tenKhuyenMai = txtTenKhuyenMai.getText().trim().replaceAll("\\s+", " ");
        String maCode = txtMaCode.getText().trim().replaceAll("\\s+", "");
        String loaiKM = cboLoaiKhuyenMai.getSelectedItem().toString();
        boolean loaiKMInt;
        String giaTri = txtGiaTri.getText().trim().replaceAll("\\s+", "");
        float giaTriFloat;
        int soLuong = spfSoLuong.getValue();
        Date ngayBatDau = txtNgayBatDau.getDate();
        Date ngayKetThuc = txtNgayBatDau.getDate();
        if (tenKhuyenMai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên khuyến mãi không được để trống !");
            txtTenKhuyenMai.requestFocus();
            return null;
        }

        if (loaiKM.equals("%")) {
            loaiKMInt = true;
        } else {
            loaiKMInt = false;
        }
        if (giaTri.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giá trị Voucher không được để trống !");
            txtGiaTri.requestFocus();
            return null;
        }

        try {
            giaTriFloat = Float.parseFloat(giaTri);
            if (giaTriFloat <= 0) {
                JOptionPane.showMessageDialog(this, "Giá trị Voucher phải lớn hơn 0 !");
                txtGiaTri.requestFocus();
                return null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Giá trị Voucher phải là số !");
            txtGiaTri.requestFocus();
            return null;
        }
        if (loaiKM.equals("%")) {
            if (giaTriFloat <= 0 || giaTriFloat > 100) {
                JOptionPane.showMessageDialog(this, "Giá trị phải từ 1-100");
                txtGiaTri.requestFocus();
                return null;
            }
        }
        if (loaiKM.equals("Tiền")) {
            if (giaTriFloat <= 100000 || giaTriFloat > 5000000) {
                JOptionPane.showMessageDialog(this, "Giá trị phải từ 100.000 - 5.000.000");
                txtGiaTri.requestFocus();
                return null;
            }
        }

        if (soLuong <= 0) {
            JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0 !");
            spfSoLuong.requestFocus();
            return null;
        }
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        Date dateStart = null;

        try {
            String datePar = formatDate.format(ngayBatDau);
            dateStart = formatDate.parse(datePar);
            Date dateNow = new Date();
            if (dateStart.before(dateNow)) {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được nhỏ hơn ngày hiện tại !");
                txtNgayBatDau.requestFocus();
                return null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu sai định dạng !");
            txtNgayBatDau.requestFocus();
            return null;
        }
        Date dateEnd = null;
        try {
            Date dateNow = new Date();
            String dateParEnd = formatDate.format(ngayKetThuc);
            dateEnd = formatDate.parse(dateParEnd);

            if (dateStart.before(dateNow)) {
                JOptionPane.showMessageDialog(this, "Ngày kết thúc không được nhỏ hơn ngày hiện tại !");
                txtNgayKetThuc.requestFocus();
                return null;
            }

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc sai định dạng !");
            txtNgayKetThuc.requestFocus();
            return null;
        }

        ModelVoucher voucher = new ModelVoucher(tenKhuyenMai, maCode, loaiKMInt, giaTriFloat, soLuong, ngayBatDau, ngayKetThuc);

        return voucher;
    }

    ModelVoucher getFromUpdateByClick() {
        String trangThai = "";
        Date ngayBatDau = null;
        Date ngayKetThuc = null;
        int index = tblVoucher.getSelectedRow();
        String trangThaiFromTable = tblVoucher.getValueAt(index, 8).toString();

        Date currentDate = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (trangThaiFromTable.trim().equalsIgnoreCase("Hết hạn")) {
            trangThai = "Đang áp dụng";

            ngayBatDau = currentDate;
            ngayKetThuc = new Date(currentDate.getTime() + 86400000);

        } else if (trangThaiFromTable.trim().equalsIgnoreCase("Đang áp dụng")
                || trangThaiFromTable.trim().equalsIgnoreCase("Sắp áp dụng")) {
            trangThai = "Hết hạn";

            ngayBatDau = new Date(currentDate.getTime() - (86400000 * 2));
            ngayKetThuc = new Date(currentDate.getTime() - 86400000);
        }

        ModelVoucher voucher = new ModelVoucher(ngayBatDau, ngayKetThuc, trangThai);
        return voucher;
    }

    void AutoUpdate() {
        try {
            ser.AutoUpdateStatus();
        } catch (Exception e) {
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btgTrangThaiVoucher = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        txtTenKhuyenMai = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtNgayBatDau = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cboLoaiKhuyenMai = new javax.swing.JComboBox<>();
        spfSoLuong = new com.toedter.components.JSpinField();
        txtNgayKetThuc = new com.toedter.calendar.JDateChooser();
        jLabel15 = new javax.swing.JLabel();
        txtMaCode = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtGiaTri = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        btnCapNhat = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVoucher = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        btnXoaVoucher = new javax.swing.JButton();
        btnImportExcel = new javax.swing.JButton();
        btnXuatExcel = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        txtSearchKeyword = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        cboSearchLoaiVoucher = new javax.swing.JComboBox<>();
        jPanel10 = new javax.swing.JPanel();
        txtSearchNgayBatDau = new com.toedter.calendar.JDateChooser();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtSearchNgayKetThuc = new com.toedter.calendar.JDateChooser();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtSearchBeginPrice = new javax.swing.JTextField();
        txtSearchEndPrice = new javax.swing.JTextField();
        lblTrang = new javax.swing.JLabel();
        btnFirst = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblVoucherDeletd = new javax.swing.JTable();
        btnKhoiPhuc = new javax.swing.JButton();
        btnFirstDeleted = new javax.swing.JButton();
        btnPrevDeleted = new javax.swing.JButton();
        lblTrangDeleted = new javax.swing.JLabel();
        btnNextDeleted = new javax.swing.JButton();
        btnLastDeleted = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Phiếu giảm giá", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 51, 255))); // NOI18N

        txtTenKhuyenMai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenKhuyenMaiActionPerformed(evt);
            }
        });

        jLabel9.setText("Ngày bắt đầu");

        jLabel10.setText("Loại khuyến mại");

        jLabel12.setText("Số lượng");

        jLabel14.setText("Ngày kết thúc");

        spfSoLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                spfSoLuongKeyTyped(evt);
            }
        });

        jLabel15.setText("Mã voucher");

        txtMaCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaCodeActionPerformed(evt);
            }
        });

        jLabel17.setText("Tên khuyến mại");

        jLabel18.setText("Giá trị");

        txtGiaTri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaTriActionPerformed(evt);
            }
        });
        txtGiaTri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGiaTriKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGiaTriKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel12)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(txtTenKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel15)
                            .addComponent(jLabel9)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spfSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                                .addComponent(txtNgayBatDau, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtGiaTri, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cboLoaiKhuyenMai, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtMaCode, javax.swing.GroupLayout.Alignment.LEADING)))))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtMaCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboLoaiKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(22, 22, 22)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtGiaTri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(spfSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(txtNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chức năng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 51, 204))); // NOI18N

        btnCapNhat.setBackground(new java.awt.Color(255, 51, 51));
        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCapNhat.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/edit.png"))); // NOI18N
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(255, 51, 51));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/icons8-add-40.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCapNhat)
                .addGap(17, 17, 17))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        btnClear.setBackground(new java.awt.Color(255, 51, 51));
        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/icons8-clear-48.png"))); // NOI18N
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách khuyến mại", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 51, 255))); // NOI18N

        tblVoucher.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Tên", "Mã voucher", "Loại KM", "Giá trị", "Số lượng", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái", "Action", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblVoucher.setRowHeight(35);
        tblVoucher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVoucherMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblVoucherMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblVoucher);
        if (tblVoucher.getColumnModel().getColumnCount() > 0) {
            tblVoucher.getColumnModel().getColumn(0).setMinWidth(30);
            tblVoucher.getColumnModel().getColumn(0).setMaxWidth(30);
            tblVoucher.getColumnModel().getColumn(5).setMinWidth(70);
            tblVoucher.getColumnModel().getColumn(5).setMaxWidth(70);
            tblVoucher.getColumnModel().getColumn(10).setMinWidth(30);
            tblVoucher.getColumnModel().getColumn(10).setMaxWidth(30);
        }

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chức năng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 51, 204))); // NOI18N

        btnXoaVoucher.setBackground(new java.awt.Color(255, 0, 51));
        btnXoaVoucher.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaVoucher.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaVoucher.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/icons8-delete-60.png"))); // NOI18N
        btnXoaVoucher.setText("Xóa Voucher");
        btnXoaVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaVoucherActionPerformed(evt);
            }
        });

        btnImportExcel.setBackground(new java.awt.Color(255, 0, 51));
        btnImportExcel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnImportExcel.setForeground(new java.awt.Color(255, 255, 255));
        btnImportExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/import.png"))); // NOI18N
        btnImportExcel.setText("Import");
        btnImportExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportExcelActionPerformed(evt);
            }
        });

        btnXuatExcel.setBackground(new java.awt.Color(255, 0, 51));
        btnXuatExcel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXuatExcel.setForeground(new java.awt.Color(255, 255, 255));
        btnXuatExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/expoert.png"))); // NOI18N
        btnXuatExcel.setText("Export");
        btnXuatExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(139, 139, 139)
                .addComponent(btnXoaVoucher)
                .addGap(42, 42, 42)
                .addComponent(btnXuatExcel)
                .addGap(40, 40, 40)
                .addComponent(btnImportExcel)
                .addContainerGap(113, Short.MAX_VALUE))
        );

        jPanel8Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnImportExcel, btnXoaVoucher, btnXuatExcel});

        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoaVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImportExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXuatExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel8Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnImportExcel, btnXoaVoucher, btnXuatExcel});

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));

        txtSearchKeyword.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        txtSearchKeyword.setForeground(new java.awt.Color(204, 204, 204));
        txtSearchKeyword.setText("Tên Voucher - Mã Voucher");
        txtSearchKeyword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchKeywordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSearchKeywordFocusLost(evt);
            }
        });
        txtSearchKeyword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchKeywordActionPerformed(evt);
            }
        });
        txtSearchKeyword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeywordKeyReleased(evt);
            }
        });

        jLabel20.setText("Trạng thái");

        cboSearchLoaiVoucher.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Sắp áp dụng", "Đang áp dụng", "Hết hạn" }));
        cboSearchLoaiVoucher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboSearchLoaiVoucherMouseClicked(evt);
            }
        });
        cboSearchLoaiVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSearchLoaiVoucherActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearchKeyword, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboSearchLoaiVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchKeyword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(cboSearchLoaiVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Lọc theo ngày"));

        txtSearchNgayBatDau.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchNgayBatDauKeyReleased(evt);
            }
        });

        jLabel21.setText("Từ");

        jLabel22.setText("Đến ");

        txtSearchNgayKetThuc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchNgayKetThucKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearchNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addGap(18, 18, 18)
                .addComponent(txtSearchNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtSearchNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel21)
                    .addComponent(txtSearchNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Lọc theo giá"));

        jLabel23.setText("Từ");

        jLabel24.setText("Đến ");

        txtSearchBeginPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchBeginPriceKeyReleased(evt);
            }
        });

        txtSearchEndPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchEndPriceKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSearchBeginPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearchEndPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel24)
                        .addComponent(txtSearchEndPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel23)
                        .addComponent(txtSearchBeginPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        lblTrang.setText("Page");

        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnFirst)
                        .addGap(18, 18, 18)
                        .addComponent(btnPrev)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext)
                        .addGap(18, 18, 18)
                        .addComponent(btnLast)
                        .addGap(202, 202, 202))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(55, 55, 55))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrev)
                    .addComponent(btnNext)
                    .addComponent(lblTrang)
                    .addComponent(btnFirst)
                    .addComponent(btnLast))
                .addGap(48, 48, 48)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                        .addGap(84, 84, 84)))
                .addGap(145, 145, 145)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(31, 31, 31)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(99, 99, 99))
        );

        jTabbedPane1.addTab("Voucher", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tblVoucherDeletd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên", "Mã voucher", "Loại KM", "Giá trị", "Số lượng", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái", "Khôi phục"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblVoucherDeletd.setRowHeight(35);
        tblVoucherDeletd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVoucherDeletdMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblVoucherDeletdMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblVoucherDeletd);

        btnKhoiPhuc.setBackground(new java.awt.Color(0, 204, 51));
        btnKhoiPhuc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnKhoiPhuc.setForeground(new java.awt.Color(255, 255, 255));
        btnKhoiPhuc.setText("Thay đổi");
        btnKhoiPhuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhoiPhucActionPerformed(evt);
            }
        });

        btnFirstDeleted.setText("|<");
        btnFirstDeleted.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstDeletedActionPerformed(evt);
            }
        });

        btnPrevDeleted.setText("<<");
        btnPrevDeleted.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevDeletedActionPerformed(evt);
            }
        });

        lblTrangDeleted.setText("Page");

        btnNextDeleted.setText(">>");
        btnNextDeleted.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextDeletedActionPerformed(evt);
            }
        });

        btnLastDeleted.setText(">|");
        btnLastDeleted.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastDeletedActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnFirstDeleted)
                        .addGap(31, 31, 31)
                        .addComponent(btnPrevDeleted)
                        .addGap(18, 18, 18)
                        .addComponent(lblTrangDeleted, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNextDeleted)
                        .addGap(18, 18, 18)
                        .addComponent(btnLastDeleted)
                        .addGap(228, 228, 228)
                        .addComponent(btnKhoiPhuc, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1078, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirstDeleted)
                    .addComponent(btnPrevDeleted)
                    .addComponent(lblTrangDeleted)
                    .addComponent(btnNextDeleted)
                    .addComponent(btnLastDeleted)
                    .addComponent(btnKhoiPhuc, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(124, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Voucher đã xóa", jPanel3);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setText("PHIẾU GIẢM GIÁ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(436, 436, 436)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel7)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTenKhuyenMaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenKhuyenMaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenKhuyenMaiActionPerformed

    private void txtMaCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaCodeActionPerformed

    private void txtGiaTriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaTriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaTriActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        if (Auth.user.isVaiTro()) {
            int index = tblVoucher.getSelectedRow();
            if (index < 0) {
                JOptionPane.showMessageDialog(this, "Chưa chọn dòng thực hiện");
                return;
            }
            if (checkValidateAddVoucher()) {
                int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa voucher này hay không ?", "add", JOptionPane.YES_NO_OPTION);
                if (check != JOptionPane.YES_OPTION) {
                    return;
                }
                String maVoucher = tblVoucher.getValueAt(index, 2).toString();
                String maVoucherCheck = txtMaCode.getText().trim().replaceAll("\\s+", "");
                boolean maVoucherTT = false;
                int idCheck = ser.getIdVoucher(maVoucher);
                for (ModelVoucher vch : listVoucher) {
                    if (vch.getMaVoucher().trim().equals(maVoucherCheck)) {
                        if (vch.getId() != idCheck) {
                            maVoucherTT = true;
                            break;
                        }
                    }
                }
                if (maVoucherTT) {
                    JOptionPane.showMessageDialog(this, "Mã voucher này đã tồn tại không thể sửa !");
                    txtMaCode.requestFocus();
                } else {
                    try {
                        int id = ser.getIdVoucher(maVoucher);
                        ModelVoucher voucher = getFromUpdate();
                        if (voucher != null) {
                            voucher.setId(id);
                            if (ser.updateVoucher(voucher) != null) {
                                JOptionPane.showMessageDialog(this, "Sửa thành công Voucher");
                                listVoucher = ser.phanTrang(trangVoucher, 3);
                                showData(listVoucher);
                                int max = ser.getMaxPages(3);
                                lblTrang.setText(trangVoucher + "/" + max);
                                showDataTableDeleted(ser.phanTrangDeleted(trangVoucherDeleted, 7));
                                int maxDeleted = ser.getMaxPages(7);
                                lblTrangDeleted.setText(trangVoucherDeleted + "/" + maxDeleted);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Voucher không tồn tại hoặc không hợp lệ");
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Sửa thất bại");
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn không được phép");
        }

    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (Auth.user.isVaiTro()) {
            if (checkValidateAddVoucher()) {
                int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm voucher này hay không ?", "add", JOptionPane.YES_NO_OPTION);
                if (check != JOptionPane.YES_NO_OPTION) {
                    return;
                }
                String maVoucher = txtMaCode.getText().trim().replaceAll("\\s+", "");
                boolean maVoucherTT = false;
                for (ModelVoucher vch : listVoucher) {
                    if (vch.getMaVoucher().trim().equals(maVoucher)) {
                        maVoucherTT = true;
                        break;
                    }
                }

                if (maVoucherTT) {
                    JOptionPane.showMessageDialog(this, "Mã voucher này đã tồn tại không thể thêm !");
                    txtMaCode.requestFocus();
                } else {
                    ModelVoucher voucher = getFrom();
                    try {
                        if (ser.addVoucher(voucher) != null) {
                            JOptionPane.showMessageDialog(this, "Thêm thành công Voucher");
                            AutoUpdate();
                            showData(ser.phanTrang(trangVoucher, 3));
                            int max = ser.getMaxPages(3);
                            lblTrang.setText(trangVoucher + "/" + max);
                            if (max != 1) {
                                btnLast.setEnabled(true);
                                btnNext.setEnabled(true);
                            }
                            showDataTableDeleted(ser.phanTrangDeleted(trangVoucherDeleted, 7));
                            int maxDeleted = ser.getMaxPagesDeleted(7);
                            lblTrangDeleted.setText(trangVoucherDeleted + "/" + maxDeleted);
                            if (maxDeleted != 1) {
                                btnLastDeleted.setEnabled(true);
                                btnNextDeleted.setEnabled(true);
                            }
                        }
                    } catch (Exception e) {
//                    e.printStackTrace();
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn không được phép");
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn Clear From không ?", "clear", JOptionPane.YES_NO_OPTION);
        if (check != JOptionPane.YES_NO_OPTION) {
            return;
        }
        clearFrom();
    }//GEN-LAST:event_btnClearActionPerformed

    private void tblVoucherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVoucherMouseClicked
        try {
            showDetail();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tblVoucherMouseClicked

    private void tblVoucherMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVoucherMousePressed

    }//GEN-LAST:event_tblVoucherMousePressed

    private void btnXoaVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaVoucherActionPerformed
        if (Auth.user.isVaiTro()) {
            int index = tblVoucher.getSelectedRow();
            if (index < 0) {
                JOptionPane.showMessageDialog(this, "Chưa chọn checkbox thực hiện");
                return;
            }
            int columnIndexToDelete = 10;
            List<Integer> selectedIds = new ArrayList<>();
            for (int i = modelVoucher.getRowCount() - 1; i >= 0; i--) {
                Boolean isChecked = (Boolean) modelVoucher.getValueAt(i, columnIndexToDelete);
                if (isChecked != null && isChecked) {
                    int id = ser.phanTrang(trangVoucher, 3).get(i).getId();
                    System.out.println("ID Voucher deleted 1" + id);
                    selectedIds.add(id);
                }
            }
            if (!selectedIds.isEmpty() && JOptionPane.showConfirmDialog(this, "Bạn có xóa không ?", "deleted", JOptionPane.YES_NO_OPTION) == 0) {
                for (int id : selectedIds) {
                    if (ser.deleteVoucher(id) != null) {
                        System.out.println(ser.deleteVoucher(id));
                        showData(ser.phanTrang(trangVoucher, 3));

                    } else {
                        JOptionPane.showMessageDialog(this, "Khôi phục thất bại");
                    }
                }

                JOptionPane.showMessageDialog(this, "Xóa thành công");

                //Load lại dữ liệu Voucher
                listVoucher = ser.phanTrang(trangVoucher, 3);
                showData(listVoucher);
                int max = ser.getMaxPages(3);
                lblTrang.setText(trangVoucher + "/" + max);

                if (max == 1) {
                    btnLast.setEnabled(false);
                    btnNext.setEnabled(false);
                }
                if (trangVoucher == 1) {
                    btnPrev.setEnabled(false);
                    btnFirst.setEnabled(false);
                }
                //Load lại dữ liệu VoucherDeleted
                listVoucherDeleted = ser.phanTrangDeleted(trangVoucherDeleted, 7);
                int maxByTableDeleted = ser.getMaxPagesDeleted(7);
                showDataTableDeleted(listVoucherDeleted);
                if (trangVoucherDeleted == 1) {
                    btnPrevDeleted.setEnabled(false);
                    btnFirstDeleted.setEnabled(false);
                }
                if (maxByTableDeleted == 1) {
                    btnLastDeleted.setEnabled(false);
                    btnNextDeleted.setEnabled(false);
                }
                showDataTableDeleted(listVoucherDeleted = ser.phanTrangDeleted(trangVoucherDeleted, 7));
                lblTrangDeleted.setText(trangVoucher + "/" + maxByTableDeleted);

            }

        } else {
            JOptionPane.showMessageDialog(this, "Bạn không được phép");
        }
    }//GEN-LAST:event_btnXoaVoucherActionPerformed

    private void btnImportExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportExcelActionPerformed
        if (Auth.user.isVaiTro()) {
            File excelFile;
            FileInputStream excelFIS = null;
            BufferedInputStream excelBIS = null;
            XSSFWorkbook excelImporToJTable = null;
            String path = "D:\\DUAN1\\QR_SAN_PHAM";
            JFileChooser excelJFileChooser = new JFileChooser(path);
            excelJFileChooser.setDialogTitle("Select Excel File");
            FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILE", "xls", "xlsx", "xlsm");
            excelJFileChooser.setFileFilter(fnef);
            int excelResult = excelJFileChooser.showOpenDialog(null);
            modelVoucher.setRowCount(0);
            if (excelResult == JFileChooser.APPROVE_OPTION) {
                try {
                    excelFile = excelJFileChooser.getSelectedFile();
                    excelFIS = new FileInputStream(excelFile);
                    excelBIS = new BufferedInputStream(excelFIS);
                    excelImporToJTable = new XSSFWorkbook(excelBIS);
                    XSSFSheet excelSheet = excelImporToJTable.getSheetAt(0);
                    for (int row = 1; row <= excelSheet.getLastRowNum(); row++) {
                        XSSFRow excelRow = excelSheet.getRow(row);
                        if (excelRow != null) {

                            String STT = excelRow.getCell(0).getStringCellValue();
                            String tenVoucher = excelRow.getCell(1).getStringCellValue();
                            String maVoucher = excelRow.getCell(2).getStringCellValue();
                            String loaiKM = excelRow.getCell(3).getStringCellValue();
                            String giaTri = excelRow.getCell(4).getStringCellValue();
                            String soLuong = excelRow.getCell(5).getStringCellValue();
                            String ngayBatDau = excelRow.getCell(6).getStringCellValue();
                            String ngayKetThuc = excelRow.getCell(7).getStringCellValue();
                            String trangThai = excelRow.getCell(8).getStringCellValue();

                            modelVoucher.addRow(new Object[]{
                                STT, tenVoucher, maVoucher, loaiKM, giaTri, soLuong, ngayBatDau, ngayKetThuc, trangThai
                            });
                        } else {
                            System.out.println("Row " + row + " is null");
                        }
                    }
                    JOptionPane.showMessageDialog(this, "Imported Succesfully !!");
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        } else {
            JOptionPane.showMessageDialog(this, "Bạn không được phép");
        }
    }//GEN-LAST:event_btnImportExcelActionPerformed

    private void btnXuatExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExcelActionPerformed
        if (Auth.user.isVaiTro()) {
            try {
                String path = "D:\\DUAN1\\QR_SAN_PHAM";
                JFileChooser jFileChooser = new JFileChooser(path);
                jFileChooser.showSaveDialog(this);
                File saveFile = jFileChooser.getSelectedFile();
                if (saveFile != null) {
                    saveFile = new File(saveFile.toString() + ".xlsx");
                    Workbook wb = new XSSFWorkbook();
                    Sheet sheet = wb.createSheet("Account");

                    Row rowCol = sheet.createRow(0);
                    for (int i = 0; i < tblVoucher.getColumnCount(); i++) {
                        Cell cell = rowCol.createCell(i);
                        cell.setCellValue(tblVoucher.getColumnName(i));
                    }
                    for (int j = 0; j < tblVoucher.getRowCount(); j++) {
                        Row row = sheet.createRow(j + 1);
                        for (int k = 0; k < tblVoucher.getColumnCount(); k++) {
                            Cell cell = row.createCell(k);
                            if (tblVoucher.getValueAt(j, k) != null) {
                                cell.setCellValue(tblVoucher.getValueAt(j, k).toString());
                            }
                        }
                    }
                    FileOutputStream out = new FileOutputStream(new File(saveFile.toString()));
                    wb.write(out);
                    wb.close();
                    out.close();
                    EventStream.openFile(saveFile.toPath());
                    JOptionPane.showMessageDialog(this, "Export Excel thành công ");
                    showData(ser.phanTrang(trangVoucher, 3));
                    int max = ser.getMaxPages(3);
                    lblTrang.setText(trangVoucher + "/" + max);
                    showDataTableDeleted(ser.phanTrangDeleted(trangVoucherDeleted, 7));
                    int maxDeleted = ser.getMaxPages(7);
                    lblTrangDeleted.setText(trangVoucherDeleted + "/" + maxDeleted);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            JOptionPane.showMessageDialog(this, "Bạn không được phép");
        }
    }//GEN-LAST:event_btnXuatExcelActionPerformed

    private void txtSearchKeywordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchKeywordActionPerformed

    }//GEN-LAST:event_txtSearchKeywordActionPerformed

    private void txtSearchKeywordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeywordKeyReleased
        String selectedCbo = cboSearchLoaiVoucher.getSelectedItem().toString();
        String keyword = txtSearchKeyword.getText().trim();

        if (!keyword.isEmpty() && (!selectedCbo.equals("Sắp áp dụng") || !selectedCbo.equals("Đang áp dụng") || !selectedCbo.equals("Hết hạn"))) {
            showData(ser.FindVoucherByKeyword(keyword, ""));
        } else if (keyword.isEmpty() && (selectedCbo.equals("Sắp áp dụng") || selectedCbo.equals("Đang áp dụng") || selectedCbo.equals("Hết hạn"))) {
            showData(ser.FindVoucherByKeyword("", selectedCbo));
        } else if (keyword.isEmpty() && selectedCbo.equals("Tất cả")) {
            showData(ser.getAllVoucher());
        }


    }//GEN-LAST:event_txtSearchKeywordKeyReleased

    private void txtSearchNgayBatDauKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchNgayBatDauKeyReleased


    }//GEN-LAST:event_txtSearchNgayBatDauKeyReleased

    private void txtSearchNgayKetThucKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchNgayKetThucKeyReleased

    }//GEN-LAST:event_txtSearchNgayKetThucKeyReleased

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        this.trangVoucher--;
        listVoucher = ser.phanTrang(trangVoucher, 3);
        int max = ser.getMaxPages(3);
        showData(listVoucher);
        if (trangVoucher == 1) {
            btnNext.setEnabled(true);
            btnLast.setEnabled(true);
            btnPrev.setEnabled(false);
            btnFirst.setEnabled(false);
        }
        if (trangVoucher != 1 && trangVoucher < max) {
            btnNext.setEnabled(true);
            btnLast.setEnabled(true);
        }
        lblTrang.setText(String.valueOf(trangVoucher) + "/" + max);

    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        this.trangVoucher++;
        listVoucher = ser.phanTrang(trangVoucher, 3);
        showData(listVoucher);
        int max = ser.getMaxPages(3);
        if (trangVoucher >= max) {
            btnNext.setEnabled(false);
            btnLast.setEnabled(false);
            btnPrev.setEnabled(true);
            btnFirst.setEnabled(true);
        }
        if (trangVoucher != 1 && trangVoucher < max) {
            btnPrev.setEnabled(true);
            btnFirst.setEnabled(true);
        }
        lblTrang.setText(String.valueOf(trangVoucher) + "/" + max);

    }//GEN-LAST:event_btnNextActionPerformed

    private void txtSearchBeginPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchBeginPriceKeyReleased
        updateTableByPrice();
    }//GEN-LAST:event_txtSearchBeginPriceKeyReleased

    private void txtSearchEndPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchEndPriceKeyReleased
        updateTableByPrice();
    }//GEN-LAST:event_txtSearchEndPriceKeyReleased

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        this.trangVoucher = 1;
        listVoucher = ser.phanTrang(trangVoucher, 3);
        int max = ser.getMaxPages(3);
        showData(listVoucher);
        if (trangVoucher == 1) {
            btnNext.setEnabled(true);
            btnLast.setEnabled(true);
            btnPrev.setEnabled(false);
            btnFirst.setEnabled(false);
        }
        lblTrang.setText(String.valueOf(trangVoucher) + "/" + max);
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed

        int max = ser.getMaxPages(3);
        this.trangVoucher = max;
        listVoucher = ser.phanTrang(trangVoucher, 3);
        if (trangVoucher == max) {
            btnNext.setEnabled(false);
            btnLast.setEnabled(false);
            btnPrev.setEnabled(true);
            btnFirst.setEnabled(true);

        }
        listVoucher = ser.phanTrang(trangVoucher, 3);
        this.trangVoucher = max;
        showData(listVoucher);
        lblTrang.setText(String.valueOf(trangVoucher) + "/" + max);
    }//GEN-LAST:event_btnLastActionPerformed

    private void tblVoucherDeletdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVoucherDeletdMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblVoucherDeletdMouseClicked

    private void tblVoucherDeletdMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVoucherDeletdMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblVoucherDeletdMousePressed

    private void btnFirstDeletedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstDeletedActionPerformed
        this.trangVoucherDeleted = 1;
        listVoucherDeleted = ser.phanTrangDeleted(trangVoucher, 7);
        int max = ser.getMaxPagesDeleted(7);
        showDataTableDeleted(listVoucherDeleted);
        if (trangVoucherDeleted == 1) {
            btnNextDeleted.setEnabled(true);
            btnLastDeleted.setEnabled(true);
            btnPrevDeleted.setEnabled(false);
            btnFirstDeleted.setEnabled(false);
        }
        lblTrangDeleted.setText(String.valueOf(trangVoucherDeleted) + "/" + max);
    }//GEN-LAST:event_btnFirstDeletedActionPerformed

    private void btnPrevDeletedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevDeletedActionPerformed
        this.trangVoucherDeleted--;
        listVoucherDeleted = ser.phanTrangDeleted(trangVoucherDeleted, 7);
        int max = ser.getMaxPagesDeleted(7);
        showDataTableDeleted(listVoucherDeleted);
        if (trangVoucherDeleted == 1) {
            btnNextDeleted.setEnabled(true);
            btnLastDeleted.setEnabled(true);
            btnPrevDeleted.setEnabled(false);
            btnFirstDeleted.setEnabled(false);
        }
        if (trangVoucherDeleted != 1 && trangVoucherDeleted < max) {
            btnNextDeleted.setEnabled(true);
            btnLastDeleted.setEnabled(true);
        }
        lblTrangDeleted.setText(String.valueOf(trangVoucherDeleted) + "/" + max);
    }//GEN-LAST:event_btnPrevDeletedActionPerformed

    private void btnNextDeletedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextDeletedActionPerformed
        this.trangVoucherDeleted++;
        listVoucherDeleted = ser.phanTrangDeleted(trangVoucherDeleted, 7);
        showDataTableDeleted(listVoucherDeleted);
        int max = ser.getMaxPagesDeleted(7);
        if (trangVoucherDeleted >= max) {
            btnNextDeleted.setEnabled(false);
            btnLastDeleted.setEnabled(false);
            btnPrevDeleted.setEnabled(true);
            btnFirstDeleted.setEnabled(true);
        }
        if (trangVoucherDeleted != 1 && trangVoucherDeleted < max) {
            btnPrevDeleted.setEnabled(true);
            btnFirstDeleted.setEnabled(true);
        }
        lblTrangDeleted.setText(String.valueOf(trangVoucherDeleted) + "/" + max);
    }//GEN-LAST:event_btnNextDeletedActionPerformed

    private void btnLastDeletedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastDeletedActionPerformed

        int max = ser.getMaxPagesDeleted(7);
        this.trangVoucherDeleted = max;
        listVoucherDeleted = ser.phanTrangDeleted(trangVoucherDeleted, 7);
        if (trangVoucherDeleted == max) {
            btnNextDeleted.setEnabled(false);
            btnLastDeleted.setEnabled(false);
            btnPrevDeleted.setEnabled(true);
            btnFirstDeleted.setEnabled(true);

        }
        listVoucherDeleted = ser.phanTrangDeleted(trangVoucherDeleted, 7);
        this.trangVoucherDeleted = max;
        showDataTableDeleted(listVoucherDeleted);
        lblTrangDeleted.setText(String.valueOf(trangVoucherDeleted) + "/" + max);
    }//GEN-LAST:event_btnLastDeletedActionPerformed

    private void btnKhoiPhucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhoiPhucActionPerformed
        int index = tblVoucherDeletd.getSelectedRow();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Chưa chọn checkbox thực hiện");
            return;
        }
        int columnIndexToDelete = 9;
        List<Integer> selectedIds = new ArrayList<>();
        for (int i = modelVoucherDeleted.getRowCount() - 1; i >= 0; i--) {
            Boolean isChecked = (Boolean) modelVoucherDeleted.getValueAt(i, columnIndexToDelete);
            if (isChecked != null && isChecked) {
                int id = ser.phanTrangDeleted(trangVoucherDeleted, 7).get(i).getId();
                System.out.println(id);
                selectedIds.add(id);
            }
        }
        if (!selectedIds.isEmpty() && JOptionPane.showConfirmDialog(this, "Bạn có muốn khôi phục voucher này không ?", "update", JOptionPane.YES_NO_OPTION) == 0) {
            for (int id : selectedIds) {
                if (ser.restoreVoucher(id) != null) {
                    System.out.println(ser.restoreVoucher(id));
                    showDataTableDeleted(ser.phanTrangDeleted(trangVoucherDeleted, 7));

                } else {
                    JOptionPane.showMessageDialog(this, "Khôi phục thất bại");
                }
            }

            JOptionPane.showMessageDialog(this, "Khôi phục thành công");

            //Load lại dữ liệu Voucher
            listVoucher = ser.phanTrang(trangVoucher, 3);
            showData(listVoucher);
            int max = ser.getMaxPages(3);
            lblTrang.setText(trangVoucher + "/" + max);

            if (max == 1) {
                btnLast.setEnabled(false);
                btnNext.setEnabled(false);
            }
            if (trangVoucher == 1) {
                btnPrev.setEnabled(false);
                btnFirst.setEnabled(false);
            }
            //Load lại dữ liệu VoucherDeleted
            listVoucherDeleted = ser.phanTrangDeleted(trangVoucherDeleted, 7);
            int maxByTableDeleted = ser.getMaxPagesDeleted(7);
            showDataTableDeleted(listVoucherDeleted);
            if (trangVoucherDeleted == 1) {
                btnPrevDeleted.setEnabled(false);
                btnFirstDeleted.setEnabled(false);
            }
            if (maxByTableDeleted == 1) {
                btnLastDeleted.setEnabled(false);
                btnNextDeleted.setEnabled(false);
            }
            showDataTableDeleted(listVoucherDeleted = ser.phanTrangDeleted(trangVoucherDeleted, 7));
            lblTrangDeleted.setText(trangVoucherDeleted + "/" + maxByTableDeleted);

        }


    }//GEN-LAST:event_btnKhoiPhucActionPerformed

    private void cboSearchLoaiVoucherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboSearchLoaiVoucherMouseClicked

        String seletedCO = cboSearchLoaiVoucher.getSelectedItem().toString().trim();
        if (seletedCO.equalsIgnoreCase("Tất cả")) {
            listVoucher = ser.getAllVoucher();
            showData(listVoucher);
        }

    }//GEN-LAST:event_cboSearchLoaiVoucherMouseClicked

    private void txtGiaTriKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiaTriKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaTriKeyReleased

    private void cboSearchLoaiVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSearchLoaiVoucherActionPerformed
        loadToTableVoucherByAll();
    }//GEN-LAST:event_cboSearchLoaiVoucherActionPerformed

    private void txtSearchKeywordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchKeywordFocusGained
        if (txtSearchKeyword.getText().equals("Tên Voucher - Mã Voucher")) {
            txtSearchKeyword.setText("");
            setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_txtSearchKeywordFocusGained

    private void txtSearchKeywordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchKeywordFocusLost
        // Kiểm tra nếu text là rỗng thì thiết lập lại với text mặc định và chữ in nghiêng
        if (txtSearchKeyword.getText().equals("")) {
            txtSearchKeyword.setText("Tên Voucher - Mã Voucher");
            txtSearchKeyword.setForeground(new Color(53, 153, 153));
        }

    }//GEN-LAST:event_txtSearchKeywordFocusLost

    private void spfSoLuongKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_spfSoLuongKeyTyped
        // TODO add your handling code here:
        char key = evt.getKeyChar();
// Chỉ cho phép nhập số và backspace
        if ((key < '0' || key > '9') && (key != '\b')) {
            evt.consume(); // Bỏ qua sự kiện phím nếu không phải số hoặc backspace
        }
    }//GEN-LAST:event_spfSoLuongKeyTyped

    private void txtGiaTriKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiaTriKeyTyped
        // TODO add your handling code here:
        char key = evt.getKeyChar();
// Chỉ cho phép nhập số và backspace
        if ((key < '0' || key > '9') && (key != '\b')) {
            evt.consume(); // Bỏ qua sự kiện phím nếu không phải số hoặc backspace
        }
    }//GEN-LAST:event_txtGiaTriKeyTyped

    public void AutoUpdateStatus() {

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AutoUpdate();
            }
        });
        timer.start();

        setVisible(true);
    }

    void loadTableByTrangThai() {
        cboSearchLoaiVoucher.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                loadToTableVoucherByAll();
            }

        });
    }

    private String finTrangThai(String maVoucher) {
        for (ModelVoucher voucher : listVoucher) {
            int maVoucherByVCH = voucher.getId();
            String maVoucherString = String.valueOf(maVoucherByVCH);

            if (maVoucherString.equals(maVoucher)) {
                return voucher.getTrangThai();
            }

        }
        return "";

    }

    void ActionButton() {
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void updateStatus(int id) {
                int rowIndex = tblVoucher.getSelectedRow();
                int check = JOptionPane.showConfirmDialog(btnCapNhat, "Bạn có muốn sửa trạng thái của Voucher này không ?", "update", JOptionPane.YES_NO_OPTION);
                if (check == JOptionPane.YES_OPTION) {
                    String maVoucher = tblVoucher.getValueAt(rowIndex, 2).toString();
                    id = ser.getIdVoucher(maVoucher);
                    ModelVoucher voucher = getFromUpdateByClick();
                    voucher.setId(id);
                    try {
                        if (ser.UpdateStatusByClick(voucher) != null) {
                            JOptionPane.showMessageDialog(btnCapNhat, "Cập nhật trạng thái Voucher thành công !");
                            listVoucher = ser.phanTrang(trangVoucher, 3);
                            showData(listVoucher);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        tblVoucher.getColumnModel()
                .getColumn(9).setCellRenderer(new TableActionCellRender());
        tblVoucher.getColumnModel()
                .getColumn(9).setCellEditor(new TableActionCellEditor(event));
    }

    void editByDateStart() {
        txtSearchNgayBatDau.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                updateTableByDate();
            }
        });
    }

    void editByDateEnd() {
        txtSearchNgayKetThuc.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                updateTableByDate();
            }
        });
    }

    void updateTableByDate() {
        java.util.Date utilNgayBatDauDate = txtSearchNgayBatDau.getDate();
        java.util.Date utilNgayKetThucDate = txtSearchNgayKetThuc.getDate();

        if (utilNgayBatDauDate != null && utilNgayKetThucDate != null) {
            java.sql.Date ngayBatDauDate = new java.sql.Date(utilNgayBatDauDate.getTime());
            java.sql.Date ngayKetThucDate = new java.sql.Date(utilNgayKetThucDate.getTime());

            try {
                listVoucher = ser.getVoucherByDate(ngayBatDauDate, ngayKetThucDate);
                showData(listVoucher);
                AutoUpdate();

            } catch (Exception ex) {
                Logger.getLogger(Voucher.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else {

        }
    }

    void updateTableByPrice() {
        String priceStart = txtSearchBeginPrice.getText().trim();
        String priceEnd = txtSearchEndPrice.getText().trim();

        if (!priceStart.isEmpty() && !priceEnd.isEmpty()) {
            try {
                float priceStartFL = Float.parseFloat(priceStart);
                float priceEndFL = Float.parseFloat(priceEnd);

                listVoucher = ser.getVoucherByPrice(priceStartFL, priceEndFL);
                showData(listVoucher);
            } catch (NumberFormatException ex) {
                // Xử lý khi có lỗi chuyển đổi số
                ex.printStackTrace();
            }
        }
    }

    void editByPriceStart() {
        txtSearchBeginPrice.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                updateTableByPrice();
            }
        });
    }

    void editByPriceEnd() {
        txtSearchEndPrice.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                updateTableByPrice();
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btgTrangThaiVoucher;
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnFirstDeleted;
    private javax.swing.JButton btnImportExcel;
    private javax.swing.JButton btnKhoiPhuc;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnLastDeleted;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnNextDeleted;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnPrevDeleted;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoaVoucher;
    private javax.swing.JButton btnXuatExcel;
    private javax.swing.JComboBox<String> cboLoaiKhuyenMai;
    private javax.swing.JComboBox<String> cboSearchLoaiVoucher;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblTrang;
    private javax.swing.JLabel lblTrangDeleted;
    private com.toedter.components.JSpinField spfSoLuong;
    private javax.swing.JTable tblVoucher;
    private javax.swing.JTable tblVoucherDeletd;
    private javax.swing.JTextField txtGiaTri;
    private javax.swing.JTextField txtMaCode;
    private com.toedter.calendar.JDateChooser txtNgayBatDau;
    private com.toedter.calendar.JDateChooser txtNgayKetThuc;
    private javax.swing.JTextField txtSearchBeginPrice;
    private javax.swing.JTextField txtSearchEndPrice;
    private javax.swing.JTextField txtSearchKeyword;
    private com.toedter.calendar.JDateChooser txtSearchNgayBatDau;
    private com.toedter.calendar.JDateChooser txtSearchNgayKetThuc;
    private javax.swing.JTextField txtTenKhuyenMai;
    // End of variables declaration//GEN-END:variables
}
