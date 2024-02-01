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
import com.raven.dialog.KhachHangJDialog;
import com.raven.form.frameViewCtsp.SelectImei;
import com.raven.form.frameViewCtsp.ViewQRBanHang;
import com.raven.model.ModelBill;
import com.raven.repositories.impl.NhanVienRepositoryImpl;
import com.raven.services.BanHangService;
import com.raven.services.BillService;
import com.raven.services.ChiTietSanPhamService;
import com.raven.services.impl.ChiTietSanPhamServiceImpl;
import com.raven.utils.Auth;
import com.raven.viewmodels.GioHangModel;
import com.raven.viewmodels.HoaDoninBanHang;
import com.raven.viewmodels.SanPhamChiTietResponse;
import com.raven.viewmodels.ToanCuc;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class BanHang extends javax.swing.JPanel implements Runnable, ThreadFactory {
    
    private WebcamPanel panel = null;
    private Webcam webcam = null;
    private Executor executor = Executors.newSingleThreadExecutor(this);
    public DefaultTableModel dtmsp = new DefaultTableModel();
    private SanPham product = new SanPham();
    private ChiTietSanPhamService ctsps = new ChiTietSanPhamServiceImpl();
    private BanHangService banHang = new BanHangService();
    private BillService billSV = new BillService();
    public int rowSP;
    public String tensp, chatlieu, cpuu, dungluong, gpu, hedieuhanh, manhinh, mausac, nhasanxuat;
    public int ram;
    public int rowHD;
    public int countTMTQ = 0;
    public int countCKTQ = 0;
    public int countTMDH = 0;
    public int countCKDH = 0;
    public int countPhiShip = 0;
    public int countSDT = 0;
    
    public BanHang() {
        initComponents();
        dtmsp = (DefaultTableModel) tableSP.getModel();
        showData(ctsps.getAllSanPhamBH());
        fillTableHoaDon(banHang.fillHoaDon(), -1);
        fillCboVoucher();
        cronJob();
        fillTableHoaDon(banHang.fillHoaDon(), -1);
    }
    
    public void fillCboVoucher() {
        cboGiamGia.removeAllItems();
        cboGiamGia1.removeAllItems();
        cboGiamGia.addItem("Không áp dụng");
        cboGiamGia1.addItem("Không áp dụng");
        for (int i = 0; i < banHang.getAllVoucher().size(); i++) {
            if (banHang.getAllVoucher().get(i).isLoaiGiamGia()) {
                cboGiamGia.addItem(banHang.getAllVoucher().get(i).getTenVoucher() + "(" + banHang.getAllVoucher().get(i).getGiaTri() + "%)");
                cboGiamGia1.addItem(banHang.getAllVoucher().get(i).getTenVoucher() + "(" + banHang.getAllVoucher().get(i).getGiaTri() + "%)");
            } else {
                cboGiamGia.addItem(banHang.getAllVoucher().get(i).getTenVoucher() + "(" + banHang.getAllVoucher().get(i).getGiaTri() + ")");
                cboGiamGia1.addItem(banHang.getAllVoucher().get(i).getTenVoucher() + "(" + banHang.getAllVoucher().get(i).getGiaTri() + ")");
            }
        }
    }
    
    public void cronJob() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);

        // Sửa lỗi ở đây: Bỏ dấu phẩy và thêm dấu ngoặc đơn
        LocalDate vinh = LocalDate.parse("2020-12-10", formatter);
        
        for (int i = 0; i < tableHD.getRowCount(); i++) {
            // Giả sử cột thứ 2 chứa ngày cần so sánh dưới dạng chuỗi
            LocalDate dateToCompare = LocalDate.parse(tableHD.getValueAt(i, 2).toString(), formatter);
            if (currentDate.isAfter(dateToCompare)) {
                banHang.updateHD(Integer.parseInt(tableHD.getValueAt(i, 1).toString()));
            }
        }
    }
    
    public BanHang(List<HoaDoninBanHang> list) {
        dtmsp = (DefaultTableModel) tableHD.getModel();
        dtmsp.setRowCount(0);
        int i = 0;
        for (HoaDoninBanHang hd : banHang.fillHoaDon()) {
            i++;
            dtmsp.addRow(hd.toDataRow(i));
        }
    }
    
    public void showData(List<SanPhamChiTietResponse> list) {
        dtmsp = (DefaultTableModel) tableSP.getModel();
        dtmsp.setRowCount(0);
        int sl = 0;
        for (SanPhamChiTietResponse spct : list) {
            dtmsp.addRow(new Object[]{
                sl += 1,
                spct.getSanPham().getTenSanPham(),
                spct.getChatLieu().getTenChatLieu(),
                spct.getCpu().getLoaiCPU(),
                spct.getDungLuong().getDungLuong(),
                spct.getGpu().getLoaiGPU(),
                spct.getHeDieuHanh().getHeDieuHanh(),
                spct.getManHinh().getLoaiManHinh(),
                spct.getMauSac().getTenMauSac(),
                spct.getNhaSanXuat().getTenNhaSanXuat(),
                spct.getRam().getDungLuongRam(),
                spct.getSoLuong()
            });
        }
    }
    
    public void fillTableHoaDon(List<HoaDoninBanHang> list, int a) {
        dtmsp = (DefaultTableModel) tableHD.getModel();
        dtmsp.setRowCount(0);
        int i = 0;
        for (HoaDoninBanHang hd : list) {
            i++;
            dtmsp.addRow(hd.toDataRow(i));
        }
        if (a >= 0) {
            tableHD.setRowSelectionInterval(a, a);
        }
    }
    
    public void fillTableGioHang(List<GioHangModel> list) {
        dtmsp = (DefaultTableModel) tableGH.getModel();
        dtmsp.setRowCount(0);
        int i = 0;
        for (GioHangModel hd : list) {
            i++;
            dtmsp.addRow(hd.toDataRow(i));
        }
    }
    
    public void load() {
        dtmsp = (DefaultTableModel) tableHD.getModel();
        dtmsp.setRowCount(0);
        int i = 0;
        for (HoaDoninBanHang hd : banHang.fillHoaDon()) {
            i++;
            dtmsp.addRow(hd.toDataRow(i));
        }
    }
    
    public boolean valiDateTaiQuay() {
        double tienKhachDuaTQ = parseDouble(txtTienKhachDuaTaiQuay.getText());
        double tienKhachCKTQ = parseDouble(txtTienKhachChuyenKhoanTaiQuay.getText());
        double tongTienTQ = parseDouble(txtTongTienTaiQuay.getText());
        double tienTraLai = parseDouble(txtTienTraLaiTQ.getText()); // Biến này không được sử dụng trong phương thức

        if (tongTienTQ <= 0) {
            JOptionPane.showMessageDialog(this, "Hóa đơn rỗng, không thể thanh toán!");
            return false;
        }
        if (tienKhachCKTQ + tienKhachDuaTQ < tongTienTQ) {
            JOptionPane.showMessageDialog(this, "Khách chưa trả đủ tiền không thể thanh toán!");
            return false;
        }
        if (tienTraLai > (tongTienTQ / 100 * 10)) {
            JOptionPane.showMessageDialog(this, "Tiền khách trả chỉ được lớn hơn 10% so với tổng tiền");
            return false;
        }
        return true;
    }
    
    private double parseDouble(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        try {
            return Double.parseDouble(text.replaceAll(",", ""));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số tiền nhập không hợp lệ: " + text);
            return 0;
        }
    }
    
    public boolean valiDateDatHang() {
        String regex = "^(0|84)(2(0[3-9]|1[0-6|8|9]|2[0-2|5-9]|3[2-9]|4[0-9]|5[1|2|4-9]|6[0-3|9]|7[0-7]|8[0-9]|9[0-4|6|7|9])|3[2-9]|5[5|6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])([0-9]{7})$";
        String tenKH = txtNguoiNhanDH.getText().trim();
        String soDienThoai = txtSDTDatHang.getText().trim();
        String diaChi = txtDiaChi.getText().trim();
        double tienKhachDuaTQ = parseDouble(txtTienMatDH.getText());
        double tienKhachCKTQ = parseDouble(txtTienCKDH.getText());
        double tongTienTQ = parseDouble(txtTongTienDH.getText());
        double tienTraLai = parseDouble(txtTienTraLaiDH.getText()); // Biến này không được sử dụng trong phương thức

        if (soDienThoai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!");
            txtSDTDatHang.requestFocus();
            return false;
        } else if (!soDienThoai.matches(regex)) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không đúng định dạng!");
            txtSDTDatHang.requestFocus();
            return false;
        }
        if (tenKH.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên người nhận!");
            txtNguoiNhanDH.requestFocus();
            return false;
        }
        if (diaChi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập địa chỉ!");
            txtDiaChi.requestFocus();
            return false;
        }
        if (tongTienTQ <= 0) {
            JOptionPane.showMessageDialog(this, "Hóa đơn rỗng, không thể thanh toán!");
            return false;
        }
        if (tienKhachCKTQ + tienKhachDuaTQ < tongTienTQ) {
            JOptionPane.showMessageDialog(this, "Khách chưa trả đủ tiền không thể thanh toán!");
            return false;
        }
        if (tienTraLai > (tongTienTQ / 100 * 10)) {
            JOptionPane.showMessageDialog(this, "Tiền khách trả chỉ được lớn hơn 10% so với tổng tiền");
            return false;
        }
        return true;
    }
    
    @Override
    public void run() {
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Result result = null;
            BufferedImage image = null;
            
            if (webcam.isOpen()) {
                image = webcam.getImage();
            }
            
            if (image != null) {
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                
                try {
                    result = new MultiFormatReader().decode(bitmap);
                } catch (NotFoundException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if (result != null) {
//                txtResult.setText(result.getText());
            } else {
                System.out.println("Không tìm thấy mã QR");
            }
            
        } while (true);
    }
    
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "My Thread");
        t.setDaemon(true);
        return t;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableSP = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableHD = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableGH = new javax.swing.JTable();
        tabS = new javax.swing.JTabbedPane();
        tab1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMaHDTaiQuay = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTenNVTaiQuay = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNgayTaoTaiQuay = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTongTienTaiQuay = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTienKhachDuaTaiQuay = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtTienKhachChuyenKhoanTaiQuay = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtTienTraLaiTQ = new javax.swing.JTextField();
        btnThanhToanTaiQuay = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        txtSDTTaiQuay = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtTenKHTaiQuay = new javax.swing.JTextField();
        btnKhachHang = new javax.swing.JButton();
        cboGiamGia = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cboHinhThucThanhToan = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        txtMaGiaoDichTQ = new javax.swing.JTextField();
        tab2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        txtSDTDatHang = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtTenKhachHangDH = new javax.swing.JTextField();
        btnKhachHang1 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        txtMaHoaDonDH = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtTenNhanVienDH = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txtTienMatDH = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtTongTienDH = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        btnGiaoHang = new javax.swing.JButton();
        cboGiamGia1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        btnThanhToanDH = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtNguoiNhanDH = new javax.swing.JTextField();
        txtTienCKDH = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txtPhiShip = new javax.swing.JTextField();
        txtTienTraLaiDH = new javax.swing.JTextField();
        cboHinhThucTTDH = new javax.swing.JComboBox<>();
        jLabel37 = new javax.swing.JLabel();
        txtMaGiaoDichDH = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1058, 770));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Sản phẩm"));

        tableSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Tên sản phẩm", "Chất liệu", "Cpu", "Dung lượng", "Gpu", "Hệ điều hành", "Màn hình", "Màu sắc", "Hãng", "Ram", "SL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableSPMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tableSP);
        if (tableSP.getColumnModel().getColumnCount() > 0) {
            tableSP.getColumnModel().getColumn(0).setPreferredWidth(30);
            tableSP.getColumnModel().getColumn(1).setPreferredWidth(100);
            tableSP.getColumnModel().getColumn(3).setPreferredWidth(45);
            tableSP.getColumnModel().getColumn(5).setPreferredWidth(45);
            tableSP.getColumnModel().getColumn(8).setPreferredWidth(50);
            tableSP.getColumnModel().getColumn(10).setPreferredWidth(30);
            tableSP.getColumnModel().getColumn(11).setMinWidth(30);
            tableSP.getColumnModel().getColumn(11).setPreferredWidth(30);
            tableSP.getColumnModel().getColumn(11).setMaxWidth(30);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 237, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Hóa đơn"));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        tableHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã HD", "Ngày tạo", "Mã NV", "Số lượng SP", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableHDMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tableHD);
        if (tableHD.getColumnModel().getColumnCount() > 0) {
            tableHD.getColumnModel().getColumn(0).setMinWidth(40);
            tableHD.getColumnModel().getColumn(0).setPreferredWidth(40);
            tableHD.getColumnModel().getColumn(0).setMaxWidth(40);
            tableHD.getColumnModel().getColumn(1).setMinWidth(60);
            tableHD.getColumnModel().getColumn(1).setPreferredWidth(60);
            tableHD.getColumnModel().getColumn(1).setMaxWidth(60);
            tableHD.getColumnModel().getColumn(2).setMinWidth(80);
            tableHD.getColumnModel().getColumn(2).setPreferredWidth(80);
            tableHD.getColumnModel().getColumn(2).setMaxWidth(80);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Giỏ hàng"));

        tableGH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên sản phẩm", "Hãng", "Đơn giá", "Số lượng", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableGH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableGHMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableGH);
        if (tableGH.getColumnModel().getColumnCount() > 0) {
            tableGH.getColumnModel().getColumn(5).setPreferredWidth(40);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        tabS.setBackground(new java.awt.Color(255, 255, 255));
        tabS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tabS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabSMouseClicked(evt);
            }
        });

        tab1.setBackground(new java.awt.Color(255, 255, 255));
        tab1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel1.setText("Giảm giá:");

        txtMaHDTaiQuay.setEditable(false);

        jLabel3.setText("Mã hóa đơn:");

        txtTenNVTaiQuay.setEditable(false);

        jLabel4.setText("Mã NV:");

        txtNgayTaoTaiQuay.setEditable(false);

        jLabel5.setText("Ngày tạo:");

        jLabel6.setText("Tổng tiền :");

        txtTongTienTaiQuay.setEditable(false);

        jLabel8.setText("TIền mặt:");

        txtTienKhachDuaTaiQuay.setText("0");
        txtTienKhachDuaTaiQuay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienKhachDuaTaiQuayActionPerformed(evt);
            }
        });
        txtTienKhachDuaTaiQuay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienKhachDuaTaiQuayKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTienKhachDuaTaiQuayKeyTyped(evt);
            }
        });

        jLabel9.setText("Tiền CK:");

        txtTienKhachChuyenKhoanTaiQuay.setEditable(false);
        txtTienKhachChuyenKhoanTaiQuay.setText("0");
        txtTienKhachChuyenKhoanTaiQuay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienKhachChuyenKhoanTaiQuayKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTienKhachChuyenKhoanTaiQuayKeyTyped(evt);
            }
        });

        jLabel10.setText("Tiền trả lại:");

        txtTienTraLaiTQ.setEditable(false);
        txtTienTraLaiTQ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienTraLaiTQKeyReleased(evt);
            }
        });

        btnThanhToanTaiQuay.setBackground(new java.awt.Color(153, 0, 0));
        btnThanhToanTaiQuay.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThanhToanTaiQuay.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToanTaiQuay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/icons8-card-payment-50.png"))); // NOI18N
        btnThanhToanTaiQuay.setText("Thanh toán");
        btnThanhToanTaiQuay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanTaiQuayActionPerformed(evt);
            }
        });

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin khách hàng"));

        txtSDTTaiQuay.setEditable(false);

        jLabel30.setText("SDT:");

        jLabel31.setText("Tên KH:");

        txtTenKHTaiQuay.setEditable(false);
        txtTenKHTaiQuay.setText("Khách lẻ");

        btnKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnKhachHang.setForeground(new java.awt.Color(255, 255, 255));
        btnKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/icons8-customer-30.png"))); // NOI18N
        btnKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhachHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSDTTaiQuay, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenKHTaiQuay, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(btnKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtSDTTaiQuay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(txtTenKHTaiQuay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(btnKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        cboGiamGia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboGiamGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboGiamGiaActionPerformed(evt);
            }
        });

        jLabel7.setText("Hình thức TT:");

        cboHinhThucThanhToan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền mặt", "Chuyển khoản", "Cả hai" }));
        cboHinhThucThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHinhThucThanhToanActionPerformed(evt);
            }
        });

        jLabel13.setText("Mã giao dịch:");

        txtMaGiaoDichTQ.setEditable(false);

        javax.swing.GroupLayout tab1Layout = new javax.swing.GroupLayout(tab1);
        tab1.setLayout(tab1Layout);
        tab1Layout.setHorizontalGroup(
            tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab1Layout.createSequentialGroup()
                .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tab1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tab1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(29, 29, 29)
                                .addComponent(cboGiamGia, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(tab1Layout.createSequentialGroup()
                                .addGap(79, 79, 79)
                                .addComponent(txtTienKhachChuyenKhoanTaiQuay))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(tab1Layout.createSequentialGroup()
                                .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(tab1Layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(6, 6, 6)
                                        .addComponent(txtMaHDTaiQuay, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(tab1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(41, 41, 41)
                                        .addComponent(txtTenNVTaiQuay, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(tab1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(28, 28, 28)
                                        .addComponent(txtNgayTaoTaiQuay, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(tab1Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(30, 30, 30)
                                        .addComponent(txtTienKhachDuaTaiQuay, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(tab1Layout.createSequentialGroup()
                                        .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(tab1Layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addGap(6, 6, 6))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab1Layout.createSequentialGroup()
                                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)))
                                        .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtTongTienTaiQuay, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                                            .addComponent(cboHinhThucThanhToan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(tab1Layout.createSequentialGroup()
                                .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnThanhToanTaiQuay, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTienTraLaiTQ, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                                    .addComponent(txtMaGiaoDichTQ))))))
                .addContainerGap())
        );
        tab1Layout.setVerticalGroup(
            tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(tab1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtMaHDTaiQuay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(tab1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtTenNVTaiQuay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgayTaoTaiQuay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTongTienTaiQuay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab1Layout.createSequentialGroup()
                        .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTienKhachDuaTaiQuay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTienKhachChuyenKhoanTaiQuay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(txtTienTraLaiTQ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtMaGiaoDichTQ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(btnThanhToanTaiQuay, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79))
        );

        tabS.addTab("Tại quầy", tab1);

        tab2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Nhân viên vận chuyển"));

        jLabel15.setText("SDT:");

        jLabel16.setText("Tên KH:");

        jLabel19.setBackground(new java.awt.Color(255, 153, 204));
        jLabel19.setText("Nguyễn Khắc Kiên");

        jLabel20.setBackground(new java.awt.Color(255, 153, 204));
        jLabel20.setText("0393524703");

        jButton4.setBackground(new java.awt.Color(255, 153, 255));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 204, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/icons8-delivery-30.png"))); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                        .addGap(29, 29, 29))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jButton4)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jButton4)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin khách hàng"));

        txtSDTDatHang.setEnabled(false);
        txtSDTDatHang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSDTDatHangKeyTyped(evt);
            }
        });

        jLabel17.setText("SDT:");

        jLabel18.setText("Tên KH:");

        txtTenKhachHangDH.setEditable(false);

        btnKhachHang1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnKhachHang1.setForeground(new java.awt.Color(255, 255, 255));
        btnKhachHang1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/icons8-customer-30.png"))); // NOI18N
        btnKhachHang1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhachHang1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSDTDatHang, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                    .addComponent(txtTenKhachHangDH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnKhachHang1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(txtSDTDatHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(txtTenKhachHangDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnKhachHang1))
        );

        jLabel21.setText("Mã hóa đơn:");

        txtMaHoaDonDH.setEditable(false);

        jLabel22.setText("Mã NV:");

        txtTenNhanVienDH.setEditable(false);

        jLabel23.setText("Giảm giá:");

        jLabel24.setText("Địa chỉ:");

        jLabel25.setText("Phí ship:");

        jLabel27.setText("Tiền mặt:");

        txtTienMatDH.setText("0");
        txtTienMatDH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienMatDHKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTienMatDHKeyTyped(evt);
            }
        });

        jLabel28.setText("Chuyển khoản :");

        txtTongTienDH.setEditable(false);
        txtTongTienDH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongTienDHActionPerformed(evt);
            }
        });

        jLabel34.setText("Tổng tiền:");

        jLabel36.setText("Tiền trả lại");

        btnGiaoHang.setBackground(new java.awt.Color(153, 0, 0));
        btnGiaoHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGiaoHang.setForeground(new java.awt.Color(255, 255, 255));
        btnGiaoHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/icons8-delivery-30.png"))); // NOI18N
        btnGiaoHang.setText("Giao hàng");
        btnGiaoHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGiaoHangActionPerformed(evt);
            }
        });

        cboGiamGia1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboGiamGia1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboGiamGia1ActionPerformed(evt);
            }
        });

        txtDiaChi.setColumns(20);
        txtDiaChi.setRows(5);
        jScrollPane1.setViewportView(txtDiaChi);

        btnThanhToanDH.setBackground(new java.awt.Color(153, 0, 0));
        btnThanhToanDH.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThanhToanDH.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToanDH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/icons8-card-payment-50.png"))); // NOI18N
        btnThanhToanDH.setText("Thanh toán");
        btnThanhToanDH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanDHActionPerformed(evt);
            }
        });

        jLabel2.setText("Người nhận:");

        txtNguoiNhanDH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNguoiNhanDHKeyTyped(evt);
            }
        });

        txtTienCKDH.setEditable(false);
        txtTienCKDH.setText("0");
        txtTienCKDH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienCKDHActionPerformed(evt);
            }
        });
        txtTienCKDH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienCKDHKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTienCKDHKeyTyped(evt);
            }
        });

        jLabel29.setText("Hình thức TT:");

        txtPhiShip.setText("0");
        txtPhiShip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPhiShipKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPhiShipKeyTyped(evt);
            }
        });

        txtTienTraLaiDH.setEditable(false);
        txtTienTraLaiDH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienTraLaiDHActionPerformed(evt);
            }
        });

        cboHinhThucTTDH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền mặt", "Chuyển khoản", "Cả hai" }));
        cboHinhThucTTDH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHinhThucTTDHActionPerformed(evt);
            }
        });

        jLabel37.setText("Mã giao dịch:");

        txtMaGiaoDichDH.setEditable(false);
        txtMaGiaoDichDH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaGiaoDichDHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tab2Layout = new javax.swing.GroupLayout(tab2);
        tab2.setLayout(tab2Layout);
        tab2Layout.setHorizontalGroup(
            tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tab2Layout.createSequentialGroup()
                                .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel29)
                                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(29, 29, 29)
                                .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMaHoaDonDH, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTenNhanVienDH, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboGiamGia1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTienMatDH, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboHinhThucTTDH, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNguoiNhanDH, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPhiShip, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(tab2Layout.createSequentialGroup()
                                .addComponent(btnThanhToanDH)
                                .addGap(18, 18, 18)
                                .addComponent(btnGiaoHang, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(77, 77, 77))
                    .addGroup(tab2Layout.createSequentialGroup()
                        .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34)
                            .addComponent(jLabel36)
                            .addComponent(jLabel37))
                        .addGap(23, 23, 23)
                        .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTongTienDH, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                            .addComponent(txtTienCKDH, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTienTraLaiDH)
                            .addComponent(txtMaGiaoDichDH))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(tab2Layout.createSequentialGroup()
                        .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        tab2Layout.setVerticalGroup(
            tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaHoaDonDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenNhanVienDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNguoiNhanDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboGiamGia1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPhiShip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(cboHinhThucTTDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTienMatDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tab2Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTongTienDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTienTraLaiDH, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTienCKDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaGiaoDichDH, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThanhToanDH, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGiaoHang, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        tabS.addTab("Đặt hàng", tab2);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 255));
        jLabel14.setText("Bán Hàng");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/z4947982062204_cc726bd2d83818a4619a57822d9df5f0.gif"))); // NOI18N
        jButton1.setText("Quét QR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/icons8-add-40.png"))); // NOI18N
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThem)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(tabS, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(983, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(tabS, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel14)
                                .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnThem, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(736, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTienKhachDuaTaiQuayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienKhachDuaTaiQuayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienKhachDuaTaiQuayActionPerformed

    private void tableHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableHDMouseClicked
        // TODO add your handling code here:
        int index = tableHD.getSelectedRow();
        int maHD = Integer.parseInt(tableHD.getValueAt(index, 1).toString());
        int maNV = Integer.parseInt(tableHD.getValueAt(index, 3).toString().replaceAll("NV0", ""));
        NhanVienRepositoryImpl nv = new NhanVienRepositoryImpl();
        ModelBill modelBill = billSV.selectHoaDonById(maHD);
        txtSDTDatHang.setText(modelBill.getSoDienThoai());
        txtSDTTaiQuay.setText(modelBill.getSoDienThoai());
        txtDiaChi.setText(modelBill.getDiaChi());
        txtPhiShip.setText(modelBill.getTienShip() + "");
        txtMaHDTaiQuay.setText(modelBill.getId() + "");
        txtMaHoaDonDH.setText(modelBill.getId() + "");
        txtNguoiNhanDH.setText(modelBill.getTenNguoiNhan());
        txtTenKhachHangDH.setText(modelBill.getTenKhachHang());
        txtTenNVTaiQuay.setText("NV0" + modelBill.getTenNhanVien() + "-" + nv.getOneByID(maNV).getTenNhanVien());
        txtTenNhanVienDH.setText("NV0" + modelBill.getTenNhanVien() + "-" + nv.getOneByID(maNV).getTenNhanVien());
        txtNgayTaoTaiQuay.setText(modelBill.getNgayTao() + "");
        fillTableGioHang(banHang.getAllHDCT(maHD));
        double tongTien = 0;
        for (int i = 0; i < tableGH.getRowCount(); i++) {
            tongTien = tongTien + Double.parseDouble(tableGH.getValueAt(i, 5).toString().replaceAll(",", ""));
        }
        txtTongTienTaiQuay.setText(String.format("%,.0f", tongTien));
        txtTongTienDH.setText(String.format("%,.0f", tongTien));
        if (!txtTienKhachDuaTaiQuay.getText().isEmpty() && !txtTienKhachChuyenKhoanTaiQuay.getText().isEmpty()) {
            double tienMat = Double.parseDouble(txtTienKhachDuaTaiQuay.getText());
            double tienCK = Double.parseDouble(txtTienKhachChuyenKhoanTaiQuay.getText());
            double tienTraLai = tienCK + tienMat - tongTien;
            if (tienTraLai > 0) {
                txtTienTraLaiTQ.setText(String.format("%,.0f", tienTraLai));
            }
        }
        cboGiamGia.setSelectedIndex(0);
        cboGiamGia1.setSelectedIndex(0);
        txtTienTraLaiDH.setText("");
        txtTienTraLaiTQ.setText("");
        txtTienCKDH.setText("");
        txtTienKhachDuaTaiQuay.setText("");
        txtTienMatDH.setText("");
        txtTienKhachChuyenKhoanTaiQuay.setText("");
    }//GEN-LAST:event_tableHDMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        dtmsp = (DefaultTableModel) tableHD.getModel();
        int tab = tabS.getSelectedIndex();
        if (dtmsp.getRowCount() < 10) {
            if (tab == 0) {
                billSV.add("Bán tại quầy", 2, Auth.user.getId());
                fillTableHoaDon(banHang.fillHoaDon(), -1);
                banHang.insertLichSu(Integer.parseInt(tableHD.getValueAt(0, 1).toString()), "Tạo hóa đơn", Auth.user.getId());
            } else if (tab == 1) {
                billSV.add("Khách đặt hàng", 4, Auth.user.getId());
                fillTableHoaDon(banHang.fillHoaDon(), -1);
                banHang.insertLichSu(Integer.parseInt(tableHD.getValueAt(0, 1).toString()), "Tạo hóa đơn", Auth.user.getId());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Chỉ được phép tạo 10 hóa đơn chờ");
            return;
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void tableSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSPMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int index = tableHD.getSelectedRow();
            if (index < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn");
                return;
            }
            int maNV = Integer.parseInt(tableHD.getValueAt(index, 3).toString().replaceAll("NV0", ""));
            NhanVienRepositoryImpl nv = new NhanVienRepositoryImpl();
            rowSP = tableSP.getSelectedRow();
            rowHD = tableHD.getSelectedRow();
            tensp = tableSP.getValueAt(rowSP, 1).toString();
            chatlieu = tableSP.getValueAt(rowSP, 2).toString();
            cpuu = tableSP.getValueAt(rowSP, 3).toString();
            dungluong = tableSP.getValueAt(rowSP, 4).toString();
            gpu = tableSP.getValueAt(rowSP, 5).toString();
            hedieuhanh = tableSP.getValueAt(rowSP, 6).toString();
            manhinh = tableSP.getValueAt(rowSP, 7).toString();
            mausac = tableSP.getValueAt(rowSP, 8).toString();
            nhasanxuat = tableSP.getValueAt(rowSP, 9).toString();
            ram = Integer.parseInt(tableSP.getValueAt(rowSP, 10).toString());
            if (rowHD >= 0) {
                int maHD = Integer.parseInt(tableHD.getValueAt(rowHD, 1).toString());
                SelectImei frame = new SelectImei(null, true, rowSP, tensp, chatlieu, cpuu, dungluong, gpu, hedieuhanh, manhinh, mausac, nhasanxuat, ram, maHD);
                frame.setVisible(true);
                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        int a = tableHD.getSelectedRow();
                        fillTableHoaDon(banHang.fillHoaDon(), a);
                        fillTableGioHang(banHang.getAllHDCT(maHD));
                        showData(ctsps.getAllSanPhamBH());
                        int index = tableHD.getSelectedRow();
                        int maHD = Integer.parseInt(tableHD.getValueAt(index, 1).toString());
                        ModelBill modelBill = billSV.selectHoaDonById(maHD);
                        txtMaHDTaiQuay.setText(modelBill.getId() + "");
                        txtMaHoaDonDH.setText(modelBill.getId() + "");
                        txtTenNVTaiQuay.setText("NV0" + modelBill.getTenNhanVien() + "-" + nv.getOneByID(maNV).getTenNhanVien());
                        txtTenNhanVienDH.setText("NV0" + modelBill.getTenNhanVien() + "-" + nv.getOneByID(maNV).getTenNhanVien());
                        txtNgayTaoTaiQuay.setText(modelBill.getNgayTao() + "");
                        txtNguoiNhanDH.setText(modelBill.getNgayTao() + "");
                        fillTableGioHang(banHang.getAllHDCT(maHD));
                        double tongTien = 0;
                        for (int i = 0; i < tableGH.getRowCount(); i++) {
                            tongTien = tongTien + Double.parseDouble(tableGH.getValueAt(i, 5).toString().replaceAll(",", ""));
                        }
                        txtTongTienTaiQuay.setText(String.format("%,.0f", tongTien));
                        txtTongTienDH.setText(String.format("%,.0f", tongTien));
                        if (!txtTienKhachDuaTaiQuay.getText().isEmpty() && !txtTienKhachChuyenKhoanTaiQuay.getText().isEmpty()) {
                            double tienMat = Double.parseDouble(txtTienKhachDuaTaiQuay.getText());
                            double tienCK = Double.parseDouble(txtTienKhachChuyenKhoanTaiQuay.getText());
                            double tienTraLai = tienCK + tienMat - tongTien;
                            if (tienTraLai > 0) {
                                txtTienTraLaiTQ.setText(String.format("%,.0f", tienTraLai));
                            }
                        }
                        cboGiamGia.setSelectedIndex(0);
                        cboGiamGia1.setSelectedIndex(0);
                        txtTienTraLaiDH.setText("");
                        txtTienTraLaiTQ.setText("");
                    }
                });
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để thêm sản phầm");
            }
        }
    }//GEN-LAST:event_tableSPMouseClicked

    private void txtTienTraLaiTQKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienTraLaiTQKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txtTienTraLaiTQKeyReleased

    private void txtTienKhachDuaTaiQuayKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachDuaTaiQuayKeyReleased
        // TODO add your handling code here:
        if (!txtTongTienTaiQuay.getText().isEmpty()) {
            BigDecimal tongTien = BigDecimal.ZERO;
            tongTien = tongTien.add(new BigDecimal(txtTongTienTaiQuay.getText().replaceAll(",", "")));
            String tienMatText = txtTienKhachDuaTaiQuay.getText();
            String tienCKText = txtTienKhachChuyenKhoanTaiQuay.getText();
            BigDecimal tienMat = tienMatText.isEmpty() ? BigDecimal.ZERO : new BigDecimal(tienMatText);
            BigDecimal tienCK = tienCKText.isEmpty() ? BigDecimal.ZERO : new BigDecimal(tienCKText);
            BigDecimal tienTraLai = tienMat.add(tienCK).subtract(tongTien);
//            if (tienTraLai.compareTo(BigDecimal.ZERO) > 0) {
            txtTienTraLaiTQ.setText(String.format("%,.0f", tienTraLai));
//            } else {
//                txtTienTraLaiTQ.setText("0");
//            }
        }
    }//GEN-LAST:event_txtTienKhachDuaTaiQuayKeyReleased

    private void txtTienKhachDuaTaiQuayKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachDuaTaiQuayKeyTyped
        // TODO add your handling code here:
        char key = evt.getKeyChar();
        if ((key < '0' || key > '9') && (key != '\b')) {
            evt.consume(); // Bỏ qua sự kiện phím nếu không phải số hoặc backspace
        }
    }//GEN-LAST:event_txtTienKhachDuaTaiQuayKeyTyped

    private void txtTienKhachChuyenKhoanTaiQuayKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachChuyenKhoanTaiQuayKeyTyped
        // TODO add your handling code here:
        char key = evt.getKeyChar();
        if ((key < '0' || key > '9') && (key != '\b')) {
            evt.consume(); // Bỏ qua sự kiện phím nếu không phải số hoặc backspace
        }
    }//GEN-LAST:event_txtTienKhachChuyenKhoanTaiQuayKeyTyped

    private void txtTienKhachChuyenKhoanTaiQuayKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachChuyenKhoanTaiQuayKeyReleased
        // TODO add your handling code here:
        if (!txtTongTienTaiQuay.getText().isEmpty()) {
            BigDecimal tongTien = BigDecimal.ZERO;
            tongTien = tongTien.add(new BigDecimal(txtTongTienTaiQuay.getText().replaceAll(",", "")));
            String tienMatText = txtTienKhachDuaTaiQuay.getText();
            String tienCKText = txtTienKhachChuyenKhoanTaiQuay.getText();
            BigDecimal tienMat = tienMatText.isEmpty() ? BigDecimal.ZERO : new BigDecimal(tienMatText);
            BigDecimal tienCK = tienCKText.isEmpty() ? BigDecimal.ZERO : new BigDecimal(tienCKText);
            BigDecimal tienTraLai = tienMat.add(tienCK).subtract(tongTien);
            txtTienTraLaiTQ.setText(String.format("%,.0f", tienTraLai));
        }
    }//GEN-LAST:event_txtTienKhachChuyenKhoanTaiQuayKeyReleased

    private void btnThanhToanTaiQuayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanTaiQuayActionPerformed
        // TODO add your handling code here:
        if (txtMaHDTaiQuay.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để thanh toán");
        } else {
            int check = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thành toán");
            if (check == JOptionPane.YES_OPTION) {
                int i = tableHD.getSelectedRow();
                String trangThai = tableHD.getValueAt(i, 5).toString();
                if (trangThai.equals("Chờ thanh toán")) {
                    if (valiDateTaiQuay()) {
                        int maHD = Integer.parseInt(txtMaHDTaiQuay.getText());
                        String tenKH = txtTenKHTaiQuay.getText();
                        String loaiHD = "Bán tại quầy";
                        String soDienThoai = txtSDTTaiQuay.getText();
                        String maGiaoDich = " ";
                        if (!txtMaGiaoDichTQ.getText().isEmpty()) {
                            maGiaoDich = txtMaGiaoDichTQ.getText().trim();
                        }
                        Double tienMat = 0.0;
                        Double tienCK = 0.0;
                        int phuong_thuc_tt;
                        if (cboHinhThucThanhToan.getSelectedIndex() == 0) {
                            tienMat = Double.parseDouble(txtTienKhachDuaTaiQuay.getText());
                            phuong_thuc_tt = 1;
                        } else if (cboHinhThucThanhToan.getSelectedIndex() == 1) {
                            tienCK = Double.parseDouble(txtTienKhachChuyenKhoanTaiQuay.getText());
                            phuong_thuc_tt = 2;
                        } else {
                            phuong_thuc_tt = 3;
                        }
                        Double tongTien = Double.parseDouble(txtTongTienTaiQuay.getText().replaceAll(",", ""));
                        if (banHang.getIDKhachHang(soDienThoai) > 0) {
                            if (banHang.updateHoaDonTQ(Auth.user.getId(), banHang.getIDKhachHang(soDienThoai),
                                    loaiHD, tenKH, soDienThoai, tienMat, tienCK, tongTien, maHD, phuong_thuc_tt, maGiaoDich) > 0) {
                                JOptionPane.showMessageDialog(this, "Thanh toán thành công");
                                fillTableHoaDon(banHang.fillHoaDon(), -2);
                                DefaultTableModel dtm = (DefaultTableModel) tableGH.getModel();
                                dtm.setRowCount(0);
                                txtMaHDTaiQuay.setText("");
                                txtTenNVTaiQuay.setText("");
                                txtSDTTaiQuay.setText("");
                                txtTienKhachChuyenKhoanTaiQuay.setText("");
                                txtTienKhachDuaTaiQuay.setText("");
                                txtNgayTaoTaiQuay.setText("");
                                txtTenKHTaiQuay.setText("");
                                txtTienTraLaiTQ.setText("");
                                txtTongTienTaiQuay.setText("");
                                txtMaGiaoDichTQ.setText("");
                                banHang.insertLichSu(maHD, "Thanh toán thành công", Auth.user.getId());
                            } else {
                                JOptionPane.showMessageDialog(this, "Thanh toán thất bại");
                            }
                        } else {
                            if (banHang.updateHoaDonTQ2(Auth.user.getId(), loaiHD, "Khách lẻ", tienMat, tienCK, tongTien, maHD, phuong_thuc_tt, maGiaoDich) > 0) {
                                JOptionPane.showMessageDialog(this, "Thanh toán thành công");
                                fillTableHoaDon(banHang.fillHoaDon(), -2);
                                DefaultTableModel dtm = (DefaultTableModel) tableGH.getModel();
                                dtm.setRowCount(0);
                                txtMaHDTaiQuay.setText("");
                                txtTenNVTaiQuay.setText("");
                                txtSDTTaiQuay.setText("");
                                txtTienKhachChuyenKhoanTaiQuay.setText("");
                                txtTienKhachDuaTaiQuay.setText("");
                                txtNgayTaoTaiQuay.setText("");
                                txtTenKHTaiQuay.setText("");
                                txtTienTraLaiTQ.setText("");
                                txtTongTienTaiQuay.setText("");
                                txtMaGiaoDichTQ.setText("");
                                banHang.insertLichSu(maHD, "Thanh toán thành công", Auth.user.getId());
                            } else {
                                JOptionPane.showMessageDialog(this, "Thanh toán thất bại");
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Vui lòng thanh toán ở màn Đặt Hàng");
                }
            }
        }
    }//GEN-LAST:event_btnThanhToanTaiQuayActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:

//        fillTableHoaDon(banHang.fillHoaDon());
//        fillTableGioHang(banHang.getAllHDCT(WIDTH));
    }//GEN-LAST:event_formMouseClicked

    private void btnGiaoHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGiaoHangActionPerformed
        // TODO add your handling code here:
        if (txtMaHDTaiQuay.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để giao hàng");
        } else {
            if (tableGH.getRowCount() < 1) {
                JOptionPane.showMessageDialog(this, "Không thể giao đơn hàng rỗng");
            } else {
                int i = tableHD.getSelectedRow();
                String trangThai = tableHD.getValueAt(i, 5).toString();
                if (trangThai.equals("Chờ giao hàng")) {
                    if (!txtSDTDatHang.getText().isEmpty() && !txtDiaChi.getText().isEmpty() && !txtNguoiNhanDH.getText().isEmpty()) {
                        String diaChi = txtDiaChi.getText();
                        double phiShip = 0;
                        if (!txtPhiShip.getText().isEmpty()) {
                            phiShip = Double.parseDouble(txtPhiShip.getText());
                            if (phiShip < 0 || phiShip > 500000) {
                                JOptionPane.showMessageDialog(this, "Phí ship chỉ được nhỏ hơn 500000");
                                txtPhiShip.requestFocus();
                                return;
                            }
                        }
                        String nguoiNhan = txtNguoiNhanDH.getText().trim();
                        banHang.insertLichSu(Integer.parseInt(txtMaHoaDonDH.getText()), "Đơn hàng đang trên đường giao", Auth.user.getId());
                        banHang.updateTrangThaiHD(5, Integer.parseInt(txtMaHoaDonDH.getText()),
                                txtSDTDatHang.getText(), diaChi, phiShip, banHang.getIDKhachHang(txtSDTDatHang.getText()), nguoiNhan);
                        fillTableHoaDon(banHang.fillHoaDon(), i);
                        JOptionPane.showMessageDialog(this, "Đơn hàng bắt đầu được giao");
                    } else {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng và nhập tên người nhận để giao hàng ");
                        txtSDTDatHang.requestFocus();
                    }
                } else if (trangThai.equals("Đang giao")) {
                    JOptionPane.showMessageDialog(this, "Đơn hàng hiện đang được giao");
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể giao đơn hàng tại quầy");
                }
            }
            
        }
    }//GEN-LAST:event_btnGiaoHangActionPerformed

    private void btnThanhToanDHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanDHActionPerformed
        // TODO add your handling code here:
        if (txtMaHDTaiQuay.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để thanh toán");
        } else {
            int check = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thành toán");
            if (check == JOptionPane.YES_OPTION) {
                int i = tableHD.getSelectedRow();
                String trangThai = tableHD.getValueAt(i, 5).toString();
                if (trangThai.equals("Đang giao")) {
                    if (valiDateDatHang()) {
                        int maHD = Integer.parseInt(txtMaHDTaiQuay.getText());
                        String tenNguoiNhan = txtNguoiNhanDH.getText();
                        String loaiHD = "Khách đặt hàng";
                        String soDienThoai = txtSDTDatHang.getText();
                        String diaChi = txtDiaChi.getText();
                        String maGiaoDich = " ";
                        double phiShip = 0;
                        if (!txtPhiShip.getText().isEmpty()) {
                            phiShip = Double.parseDouble(txtPhiShip.getText());
                        }
                        if (!txtMaGiaoDichDH.getText().isEmpty()) {
                            maGiaoDich = txtMaGiaoDichDH.getText().trim();
                        }
                        Double tienMat = 0.0;
                        if (!txtTienMatDH.getText().isEmpty() && txtTienCKDH.getText().isEmpty() || Double.parseDouble(txtTienCKDH.getText()) == 0) {
                            tienMat = Double.parseDouble(txtTienMatDH.getText().replaceAll(",", ""));
                        }
                        Double tienCK = 0.0;
                        if (!txtTienCKDH.getText().isEmpty() && txtTienMatDH.getText().isEmpty() || Double.parseDouble(txtTienMatDH.getText()) == 0) {
                            tienCK = Double.parseDouble(txtTienCKDH.getText().replaceAll(",", ""));
                        }
                        Double tongTien = Double.parseDouble(txtTongTienDH.getText().replaceAll(",", ""));
                        int phuong_thuc_tt;
                        if (txtTienCKDH.getText().isEmpty() || tienCK == 0) {
                            phuong_thuc_tt = 1;
                        } else if (txtTienMatDH.getText().trim().isEmpty() || tienMat == 0) {
                            phuong_thuc_tt = 2;
                        } else {
                            phuong_thuc_tt = 3;
                        }
                        if (banHang.getIDKhachHang(soDienThoai) > 0) {
                            if (banHang.updateHoaDonDH(Auth.user.getId(), banHang.getIDKhachHang(soDienThoai), loaiHD,
                                    tenNguoiNhan, soDienThoai, phiShip, 1, tienMat, tienCK, tongTien, maHD, phuong_thuc_tt, diaChi, maGiaoDich) > 0) {
                                JOptionPane.showMessageDialog(this, "Thanh toán thành công");
                                fillTableHoaDon(banHang.fillHoaDon(), -2);
                                DefaultTableModel dtm = (DefaultTableModel) tableGH.getModel();
                                dtm.setRowCount(0);
                                txtMaHoaDonDH.setText("");
                                txtTenNhanVienDH.setText("");
                                txtSDTDatHang.setText("");
                                txtTienCKDH.setText("");
                                txtTienMatDH.setText("");
                                txtNguoiNhanDH.setText("");
                                txtTenKHTaiQuay.setText("");
                                txtTienTraLaiDH.setText("");
                                txtTongTienDH.setText("");
                                txtDiaChi.setText("");
                                txtTenKhachHangDH.setText("");
                                txtMaGiaoDichDH.setText("");
                                banHang.insertLichSu(maHD, "Thanh toán thành công", Auth.user.getId());
                            } else {
                                JOptionPane.showMessageDialog(this, "Thanh toán thất bại");
                            }
                        } else {
                            if (banHang.updateHoaDonDH2(Auth.user.getId(), loaiHD, "Khách lẻ", phiShip, soDienThoai, maGiaoDich, 1, tienMat, tienCK, tongTien, maHD, phuong_thuc_tt, diaChi) > 0) {
                                JOptionPane.showMessageDialog(this, "Thanh toán thành công");
                                fillTableHoaDon(banHang.fillHoaDon(), -2);
                                DefaultTableModel dtm = (DefaultTableModel) tableGH.getModel();
                                dtm.setRowCount(0);
                                txtMaHoaDonDH.setText("");
                                txtTenNhanVienDH.setText("");
                                txtSDTDatHang.setText("");
                                txtTienCKDH.setText("");
                                txtTienMatDH.setText("");
                                txtNguoiNhanDH.setText("");
                                txtTenKHTaiQuay.setText("");
                                txtTienTraLaiDH.setText("");
                                txtTongTienDH.setText("");
                                txtDiaChi.setText("");
                                txtTenKhachHangDH.setText("");
                                txtMaGiaoDichDH.setText("");
                                banHang.insertLichSu(maHD, "Thanh toán thành công", Auth.user.getId());
                            } else {
                                JOptionPane.showMessageDialog(this, "Thanh toán thất bại");
                            }
                        }
                    }
                } else if (trangThai.equals("Chờ thanh toán")) {
                    JOptionPane.showMessageDialog(this, "Vui lòng thanh toán ở màn Tại Quầy");
                } else {
                    JOptionPane.showMessageDialog(this, "Vui lòng giao hàng để thanh toán");
                }
            }
        }
    }//GEN-LAST:event_btnThanhToanDHActionPerformed

    private void txtTongTienDHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongTienDHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongTienDHActionPerformed

    private void txtTienCKDHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienCKDHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienCKDHActionPerformed

    private void txtTienTraLaiDHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienTraLaiDHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienTraLaiDHActionPerformed

    private void txtTienMatDHKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienMatDHKeyTyped
        // TODO add your handling code here:
        char key = evt.getKeyChar();
