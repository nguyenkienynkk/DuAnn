/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.raven.dialog;

import com.raven.domainmodels.ModelDiaChi;
import com.raven.domainmodels.ModelKhachHang;
import com.raven.form.attribute.DiaChiView;
import com.raven.services.DiaChiService;
import com.raven.services.KhachHangService;
import com.raven.services.impl.DiaChiServiceIplm;
import com.raven.services.impl.KhachHangServiceIplm;
import com.raven.viewmodels.LichSuMuaHang;
import com.raven.viewmodels.ToanCuc;
import com.raven.viewmodels.addKhachHang;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import jdk.jfr.consumer.EventStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author vinhd
 */
public class KhachHangJDialog extends javax.swing.JDialog {

    List<ModelKhachHang> listKH = new ArrayList<>();
    List<ModelKhachHang> listKHAll = new ArrayList<>();
    List<ModelKhachHang> listKHDeleted = new ArrayList<>();
    List<LichSuMuaHang> listLS = new ArrayList<>();
    List<ModelKhachHang> listResut = new ArrayList<>();
    List<ModelDiaChi> listDC = new ArrayList<>();
    DefaultTableModel modelKH = new DefaultTableModel();
    DefaultTableModel modelLS = new DefaultTableModel();
    DefaultTableModel modelDeleted = new DefaultTableModel();
    KhachHangService ser = new KhachHangServiceIplm();
    DiaChiService serdiaChi = new DiaChiServiceIplm();
    DefaultComboBoxModel<ModelDiaChi> comboboxDiaChi = new DefaultComboBoxModel<>();
    int trangCustomer = 1;
    int trangCustomerDeleted = 1;
    private static final String regexEmail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private int sdtLength = 0;
    DecimalFormat decimalFormat = new DecimalFormat("###0.##");