// Chỉ cho phép nhập số và backspace
        if ((key < '0' || key > '9') && (key != '\b')) {
            evt.consume(); // Bỏ qua sự kiện phím nếu không phải số hoặc backspace
        }
    }//GEN-LAST:event_txtTienMatDHKeyTyped

    private void txtTienCKDHKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienCKDHKeyTyped
        // TODO add your handling code here:
        char key = evt.getKeyChar();
// Chỉ cho phép nhập số và backspace
        if ((key < '0' || key > '9') && (key != '\b')) {
            evt.consume(); // Bỏ qua sự kiện phím nếu không phải số hoặc backspace
        }
    }//GEN-LAST:event_txtTienCKDHKeyTyped

    private void txtTienMatDHKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienMatDHKeyReleased
        if (!txtTongTienDH.getText().isEmpty()) {
            BigDecimal tongTien = BigDecimal.ZERO;
            for (int i = 0; i < tableGH.getRowCount(); i++) {
                tongTien = tongTien.add(new BigDecimal(tableGH.getValueAt(i, 5).toString().replaceAll(",", "")));
            }
            String tienMatText = txtTienMatDH.getText();
            String tienCKText = txtTienCKDH.getText();
            String phiShipText = txtPhiShip.getText();
            BigDecimal tienMat = tienMatText.isEmpty() ? BigDecimal.ZERO : new BigDecimal(tienMatText);
            BigDecimal tienCK = tienCKText.isEmpty() ? BigDecimal.ZERO : new BigDecimal(tienCKText);
            BigDecimal phiShip = phiShipText.isEmpty() ? BigDecimal.ZERO : new BigDecimal(phiShipText);
            tongTien = tongTien.add(phiShip);
            BigDecimal tienTraLai = tienMat.add(tienCK).subtract(tongTien);
//            if (tienTraLai.compareTo(BigDecimal.ZERO) > 0) {
            txtTienTraLaiDH.setText(String.format("%,.0f", tienTraLai));
//            } else {
//                txtTienTraLaiDH.setText("0");
//            }
        }
    }//GEN-LAST:event_txtTienMatDHKeyReleased

    private void txtTienCKDHKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienCKDHKeyReleased
        if (!txtTongTienDH.getText().isEmpty()) {
            BigDecimal tongTien = BigDecimal.ZERO;
            for (int i = 0; i < tableGH.getRowCount(); i++) {
                tongTien = tongTien.add(new BigDecimal(tableGH.getValueAt(i, 5).toString().replaceAll(",", "")));
            }
            String tienMatText = txtTienMatDH.getText();
            String tienCKText = txtTienCKDH.getText();
            String phiShipText = txtPhiShip.getText();
            BigDecimal tienMat = tienMatText.isEmpty() ? BigDecimal.ZERO : new BigDecimal(tienMatText);
            BigDecimal tienCK = tienCKText.isEmpty() ? BigDecimal.ZERO : new BigDecimal(tienCKText);
            BigDecimal phiShip = phiShipText.isEmpty() ? BigDecimal.ZERO : new BigDecimal(phiShipText);
            tongTien = tongTien.add(phiShip);
            BigDecimal tienTraLai = tienMat.add(tienCK).subtract(tongTien);
//            if (tienTraLai.compareTo(BigDecimal.ZERO) > 0) {
            txtTienTraLaiDH.setText(String.format("%,.0f", tienTraLai));
//            } else {
//                txtTienTraLaiDH.setText("0");
//            }
        }

    }//GEN-LAST:event_txtTienCKDHKeyReleased

    private void cboHinhThucThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHinhThucThanhToanActionPerformed
        // TODO add your handling code here:
        if (cboHinhThucThanhToan.getSelectedItem().equals("Tiền mặt")) {
            txtTienKhachDuaTaiQuay.setEditable(true);
            txtTienKhachChuyenKhoanTaiQuay.setText("0");
            txtTienKhachChuyenKhoanTaiQuay.setEditable(false);
            txtMaGiaoDichTQ.setEditable(false);
        } else if (cboHinhThucThanhToan.getSelectedItem().equals("Chuyển khoản")) {
            txtTienKhachDuaTaiQuay.setText("0");
            txtTienKhachDuaTaiQuay.setEditable(false);
            txtMaGiaoDichTQ.setEditable(true);
            txtTienKhachChuyenKhoanTaiQuay.setEditable(true);
        } else {
//            txtTienKhachDuaTaiQuay.setText("0");
//            txtTienKhachChuyenKhoanTaiQuay.setText("0");
            txtTienKhachDuaTaiQuay.setEditable(true);
            txtTienKhachChuyenKhoanTaiQuay.setEditable(true);
            txtMaGiaoDichTQ.setEditable(true);
        }
    }//GEN-LAST:event_cboHinhThucThanhToanActionPerformed

    private void txtPhiShipKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhiShipKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhiShipKeyReleased

    private void txtPhiShipKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhiShipKeyTyped
        // TODO add your handling code here:
        char key = evt.getKeyChar();
// Chỉ cho phép nhập số và backspace
        if ((key < '0' || key > '9') && (key != '\b')) {
            evt.consume(); // Bỏ qua sự kiện phím nếu không phải số hoặc backspace
        }
    }//GEN-LAST:event_txtPhiShipKeyTyped

    private void cboHinhThucTTDHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHinhThucTTDHActionPerformed
        // TODO add your handling code here:
        if (cboHinhThucTTDH.getSelectedItem().equals("Tiền mặt")) {
            txtTienMatDH.setEditable(true);
            txtTienCKDH.setText("0");
            txtTienCKDH.setEditable(false);
            txtMaGiaoDichDH.setEditable(false);
        } else if (cboHinhThucTTDH.getSelectedItem().equals("Chuyển khoản")) {
            txtTienMatDH.setText("0");
            txtTienMatDH.setEditable(false);
            txtTienCKDH.setEditable(true);
            txtMaGiaoDichDH.setEditable(true);
        } else {
//            txtTienMatDH.setText("0");
//            txtTienCKDH.setText("0");
            txtTienMatDH.setEditable(true);
            txtTienCKDH.setEditable(true);
            txtMaGiaoDichDH.setEditable(true);
        }
    }//GEN-LAST:event_cboHinhThucTTDHActionPerformed

    private void cboGiamGia1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboGiamGia1ActionPerformed
        Object selectedItem = cboGiamGia1.getSelectedItem();
        if (selectedItem != null) {
            Pattern pattern = Pattern.compile("\\((\\d+(\\.\\d+)?)(%?)\\)");
            Matcher matcher = pattern.matcher(selectedItem.toString());
            if (matcher.find()) {
                // Lấy ra chuỗi số
                String number = matcher.group(1);
                double giamGia = Double.parseDouble(number);
                double tongTien = 0;
                if (!txtTongTienDH.getText().isEmpty()) {
                    for (int i = 0; i < tableGH.getRowCount(); i++) {
                        tongTien = tongTien + Double.parseDouble(tableGH.getValueAt(i, 5).toString().replaceAll(",", ""));
                    }
                    if (selectedItem.toString().contains("%")) {
                        tongTien = tongTien - (tongTien / 100 * giamGia);
                        txtTongTienDH.setText(String.format("%,.0f", tongTien));
                    } else {
                        tongTien = tongTien - giamGia;
                        txtTongTienDH.setText(String.format("%,.0f", tongTien));
                    }
                }
            } else {
                double tongTien = 0;
                for (int i = 0; i < tableGH.getRowCount(); i++) {
                    tongTien = tongTien + Double.parseDouble(tableGH.getValueAt(i, 5).toString().replaceAll(",", ""));
                }
                txtTongTienDH.setText(String.format("%,.0f", tongTien));
            }
        }
    }//GEN-LAST:event_cboGiamGia1ActionPerformed

    private void btnKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhachHangActionPerformed
        // TODO add your handling code here:
        KhachHangJDialog kh = new KhachHangJDialog(null, true);
        kh.setVisible(true);
        kh.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                txtSDTTaiQuay.setText(ToanCuc.getSdt());
                txtTenKHTaiQuay.setText(ToanCuc.getTenKH());
                txtDiaChi.setText(ToanCuc.getDiaChi());
            }
        });
    }//GEN-LAST:event_btnKhachHangActionPerformed

    private void cboGiamGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboGiamGiaActionPerformed
        Object selectedItem = cboGiamGia.getSelectedItem();
        if (selectedItem != null) {
            Pattern pattern = Pattern.compile("\\((\\d+(\\.\\d+)?)(%?)\\)");
            Matcher matcher = pattern.matcher(selectedItem.toString());
            if (matcher.find()) {
                // Lấy ra chuỗi số
                String number = matcher.group(1);
                double giamGia = Double.parseDouble(number);
                double tongTien = 0;
                if (!txtTongTienTaiQuay.getText().isEmpty()) {
                    for (int i = 0; i < tableGH.getRowCount(); i++) {
                        tongTien = tongTien + Double.parseDouble(tableGH.getValueAt(i, 5).toString().replaceAll(",", ""));
                    }
                    if (selectedItem.toString().contains("%")) {
                        tongTien = tongTien - (tongTien / 100 * giamGia);
                        txtTongTienTaiQuay.setText(String.format("%,.0f", tongTien));
                    } else {
                        tongTien = tongTien - giamGia;
                        txtTongTienTaiQuay.setText(String.format("%,.0f", tongTien));
                    }
                }
            } else {
                double tongTien = 0;
                for (int i = 0; i < tableGH.getRowCount(); i++) {
                    tongTien = tongTien + Double.parseDouble(tableGH.getValueAt(i, 5).toString().replaceAll(",", ""));
                }
                txtTongTienTaiQuay.setText(String.format("%,.0f", tongTien));
            }
        }
    }//GEN-LAST:event_cboGiamGiaActionPerformed

    private void txtMaGiaoDichDHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaGiaoDichDHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaGiaoDichDHActionPerformed

    private void btnKhachHang1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhachHang1ActionPerformed
        // TODO add your handling code here:
        KhachHangJDialog kh = new KhachHangJDialog(null, true);
        kh.setVisible(true);
        kh.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                txtSDTDatHang.setText(ToanCuc.getSdt());
                txtTenKhachHangDH.setText(ToanCuc.getTenKH());
                txtDiaChi.setText(ToanCuc.getDiaChi());
                ToanCuc.setDiaChi("");
                ToanCuc.setSdt("");
                ToanCuc.setTenKH("");
            }
        });
    }//GEN-LAST:event_btnKhachHang1ActionPerformed

    private void txtSDTDatHangKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSDTDatHangKeyTyped
        // TODO add your handling code here:
        char key = evt.getKeyChar();