    public KhachHangJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initCustomer();
    }

    void initCustomer() {
        listKHAll = ser.getKhachHangByPage(trangCustomer, 1000000);
        modelKH = (DefaultTableModel) tblkhachhang.getModel();
        modelLS = (DefaultTableModel) tblLichSuMuaHang.getModel();
        modelDeleted = (DefaultTableModel) tblKhachHangDeleted.getModel();
        listDC = serdiaChi.getAllDiaChi();
        listKH = ser.getKhachHangByPage(trangCustomer, 5);
        showData(listKH);
        int maxPage = ser.getMaxPage(5);
        lblPage.setText("1/" + maxPage);

        showDataDeleted(ser.getKhachHangDeletedByPage(trangCustomerDeleted, 5));
        int maxPageDeletd = ser.getMaxPageDeleted(5);
        lblPageDelete.setText("1/" + maxPageDeletd);

        if (trangCustomer == 1) {
            btnPrev.setEnabled(false);
            btnFirst.setEnabled(false);
        }
        if (trangCustomerDeleted == 1) {
            btnPrevDeleted.setEnabled(false);
            btnFirstDeleted.setEnabled(false);
        }
        if (maxPage == 1) {
            btnLast.setEnabled(false);
            btnNext.setEnabled(false);
        }
        if (maxPageDeletd == 1) {
            btnLastDeleted.setEnabled(false);
            btnNextDeleted.setEnabled(false);
        }
        showData(listKH);
        showDataDeleted(ser.getKhachHangDeletedByPage(trangCustomerDeleted, 5));
        dateNgaySinh.setDateFormatString("yyyy-MM-dd");
        loaComBoBoxDiaChi();

    }

    void showData(List<ModelKhachHang> listKH) {
        int index = 1;
        modelKH.setRowCount(0);
        for (ModelKhachHang kh : listKH) {
            modelKH.addRow(new Object[]{
                index++,
                kh.getTenKhachHang(),
                kh.getGioiTinh() == 1 ? "Nam" : "Nữ",
                kh.getNgaySinh(),
                kh.getSDT(),
                kh.getEmail(),
                kh.getDiaChiMacDinh().getDiaChiCuThe(),});

        }

    }

    void showDataDeleted(List<ModelKhachHang> listKH) {
        int index = 1;
        modelDeleted.setRowCount(0);
        for (ModelKhachHang kh : listKH) {
            modelDeleted.addRow(new Object[]{
                index++,
                kh.getTenKhachHang(),
                kh.getGioiTinh() == 1 ? "Nam" : "Nữ",
                kh.getNgaySinh(),
                kh.getSDT(),
                kh.getEmail(),
                kh.getDiaChiMacDinh().getDiaChiCuThe(),});

        }

    }

    void loadToTableByKeyWord(String keyword, int gioiTinh) {
        int index = 1;
        listResut = ser.FindByAll(keyword, gioiTinh);
        modelKH.setRowCount(0);
        for (ModelKhachHang kh : listResut) {
            modelKH.addRow(new Object[]{
                index++,
                kh.getTenKhachHang(),
                kh.getGioiTinh() == 1 ? "Nam" : "Nữ",
                kh.getNgaySinh(),
                kh.getSDT(),
                kh.getEmail(),
                kh.getDiaChiMacDinh().getDiaChiCuThe(),});

        }

    }

    void loaComBoBoxDiaChi() {
        Set<String> setDC = new HashSet<>();
        for (ModelDiaChi dc : listDC) {
            setDC.add(dc.toString());
        }
        comboboxDiaChi = (DefaultComboBoxModel) new DefaultComboBoxModel<>(setDC.toArray());
        cboDiaChi.setModel((DefaultComboBoxModel) comboboxDiaChi);
    }

    void showDetail() {
        int index = tblkhachhang.getSelectedRow();
        boolean gt = true;
        txtTenKhachHang.setText(tblkhachhang.getValueAt(index, 1).toString());
        String gioiTinh = tblkhachhang.getValueAt(index, 2).toString();
        if (gioiTinh.equals("Nam")) {
            rdoNam.setSelected(true);
            rdoNu.setSelected(false);
        } else {
            rdoNu.setSelected(true);
            rdoNam.setSelected(false);
        }
        String getValueDae = "";
        if (tblkhachhang.getValueAt(index, 3) != null) {
            getValueDae = tblkhachhang.getValueAt(index, 3).toString();
        }

        Date ngaySinh = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            ngaySinh = format.parse(getValueDae);
        } catch (Exception e) {
        }
        dateNgaySinh.setDate(ngaySinh);
        txtSoDienThoai.setText(tblkhachhang.getValueAt(index, 4).toString());
        txtEmail.setText(tblkhachhang.getValueAt(index, 5).toString());
    }

    ModelKhachHang getFrom() {
        String tenKhachHang = txtTenKhachHang.getText().trim();
        int gioiTinh;
        Date ngaySinh = dateNgaySinh.getDate();
        String email = txtEmail.getText().trim().replaceAll("\\s+", "");
        String soDienThoai = txtSoDienThoai.getText().trim();

        if (rdoNam.isSelected()) {
            gioiTinh = 1;
        } else {
            gioiTinh = 0;
        }
        String selectedDiaChi = cboDiaChi.getSelectedItem().toString();
        int id = ser.getIDDiaChiByDiaChi(selectedDiaChi);
        System.out.println("ID By getForm :" + id);
        ModelDiaChi dc = new ModelDiaChi();
        dc.setID(id);

        ModelKhachHang kh = new ModelKhachHang(tenKhachHang, gioiTinh, ngaySinh, soDienThoai, email, dc);
        return kh;
    }

    boolean validateKhachHang() {
        String regex = "^(0|84)(2(0[3-9]|1[0-6|8|9]|2[0-2|5-9]|3[2-9]|4[0-9]|5[1|2|4-9]|6[0-3|9]|7[0-7]|8[0-9]|9[0-4|6|7|9])|3[2-9]|5[5|6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])([0-9]{7})$";
        String tenKhachHang = txtTenKhachHang.getText().trim();
        Date ngaySinh = dateNgaySinh.getDate();
        String ngaySinhStr = dateNgaySinh.getDateFormatString();
        String email = txtEmail.getText().trim().replaceAll("\\s+", "");
        String soDienThoai = txtSoDienThoai.getText().trim();
        ModelDiaChi dc = new ModelDiaChi();
        if (tenKhachHang.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên khách hàng không được để trống");
            txtTenKhachHang.requestFocus();
            return false;
        }
        if (!tenKhachHang.matches("[a-zA-Z\\sàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễđòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳỹỷýÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄĐÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲỸỶÝ]+")) {
            JOptionPane.showMessageDialog(this, "Tên khách hàng chỉ được nhập chữ");
            return false;
        }
        if (!soDienThoai.matches(regex)) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không đúng định dạng!");
            txtSoDienThoai.requestFocus();
            return false;
        }
        if (ngaySinh != null) {
            Date curentDate = new Date();
            if (ngaySinh != null && ngaySinh.after(curentDate)) {
                JOptionPane.showMessageDialog(this, "Ngày sinh không được lớn hơn ngày hiện tại");
                dateNgaySinh.requestFocus();
                return false;
            }
        }

        if (soDienThoai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số điện thoại khách hàng không được để trống");
            txtSoDienThoai.requestFocus();
            return false;
        }

        if (!email.isEmpty()) {
            Matcher matcher = Pattern.compile(regexEmail).matcher(email);
            if (!matcher.matches()) {
                JOptionPane.showMessageDialog(this, "Email sai định dạng");
                txtEmail.requestFocus();
                return false;
            }
        }
        if (cboDiaChi.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this, "Chưa chọn địa chỉ cho khách hàng");
            cboDiaChi.requestFocus();
            return false;

        }
        return true;
    }

    ModelKhachHang getFromForUpdate() {
        String tenKhachHang = txtTenKhachHang.getText().trim();
        int gioiTinh;
        Date ngaySinh = dateNgaySinh.getDate();
        String email = txtEmail.getText().trim().replaceAll("\\s+", "");
        String soDienThoai = txtSoDienThoai.getText().trim();

        String cboDiaChi = this.cboDiaChi.getSelectedItem().toString();
        ModelDiaChi dc = new ModelDiaChi();
        dc.setDiaChiCuThe(cboDiaChi);

        if (rdoNam.isSelected()) {
            gioiTinh = 1;
        } else {
            gioiTinh = 0;
        }
        ModelKhachHang kh = new ModelKhachHang(tenKhachHang, gioiTinh, ngaySinh, soDienThoai, email);
        return kh;
    }

    boolean validateUpdate() {
        String regex = "^(0|84)(2(0[3-9]|1[0-6|8|9]|2[0-2|5-9]|3[2-9]|4[0-9]|5[1|2|4-9]|6[0-3|9]|7[0-7]|8[0-9]|9[0-4|6|7|9])|3[2-9]|5[5|6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])([0-9]{7})$";
        String tenKhachHang = txtTenKhachHang.getText().trim();
        int gioiTinh;
        Date ngaySinh = dateNgaySinh.getDate();
        String email = txtEmail.getText().trim().replaceAll("\\s+", "");
        String soDienThoai = txtSoDienThoai.getText().trim();
        String cboDiaChi = this.cboDiaChi.getSelectedItem().toString();
        ModelDiaChi dc = new ModelDiaChi();
        dc.setDiaChiCuThe(cboDiaChi);

        if (tenKhachHang.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên khách hàng không được để trống");
            txtTenKhachHang.requestFocus();
            return false;
        }
        Date curentDate = new Date();
        if (ngaySinh != null) {
            if (ngaySinh.after(curentDate)) {
                JOptionPane.showMessageDialog(this, "Ngày sinh không được lớn hơn ngày hiện tại");
                dateNgaySinh.requestFocus();
                return false;
            }
        }

        Matcher matcher = Pattern.compile(regexEmail).matcher(email);
        if (!txtEmail.getText().isEmpty()) {
            if (!matcher.matches()) {
                JOptionPane.showMessageDialog(this, "Email sai định dạng");
                txtEmail.requestFocus();
                return false;
            }
        }

        if (soDienThoai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số điện thoại khách hàng không được để trống");
            txtSoDienThoai.requestFocus();
            return false;
        }
        if (!soDienThoai.matches(regex)) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không đúng định dạng");
            txtSoDienThoai.requestFocus();
            return false;
        }

        return true;
    }

    void LoadTableLichSu() {
        int index = tblkhachhang.getSelectedRow();
        String sdt = tblkhachhang.getValueAt(index, 4).toString();
        int idKH = ser.getIdBySoDienThoai(sdt);
        System.out.println("ID" + idKH);
        listLS = ser.getLichSuMuaHangByIDKhachHang(idKH);
        modelLS.setRowCount(0);
        for (LichSuMuaHang ls : listLS) {
            modelLS.addRow(new Object[]{
                ls.getIdHoaDon(),
                ls.getNgayTao(),
                decimalFormat.format(ls.getTongTien()) + " VNĐ"
            });
        }
    }

    void clearFrom() {
        txtTenKhachHang.setText("");
        rdoNam.isSelected();
        dateNgaySinh.setDate(null);
        txtSoDienThoai.setText("");
        txtEmail.setText("");
        btnAddDiaChiMacDinh.setEnabled(true);
        btnThem.setEnabled(true);
        showData(ser.getKhachHangByPage(trangCustomer, 5));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel9 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLichSuMuaHang = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        rdoNamSearch = new javax.swing.JRadioButton();
        rdoNuSearch = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtTenKhachHang = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        dateNgaySinh = new com.toedter.calendar.JDateChooser();
        txtEmail = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        btnAddDiaChiMacDinh = new javax.swing.JButton();
        txtSoDienThoai = new javax.swing.JTextField();
        cboDiaChi = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jButton13 = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblkhachhang = new javax.swing.JTable();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        lblPage = new javax.swing.JLabel();
        btnLast = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblKhachHangDeleted = new javax.swing.JTable();
        btnKhoiPhuc = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnFirstDeleted = new javax.swing.JButton();
        btnPrevDeleted = new javax.swing.JButton();
        lblPageDelete = new javax.swing.JLabel();
        btnNextDeleted = new javax.swing.JButton();
        btnLastDeleted = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setMaximumSize(new java.awt.Dimension(1058, 770));
        jPanel9.setMinimumSize(new java.awt.Dimension(1058, 770));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setText("KHÁCH HÀNG");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Lịch sử giao dịch"));

        tblLichSuMuaHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã HD", "Ngày Tạo", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblLichSuMuaHang);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm  kiếm"));

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        jLabel4.setText("Giới tính");

        buttonGroup2.add(rdoNamSearch);
        rdoNamSearch.setText("Nam");
        rdoNamSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNamSearchActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdoNuSearch);
        rdoNuSearch.setText("Nữ");
        rdoNuSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNuSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(81, 81, 81)
                .addComponent(rdoNamSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(rdoNuSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                .addGap(52, 52, 52))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(rdoNamSearch)
                    .addComponent(rdoNuSearch))
                .addGap(14, 14, 14))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Khách hàng"));

        jLabel6.setText("Tên khách hàng ");

        jLabel7.setText("Giới tính");

        buttonGroup1.add(rdoNam);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        jLabel8.setText("Ngày sinh");

        jLabel9.setText("Số điện thoại");

        jLabel10.setText("Email");

        jLabel12.setText("Địa chỉ");

        btnAddDiaChiMacDinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/icons8-add.gif"))); // NOI18N
        btnAddDiaChiMacDinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDiaChiMacDinhActionPerformed(evt);
            }
        });

        txtSoDienThoai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSoDienThoaiKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSoDienThoaiKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(cboDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 541, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAddDiaChiMacDinh, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTenKhachHang)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(rdoNam, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdoNu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(dateNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 36, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(rdoNam)
                            .addComponent(rdoNu)
                            .addComponent(jLabel10)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)))
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(dateNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12)
                    .addComponent(btnAddDiaChiMacDinh, javax.swing.GroupLayout.PREFERRED_SIZE, 52, Short.MAX_VALUE)
                    .addComponent(cboDiaChi))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức năng"));

        jButton13.setBackground(new java.awt.Color(255, 0, 0));
        jButton13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton13.setForeground(new java.awt.Color(255, 255, 255));
        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/expoert.png"))); // NOI18N
        jButton13.setText("Export");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        btnLamMoi.setBackground(new java.awt.Color(255, 51, 51));
        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/icons8-clear-48.png"))); // NOI18N
        btnLamMoi.setText("Clear");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(255, 51, 51));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/edit.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(255, 0, 0));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/icons8-delete-60.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(btnThem)
                            .addGap(24, 24, 24)
                            .addComponent(btnSua))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(btnXoa)
                            .addGap(26, 26, 26)
                            .addComponent(btnLamMoi))))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSua)
                    .addComponent(btnThem))
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLamMoi)
                    .addComponent(btnXoa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(jButton13)
                .addGap(17, 17, 17))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblkhachhang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Tên khách hàng", "Giới tính", "Ngày sinh", "SDT", "Email", "Địa chỉ", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblkhachhang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblkhachhangMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblkhachhangMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblkhachhangMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblkhachhang);

        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        lblPage.setText("Page");

        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(202, 202, 202)
                .addComponent(btnFirst)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPrev)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnNext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLast)
                .addContainerGap(232, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst)
                    .addComponent(btnPrev)
                    .addComponent(lblPage)
                    .addComponent(btnNext)
                    .addComponent(btnLast))
                .addGap(0, 33, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Danh sách khách hàng ", jPanel6);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblKhachHangDeleted.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Tên khách hàng", "Giới tính", "Ngày sinh", "SDT", "Email", "Địa chỉ mặc định", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhachHangDeleted.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangDeletedMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblKhachHangDeletedMousePressed(evt);
            }
        });
        jScrollPane5.setViewportView(tblKhachHangDeleted);

        btnKhoiPhuc.setBackground(new java.awt.Color(51, 153, 0));
        btnKhoiPhuc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnKhoiPhuc.setForeground(new java.awt.Color(255, 255, 255));
        btnKhoiPhuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/icons8-change-50 (2).png"))); // NOI18N
        btnKhoiPhuc.setText("Khôi phục");
        btnKhoiPhuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhoiPhucActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

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

        lblPageDelete.setText("Page");

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnFirstDeleted)
                .addGap(15, 15, 15)
                .addComponent(btnPrevDeleted)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPageDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnNextDeleted)
                .addGap(10, 10, 10)
                .addComponent(btnLastDeleted)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirstDeleted)
                    .addComponent(btnPrevDeleted)
                    .addComponent(lblPageDelete)
                    .addComponent(btnNextDeleted)
                    .addComponent(btnLastDeleted))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(111, 111, 111)
                .addComponent(btnKhoiPhuc)
                .addContainerGap())
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 790, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnKhoiPhuc)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Khách hàng đã xóa", jPanel7);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(388, 388, 388)
                        .addComponent(jLabel1))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1111, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 750, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        int gioiTinh;
        if (rdoNamSearch.isSelected()) {
            gioiTinh = 1;
        } else {
            gioiTinh = 0;
        }
        String keyword = txtSearch.getText().trim();
        loadToTableByKeyWord(keyword, gioiTinh);
    }//GEN-LAST:event_txtSearchKeyReleased

    private void rdoNamSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNamSearchActionPerformed
        int gioiTinh;
        if (rdoNamSearch.isSelected()) {
            gioiTinh = 1;
        } else {
            gioiTinh = 0;
        }
        String keyword = txtSearch.getText().trim();
        loadToTableByKeyWord(keyword, gioiTinh);
    }//GEN-LAST:event_rdoNamSearchActionPerformed

    private void rdoNuSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNuSearchActionPerformed
        int gioiTinh;
        if (rdoNamSearch.isSelected()) {
            gioiTinh = 1;
        } else {
            gioiTinh = 0;
        }
        String keyword = txtSearch.getText().trim();
        loadToTableByKeyWord(keyword, gioiTinh);
    }//GEN-LAST:event_rdoNuSearchActionPerformed

    private void btnAddDiaChiMacDinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDiaChiMacDinhActionPerformed

        new DiaChiView().setVisible(true);
    }//GEN-LAST:event_btnAddDiaChiMacDinhActionPerformed

    private void txtSoDienThoaiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoDienThoaiKeyReleased

    }//GEN-LAST:event_txtSoDienThoaiKeyReleased

    private void txtSoDienThoaiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoDienThoaiKeyTyped
        char key = evt.getKeyChar();
        String text = txtSoDienThoai.getText();
        sdtLength = text.length();

        if (key == '\b' && sdtLength > 0) {
            sdtLength--;
        }

        if (((key < '0') || (key > '9')) && (key != '\b') || sdtLength >= 10) {
            evt.consume();
        } else if (key >= '0' && key <= '9' && sdtLength < 10) {
            sdtLength++;
        }
    }//GEN-LAST:event_txtSoDienThoaiKeyTyped

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed

        try {
            String path = "D:\\FALL_2023\\BLOCK 2\\PRO1041\\EXCEL";
            JFileChooser jFileChooser = new JFileChooser(path);
            jFileChooser.showSaveDialog(this);
            File saveFile = jFileChooser.getSelectedFile();
            if (saveFile != null) {
                saveFile = new File(saveFile.toString() + ".xlsx");
                Workbook wb = new XSSFWorkbook();
                Sheet sheet = wb.createSheet("Account");

                Row rowCol = sheet.createRow(0);
                for (int i = 0; i < tblkhachhang.getColumnCount(); i++) {
                    Cell cell = rowCol.createCell(i);
                    cell.setCellValue(tblkhachhang.getColumnName(i));
                }
                for (int j = 0; j < tblkhachhang.getRowCount(); j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < tblkhachhang.getColumnCount(); k++) {
                        Cell cell = row.createCell(k);
                        if (tblkhachhang.getValueAt(j, k) != null) {
                            cell.setCellValue(tblkhachhang.getValueAt(j, k).toString());
                        }
                    }
                }
                FileOutputStream out = new FileOutputStream(new File(saveFile.toString()));
                wb.write(out);
                wb.close();
                out.close();
                EventStream.openFile(saveFile.toPath());
                JOptionPane.showMessageDialog(this, "Export Excel thành công ");
                showData(listKH);
                int maxPage = ser.getMaxPage(5);
                lblPage.setText("1/" + maxPage);

                showDataDeleted(ser.getKhachHangDeletedByPage(trangCustomerDeleted, 5));
                int maxPageDeletd = ser.getMaxPageDeleted(5);
                lblPageDelete.setText("1/" + maxPageDeletd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        int chek = JOptionPane.showConfirmDialog(this, "Bạn có muốn làm mới không ?", "Clear", JOptionPane.YES_NO_OPTION);
        if (chek != JOptionPane.YES_OPTION) {
            return;
        }
        clearFrom();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (validateUpdate()) {
            int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa khách hàng này không ?", "add", JOptionPane.YES_NO_OPTION);
            if (check != JOptionPane.YES_NO_OPTION) {
                return;
            }
            ModelKhachHang kh = getFromForUpdate();
            int index = tblkhachhang.getSelectedRow();
            if (index < 0) {
                JOptionPane.showMessageDialog(this, "Chưa chọn dòng thực hiện");
                return;
            }
            String sdt = tblkhachhang.getValueAt(index, 4).toString();
            int idKhachHang = ser.getIdBySoDienThoai(sdt);
            kh.setID(idKhachHang);
            try {

                if (ser.updateKhachHang(kh) != null) {
                    JOptionPane.showMessageDialog(this, "Sửa khách hàng thành công");
                    listKH = ser.getKhachHangByPage(trangCustomer, 5);
                    showData(listKH);
                    int maxPage = ser.getMaxPage(5);
                    lblPage.setText("1/" + maxPage);
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        int index = tblkhachhang.getSelectedRow();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Chưa chọn checkbox để thực hiện");
            return;
        }
        int columnDeleted = 7;
        List<Integer> seletedID = new ArrayList<>();
        for (int i = modelKH.getRowCount() - 1; i >= 0; i--) {
            Boolean isChecked = (Boolean) tblkhachhang.getValueAt(i, columnDeleted);
            if (isChecked != null && isChecked) {
                int id = ser.getKhachHangByPage(trangCustomer, 5).get(i).getID();
                System.out.println("ID khách hàng: " + id);
                seletedID.add(id);
            }

        }
        if (!seletedID.isEmpty() && JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa khách hàng này hay không ?", "update", JOptionPane.YES_NO_OPTION) == 0) {
            for (int id : seletedID) {
                if (ser.deleteKhachHang(id) != null) {
                    showData(ser.getKhachHangByPage(trangCustomer, 5));
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại");
                }

            }
            JOptionPane.showMessageDialog(this, "Xóa thành công");

            showData(ser.getKhachHangByPage(trangCustomer = 1, 5));
            int max = ser.getMaxPage(5);
            lblPage.setText(trangCustomer + "/" + max);
            if (max != 1) {
                btnLast.setEnabled(true);
                btnNext.setEnabled(true);

            }

            if (trangCustomerDeleted == 1) {
                btnFirst.setEnabled(false);
                btnPrev.setEnabled(false);
            }
            showDataDeleted(ser.getKhachHangDeletedByPage(trangCustomerDeleted = 1, 5));
            int maxDeleted = ser.getMaxPageDeleted(5);
            lblPageDelete.setText(trangCustomer + "/" + maxDeleted);
            if (maxDeleted != 1) {
                btnLastDeleted.setEnabled(true);
                btnNextDeleted.setEnabled(true);
            }
            if (maxDeleted == 1 && trangCustomerDeleted == 1) {
                btnLastDeleted.setEnabled(false);
                btnNextDeleted.setEnabled(false);
                btnPrevDeleted.setEnabled(false);
                btnFirstDeleted.setEnabled(false);

            }
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (validateKhachHang()) {
            int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm khách hàng này không ?", "add", JOptionPane.YES_NO_OPTION);
            if (check != JOptionPane.YES_NO_OPTION) {
                return;
            }
            ModelKhachHang kh = getFrom();
            int index = tblkhachhang.getSelectedRowCount();
            String sdt = txtSoDienThoai.getText().trim();
            boolean sdtTT = false;
            for (ModelKhachHang modelKhachHang : listKH) {
                if (modelKhachHang.getSDT().trim().equals(sdt)) {
                    sdtTT = true;
                    break;

                }

            }
            if (sdtTT) {
                JOptionPane.showMessageDialog(this, "Số điện thoại đã tồn tại không thể thêm");
                txtSoDienThoai.requestFocus();
            } else {
                try {

                    if (ser.addKhachHang(kh) != null) {
                        JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công");
                        listKH = ser.getKhachHangByPage(trangCustomer, 5);
                        showData(listKH);
                        int maxPage = ser.getMaxPage(5);
                        lblPage.setText("1/" + maxPage);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + e.getMessage());

                }
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void tblkhachhangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblkhachhangMouseClicked
        showDetail();
        LoadTableLichSu();
        if (evt.getClickCount() == 2) {
            ToanCuc tc = new ToanCuc();
            tc.setSdt(txtSoDienThoai.getText());
            tc.setTenKH(txtTenKhachHang.getText());
            tc.setDiaChi(tblkhachhang.getValueAt(tblkhachhang.getSelectedRow(), 6).toString());
            this.dispose();
        }
    }//GEN-LAST:event_tblkhachhangMouseClicked

    private void tblkhachhangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblkhachhangMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblkhachhangMouseEntered

    private void tblkhachhangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblkhachhangMousePressed

    }//GEN-LAST:event_tblkhachhangMousePressed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        this.trangCustomer = 1;
        listKH = ser.getKhachHangByPage(trangCustomer, 5);
        int max = ser.getMaxPage(5);
        showData(listKH);
        if (trangCustomer == 1) {
            btnFirst.setEnabled(false);
            btnPrev.setEnabled(false);
            btnNext.setEnabled(true);
            btnLast.setEnabled(true);
        }
        lblPage.setText(String.valueOf(trangCustomer) + "/" + max);
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        this.trangCustomer--;
        listKH = ser.getKhachHangByPage(trangCustomer, 5);
        int max = ser.getMaxPage(5);
        showData(listKH);
        if (trangCustomer == 1) {
            btnNext.setEnabled(true);
            btnLast.setEnabled(true);
            btnPrev.setEnabled(false);
            btnFirst.setEnabled(false);
        }
        if (trangCustomer != 1 && trangCustomer < max) {
            btnNext.setEnabled(true);
            btnLast.setEnabled(true);
        }
        lblPage.setText(String.valueOf(trangCustomer) + "/" + max);
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        int max = ser.getMaxPage(5);
        this.trangCustomer = max;
        listKH = ser.getKhachHangByPage(trangCustomer, 5);
        if (trangCustomer == max) {
            btnNext.setEnabled(false);
            btnLast.setEnabled(false);
            btnPrev.setEnabled(true);
            btnFirst.setEnabled(true);
        }
        listKH = ser.getKhachHangByPage(trangCustomer, 5);
        this.trangCustomer = max;
        showData(listKH);
        lblPage.setText(String.valueOf(trangCustomer) + "/" + max);
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        this.trangCustomer++;
        listKH = ser.getKhachHangByPage(trangCustomer, 5);
        showData(listKH);
        int max = ser.getMaxPage(5);
        if (trangCustomer >= max) {
            btnNext.setEnabled(false);
            btnLast.setEnabled(false);
            btnPrev.setEnabled(true);
            btnFirst.setEnabled(true);
        }
        if (trangCustomer != 1 && trangCustomer < max) {
            btnPrev.setEnabled(true);
            btnFirst.setEnabled(true);
        }
        lblPage.setText(String.valueOf(trangCustomer) + "/" + max);
    }//GEN-LAST:event_btnNextActionPerformed

    private void tblKhachHangDeletedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangDeletedMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblKhachHangDeletedMouseClicked

    private void tblKhachHangDeletedMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangDeletedMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblKhachHangDeletedMousePressed

    private void btnKhoiPhucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhoiPhucActionPerformed
        int index = tblKhachHangDeleted.getSelectedRow();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Chưa chọn checkbox để thực hiện");
            return;
        }

        int columnDeleted = 7;
        List<Integer> seletedID = new ArrayList<>();
        for (int i = modelDeleted.getRowCount() - 1; i >= 0; i--) {
            Boolean isChecked = (Boolean) tblKhachHangDeleted.getValueAt(i, columnDeleted);
            if (isChecked != null && isChecked) {
                int id = ser.getKhachHangDeletedByPage(trangCustomerDeleted, 5).get(i).getID();
                System.out.println("ID khách hàng: " + id);
                seletedID.add(id);
            }

        }
        if (!seletedID.isEmpty() && JOptionPane.showConfirmDialog(this, "Bạn có muốn khôi phục khách hàng này hay không ?", "update", JOptionPane.YES_NO_OPTION) == 0) {
            for (int id : seletedID) {
                if (ser.restoreKhachHang(id) != null) {
                    showDataDeleted(ser.getKhachHangDeletedByPage(trangCustomerDeleted, 5));
                } else {
                    JOptionPane.showMessageDialog(this, "Khôi phục thất bại");
                }

            }
            JOptionPane.showMessageDialog(this, "Khôi phục thành công");
            showData(ser.getKhachHangByPage(trangCustomer, 5));
            int max = ser.getMaxPage(5);
            lblPage.setText(trangCustomer + "/" + max);
            if (max == 1) {
                btnLast.setEnabled(false);
                btnNext.setEnabled(false);
            }
            if (trangCustomer == 1) {
                btnFirst.setEnabled(false);
                btnPrev.setEnabled(false);
            }

            showDataDeleted(ser.getKhachHangDeletedByPage(trangCustomerDeleted, 5));
            int maxdeleted = ser.getMaxPageDeleted(5);
            lblPageDelete.setText(trangCustomerDeleted + "/" + maxdeleted);
            if (maxdeleted == 1) {
                btnLastDeleted.setEnabled(false);
                btnNextDeleted.setEnabled(false);
            }
            if (trangCustomerDeleted == 1) {
                btnFirstDeleted.setEnabled(false);
                btnPrevDeleted.setEnabled(false);
            }
            if (maxdeleted == 1 && trangCustomerDeleted == 1) {
                btnLastDeleted.setEnabled(false);
                btnNextDeleted.setEnabled(false);
                btnFirstDeleted.setEnabled(false);
                btnPrevDeleted.setEnabled(false);
            }
            showDataDeleted(ser.getKhachHangDeletedByPage(trangCustomerDeleted, 5));
            lblPageDelete.setText(trangCustomerDeleted + "/" + maxdeleted);

        }
    }//GEN-LAST:event_btnKhoiPhucActionPerformed

    private void btnFirstDeletedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstDeletedActionPerformed
        this.trangCustomerDeleted = 1;
        listKHDeleted = ser.getKhachHangDeletedByPage(trangCustomerDeleted, 5);
        int max = ser.getMaxPageDeleted(5);
        showDataDeleted(listKHDeleted);
        if (trangCustomerDeleted == 1) {
            btnFirstDeleted.setEnabled(false);
            btnPrevDeleted.setEnabled(false);
            btnNextDeleted.setEnabled(true);
            btnLastDeleted.setEnabled(true);
        }
        lblPageDelete.setText(String.valueOf(trangCustomerDeleted) + "/" + max);
    }//GEN-LAST:event_btnFirstDeletedActionPerformed

    private void btnPrevDeletedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevDeletedActionPerformed
        this.trangCustomerDeleted--;
        listKHDeleted = ser.getKhachHangDeletedByPage(trangCustomerDeleted, 5);
        showDataDeleted(listKHDeleted);
        int max = ser.getMaxPageDeleted(5);

        if (trangCustomerDeleted == 1) {
            btnNextDeleted.setEnabled(true);
            btnLastDeleted.setEnabled(true);
            btnPrevDeleted.setEnabled(false);
            btnFirstDeleted.setEnabled(false);
        }
        if (trangCustomerDeleted != 1 && trangCustomerDeleted < max) {
            btnNextDeleted.setEnabled(true);
            btnLastDeleted.setEnabled(true);
        }
        lblPageDelete.setText(String.valueOf(trangCustomerDeleted) + "/" + max);
    }//GEN-LAST:event_btnPrevDeletedActionPerformed

    private void btnNextDeletedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextDeletedActionPerformed
        this.trangCustomerDeleted++;
        listKHDeleted = ser.getKhachHangDeletedByPage(trangCustomerDeleted, 5);
        showDataDeleted(listKHDeleted);
        int max = ser.getMaxPageDeleted(5);
        if (trangCustomerDeleted >= max) {
            btnNextDeleted.setEnabled(false);
            btnLastDeleted.setEnabled(false);
            btnPrevDeleted.setEnabled(true);
            btnFirstDeleted.setEnabled(true);
        }
        if (trangCustomerDeleted != 1 && trangCustomerDeleted < max) {
            btnPrevDeleted.setEnabled(true);
            btnFirstDeleted.setEnabled(true);
        }
        lblPageDelete.setText(String.valueOf(trangCustomerDeleted) + "/" + max);
    }//GEN-LAST:event_btnNextDeletedActionPerformed

    private void btnLastDeletedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastDeletedActionPerformed
        int max = ser.getMaxPageDeleted(5);
        this.trangCustomerDeleted = max;
        listKHDeleted = ser.getKhachHangDeletedByPage(trangCustomerDeleted, 5);
        if (trangCustomerDeleted == max) {
            btnNextDeleted.setEnabled(false);
            btnLastDeleted.setEnabled(false);
            btnPrevDeleted.setEnabled(true);
            btnFirstDeleted.setEnabled(true);
        }
        listKH = ser.getKhachHangByPage(trangCustomerDeleted, 5);
        this.trangCustomerDeleted = max;
        showDataDeleted(listKHDeleted);
        lblPageDelete.setText(String.valueOf(trangCustomerDeleted) + "/" + max);
    }//GEN-LAST:event_btnLastDeletedActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(KhachHangJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KhachHangJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KhachHangJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KhachHangJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                KhachHangJDialog dialog = new KhachHangJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddDiaChiMacDinh;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnFirstDeleted;
    private javax.swing.JButton btnKhoiPhuc;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnLastDeleted;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnNextDeleted;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnPrevDeleted;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cboDiaChi;
    private com.toedter.calendar.JDateChooser dateNgaySinh;
    private javax.swing.JButton jButton13;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lblPage;
    private javax.swing.JLabel lblPageDelete;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNamSearch;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JRadioButton rdoNuSearch;
    private javax.swing.JTable tblKhachHangDeleted;
    private javax.swing.JTable tblLichSuMuaHang;
    private javax.swing.JTable tblkhachhang;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTenKhachHang;
    // End of variables declaration//GEN-END:variables
}