// Chỉ cho phép nhập số và backspace
        if ((key < '0' || key > '9') && (key != '\b')) {
            evt.consume(); // Bỏ qua sự kiện phím nếu không phải số hoặc backspace
        }
    }//GEN-LAST:event_txtSDTDatHangKeyTyped

    private void tabSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabSMouseClicked
        // TODO add your handling code here:
//        int tab = tabS.getSelectedIndex();
//        System.out.println(tab);
    }//GEN-LAST:event_tabSMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (tableHD.getSelectedRow() != -1) {
            ViewQRBanHang view = new ViewQRBanHang(null, true);
            view.setVisible(true);
            view.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    System.out.println("window closing");
                    int index = tableHD.getSelectedRow();
                    int maHD = Integer.parseInt(tableHD.getValueAt(index, 1).toString());
                    if (ViewQRBanHang.txtBanHang != null) {
                        banHang.addHDCT(maHD, banHang.getIDctsp(ViewQRBanHang.txtBanHang));
                        fillTableHoaDon(banHang.fillHoaDon(), index);
                        fillTableGioHang(banHang.getAllHDCT(maHD));
                    }
                    
                }
            });
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để thêm sản phẩm");
            return;
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void tableGHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableGHMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int check = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa Sản phảm này không");
            if (check == JOptionPane.YES_OPTION) {
                int maHD = Integer.parseInt(txtMaHDTaiQuay.getText());
                int row = tableGH.getSelectedRow();
                double gia = Double.parseDouble(tableGH.getValueAt(row, 3).toString().replaceAll(",", ""));
                banHang.deleteHDCT(maHD, gia);
                int index = tableHD.getSelectedRow();
                fillTableHoaDon(banHang.fillHoaDon(), index);
                fillTableGioHang(banHang.getAllHDCT(maHD));
                showData(ctsps.getAllSanPhamBH());
                cboGiamGia.setSelectedIndex(0);
                cboGiamGia1.setSelectedIndex(0);
                txtTienTraLaiDH.setText("");
                txtTienTraLaiTQ.setText("");
            }
            
        }
    }//GEN-LAST:event_tableGHMouseClicked

    private void txtNguoiNhanDHKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNguoiNhanDHKeyTyped
        // TODO add your handling code here:
        char key = evt.getKeyChar();
        if ((!Character.isLetter(key) && key != ' ') && (key != '\b')) {
            evt.consume(); // Bỏ qua sự kiện phím nếu không phải chữ, dấu cách hoặc backspace
        }
        

    }//GEN-LAST:event_txtNguoiNhanDHKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGiaoHang;
    private javax.swing.JButton btnKhachHang;
    private javax.swing.JButton btnKhachHang1;
    private javax.swing.JButton btnThanhToanDH;
    private javax.swing.JButton btnThanhToanTaiQuay;
    private javax.swing.JButton btnThem;
    private javax.swing.JComboBox<String> cboGiamGia;
    private javax.swing.JComboBox<String> cboGiamGia1;
    private javax.swing.JComboBox<String> cboHinhThucTTDH;
    private javax.swing.JComboBox<String> cboHinhThucThanhToan;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPanel tab1;
    private javax.swing.JPanel tab2;
    private javax.swing.JTabbedPane tabS;
    private javax.swing.JTable tableGH;
    private javax.swing.JTable tableHD;
    private javax.swing.JTable tableSP;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txtMaGiaoDichDH;
    private javax.swing.JTextField txtMaGiaoDichTQ;
    private javax.swing.JTextField txtMaHDTaiQuay;
    private javax.swing.JTextField txtMaHoaDonDH;
    private javax.swing.JTextField txtNgayTaoTaiQuay;
    private javax.swing.JTextField txtNguoiNhanDH;
    private javax.swing.JTextField txtPhiShip;
    private javax.swing.JTextField txtSDTDatHang;
    private javax.swing.JTextField txtSDTTaiQuay;
    private javax.swing.JTextField txtTenKHTaiQuay;
    private javax.swing.JTextField txtTenKhachHangDH;
    private javax.swing.JTextField txtTenNVTaiQuay;
    private javax.swing.JTextField txtTenNhanVienDH;
    private javax.swing.JTextField txtTienCKDH;
    private javax.swing.JTextField txtTienKhachChuyenKhoanTaiQuay;
    private javax.swing.JTextField txtTienKhachDuaTaiQuay;
    private javax.swing.JTextField txtTienMatDH;
    private javax.swing.JTextField txtTienTraLaiDH;
    private javax.swing.JTextField txtTienTraLaiTQ;
    private javax.swing.JTextField txtTongTienDH;
    private javax.swing.JTextField txtTongTienTaiQuay;
    // End of variables declaration//GEN-END:variables
}
