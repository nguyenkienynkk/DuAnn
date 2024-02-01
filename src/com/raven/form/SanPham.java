/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.form;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.raven.cell.TableActionCellEditor;
import com.raven.cell.TableActionCellRender;
import com.raven.cell.TableActionEvent;
import com.raven.form.attribute.ChatLieuDialog;
import com.raven.form.attribute.CpuDialog;
import com.raven.form.attribute.DungLuongDialog;
import com.raven.form.attribute.GpuDialog;
import com.raven.form.attribute.HeDieuHanhDialog;
import com.raven.form.attribute.ManHinhDialog;
import com.raven.form.attribute.MauSacDialog;
import com.raven.form.attribute.NhaSanXuatDialog;
import com.raven.form.attribute.RamDialog;
import com.raven.form.attribute.SanPhamDialog;
import com.raven.form.frameViewCtsp.EditChiTiet;
import com.raven.form.frameViewCtsp.OpenImei;
import com.raven.form.frameViewCtsp.QRCodeGenerator;
import com.raven.form.frameViewCtsp.ViewDialogCtsp;
import com.raven.form.frameViewCtsp.ViewQr;
import com.raven.services.ChatLieuService;
import com.raven.services.ChiTietSanPhamService;
import com.raven.services.CpuService;
import com.raven.services.DungLuongService;
import com.raven.services.GpuService;
import com.raven.services.HeDieuHanhService;
import com.raven.services.ImeiService;
import com.raven.services.ManHinhService;
import com.raven.services.MauSacService;
import com.raven.services.NhaSanXuatService;
import com.raven.services.RamService;
import com.raven.services.SanPhamService;
import com.raven.services.impl.ChatLieuServiceImpl;
import com.raven.services.impl.ChiTietSanPhamServiceImpl;
import com.raven.services.impl.CpuServiceImpl;
import com.raven.services.impl.DungLuongServiceImpl;
import com.raven.services.impl.GpuServiceImpl;
import com.raven.services.impl.HeDieuHanhServiceImpl;
import com.raven.services.impl.ImeiServiceImpl;
import com.raven.services.impl.ManHinhServiceImpl;
import com.raven.services.impl.MauSacServiceImpl;
import com.raven.services.impl.NhaSanXuatServiceImpl;
import com.raven.services.impl.RamServiceImpl;
import com.raven.services.impl.SanPhamServiceImpl;
import com.raven.viewmodels.ChatLieuResponse;
import com.raven.viewmodels.CpuResponse;
import com.raven.viewmodels.DungLuongResponse;
import com.raven.viewmodels.GpuResponse;
import com.raven.viewmodels.HeDieuHanhResponse;
import com.raven.viewmodels.ImeiResponse;
import com.raven.viewmodels.ManHinhResponse;
import com.raven.viewmodels.MauSacResponse;
import com.raven.viewmodels.NhaSanXuatResponse;
import com.raven.viewmodels.RamResponse;
import com.raven.viewmodels.SanPhamChiTietResponse;
import com.raven.viewmodels.SanPhamResponse;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import jdk.jfr.consumer.EventStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.JTextField;

/**
 *
 * @author nguye
 */
public final class SanPham extends javax.swing.JPanel {

    private DefaultComboBoxModel dcmCpu, dcmDungLuong, dcmGpu, dcmHeDieuHanh, dcmManHinh, dcmMauSac, dcmNhaSanXuat, dcmImei, dcmRam, dcmSanPham, dcmChatLieu = new DefaultComboBoxModel<>();
    private DefaultTableModel dtmctsp, dtmttsp, dtmsp, dtmtimkiem = new DefaultTableModel();
    private ChiTietSanPhamService ctsps = new ChiTietSanPhamServiceImpl();
    private CpuService cpus = new CpuServiceImpl();
    private DungLuongService dls = new DungLuongServiceImpl();
    private ImeiService ims = new ImeiServiceImpl();
    private GpuService gpus = new GpuServiceImpl();
    private HeDieuHanhService hdhs = new HeDieuHanhServiceImpl();
    private ManHinhService mhs = new ManHinhServiceImpl();
    private MauSacService mssu = new MauSacServiceImpl();
    private NhaSanXuatService nsxs = new NhaSanXuatServiceImpl();
    private RamService rams = new RamServiceImpl();
    public static SanPhamService sps = new SanPhamServiceImpl();
    private ChatLieuService cls = new ChatLieuServiceImpl();
    private static final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
    private static final String alphaUpperCase = alpha.toUpperCase(); // A-Z
    private static final String digits = "0123456789"; // 0-9
    private static final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;
    private static Random generator = new Random();
    private int page = 1;
    public int index;
    private int trangproduct = 1;
    List<SanPhamChiTietResponse> list = new ArrayList<>();
    private DefaultTableModel dtmDelete = new DefaultTableModel();
    public static String name;
    public String sp, nsx, dl, ms, hdh, gpu, ram, cpu, mh, cl, sl, trongluong, imei, gia;
    public float giaa, trongluongg;
    String qrCodeImagePath;

    public interface QRCallback {

        void getQRdata(String qrCode);
    }

    public SanPham() {
        initComponents();
        setOpaque(false);
        sps.updateStatus(0, 0);
        btnBan.setVisible(false);
        btnNgung.setVisible(false);
        dtmctsp = (DefaultTableModel) tableChiTietSanPham.getModel();
        dcmCpu = (DefaultComboBoxModel) cbbCpu.getModel();
        dcmDungLuong = (DefaultComboBoxModel) cbbDungLuong.getModel();
        dcmGpu = (DefaultComboBoxModel) cbbGpu.getModel();
        dcmHeDieuHanh = (DefaultComboBoxModel) cbbHeDieuHanh.getModel();
        dcmManHinh = (DefaultComboBoxModel) cbbManHinh.getModel();
        dcmNhaSanXuat = (DefaultComboBoxModel) cbbNhaSanXuat.getModel();
        dtmDelete = (DefaultTableModel) tableCtspDelete.getModel();
        dcmRam = (DefaultComboBoxModel) cbbRam.getModel();
        dcmSanPham = (DefaultComboBoxModel) cbbSanPham.getModel();
        dcmMauSac = (DefaultComboBoxModel) cbbMauSac.getModel();
        dcmChatLieu = (DefaultComboBoxModel) cbbChatLieu.getModel();
        dtmttsp = (DefaultTableModel) tableThuocTinh.getModel();
        dtmsp = (DefaultTableModel) tableSanPham.getModel();
        dtmtimkiem = (DefaultTableModel) tableSearchImei.getModel();
        showData(ctsps.getAll());
        showDataDelete(ctsps.getAllDelete());
        showSanPham(sps.getTenSanPham());
        loadDataCpu();
        loadDataDungLuong();
        loadDataGpu();
        loadDataHeDieuHanh();
        loadDataManHinh();
        loadDataMauSac();
        loadDataNhaSanXuat();
        loadDataRam();
        loadDataSanPham();
        loadDataChatLieu();
        loadDataSanPham();
        showChatLieu(cls.getTenChatLieu());
        showButton();
    }

//    public Product(String imei) {
//        cbbImei.addItem(imei);
//    }
//
//    public JComboBox<String> getCbbImei() {
//        return cbbImei;
//    }
    public void setTextQR(String code) {
        this.txtTimKiemQr.setText(code);
    }

    public JTextField getTesxt() {
        return txtTimKiemQr;
    }

    public static int randomNumber(int min, int max) {
        return generator.nextInt((max - min) + 1) + min;
    }

    public String randomAlphaNumeric(int numberOfCharactor) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfCharactor; i++) {
            int number = randomNumber(0, ALPHA_NUMERIC.length() - 1);
            char ch = ALPHA_NUMERIC.charAt(number);
            sb.append(ch);
        }
        return sb.toString();
    }

    public void loadDataCpu() {
        cbbCpu.removeAllItems();
        for (CpuResponse cpur : cpus.getLoaiCpu()) {
            dcmCpu.addElement(cpur);
        }
    }

    void loadDataDungLuong() {
        cbbDungLuong.removeAllItems();
        for (DungLuongResponse dlr : dls.getDungLuongS()) {
            dcmDungLuong.addElement(dlr);
        }
    }

//    public void loadDataImei() {
//        cbbImei.removeAllItems();
//        for (ImeiResponse imeis : ims.getMaImei()) {
//            dcmImei.addElement(imeis);
//        }
//    }
    void loadDataGpu() {
        cbbGpu.removeAllItems();
        for (GpuResponse gpur : gpus.getGpu()) {
            dcmGpu.addElement(gpur);
        }
    }

    void loadDataHeDieuHanh() {
        cbbHeDieuHanh.removeAllItems();
        for (HeDieuHanhResponse hdhr : hdhs.getTenHeDieuHanh()) {
            dcmHeDieuHanh.addElement(hdhr);
        }
    }

    void loadDataManHinh() {
        cbbManHinh.removeAllItems();
        for (ManHinhResponse mhr : mhs.getLoaiManHinh()) {
            dcmManHinh.addElement(mhr);
        }
    }

    void loadDataMauSac() {
        cbbMauSac.removeAllItems();
        for (MauSacResponse ur : mssu.getTenMauSac()) {
            dcmMauSac.addElement(ur);
        }
    }

    public void loadDataNhaSanXuat() {
        cbbNhaSanXuat.removeAllItems();
        for (NhaSanXuatResponse nsxr : nsxs.getTenNhaSanXuat()) {
            dcmNhaSanXuat.addElement(nsxr);
        }
    }

    void loadDataRam() {
        cbbRam.removeAllItems();
        for (RamResponse rams : rams.getDungLuongRam()) {
            dcmRam.addElement(rams);
        }
    }

    void loadDataSanPham() {
        cbbSanPham.removeAllItems();
        for (SanPhamResponse spr : sps.getTenSanPham()) {
            dcmSanPham.addElement(spr);
        }
    }

    void loadDataChatLieu() {
        cbbChatLieu.removeAllItems();
        for (ChatLieuResponse clr : cls.getTenChatLieu()) {
            dcmChatLieu.addElement(clr);
        }
    }

    public void showData(List<SanPhamChiTietResponse> list) {
        dtmctsp.setRowCount(0);
        int sllll = 0;
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        for (SanPhamChiTietResponse spct : list) {
            dtmctsp.addRow(new Object[]{
                sllll += 1,
                spct.getSanPham().getTenSanPham(),
                spct.getNhaSanXuat().getTenNhaSanXuat(),
                spct.getDungLuong().getDungLuong(),
                spct.getMauSac().getTenMauSac(),
                spct.getHeDieuHanh().getHeDieuHanh(),
                spct.getGpu().getLoaiGPU(),
                spct.getRam().getDungLuongRam(),
                spct.getCpu().getLoaiCPU(),
                spct.getManHinh().getLoaiManHinh(),
                spct.getChatLieu().getTenChatLieu(),
                spct.getSoLuong(),
                spct.getTrongLuong(),
                numberFormat.format(spct.getGia()) + " VNĐ"
            });
        }
    }

    public void showQrRender(List<SanPhamChiTietResponse> list) {
        dtmtimkiem.setRowCount(0);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        for (SanPhamChiTietResponse spct : list) {
            dtmtimkiem.addRow(new Object[]{
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
                spct.getSoLuong(),
                spct.getTrongLuong(),
                numberFormat.format(spct.getGia()) + " VNĐ"
            });
        }
    }

    public void showDataDelete(List<SanPhamChiTietResponse> list) {
        dtmDelete.setRowCount(0);
        int sll = 0;
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        for (SanPhamChiTietResponse spct : list) {
            dtmDelete.addRow(new Object[]{
                sll += 1,
                spct.getSanPham().getTenSanPham(),
                spct.getNhaSanXuat().getTenNhaSanXuat(),
                spct.getDungLuong().getDungLuong(),
                spct.getMauSac().getTenMauSac(),
                spct.getHeDieuHanh().getHeDieuHanh(),
                spct.getGpu().getLoaiGPU(),
                spct.getRam().getDungLuongRam(),
                spct.getCpu().getLoaiCPU(),
                spct.getManHinh().getLoaiManHinh(),
                spct.getChatLieu().getTenChatLieu(),
                spct.getSoLuong(),
                spct.getTrongLuong(),
                numberFormat.format(spct.getGia()) + " VNĐ"
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        tableTong = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        tab1 = new javax.swing.JPanel();
        txtTenSanPham = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        btnThemSP = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableSanPham = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        rdTatCa = new javax.swing.JRadioButton();
        rdDangBan = new javax.swing.JRadioButton();
        rdNgungBan = new javax.swing.JRadioButton();
        btnBan = new javax.swing.JButton();
        btnNgung = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        tab2 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableChiTietSanPham = new javax.swing.JTable();
        sliderGia = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        labelSlideDLRam = new javax.swing.JLabel();
        prevButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        cbbMauSac = new javax.swing.JComboBox<>();
        cbbHeDieuHanh = new javax.swing.JComboBox<>();
        btnMs = new javax.swing.JButton();
        btnHdh = new javax.swing.JButton();
        cbbCpu = new javax.swing.JComboBox<>();
        cbbManHinh = new javax.swing.JComboBox<>();
        btnCu = new javax.swing.JButton();
        btnMh = new javax.swing.JButton();
        cbbGpu = new javax.swing.JComboBox<>();
        btnNsx = new javax.swing.JButton();
        btnDl = new javax.swing.JButton();
        cbbDungLuong = new javax.swing.JComboBox<>();
        btnRm = new javax.swing.JButton();
        cbbNhaSanXuat = new javax.swing.JComboBox<>();
        cbbRam = new javax.swing.JComboBox<>();
        btnGu = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cbbChatLieu = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        btnCl = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnAddTenSP = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtTrongLuong = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        btnDowloadQR = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        cbbSanPham = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        txtGia = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        txtImei = new javax.swing.JTextField();
        tab3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        txtMaThuocTinh = new javax.swing.JTextField();
        txtTenThuocTinh = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        rdCpu = new javax.swing.JRadioButton();
        rdChatLieu = new javax.swing.JRadioButton();
        rdManHinh = new javax.swing.JRadioButton();
        rdGpu = new javax.swing.JRadioButton();
        rdMauSac = new javax.swing.JRadioButton();
        rdHeDieuHanh = new javax.swing.JRadioButton();
        rdRam = new javax.swing.JRadioButton();
        rdDungLuong = new javax.swing.JRadioButton();
        rdNhaSanXuat = new javax.swing.JRadioButton();
        jLabel21 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        btnThemTT = new javax.swing.JButton();
        btnSuaTT = new javax.swing.JButton();
        btnLamMoiTT = new javax.swing.JButton();
        btnXoaTT = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableThuocTinh = new javax.swing.JTable();
        jTextField4 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableCtspDelete = new javax.swing.JTable();
        btnDelete = new javax.swing.JButton();
        btnDelete1 = new javax.swing.JButton();
        txtTimKiemQr = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        btnQuetQr = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableSearchImei = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        tableTong.setBackground(new java.awt.Color(255, 255, 255));
        tableTong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableTongMouseClicked(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        tab1.setBackground(new java.awt.Color(255, 255, 255));

        txtTenSanPham.setForeground(new java.awt.Color(153, 153, 153));
        txtTenSanPham.setText("Tìm kiếm theo tên, mã sản phẩm, trạng thái ,số lượng");
        txtTenSanPham.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 0, 0)));
        txtTenSanPham.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTenSanPhamFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTenSanPhamFocusLost(evt);
            }
        });
        txtTenSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenSanPhamActionPerformed(evt);
            }
        });
        txtTenSanPham.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTenSanPhamKeyReleased(evt);
            }
        });

        jLabel16.setText("Tên sản phẩm:");

        btnThemSP.setBackground(new java.awt.Color(255, 0, 0));
        btnThemSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemSP.setForeground(new java.awt.Color(255, 255, 255));
        btnThemSP.setText("Thêm sản phẩm");
        btnThemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPActionPerformed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 0, 0)));

        tableSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên sản phẩm", "Mã sản phẩm", "Số lượng", "Trạng thái", "Xóa"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableSanPham.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableSanPhamMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableSanPham);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1016, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel15.setText("Trạng thái :");

        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 0, 0)));

        buttonGroup2.add(rdTatCa);
        rdTatCa.setSelected(true);
        rdTatCa.setText("Tất cả");
        rdTatCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdTatCaActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdDangBan);
        rdDangBan.setText("Đang bán");
        rdDangBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdDangBanActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdNgungBan);
        rdNgungBan.setText("Ngừng bán");
        rdNgungBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdNgungBanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(rdTatCa)
                .addGap(28, 28, 28)
                .addComponent(rdDangBan)
                .addGap(18, 18, 18)
                .addComponent(rdNgungBan)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdTatCa)
                    .addComponent(rdDangBan)
                    .addComponent(rdNgungBan))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnBan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBan.setText("Trạng thái");
        btnBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBanActionPerformed(evt);
            }
        });

        btnNgung.setBackground(new java.awt.Color(255, 0, 0));
        btnNgung.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNgung.setForeground(new java.awt.Color(255, 255, 255));
        btnNgung.setText("Trạng thái");
        btnNgung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNgungActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tab1Layout = new javax.swing.GroupLayout(tab1);
        tab1.setLayout(tab1Layout);
        tab1Layout.setHorizontalGroup(
            tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tab1Layout.createSequentialGroup()
                        .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(tab1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnThemSP)))
                        .addGap(65, 65, 65))
                    .addGroup(tab1Layout.createSequentialGroup()
                        .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(tab1Layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(tab1Layout.createSequentialGroup()
                                        .addComponent(btnBan, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnNgung))
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        tab1Layout.setVerticalGroup(
            tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNgung, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(159, 159, 159))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tableTong.addTab("Sản phẩm", jPanel2);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tab2.setBackground(new java.awt.Color(255, 255, 255));
        tab2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 0, 51)));

        txtSearch.setBackground(new Color(0,0,0,0));
        txtSearch.setForeground(new java.awt.Color(153, 153, 153));
        txtSearch.setText("Tìm kiếm theo tất cả các thuộc tính");
        txtSearch.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 0, 0)));
        txtSearch.setPreferredSize(new java.awt.Dimension(64, 20));
        txtSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSearchFocusLost(evt);
            }
        });
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        jLabel18.setText("Search :");

        tableChiTietSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên sản phẩm", "Nhà sản xuất", "Dung lượng", "Màu sắc", "Hệ điều hành", "Gpu", "Ram", "Cpu", "Màn hình", "Chất liệu", "Số lượng", "Trọng lượng", "Giá", "Hành động"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, true, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableChiTietSanPham.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tableChiTietSanPham.setRowHeight(30);
        tableChiTietSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableChiTietSanPhamMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableChiTietSanPham);
        if (tableChiTietSanPham.getColumnModel().getColumnCount() > 0) {
            tableChiTietSanPham.getColumnModel().getColumn(0).setPreferredWidth(25);
            tableChiTietSanPham.getColumnModel().getColumn(7).setPreferredWidth(35);
            tableChiTietSanPham.getColumnModel().getColumn(11).setPreferredWidth(35);
            tableChiTietSanPham.getColumnModel().getColumn(12).setPreferredWidth(35);
            tableChiTietSanPham.getColumnModel().getColumn(14).setPreferredWidth(55);
        }

        sliderGia.setBackground(new java.awt.Color(255, 51, 51));
        sliderGia.setMaximum(250000000);
        sliderGia.setMinimum(5000000);
        sliderGia.setToolTipText("");
        sliderGia.setValue(250000000);
        sliderGia.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderGiaStateChanged(evt);
            }
        });

        jLabel1.setText("Tìm theo giá:");

        prevButton.setText("<<<");
        prevButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevButtonActionPerformed(evt);
            }
        });

        nextButton.setText(">>>");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tab2Layout = new javax.swing.GroupLayout(tab2);
        tab2.setLayout(tab2Layout);
        tab2Layout.setHorizontalGroup(
            tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab2Layout.createSequentialGroup()
                .addGap(352, 352, 352)
                .addComponent(prevButton)
                .addGap(18, 18, 18)
                .addComponent(nextButton)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(tab2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tab2Layout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addContainerGap())
                    .addGroup(tab2Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 196, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sliderGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(tab2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(labelSlideDLRam, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15))))
        );
        tab2Layout.setVerticalGroup(
            tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab2Layout.createSequentialGroup()
                .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tab2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 30, Short.MAX_VALUE))
                    .addGroup(tab2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sliderGia, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelSlideDLRam)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prevButton)
                    .addComponent(nextButton))
                .addContainerGap())
        );

        cbbMauSac.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 0, 0)));
        cbbMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbMauSacActionPerformed(evt);
            }
        });

        cbbHeDieuHanh.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 0, 0)));

        btnMs.setBackground(new java.awt.Color(255, 0, 0));
        btnMs.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnMs.setForeground(new java.awt.Color(255, 255, 255));
        btnMs.setText("+");
        btnMs.setBorder(new javax.swing.border.MatteBorder(null));
        btnMs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMsActionPerformed(evt);
            }
        });

        btnHdh.setBackground(new java.awt.Color(255, 0, 0));
        btnHdh.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnHdh.setForeground(new java.awt.Color(255, 255, 255));
        btnHdh.setText("+");
        btnHdh.setBorder(new javax.swing.border.MatteBorder(null));
        btnHdh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHdhActionPerformed(evt);
            }
        });

        cbbCpu.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 0, 0)));
        cbbCpu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbCpuActionPerformed(evt);
            }
        });

        cbbManHinh.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 0, 0)));

        btnCu.setBackground(new java.awt.Color(255, 0, 0));
        btnCu.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnCu.setForeground(new java.awt.Color(255, 255, 255));
        btnCu.setText("+");
        btnCu.setBorder(new javax.swing.border.MatteBorder(null));
        btnCu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCuActionPerformed(evt);
            }
        });

        btnMh.setBackground(new java.awt.Color(255, 0, 0));
        btnMh.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnMh.setForeground(new java.awt.Color(255, 255, 255));
        btnMh.setText("+");
        btnMh.setBorder(new javax.swing.border.MatteBorder(null));
        btnMh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMhActionPerformed(evt);
            }
        });

        cbbGpu.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 0, 0)));

        btnNsx.setBackground(new java.awt.Color(255, 0, 0));
        btnNsx.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnNsx.setForeground(new java.awt.Color(255, 255, 255));
        btnNsx.setText("+");
        btnNsx.setBorder(new javax.swing.border.MatteBorder(null));
        btnNsx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNsxActionPerformed(evt);
            }
        });

        btnDl.setBackground(new java.awt.Color(255, 0, 0));
        btnDl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnDl.setForeground(new java.awt.Color(255, 255, 255));
        btnDl.setText("+");
        btnDl.setBorder(new javax.swing.border.MatteBorder(null));
        btnDl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDlActionPerformed(evt);
            }
        });

        cbbDungLuong.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 0, 0)));

        btnRm.setBackground(new java.awt.Color(255, 0, 0));
        btnRm.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnRm.setForeground(new java.awt.Color(255, 255, 255));
        btnRm.setText("+");
        btnRm.setBorder(new javax.swing.border.MatteBorder(null));
        btnRm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRmActionPerformed(evt);
            }
        });

        cbbNhaSanXuat.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 0, 0)));
        cbbNhaSanXuat.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        cbbRam.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 0, 0)));

        btnGu.setBackground(new java.awt.Color(255, 0, 0));
        btnGu.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnGu.setForeground(new java.awt.Color(255, 255, 255));
        btnGu.setText("+");
        btnGu.setBorder(new javax.swing.border.MatteBorder(null));
        btnGu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuActionPerformed(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Nhà sản xuất");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Dung lượng");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Màu sắc");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Hệ điều hành");
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Gpu");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Ram");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Cpu");
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Màn hình");
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        cbbChatLieu.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 0, 0)));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Chất liệu");
        jLabel12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnCl.setBackground(new java.awt.Color(255, 0, 0));
        btnCl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnCl.setForeground(new java.awt.Color(255, 255, 255));
        btnCl.setText("+");
        btnCl.setBorder(new javax.swing.border.MatteBorder(null));
        btnCl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 0, 0)));

        jLabel2.setText("Tên sản phẩm :");

        btnAddTenSP.setBackground(new java.awt.Color(255, 0, 0));
        btnAddTenSP.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnAddTenSP.setForeground(new java.awt.Color(255, 255, 255));
        btnAddTenSP.setText("+");
        btnAddTenSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTenSPActionPerformed(evt);
            }
        });

        jLabel13.setText("Imei :");

        jLabel14.setText("Trọng lượng :");

        txtTrongLuong.setBackground(new Color(0,0,0,0));
        txtTrongLuong.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 0, 0)));
        txtTrongLuong.setPreferredSize(new java.awt.Dimension(64, 20));
        txtTrongLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTrongLuongActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(255, 0, 0));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm");
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

        btnLamMoi.setBackground(new java.awt.Color(255, 0, 0));
        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnDowloadQR.setBackground(new java.awt.Color(255, 0, 0));
        btnDowloadQR.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDowloadQR.setForeground(new java.awt.Color(255, 255, 255));
        btnDowloadQR.setText("Dowload QR");
        btnDowloadQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDowloadQRActionPerformed(evt);
            }
        });

        btnExport.setBackground(new java.awt.Color(255, 0, 0));
        btnExport.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnExport.setForeground(new java.awt.Color(255, 255, 255));
        btnExport.setText("Export");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        cbbSanPham.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 0, 0)));
        cbbSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbSanPhamActionPerformed(evt);
            }
        });

        jLabel23.setText("Giá :");

        txtGia.setBackground(new Color(0,0,0,0));
        txtGia.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 0, 0)));
        txtGia.setPreferredSize(new java.awt.Dimension(64, 20));
        txtGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaActionPerformed(evt);
            }
        });

        jLabel24.setText("Số lượng :");

        txtSoLuong.setBackground(new Color(0,0,0,0));
        txtSoLuong.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 0, 0)));
        txtSoLuong.setEnabled(false);
        txtSoLuong.setPreferredSize(new java.awt.Dimension(64, 20));
        txtSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLuongActionPerformed(evt);
            }
        });

        txtImei.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 0, 0)));
        txtImei.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtImeiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGap(0, 1, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cbbSanPham, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtTrongLuong, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
                                    .addComponent(txtImei, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAddTenSP))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnDowloadQR, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(14, 14, 14))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAddTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbSanPham))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtImei, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtTrongLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDowloadQR, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(tab2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(1, 1, 1))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbNhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNsx, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbGpu, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGu, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRm, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbDungLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDl, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbRam, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCu, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMs, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbCpu, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbHeDieuHanh, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMh, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnHdh, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbManHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCl, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 134, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tab2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(5, 5, 5)
                            .addComponent(cbbNhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(btnNsx, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(9, 9, 9)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(5, 5, 5)
                            .addComponent(cbbGpu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(btnGu, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(5, 5, 5)
                            .addComponent(cbbHeDieuHanh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(btnHdh, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(9, 9, 9)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(5, 5, 5)
                                    .addComponent(cbbManHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(5, 5, 5)
                                    .addComponent(cbbChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(btnCl, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(9, 9, 9)
                            .addComponent(btnMh, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(153, 153, 153)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(cbbMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnMs, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(cbbCpu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnCu, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(153, 153, 153))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(cbbDungLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnDl, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(cbbRam, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnRm, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        tableTong.addTab("Chi tiết sản phẩm", jPanel1);

        tab3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Thiết lập thuộc tính"));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 0, 0)));

        txtMaThuocTinh.setEnabled(false);

        jLabel11.setText("Mã thuộc tính :");

        jLabel19.setText("Tên thuộc tính :");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 51, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Thêm thuộc tính");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenThuocTinh, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                    .addComponent(txtMaThuocTinh))
                .addGap(17, 17, 17))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addGap(27, 27, 27)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaThuocTinh)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTenThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61))
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 0, 0)));

        buttonGroup1.add(rdCpu);
        rdCpu.setText("     Cpu");
        rdCpu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdCpuActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdChatLieu);
        rdChatLieu.setSelected(true);
        rdChatLieu.setText("Chất liệu");
        rdChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdChatLieuActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdManHinh);
        rdManHinh.setText("Màn hình");
        rdManHinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdManHinhActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdGpu);
        rdGpu.setText("Gpu");
        rdGpu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdGpuActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdMauSac);
        rdMauSac.setText("Màu sắc");
        rdMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdMauSacActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdHeDieuHanh);
        rdHeDieuHanh.setText("Hệ điều hành");
        rdHeDieuHanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdHeDieuHanhActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdRam);
        rdRam.setText("      Ram");
        rdRam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdRamActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdDungLuong);
        rdDungLuong.setText("Dung lượng");
        rdDungLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdDungLuongActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdNhaSanXuat);
        rdNhaSanXuat.setText("Nhà sản xuất");
        rdNhaSanXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdNhaSanXuatActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 51, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Thay đổi thuộc tính");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(rdMauSac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(39, 39, 39)
                                .addComponent(rdNhaSanXuat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                        .addComponent(rdGpu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(58, 58, 58))
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(rdChatLieu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(34, 34, 34)))
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rdHeDieuHanh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(rdCpu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(rdManHinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(14, 14, 14))
                            .addComponent(rdDungLuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(rdRam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(20, 20, 20))))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rdDungLuong)
                        .addComponent(rdCpu))
                    .addComponent(rdChatLieu, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(36, 36, 36)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdManHinh)
                    .addComponent(rdHeDieuHanh)
                    .addComponent(rdGpu))
                .addGap(40, 40, 40)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdMauSac)
                    .addComponent(rdNhaSanXuat)
                    .addComponent(rdRam))
                .addGap(27, 27, 27))
        );

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 0, 0)));

        btnThemTT.setBackground(new java.awt.Color(255, 0, 0));
        btnThemTT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemTT.setForeground(new java.awt.Color(255, 255, 255));
        btnThemTT.setText("Thêm");
        btnThemTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemTTActionPerformed(evt);
            }
        });

        btnSuaTT.setBackground(new java.awt.Color(255, 0, 0));
        btnSuaTT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSuaTT.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaTT.setText("Sửa");
        btnSuaTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaTTActionPerformed(evt);
            }
        });

        btnLamMoiTT.setBackground(new java.awt.Color(255, 0, 0));
        btnLamMoiTT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoiTT.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoiTT.setText("Làm mới");
        btnLamMoiTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiTTActionPerformed(evt);
            }
        });

        btnXoaTT.setBackground(new java.awt.Color(255, 0, 0));
        btnXoaTT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaTT.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaTT.setText("Xóa");
        btnXoaTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnLamMoiTT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoaTT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSuaTT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThemTT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(btnThemTT)
                .addGap(18, 18, 18)
                .addComponent(btnSuaTT)
                .addGap(27, 27, 27)
                .addComponent(btnXoaTT)
                .addGap(18, 18, 18)
                .addComponent(btnLamMoiTT)
                .addGap(32, 32, 32))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách thuộc tính"));

        tableThuocTinh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã thuộc tính", "Tên thuộc tính"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableThuocTinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableThuocTinhMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableThuocTinh);

        jTextField4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 0, 0)));

        jLabel20.setText("Search");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 993, Short.MAX_VALUE)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 750, Short.MAX_VALUE))))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField4))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout tab3Layout = new javax.swing.GroupLayout(tab3);
        tab3.setLayout(tab3Layout);
        tab3Layout.setHorizontalGroup(
            tab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab3Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(tab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(37, 37, 37))
        );
        tab3Layout.setVerticalGroup(
            tab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(162, 162, 162))
        );

        tableTong.addTab("Thuộc tính", tab3);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 0, 0));
        jLabel17.setText("Các chi tiết sản phẩm đã bị xóa ");

        tableCtspDelete.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên sản phẩm", "Nhà sản xuất", "Dung lượng", "Màu sắc", "Hệ điều hành", "Gpu", "Ram", "Cpu", "Màn hình", "Chất liệu", "Số lượng", "Trọng lượng", "Giá", "Khôi phục"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tableCtspDelete.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        jScrollPane5.setViewportView(tableCtspDelete);
        if (tableCtspDelete.getColumnModel().getColumnCount() > 0) {
            tableCtspDelete.getColumnModel().getColumn(0).setPreferredWidth(25);
            tableCtspDelete.getColumnModel().getColumn(1).setPreferredWidth(100);
            tableCtspDelete.getColumnModel().getColumn(4).setPreferredWidth(60);
            tableCtspDelete.getColumnModel().getColumn(6).setPreferredWidth(40);
            tableCtspDelete.getColumnModel().getColumn(7).setPreferredWidth(40);
            tableCtspDelete.getColumnModel().getColumn(8).setPreferredWidth(40);
            tableCtspDelete.getColumnModel().getColumn(10).setPreferredWidth(55);
            tableCtspDelete.getColumnModel().getColumn(11).setPreferredWidth(30);
            tableCtspDelete.getColumnModel().getColumn(12).setPreferredWidth(30);
            tableCtspDelete.getColumnModel().getColumn(14).setPreferredWidth(40);
        }

        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/z4947981625837_4c964943c13e2f958e6d9e56b71cb5ef.jpg"))); // NOI18N
        btnDelete.setText("Khôi phục");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnDelete1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDelete1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/z4947981491985_1fb52f9679c07662b35e0c572b2942da.jpg"))); // NOI18N
        btnDelete1.setText("Export");
        btnDelete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelete1ActionPerformed(evt);
            }
        });

        txtTimKiemQr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemQrActionPerformed(evt);
            }
        });
        txtTimKiemQr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemQrKeyReleased(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Tìm kiếm :");

        btnQuetQr.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnQuetQr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/z4947982062204_cc726bd2d83818a4619a57822d9df5f0.gif"))); // NOI18N
        btnQuetQr.setText("Quét QrCode");
        btnQuetQr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuetQrActionPerformed(evt);
            }
        });

        tableSearchImei.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Tên sản phẩm", "Chất liệu", "Cpu", "Dung Lượng", "Gpu", "Hệ Điều Hành", "Màn Hình", "Màu Sắc", "Nhà sản xuất", "Ram"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableSearchImei.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableSearchImeiMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tableSearchImei);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(209, 209, 209)
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTimKiemQr, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnQuetQr)))
                        .addGap(0, 241, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnDelete1)
                                .addGap(22, 22, 22)
                                .addComponent(btnDelete))
                            .addComponent(jScrollPane5)
                            .addComponent(jScrollPane6))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(txtTimKiemQr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnQuetQr, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(168, Short.MAX_VALUE))
        );

        tableTong.addTab("Tìm kiếm và khôi phục", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tableTong, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tableTong)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNsxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNsxActionPerformed
        new NhaSanXuatDialog(null, true).setVisible(true);
        loadDataNhaSanXuat();
    }//GEN-LAST:event_btnNsxActionPerformed

    private void btnGuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuActionPerformed
        // TODO add your handling code here:
        new GpuDialog(null, true).setVisible(true);
        loadDataGpu();
    }//GEN-LAST:event_btnGuActionPerformed

    private void btnDlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDlActionPerformed
        // TODO add your handling code here:
        new DungLuongDialog(null, true).setVisible(true);
        loadDataDungLuong();
    }//GEN-LAST:event_btnDlActionPerformed

    private void btnRmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRmActionPerformed
        // TODO add your handling code here:
        new RamDialog(null, true).setVisible(true);
        loadDataRam();
    }//GEN-LAST:event_btnRmActionPerformed

    private void btnMsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMsActionPerformed
        // TODO add your handling code here:
        new MauSacDialog(null, true).setVisible(true);
        loadDataMauSac();
    }//GEN-LAST:event_btnMsActionPerformed

    private void btnCuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCuActionPerformed
        // TODO add your handling code here:
        new CpuDialog(null, true).setVisible(true);
        loadDataCpu();
    }//GEN-LAST:event_btnCuActionPerformed

    private void btnHdhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHdhActionPerformed
        // TODO add your handling code here:
        new HeDieuHanhDialog(null, true).setVisible(true);
        loadDataHeDieuHanh();
    }//GEN-LAST:event_btnHdhActionPerformed

    private void btnMhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMhActionPerformed
        // TODO add your handling code here:
        new ManHinhDialog(null, true).setVisible(true);
        loadDataManHinh();
    }//GEN-LAST:event_btnMhActionPerformed

    private void btnClActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClActionPerformed
        // TODO add your handling code here:
        new ChatLieuDialog(null, true).setVisible(true);
        loadDataChatLieu();

    }//GEN-LAST:event_btnClActionPerformed

    private void txtTrongLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTrongLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTrongLuongActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        int row = tableChiTietSanPham.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bạn phải chọn 1 dòng");
            return;
        }
        String maImei = txtImei.getText();
        int id = (int) ctsps.getAll().get(row).getId();
        int idim = ims.iiii(txtImei.getText());
        ImeiResponse immm = getFormIMEI();
        SanPhamChiTietResponse spct = getFormUpdate();
        System.out.println(id + "Id spct");
        System.out.println(idim + "Id imei");
        if (ctsps.updateNotImei(spct, id) && ims.update(immm, idim)) {
            JOptionPane.showMessageDialog(this, "Update thành công");
            showData(ctsps.getAll());
        } else {
            JOptionPane.showMessageDialog(this, "Update thất bại");
        }

    }//GEN-LAST:event_btnSuaActionPerformed

    private void cbbMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbMauSacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbMauSacActionPerformed

    private void btnDowloadQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDowloadQRActionPerformed
        // TODO add your handling code here:
        try {
            JTable table = tableChiTietSanPham;
            int[] selectedRows = table.getSelectedRows();

            if (selectedRows.length == 0) {
                JOptionPane.showMessageDialog(this, "No items selected for download.");
                return;
            }

            // Let the user choose a destination folder
            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
            fileChooser.setDialogTitle("Save QR Codes");
            fileChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);

            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
                java.io.File saveDirectory = fileChooser.getSelectedFile();
                saveDirectory.mkdirs();  // Ensure that the directory exists

                for (int selectedRow : selectedRows) {
                    int imeiColumnIndex = 0;
                    Object imeiValue = table.getValueAt(selectedRow, imeiColumnIndex);

                    if (imeiValue != null) {
                        imei = imeiValue.toString();
                        String filePath = saveDirectory.getAbsolutePath() + java.io.File.separator + "qr_" + imei + ".png";
                        QRCodeGenerator.generateQRCode(imei, filePath);
                    } else {
                        JOptionPane.showMessageDialog(this, "IMEI value is null for the selected row.");
                    }
                }

                JOptionPane.showMessageDialog(this, "QR codes downloaded to: " + saveDirectory.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnDowloadQRActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (validateThem()) {
            try {
                // Generate QR code as before
                String QrCodeData = txtImei.getText();
                String charset = "UTF-8";
                Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
                hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
                BitMatrix matrix = new MultiFormatWriter().encode(
                        new String(QrCodeData.getBytes(charset), charset),
                        BarcodeFormat.QR_CODE, 200, 200, hintMap);
                // Specify the directory where you want to load or save the QR code
                String directoryPath = "D:\\DuAn1\\sdfake - Copy\\qr code";
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save QR Codes");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int userSelection = fileChooser.showSaveDialog(this);
                String filePath;
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File saveDirectory = fileChooser.getSelectedFile();
                    saveDirectory.mkdirs();
                    filePath = saveDirectory.getAbsolutePath() + File.separator + "qr_" + QrCodeData + ".png";
                    MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath.lastIndexOf('.') + 1), new File(filePath));
                    JOptionPane.showMessageDialog(this, "QR code downloaded to: " + filePath);
                    // Open the ViewDialogCtsp and pass the QR code image path
                    ViewDialogCtsp dialog = new ViewDialogCtsp(null, true, sp, nsx, dl, ms, hdh, gpu, ram, cpu, mh, cl, sl, trongluong, imei, gia, filePath);
//                    dialog.setVisible(true);
                } else {
                    // Use the specified directory without prompting the user
                    filePath = directoryPath + File.separator + "qr_" + QrCodeData + ".png";
                    MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath.lastIndexOf('.') + 1), new File(filePath));
                    JOptionPane.showMessageDialog(this, "QR code downloaded to the default directory: " + filePath);
                    // Open the ViewDialogCtsp and pass the QR code image path
                    ViewDialogCtsp dialog = new ViewDialogCtsp(null, true, sp, nsx, dl, ms, hdh, gpu, ram, cpu, mh, cl, sl, trongluong, imei, gia, filePath);
//                    dialog.setVisible(true);
                }

            } catch (Exception e) {
                System.out.println(e);
            }

            SanPhamChiTietResponse sp = getForm();
            if (ctsps.add(sp)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                showData(ctsps.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại");
            }
        }


    }//GEN-LAST:event_btnThemActionPerformed
    public Boolean validateThem() {
        String imei = txtImei.getText();
        String giaStr = txtGia.getText();
        String trongLuongStr = txtTrongLuong.getText();

        if (imei.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn cần nhập mã imei");
            return false;
        }
        if (isImeiExisted(imei)) {
            JOptionPane.showMessageDialog(this, "Imei này đã tồn tại");
            return false;
        }

        if (imei.length() != 15 || !imei.matches("^[0-9]+$")) {
            JOptionPane.showMessageDialog(this, "Mã imei phải đúng 15 ký tự và chỉ chứa số");
            return false;
        }

        if (giaStr.isBlank()) {
            JOptionPane.showMessageDialog(this, "Bạn phải nhập giá");
            return false;
        }

        if (trongLuongStr.isBlank()) {
            JOptionPane.showMessageDialog(this, "Bạn phải nhập trọng lượng");
            return false;
        }

        try {
            float trongLuong = Float.parseFloat(trongLuongStr);
            float gia = Float.parseFloat(giaStr);

            // Kiểm tra trọng lượng
            if (trongLuong < 1 || trongLuong > 6) {
                JOptionPane.showMessageDialog(this, "Trọng lượng phải nằm trong khoảng từ 1 đến 6");
                return false;
            }

            // Kiểm tra giá
            if (gia < 200000 || gia > 500000000) {
                JOptionPane.showMessageDialog(this, "Giá phải nằm trong khoảng từ 200,000 đến 500,000,000");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá hoặc trọng lượng không hợp lệ");
            return false;
        }

        return true;
    }

    public Boolean isCheckImeiExisted(String tenThuocTinh) {
        for (ImeiResponse imr : ims.getMaImei()) {
            if (tenThuocTinh.equalsIgnoreCase(imr.getMaImei())) {
                return true;
            }
        }
        return false;
    }

    SanPhamChiTietResponse getForm() {
        String nsx = cbbNhaSanXuat.getSelectedItem().toString();
        NhaSanXuatResponse nsu = ctsps.nsx(nsx);
        String chatlieu = cbbChatLieu.getSelectedItem().toString();
        ChatLieuResponse clieu = ctsps.cl(chatlieu);
        String cpu = cbbCpu.getSelectedItem().toString();
        CpuResponse cu = ctsps.cpu(cpu);
        System.out.println(cu);
        String dungluong = cbbDungLuong.getSelectedItem().toString();
        DungLuongResponse dl = ctsps.dl(dungluong);
        System.out.println(dl);
        String gpu = cbbGpu.getSelectedItem().toString();
        GpuResponse gu = ctsps.gpu(gpu);
        System.out.println(gu);
        String hedieuhanh = cbbHeDieuHanh.getSelectedItem().toString();
        HeDieuHanhResponse hdh = ctsps.hdh(hedieuhanh);
        System.out.println(hdh);
        String imei = txtImei.getText();
        System.out.println("Mã Imei: " + imei);
        ImeiResponse im = ctsps.imei(imei);
        System.out.println("Imei sau khi add: " + im);
        String manhinh = cbbManHinh.getSelectedItem().toString();
        System.out.println("---------------" + manhinh);
        ManHinhResponse mh = ctsps.mh(manhinh);
        String mausac = cbbMauSac.getSelectedItem().toString();
        MauSacResponse ms = ctsps.ms(mausac);
        String ram = cbbRam.getSelectedItem().toString();
        System.out.println("ram1:" + ram);
        RamResponse rm = ctsps.ram(Integer.parseInt(ram));
        System.out.println("Du lieu do vao cbb:" + rm);
        String sanpham = cbbSanPham.getSelectedItem().toString();
        SanPhamResponse sp = ctsps.sp(sanpham);
        float trongLuong = Float.parseFloat(txtTrongLuong.getText());
        float gia = Float.parseFloat(txtGia.getText());
        return new SanPhamChiTietResponse(sp, rm, cu, mh, gu, im, dl, ms, hdh, nsu, clieu, trongLuong, gia);
    }

    SanPhamChiTietResponse getFormUpdate() {
        String nsx = cbbNhaSanXuat.getSelectedItem().toString();
        NhaSanXuatResponse nsu = ctsps.nsx(nsx);
        String chatlieu = cbbChatLieu.getSelectedItem().toString();
        ChatLieuResponse clieu = ctsps.cl(chatlieu);
        String cpu = cbbCpu.getSelectedItem().toString();
        CpuResponse cu = ctsps.cpu(cpu);
        System.out.println(cu);
        String dungluong = cbbDungLuong.getSelectedItem().toString();
        DungLuongResponse dl = ctsps.dl(dungluong);
        System.out.println(dl);
        String gpu = cbbGpu.getSelectedItem().toString();
        GpuResponse gu = ctsps.gpu(gpu);
        System.out.println(gu);
        String hedieuhanh = cbbHeDieuHanh.getSelectedItem().toString();
        HeDieuHanhResponse hdh = ctsps.hdh(hedieuhanh);
        System.out.println(hdh);
        String imei = txtImei.getText();
        System.out.println("Mã Imei: " + imei);
        ImeiResponse im = ims.imei(imei);
        System.out.println("Imei sau khi add: " + im);
        String manhinh = cbbManHinh.getSelectedItem().toString();
        System.out.println("---------------" + manhinh);
        ManHinhResponse mh = ctsps.mh(manhinh);
        String mausac = cbbMauSac.getSelectedItem().toString();
        MauSacResponse ms = ctsps.ms(mausac);
        String ram = cbbRam.getSelectedItem().toString();
        System.out.println("ram1:" + ram);
        RamResponse rm = ctsps.ram(Integer.parseInt(ram));
        System.out.println("Du lieu do vao cbb:" + rm);
        String sanpham = cbbSanPham.getSelectedItem().toString();
        SanPhamResponse sp = ctsps.sp(sanpham);
        float trongLuong = Float.parseFloat(txtTrongLuong.getText());
        float gia = Float.parseFloat(txtGia.getText());
        return new SanPhamChiTietResponse(sp, rm, cu, mh, gu, im, dl, ms, hdh, nsu, clieu, trongLuong, gia);
    }
    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed

    }//GEN-LAST:event_txtSearchKeyPressed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
        System.out.println("Key Released");
        String keyword = txtSearch.getText().trim(); // Không sử dụng evt.getKeyLocation()
        List<SanPhamChiTietResponse> searchResults;

        if (keyword.isEmpty()) {
            showData(ctsps.getAll());
        } else {
            searchResults = ctsps.keyPressed(keyword);
            System.out.println(keyword);
            showData(searchResults);
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void sliderGiaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderGiaStateChanged
        // TODO add your handling code here:
        int so = sliderGia.getValue();
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        String giaDaFormat = numberFormat.format(so);
        labelSlideDLRam.setText(giaDaFormat);
        List<SanPhamChiTietResponse> listres = ctsps.sliderGia(so);
        showData(listres);

    }//GEN-LAST:event_sliderGiaStateChanged

    private void txtGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaActionPerformed
    private void setSelectedIndex(JComboBox cbo, String value) {
        for (int i = 0; i < cbo.getItemCount(); i++) {
            if (cbo.getItemAt(i).toString().equals(value)) {
                cbo.setSelectedIndex(i);
                return;
            }
        }
    }
    private void tableChiTietSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableChiTietSanPhamMouseClicked

        index = tableChiTietSanPham.getSelectedRow();
        sp = tableChiTietSanPham.getValueAt(index, 1).toString();
        setSelectedIndex(cbbSanPham, sp);
        nsx = tableChiTietSanPham.getValueAt(index, 2).toString();
        setSelectedIndex(cbbNhaSanXuat, nsx);
        dl = tableChiTietSanPham.getValueAt(index, 3).toString();
        setSelectedIndex(cbbDungLuong, dl);
        ms = tableChiTietSanPham.getValueAt(index, 4).toString();
        setSelectedIndex(cbbMauSac, ms);
        hdh = tableChiTietSanPham.getValueAt(index, 5).toString();
        setSelectedIndex(cbbHeDieuHanh, hdh);
        gpu = tableChiTietSanPham.getValueAt(index, 6).toString();
        setSelectedIndex(cbbGpu, gpu);
        ram = tableChiTietSanPham.getValueAt(index, 7).toString();
        setSelectedIndex(cbbRam, ram);
        cpu = tableChiTietSanPham.getValueAt(index, 8).toString();
        setSelectedIndex(cbbCpu, cpu);
        mh = tableChiTietSanPham.getValueAt(index, 9).toString();
        setSelectedIndex(cbbManHinh, mh);
        cl = tableChiTietSanPham.getValueAt(index, 10).toString();
        setSelectedIndex(cbbChatLieu, cl);
        sl = tableChiTietSanPham.getValueAt(index, 11).toString();
        txtSoLuong.setText(sl);
        trongluong = tableChiTietSanPham.getValueAt(index, 12).toString();
        txtTrongLuong.setText(trongluong);
        imei = ctsps.getAll().get(index).getImei().getMaImei();
        txtImei.setText(imei);
        gia = tableChiTietSanPham.getValueAt(index, 13).toString();
        String giacut = gia.replaceAll("[^\\d]", "");
        int id = ims.iiii(txtImei.getText());
        System.out.println("Mousse click " + id);
        try {
            txtGia.setText(giacut);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (evt.getClickCount() == 2) {
            System.out.println(index);
            if (index != -1) {
                new OpenImei(null, true, index).setVisible(true);
            }
        }
        txtImei.setEnabled(false);

    }//GEN-LAST:event_tableChiTietSanPhamMouseClicked

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        // TODO add your handling code here:
//        this.page++;
//        if (page >= 5) {
//            page = 1;
//        }
//        list = ctsps.phanTrang(page, 5);
//        showData(list);

    }//GEN-LAST:event_nextButtonActionPerformed

    private void prevButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevButtonActionPerformed
        // TODO add your handling code here:
//        this.page--;
//        if (page <= 1) {
//            page = 5;
//        }
//        list = ctsps.phanTrang(page, 5);
//        showData(list);

    }//GEN-LAST:event_prevButtonActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        txtGia.setText("");
        txtTrongLuong.setText("");
        showData(ctsps.getAll());
        txtImei.setEnabled(true);
    }//GEN-LAST:event_btnLamMoiActionPerformed
    public void showChatLieu(List<ChatLieuResponse> listCL) {
        dtmttsp.setRowCount(0);
        for (ChatLieuResponse clr : listCL) {
            dtmttsp.addRow(new Object[]{
                clr.getId(), clr.getTenChatLieu()
            });
        }
    }

    public void showCpu(List<CpuResponse> listCpu) {
        dtmttsp.setRowCount(0);
        for (CpuResponse cpur : listCpu) {
            dtmttsp.addRow(new Object[]{
                cpur.getId(), cpur.getLoaiCPU()
            });
        }
    }

    public void showDungLuong(List<DungLuongResponse> listDL) {
        dtmttsp.setRowCount(0);
        for (DungLuongResponse dlr : listDL) {
            dtmttsp.addRow(new Object[]{
                dlr.getId(), dlr.getDungLuong()
            });
        }
    }

    public void showGpu(List<GpuResponse> listGpu) {
        dtmttsp.setRowCount(0);
        for (GpuResponse gpur : listGpu) {
            dtmttsp.addRow(new Object[]{
                gpur.getId(), gpur.getLoaiGPU()
            });
        }
    }

    public void showHeDieuHanh(List<HeDieuHanhResponse> listHDH) {
        dtmttsp.setRowCount(0);
        for (HeDieuHanhResponse hdhr : listHDH) {
            dtmttsp.addRow(new Object[]{
                hdhr.getId(), hdhr.getHeDieuHanh()
            });
        }
    }

    public void showManHinh(List<ManHinhResponse> listMH) {
        dtmttsp.setRowCount(0);
        for (ManHinhResponse mhr : listMH) {
            dtmttsp.addRow(new Object[]{
                mhr.getId(), mhr.getLoaiManHinh()
            });
        }
    }

    public void showMauSac(List<MauSacResponse> listMS) {
        dtmttsp.setRowCount(0);
        for (MauSacResponse msr : listMS) {
            dtmttsp.addRow(new Object[]{
                msr.getId(), msr.getTenMauSac()
            });
        }
    }

    public void showNhaSanXuat(List<NhaSanXuatResponse> listNSX) {
        dtmttsp.setRowCount(0);
        for (NhaSanXuatResponse nsxr : listNSX) {
            dtmttsp.addRow(new Object[]{
                nsxr.getId(), nsxr.getTenNhaSanXuat()
            });
        }
    }

    public void showRam(List<RamResponse> listRAM) {
        dtmttsp.setRowCount(0);
        for (RamResponse rr : listRAM) {
            dtmttsp.addRow(new Object[]{
                rr.getId(), rr.getDungLuongRam()
            });
        }
    }

    public void showSanPham(List<SanPhamResponse> listSanPham) {
        dtmsp.setRowCount(0);
        int soLuong = 1;
        for (SanPhamResponse spr : listSanPham) {
            dtmsp.addRow(new Object[]{
                soLuong++,
                spr.getTenSanPham(),
                spr.getIdSanPham(),
                spr.getSoLuong(),
                spr.trangThai()
            });
        }
    }


    private void rdChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdChatLieuActionPerformed
        // TODO add your handling code here:
        showChatLieu(cls.getTenChatLieu());
        txtMaThuocTinh.setText("");
        txtTenThuocTinh.setText("");
    }//GEN-LAST:event_rdChatLieuActionPerformed

    private void rdCpuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdCpuActionPerformed
        // TODO add your handling code here:
        showCpu(cpus.getLoaiCpu());
        txtMaThuocTinh.setText("");
        txtTenThuocTinh.setText("");
    }//GEN-LAST:event_rdCpuActionPerformed

    private void rdDungLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdDungLuongActionPerformed
        // TODO add your handling code here:
        showDungLuong(dls.getDungLuongS());
        txtMaThuocTinh.setText("");
        txtTenThuocTinh.setText("");
    }//GEN-LAST:event_rdDungLuongActionPerformed

    private void rdGpuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdGpuActionPerformed
        // TODO add your handling code here:
        showGpu(gpus.getGpu());
        txtMaThuocTinh.setText("");
        txtTenThuocTinh.setText("");
    }//GEN-LAST:event_rdGpuActionPerformed

    private void rdHeDieuHanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdHeDieuHanhActionPerformed
        // TODO add your handling code here:
        showHeDieuHanh(hdhs.getTenHeDieuHanh());
        txtMaThuocTinh.setText("");
        txtTenThuocTinh.setText("");
    }//GEN-LAST:event_rdHeDieuHanhActionPerformed

    private void rdManHinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdManHinhActionPerformed
        // TODO add your handling code here:
        showManHinh(mhs.getLoaiManHinh());
        txtMaThuocTinh.setText("");
        txtTenThuocTinh.setText("");
    }//GEN-LAST:event_rdManHinhActionPerformed

    private void rdMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdMauSacActionPerformed
        // TODO add your handling code here:
        showMauSac(mssu.getTenMauSac());
        txtMaThuocTinh.setText("");
        txtTenThuocTinh.setText("");
    }//GEN-LAST:event_rdMauSacActionPerformed

    private void rdNhaSanXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdNhaSanXuatActionPerformed
        // TODO add your handling code here:
        showNhaSanXuat(nsxs.getTenNhaSanXuat());
        txtMaThuocTinh.setText("");
        txtTenThuocTinh.setText("");
    }//GEN-LAST:event_rdNhaSanXuatActionPerformed

    private void rdRamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdRamActionPerformed
        // TODO add your handling code here:
        showRam(rams.getDungLuongRam());
        txtMaThuocTinh.setText("");
        txtTenThuocTinh.setText("");
    }//GEN-LAST:event_rdRamActionPerformed
    SanPhamResponse getFormSP() {
        String ten = txtTenSanPham.getText();
        return new SanPhamResponse(ten);
    }
    private void btnThemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSPActionPerformed

        tableTong.setSelectedIndex(1);
    }//GEN-LAST:event_btnThemSPActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        // TODO add your handling code here:
        try {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showSaveDialog(this);
            File saveFile = jFileChooser.getSelectedFile();
            if (saveFile != null) {
                saveFile = new File(saveFile.toString() + ".xlsx");
                Workbook wb = new XSSFWorkbook();
                Sheet sheet = wb.createSheet("Account");

                Row rowCol = sheet.createRow(0);
                for (int i = 0; i < tableChiTietSanPham.getColumnCount(); i++) {
                    Cell cell = rowCol.createCell(i);
                    cell.setCellValue(tableChiTietSanPham.getColumnName(i));
                }
                for (int j = 0; j < tableChiTietSanPham.getRowCount(); j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < tableChiTietSanPham.getColumnCount(); k++) {
                        Cell cell = row.createCell(k);
                        if (tableChiTietSanPham.getValueAt(j, k) != null) {
                            cell.setCellValue(tableChiTietSanPham.getValueAt(j, k).toString());
                        }
                    }
                }
                FileOutputStream out = new FileOutputStream(new File(saveFile.toString()));
                wb.write(out);
                wb.close();
                out.close();
                EventStream.openFile(saveFile.toPath());
                JOptionPane.showMessageDialog(this, "Export succesfully!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnExportActionPerformed

    private void txtTenSanPhamFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTenSanPhamFocusGained
        // TODO add your handling code here:
        if (txtTenSanPham.getText().equals("Tìm kiếm theo tên, mã sản phẩm, trạng thái ,số lượng")) {
            txtTenSanPham.setText("");
            setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_txtTenSanPhamFocusGained

    private void txtTenSanPhamFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTenSanPhamFocusLost
        // TODO add your handling code here:
        if (txtTenSanPham.getText().equals("")) {
            txtTenSanPham.setText("Tìm kiếm theo tên, mã sản phẩm, trạng thái ,số lượng");
            setForeground(new Color(53, 153, 153));
        }
    }//GEN-LAST:event_txtTenSanPhamFocusLost

    private void rdTatCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdTatCaActionPerformed
        // TODO add your handling code here:
        if (rdTatCa.isSelected()) {
            showSanPham(sps.getTenSanPham());
            btnBan.setVisible(false);
            btnNgung.setVisible(false);
        }
    }//GEN-LAST:event_rdTatCaActionPerformed

    private void tableSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSanPhamMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int row = tableSanPham.getSelectedRow();
            if (row != -1) {
                name = (String) tableSanPham.getValueAt(row, 1);
                System.out.println(name);
                tableTong.setSelectedIndex(1);
                showData(ctsps.getAllByName(name));
            }
        }
    }//GEN-LAST:event_tableSanPhamMouseClicked
    void showButton() {
        TableActionEvent event;
        event = new TableActionEvent() {
            @Override
            public void onDelete(int row) {
                System.out.println("row delete:" + row);
                if (row != -1) {
                    optionConfirm();
                } else {
                    // Handle the case where no row is selected
                    System.out.println("No row selected");
                }
            }

            @Override
            public void onView(int row) {
                System.out.println("View row : " + row);
                index = tableChiTietSanPham.getSelectedRow();
                sp = tableChiTietSanPham.getValueAt(index, 1).toString();
                setSelectedIndex(cbbSanPham, sp);
                nsx = tableChiTietSanPham.getValueAt(index, 2).toString();
                setSelectedIndex(cbbNhaSanXuat, nsx);
                dl = tableChiTietSanPham.getValueAt(index, 3).toString();
                setSelectedIndex(cbbDungLuong, dl);
                ms = tableChiTietSanPham.getValueAt(index, 4).toString();
                setSelectedIndex(cbbMauSac, ms);
                hdh = tableChiTietSanPham.getValueAt(index, 5).toString();
                setSelectedIndex(cbbHeDieuHanh, hdh);
                gpu = tableChiTietSanPham.getValueAt(index, 6).toString();
                setSelectedIndex(cbbGpu, gpu);
                ram = tableChiTietSanPham.getValueAt(index, 7).toString();
                setSelectedIndex(cbbRam, ram);
                cpu = tableChiTietSanPham.getValueAt(index, 8).toString();
                setSelectedIndex(cbbCpu, cpu);
                mh = tableChiTietSanPham.getValueAt(index, 9).toString();
                setSelectedIndex(cbbManHinh, mh);
                cl = tableChiTietSanPham.getValueAt(index, 10).toString();
                setSelectedIndex(cbbChatLieu, cl);
                sl = tableChiTietSanPham.getValueAt(index, 11).toString();
                txtSoLuong.setText(sl);
                trongluong = tableChiTietSanPham.getValueAt(index, 12).toString();
                txtTrongLuong.setText(trongluong);
                imei = ctsps.getAll().get(index).getImei().getMaImei();
                txtImei.setText(imei);
                gia = tableChiTietSanPham.getValueAt(index, 13).toString();
                String giacut = gia.replaceAll("[^\\d]", "");
                new ViewDialogCtsp(null, true, sp, nsx, dl, ms, hdh, gpu, ram, cpu, mh, cl, sl, trongluong, imei, gia, qrCodeImagePath).setVisible(true);

            }
        };
        tableChiTietSanPham.getColumnModel().getColumn(14).setCellRenderer(new TableActionCellRender());
        tableChiTietSanPham.getColumnModel().getColumn(14).setCellEditor(new TableActionCellEditor(event));
        tableChiTietSanPham.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
                setHorizontalAlignment(SwingConstants.RIGHT);
                return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
            }
        });
    }

    boolean optionConfirm() {
        int option = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            int row = tableChiTietSanPham.getSelectedRow();
            if (row >= 0) {
                int id = ctsps.getAll().get(row).getId();
                System.out.println(id);

                if (ctsps.delete(id)) {
                    showData(ctsps.getAll());
                    System.out.println("Delete thành công");
                    showDataDelete(ctsps.getAllDelete());
                    showSanPham(sps.getAll());
                } else {
                    System.out.println("Delete thất bại");
                }
            } else {
                // Không có hàng được chọn, hiển thị thông báo hoặc thực hiện các hành động khác
                System.out.println("Chưa chọn hàng để xóa");
            }
        } else if (option == JOptionPane.NO_OPTION) {
            // Người dùng không muốn xóa, thực hiện các hành động khác hoặc trả về giá trị
            return false;
        }

        // Người dùng chọn YES hoặc NO, thực hiện xong và trả về giá trị true
        return true;
    }

    boolean showConfirmationDialog(String message) {
        return JOptionPane.showConfirmDialog(this, message, "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    private void rdDangBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdDangBanActionPerformed
        // TODO add your handling code here:
//        showSanPham();
        if (rdDangBan.isSelected()) {
            showSanPham(sps.showSPTheoTrangThai(1));
            btnBan.setVisible(true);
            btnNgung.setVisible(false);
        }

    }//GEN-LAST:event_rdDangBanActionPerformed

    ChatLieuResponse getFormTTCL() {
        String ten = txtTenThuocTinh.getText().trim().replaceAll("\\s+", " ");
        return new ChatLieuResponse(ten);
    }

    CpuResponse getFormTTCPU() {
        String ten = txtTenThuocTinh.getText().trim().replaceAll("\\s+", " ");
        return new CpuResponse(ten);
    }

    ImeiResponse getFormIMEI() {
        String ten = txtImei.getText().trim().replaceAll("\\s+", " ");
        return new ImeiResponse(ten);
    }

    DungLuongResponse getFormTTDL() {
        String ten = txtTenThuocTinh.getText().trim().replaceAll("\\s+", " ");
        return new DungLuongResponse(ten);
    }

    GpuResponse getFormTTGPU() {
        String ten = txtTenThuocTinh.getText().trim().replaceAll("\\s+", " ");
        return new GpuResponse(ten);
    }

    HeDieuHanhResponse getFormTTHDH() {
        String ten = txtTenThuocTinh.getText().trim().replaceAll("\\s+", " ");
        return new HeDieuHanhResponse(ten);
    }

    ManHinhResponse getFormTTMH() {
        String ten = txtTenThuocTinh.getText().trim().replaceAll("\\s+", " ");
        return new ManHinhResponse(ten);
    }

    MauSacResponse getFormTTMS() {
        String ten = txtTenThuocTinh.getText().trim().replaceAll("\\s+", " ");
        return new MauSacResponse(ten);
    }

    NhaSanXuatResponse getFormTTNSX() {
        String ten = txtTenThuocTinh.getText().trim().replaceAll("\\s+", " ");
        return new NhaSanXuatResponse(ten);
    }

    RamResponse getFormTTRAM() {
        String ten = txtTenThuocTinh.getText().trim().replaceAll("\\s+", " ");
        return new RamResponse(ten);
    }


    private void btnThemTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemTTActionPerformed
        // TODO add your handling code here:
        String ten;
        if (rdChatLieu.isSelected()) {
            if (validateTT()) {
                ten = txtTenThuocTinh.getText();
                if (isChatLieuExisted(ten)) {
                    JOptionPane.showMessageDialog(this, "Tên thuộc tính đã tồn tại");
                    return;
                }
                if (JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm không ?") == 0) {
                    ChatLieuResponse clr = getFormTTCL();
                    if (cls.add(clr)) {
                        JOptionPane.showMessageDialog(this, "Thêm thành công");
                        showChatLieu(cls.getTenChatLieu());
                        loadDataChatLieu();
                        txtTenThuocTinh.setText("");
                    } else {
                        JOptionPane.showMessageDialog(this, "Thêm thất bại");
                    }
                }
            }
        } else if (rdCpu.isSelected()) {
            if (validateTT()) {
                ten = txtTenThuocTinh.getText();
                if (isCpuExisted(ten)) {
                    JOptionPane.showMessageDialog(this, "Tên thuộc tính đã tồn tại");
                    return;
                }
                if (JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm không ?") == 0) {
                    CpuResponse cpur = getFormTTCPU();
                    if (cpus.add(cpur)) {
                        JOptionPane.showMessageDialog(this, "Thêm thành công");
                        showCpu(cpus.getLoaiCpu());
                        txtTenThuocTinh.setText("");
                        loadDataCpu();
                    } else {
                        JOptionPane.showMessageDialog(this, "Thêm thất bại");
                    }
                }
            }
        } else if (rdDungLuong.isSelected()) {
            if (validateTT()) {
                ten = txtTenThuocTinh.getText();
                if (isDungLuongExisted(ten)) {
                    JOptionPane.showMessageDialog(this, "Tên thuộc tính đã tồn tại");
                    return;
                }
                if (JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm không ?") == 0) {
                    DungLuongResponse dlr = getFormTTDL();
                    if (dls.add(dlr)) {
                        JOptionPane.showMessageDialog(this, "Thêm thành công");
                        showDungLuong(dls.getDungLuongS());
                        txtTenThuocTinh.setText("");
                        loadDataDungLuong();
                    } else {
                        JOptionPane.showMessageDialog(this, "Thêm thất bại");
                    }
                }
            }
        } else if (rdGpu.isSelected()) {
            if (validateTT()) {
                ten = txtTenThuocTinh.getText();
                if (isGpuExisted(ten)) {
                    JOptionPane.showMessageDialog(this, "Tên thuộc tính đã tồn tại");
                    return;
                }
                if (JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm không ?") == 0) {
                    GpuResponse gpur = getFormTTGPU();
                    if (gpus.add(gpur)) {
                        JOptionPane.showMessageDialog(this, "Thêm thành công");
                        showGpu(gpus.getGpu());
                        txtTenThuocTinh.setText("");
                        loadDataGpu();
                    } else {
                        JOptionPane.showMessageDialog(this, "Thêm thất bại");
                    }
                }
            }
        } else if (rdHeDieuHanh.isSelected()) {
            if (validateTT()) {
                ten = txtTenThuocTinh.getText();
                if (isHeDieuHanhExisted(ten)) {
                    JOptionPane.showMessageDialog(this, "Tên thuộc tính đã tồn tại");
                    return;
                }
                if (JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm không ?") == 0) {
                    HeDieuHanhResponse hdhr = getFormTTHDH();
                    if (hdhs.add(hdhr)) {
                        JOptionPane.showMessageDialog(this, "Thêm thành công");
                        showHeDieuHanh(hdhs.getTenHeDieuHanh());
                        txtTenThuocTinh.setText("");
                        loadDataHeDieuHanh();
                    } else {
                        JOptionPane.showMessageDialog(this, "Thêm thất bại");
                    }
                }
            }
        } else if (rdManHinh.isSelected()) {
            if (validateTT()) {
                ten = txtTenThuocTinh.getText();
                if (isManHinhExisted(ten)) {
                    JOptionPane.showMessageDialog(this, "Tên thuộc tính đã tồn tại");
                    return;
                }
                if (JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm không ?") == 0) {
                    ManHinhResponse mhr = getFormTTMH();
                    if (mhs.add(mhr)) {
                        JOptionPane.showMessageDialog(this, "Thêm thành công");
                        showManHinh(mhs.getLoaiManHinh());
                        txtTenThuocTinh.setText("");
                        loadDataManHinh();
                    } else {
                        JOptionPane.showMessageDialog(this, "Thêm thất bại");
                    }
                }
            }
        } else if (rdMauSac.isSelected()) {
            if (validateTT()) {
                ten = txtTenThuocTinh.getText();
                if (isMauSacExisted(ten)) {
                    JOptionPane.showMessageDialog(this, "Tên thuộc tính đã tồn tại");
                    return;
                }
                if (JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm không ?") == 0) {
                    MauSacResponse msr = getFormTTMS();
                    if (mssu.add(msr)) {
                        JOptionPane.showMessageDialog(this, "Thêm thành công");
                        showMauSac(mssu.getTenMauSac());
                        txtTenThuocTinh.setText("");
                        loadDataMauSac();
                    } else {
                        JOptionPane.showMessageDialog(this, "Thêm thất bại");
                    }
                }
            }
        } else if (rdNhaSanXuat.isSelected()) {
            if (validateTT()) {
                ten = txtTenThuocTinh.getText();
                if (isNhaSanXuatExisted(ten)) {
                    JOptionPane.showMessageDialog(this, "Tên thuộc tính đã tồn tại");
                    return;
                }
                if (JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm không ?") == 0) {
                    NhaSanXuatResponse nsxr = getFormTTNSX();
                    if (nsxs.add(nsxr)) {
                        JOptionPane.showMessageDialog(this, "Thêm thành công");
                        showNhaSanXuat(nsxs.getTenNhaSanXuat());
                        txtTenThuocTinh.setText("");
                        loadDataNhaSanXuat();
                    } else {
                        JOptionPane.showMessageDialog(this, "Thêm thất bại");
                    }
                }
            }
        } else if (rdRam.isSelected()) {
            if (validateTT()) {
                ten = txtTenThuocTinh.getText();
                if (isRamExisted(ten)) {
                    JOptionPane.showMessageDialog(this, "Tên thuộc tính đã tồn tại");
                    return;
                }
                if (JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm không ?") == 0) {
                    RamResponse rr = getFormTTRAM();
                    if (rams.add(rr)) {
                        JOptionPane.showMessageDialog(this, "Thêm thành công");
                        showRam(rams.getDungLuongRam());
                        txtTenThuocTinh.setText("");
                        loadDataRam();
                    } else {
                        JOptionPane.showMessageDialog(this, "Thêm thất bại");
                    }
                }
            }
        }
    }//GEN-LAST:event_btnThemTTActionPerformed


    private void btnLamMoiTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiTTActionPerformed
        // TODO add your handling code here:
        txtTenThuocTinh.setText("");
        txtMaThuocTinh.setText("");
    }//GEN-LAST:event_btnLamMoiTTActionPerformed
    void setFormTTCL(ChatLieuResponse clr) {
        txtMaThuocTinh.setText(clr.getId() + "");
        txtTenThuocTinh.setText(clr.getTenChatLieu());
    }

    void setFormTTCPU(CpuResponse cpur) {
        txtMaThuocTinh.setText(cpur.getId() + "");
        txtTenThuocTinh.setText(cpur.getLoaiCPU());
    }

    void setFormTTDL(DungLuongResponse dlr) {
        txtMaThuocTinh.setText(dlr.getId() + "");
        txtTenThuocTinh.setText(dlr.getDungLuong());
    }

    void setFormTTGPU(GpuResponse gpur) {
        txtMaThuocTinh.setText(gpur.getId() + "");
        txtTenThuocTinh.setText(gpur.getLoaiGPU());
    }

    void setFormTTHDH(HeDieuHanhResponse hdhr) {
        txtMaThuocTinh.setText(hdhr.getId() + "");
        txtTenThuocTinh.setText(hdhr.getHeDieuHanh());
    }

    void setFormTTMH(ManHinhResponse mhr) {
        txtMaThuocTinh.setText(mhr.getId() + "");
        txtTenThuocTinh.setText(mhr.getLoaiManHinh());
    }

    void setFormTTMS(MauSacResponse msr) {
        txtMaThuocTinh.setText(msr.getId() + "");
        txtTenThuocTinh.setText(msr.getTenMauSac());
    }

    void setFormTTNSX(NhaSanXuatResponse nsxr) {
        txtMaThuocTinh.setText(nsxr.getId() + "");
        txtTenThuocTinh.setText(nsxr.getTenNhaSanXuat());
    }

    void setFormTTRAM(RamResponse ramr) {
        txtMaThuocTinh.setText(ramr.getId() + "");
        txtTenThuocTinh.setText(ramr.getDungLuongRam());
    }

    private void tableThuocTinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableThuocTinhMouseClicked
        // TODO add your handling code here:
        int row = tableThuocTinh.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bạn phải chọn 1 dòng");
            return;
        }
        if (rdChatLieu.isSelected()) {
            ChatLieuResponse cl = cls.getAll().get(row);
            setFormTTCL(cl);
        } else if (rdCpu.isSelected()) {
            CpuResponse cpu = cpus.getAll().get(row);
            setFormTTCPU(cpu);
        } else if (rdDungLuong.isSelected()) {
            DungLuongResponse dl = dls.getAll().get(row);
            setFormTTDL(dl);
        } else if (rdGpu.isSelected()) {
            GpuResponse gpu = gpus.getAll().get(row);
            setFormTTGPU(gpu);
        } else if (rdHeDieuHanh.isSelected()) {
            HeDieuHanhResponse hdh = hdhs.getAll().get(row);
            setFormTTHDH(hdh);
        } else if (rdManHinh.isSelected()) {
            ManHinhResponse mh = mhs.getAll().get(row);
            setFormTTMH(mh);
        } else if (rdMauSac.isSelected()) {
            MauSacResponse ms = mssu.getAll().get(row);
            setFormTTMS(ms);
        } else if (rdNhaSanXuat.isSelected()) {
            NhaSanXuatResponse nsx = nsxs.getAll().get(row);
            setFormTTNSX(nsx);
        } else if (rdRam.isSelected()) {
            RamResponse ram = rams.getAll().get(row);
            setFormTTRAM(ram);
        }
        int id = cls.getAll().get(index).getId();
        System.out.println(id);
    }//GEN-LAST:event_tableThuocTinhMouseClicked

    private void btnSuaTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaTTActionPerformed
        // TODO add your handling code here:
        int row = tableThuocTinh.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bạn phải chọn 1 dòng");
            return;
        }
        if (rdChatLieu.isSelected()) {
            for (ChatLieuResponse dl : cls.getAll()) {
                if (!dl.getTenChatLieu().equals(txtTenThuocTinh.getText()) && String.valueOf(dl.getId()).equals(txtMaThuocTinh.getText())) {
                    JOptionPane.showMessageDialog(this, "Tên chất liệu này đã tồn tại bạn có thể sử dụng lại");
                    return;
                }
            }
            if (validateTT()) {
                   int ma = (int) tableThuocTinh.getValueAt(row, 0);
                ChatLieuResponse kh = getFormTTCL();
                if (cls.update(kh, ma)) {
                    JOptionPane.showMessageDialog(this, "Thành công");
                    showChatLieu(cls.getAll());
                    loadDataChatLieu();
                } else {
                    JOptionPane.showMessageDialog(this, "Thất bại");
                }
            }
        } else if (rdCpu.isSelected()) {
            for (CpuResponse dl : cpus.getAll()) {
                if (!dl.getLoaiCPU().equals(txtTenThuocTinh.getText()) && String.valueOf(dl.getId()).equals(txtMaThuocTinh.getText())) {
                    JOptionPane.showMessageDialog(this, "Tên cpu này đã tồn tại bạn có thể sử dụng lại");
                    return;
                }
            }
            if (validateTT()) {
                int ma = (int) tableThuocTinh.getValueAt(row, 0);
                CpuResponse kh = getFormTTCPU();
                if (cpus.update(kh, ma)) {
                    JOptionPane.showMessageDialog(this, "Thành công");
                    showCpu(cpus.getAll());
                    loadDataCpu();
                } else {
                    JOptionPane.showMessageDialog(this, "Thất bại");
                }
            }
        } else if (rdDungLuong.isSelected()) {
            for (DungLuongResponse dl : dls.getAll()) {
                if (!dl.getDungLuong().equals(txtTenThuocTinh.getText()) && String.valueOf(dl.getId()).equals(txtMaThuocTinh.getText())) {
                    JOptionPane.showMessageDialog(this, "Tên dung lượng này đã tồn tại bạn có thể sử dụng lại");
                    return;
                }
            }
            if (validateTT()) {
                int ma = (int) tableThuocTinh.getValueAt(row, 0);
                DungLuongResponse kh = getFormTTDL();
                if (dls.update(kh, ma)) {
                    JOptionPane.showMessageDialog(this, "Thành công");
                    showDungLuong(dls.getAll());
                    loadDataDungLuong();
                } else {
                    JOptionPane.showMessageDialog(this, "Thất bại");
                }
            }
        } else if (rdGpu.isSelected()) {
            for (GpuResponse dl : gpus.getAll()) {
                if (!dl.getLoaiGPU().equals(txtTenThuocTinh.getText()) && String.valueOf(dl.getId()).equals(txtMaThuocTinh.getText())) {
                    JOptionPane.showMessageDialog(this, "Tên gpu này đã tồn tại bạn có thể sử dụng lại");
                    return;
                }
            }
            if (validateTT()) {
                int ma = (int) tableThuocTinh.getValueAt(row, 0);
                GpuResponse kh = getFormTTGPU();
                if (gpus.update(kh, ma)) {
                    JOptionPane.showMessageDialog(this, "Thành công");
                    showGpu(gpus.getAll());
                    loadDataGpu();
                } else {
                    JOptionPane.showMessageDialog(this, "Thất bại");
                }
            }
        } else if (rdHeDieuHanh.isSelected()) {
            for (HeDieuHanhResponse hdh : hdhs.getAll()) {
                if (!hdh.getHeDieuHanh().equals(txtTenThuocTinh.getText()) && String.valueOf(hdh.getId()).equals(txtMaThuocTinh.getText())) {
                    JOptionPane.showMessageDialog(this, "Tên hệ điều hành này đã tồn tại bạn có thể sử dụng lại");
                    return;
                }
            }
            if (validateTT()) {
                int ma = (int) tableThuocTinh.getValueAt(row, 0);
                HeDieuHanhResponse kh = getFormTTHDH();
                if (hdhs.update(kh, ma)) {
                    JOptionPane.showMessageDialog(this, "Thành công");
                    showHeDieuHanh(hdhs.getAll());
                    loadDataHeDieuHanh();
                } else {
                    JOptionPane.showMessageDialog(this, "Thất bại");
                }
            }
        } else if (rdManHinh.isSelected()) {
            for (ManHinhResponse dl : mhs.getAll()) {
                if (!dl.getLoaiManHinh().equals(txtTenThuocTinh.getText()) && String.valueOf(dl.getId()).equals(txtMaThuocTinh.getText())) {
                    JOptionPane.showMessageDialog(this, "Tên màn hình này đã tồn tại bạn có thể sử dụng lại");
                    return;
                }
            }
            if (validateTT()) {
                int ma = (int) tableThuocTinh.getValueAt(row, 0);
                ManHinhResponse kh = getFormTTMH();
                if (mhs.update(kh, ma)) {
                    JOptionPane.showMessageDialog(this, "Thành công");
                    showManHinh(mhs.getAll());
                    loadDataManHinh();
                } else {
                    JOptionPane.showMessageDialog(this, "Thất bại");
                }
            }
        } else if (rdMauSac.isSelected()) {
            for (MauSacResponse dl : mssu.getAll()) {
                if (!dl.getTenMauSac().equals(txtTenThuocTinh.getText()) && String.valueOf(dl.getId()).equals(txtMaThuocTinh.getText())) {
                    JOptionPane.showMessageDialog(this, "Tên màu sắc này đã tồn tại bạn có thể sử dụng lại");
                    return;
                }
            }
            if (validateTT()) {
                int ma = (int) tableThuocTinh.getValueAt(row, 0);
                MauSacResponse kh = getFormTTMS();
                if (mssu.update(kh, ma)) {
                    JOptionPane.showMessageDialog(this, "Thành công");
                    showMauSac(mssu.getAll());
                    loadDataMauSac();
                } else {
                    JOptionPane.showMessageDialog(this, "Thất bại");
                }
            }
        } else if (rdNhaSanXuat.isSelected()) {
            for (NhaSanXuatResponse dl : nsxs.getAll()) {
                if (!dl.getTenNhaSanXuat().equals(txtTenThuocTinh.getText()) && String.valueOf(dl.getId()).equals(txtMaThuocTinh.getText())) {
                    JOptionPane.showMessageDialog(this, "Tên nhà sản xuất này đã tồn tại bạn có thể sử dụng lại");
                    return;
                }
            }
            if (validateTT()) {
                int ma = (int) tableThuocTinh.getValueAt(row, 0);
                NhaSanXuatResponse kh = getFormTTNSX();
                if (nsxs.update(kh, ma)) {
                    JOptionPane.showMessageDialog(this, "Thành công");
                    showNhaSanXuat(nsxs.getAll());
                    loadDataNhaSanXuat();
                } else {
                    JOptionPane.showMessageDialog(this, "Thất bại");
                }
            }
        } else if (rdRam.isSelected()) {
            for (RamResponse dl : rams.getAll()) {
                if (!dl.getDungLuongRam().equals(txtTenThuocTinh.getText()) && String.valueOf(dl.getId()).equals(txtMaThuocTinh.getText())) {
                    JOptionPane.showMessageDialog(this, "Tên ram này đã tồn tại bạn có thể sử dụng lại");
                    return;
                }
            }
            if (validateTT()) {
                int ma = (int) tableThuocTinh.getValueAt(row, 0);
                RamResponse kh = getFormTTRAM();
                if (rams.update(kh, ma)) {
                    JOptionPane.showMessageDialog(this, "Thành công");
                    showRam(rams.getAll());
                    loadDataRam();
                } else {
                    JOptionPane.showMessageDialog(this, "Thất bại");
                }
            }
        }

    }//GEN-LAST:event_btnSuaTTActionPerformed

    private void btnXoaTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTTActionPerformed
        // TODO add your handling code here:
        int row = tableThuocTinh.getSelectedRow();
        if (rdChatLieu.isSelected()) {
            if (JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không ?") == 0) {
                if (row == -1) {
                    JOptionPane.showMessageDialog(this, "Chọn dòng để xóa");
                    return;
                }
                int id = cls.getAll().get(row).getId();
                if (cls.delete(id)) {
                    JOptionPane.showMessageDialog(this, "Thành công");
                    showChatLieu(cls.getAll());
                    loadDataChatLieu();
                    txtTenThuocTinh.setText("");
                    txtMaThuocTinh.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Thất bại");
                }
            }
        } else if (rdCpu.isSelected()) {
            if (JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không ?") == 0) {
                if (row == -1) {
                    JOptionPane.showMessageDialog(this, "Chọn dòng để xóa");
                    return;
                }
                int id = cpus.getAll().get(row).getId();
                if (cpus.delete(id)) {
                    JOptionPane.showMessageDialog(this, "Thành công");
                    loadDataCpu();
                    showCpu(cpus.getAll());
                    txtTenThuocTinh.setText("");
                    txtMaThuocTinh.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Thất bại");
                }
            }
        } else if (rdDungLuong.isSelected()) {
            if (JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không ?") == 0) {
                if (row == -1) {
                    JOptionPane.showMessageDialog(this, "Chọn dòng để xóa");
                    return;
                }
                int id = dls.getAll().get(row).getId();
                if (dls.delete(id)) {
                    JOptionPane.showMessageDialog(this, "Thành công");
                    loadDataDungLuong();
                    showDungLuong(dls.getAll());
                    txtTenThuocTinh.setText("");
                    txtMaThuocTinh.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Thất bại");
                }
            }
        } else if (rdGpu.isSelected()) {
            if (JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không ?") == 0) {
                if (row == -1) {
                    JOptionPane.showMessageDialog(this, "Chọn dòng để xóa");
                    return;
                }
                int id = gpus.getAll().get(row).getId();
                if (gpus.delete(id)) {
                    JOptionPane.showMessageDialog(this, "Thành công");
                    loadDataGpu();
                    showGpu(gpus.getAll());
                    txtTenThuocTinh.setText("");
                    txtMaThuocTinh.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Thất bại");
                }
            }
        } else if (rdHeDieuHanh.isSelected()) {
            if (JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không ?") == 0) {
                if (row == -1) {
                    JOptionPane.showMessageDialog(this, "Chọn dòng để xóa");
                    return;
                }
                int id = hdhs.getAll().get(row).getId();
                if (hdhs.delete(id)) {
                    JOptionPane.showMessageDialog(this, "Thành công");
                    loadDataHeDieuHanh();
                    showHeDieuHanh(hdhs.getAll());
                    txtTenThuocTinh.setText("");
                    txtMaThuocTinh.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Thất bại");
                }
            }
        } else if (rdManHinh.isSelected()) {
            if (JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không ?") == 0) {
                if (row == -1) {
                    JOptionPane.showMessageDialog(this, "Chọn dòng để xóa");
                    return;
                }
                int id = mhs.getAll().get(row).getId();
                if (mhs.delete(id)) {
                    JOptionPane.showMessageDialog(this, "Thành công");
                    loadDataManHinh();
                    showManHinh(mhs.getAll());
                    txtTenThuocTinh.setText("");
                    txtMaThuocTinh.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Thất bại");
                }
            }
        } else if (rdMauSac.isSelected()) {
            if (JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không ?") == 0) {
                if (row == -1) {
                    JOptionPane.showMessageDialog(this, "Chọn dòng để xóa");
                    return;
                }
                int id = mssu.getAll().get(row).getId();
                if (mssu.delete(id)) {
                    JOptionPane.showMessageDialog(this, "Thành công");
                    loadDataMauSac();
                    showMauSac(mssu.getAll());
                    txtTenThuocTinh.setText("");
                    txtMaThuocTinh.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Thất bại");
                }
            }
        } else if (rdNhaSanXuat.isSelected()) {
            if (JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không ?") == 0) {
                if (row == -1) {
                    JOptionPane.showMessageDialog(this, "Chọn dòng để xóa");
                    return;
                }
                int id = nsxs.getAll().get(row).getId();
                if (nsxs.delete(id)) {
                    JOptionPane.showMessageDialog(this, "Thành công");
                    loadDataNhaSanXuat();
                    showNhaSanXuat(nsxs.getAll());
                    txtTenThuocTinh.setText("");
                    txtMaThuocTinh.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Thất bại");
                }
            }
        } else if (rdRam.isSelected()) {
            if (JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không ?") == 0) {
                if (row == -1) {
                    JOptionPane.showMessageDialog(this, "Chọn dòng để xóa");
                    return;
                }
                int id = rams.getAll().get(row).getId();
                if (rams.delete(id)) {
                    JOptionPane.showMessageDialog(this, "Thành công");
                    loadDataRam();
                    showRam(rams.getAll());
                    txtTenThuocTinh.setText("");
                    txtMaThuocTinh.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Thất bại");
                }
            }
        }

    }//GEN-LAST:event_btnXoaTTActionPerformed

    private void rdNgungBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdNgungBanActionPerformed
        // TODO add your handling code here:
        if (rdNgungBan.isSelected()) {
            showSanPham(sps.showSPTheoTrangThai(0));
            btnNgung.setVisible(true);
            btnBan.setVisible(false);
        }
    }//GEN-LAST:event_rdNgungBanActionPerformed

    private void btnAddTenSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTenSPActionPerformed
        // TODO add your handling code here:
        new SanPhamDialog(null, true).setVisible(true);
        loadDataSanPham();
        showSanPham(sps.getTenSanPham());

    }//GEN-LAST:event_btnAddTenSPActionPerformed

    private void cbbSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbSanPhamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbSanPhamActionPerformed

    private void txtTenSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenSanPhamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenSanPhamActionPerformed

    private void txtSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchFocusGained
        // TODO add your handling code here:
        if (txtSearch.getText().equals("Tìm kiếm theo tất cả các thuộc tính")) {
            txtSearch.setText("");
            setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_txtSearchFocusGained

    private void txtSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchFocusLost
        // TODO add your handling code here:
        if (txtSearch.getText().equals("")) {
            txtSearch.setText("Tìm kiếm theo tất cả các thuộc tính");
            setForeground(new Color(53, 153, 153));
        }
    }//GEN-LAST:event_txtSearchFocusLost

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtImeiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtImeiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtImeiActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Bạn cần chọn 1 dòng để khôi phục chi tiết sản phẩm này");
            return;
        }
        int columnIndexToDelete = 14;
        List<Integer> selectedIds = new ArrayList<>();
        for (int i = dtmDelete.getRowCount() - 1; i >= 0; i--) {
            Boolean isChecked = (Boolean) dtmDelete.getValueAt(i, columnIndexToDelete);
            if (isChecked != null && isChecked) {
                int id = ctsps.getAllDelete().get(i).getId();
                System.out.println(id);
                selectedIds.add(id);
            }
        }
        if (!selectedIds.isEmpty() && JOptionPane.showConfirmDialog(this, "Bạn có muốn khôi phục chi tiết sản phẩm không ?") == 0) {
            for (int id : selectedIds) {
                if (ctsps.restore(id)) {
                    // Xử lý khi khôi phục thành công
                    System.out.println(ctsps.restore(id));
                    showDataDelete(ctsps.getAllDelete());
                } else {
                    JOptionPane.showMessageDialog(this, "Khôi phục thất bại");
                }
            }
            JOptionPane.showMessageDialog(this, "Khôi phục thành công");
            showData(ctsps.getAll());
//            showData(ctsps.getAllByName(name));
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnNgungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNgungActionPerformed
        // TODO add your handling code here:
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Bạn cần chọn 1 dòng kinh doanh lại sản phẩm này");
            return;
        }
        int columnIndexToDelete = 5;
        List<Integer> selectedIds = new ArrayList<>();
        for (int i = dtmsp.getRowCount() - 1; i >= 0; i--) {
            Boolean isChecked = (Boolean) dtmsp.getValueAt(i, columnIndexToDelete);
            System.out.println("Row " + i + ", isChecked: " + isChecked);

            if (isChecked != null && isChecked) {
                int id = sps.showSPTheoTrangThai(0).get(i).getIdSanPham();
                System.out.println("Restoring ID: " + id);
                selectedIds.add(id);
            }
        }

        if (!selectedIds.isEmpty() && JOptionPane.showConfirmDialog(this, "Bạn có muốn khôi phục chi tiết sản phẩm không ?") == 0) {
            for (int id : selectedIds) {
                if (sps.restore(id)) {
                    System.out.println(sps.restore(id));
                    sps.updateStatus(1, 1);
                    showSanPham(sps.showSPTheoTrangThai(0));
                } else {
                    JOptionPane.showMessageDialog(this, "Khôi phục thất bại");
                }
            }
            JOptionPane.showMessageDialog(this, "Khôi phục thành công");
        }
        showData(ctsps.getAll());
        showDataDelete(ctsps.getAllDelete());


    }//GEN-LAST:event_btnNgungActionPerformed

    private void btnBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBanActionPerformed
        // TODO add your handling code here:
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Bạn cần chọn 1 dòng để ngừng bán sản phẩm này");
            return;
        }
        int columnIndexToDelete = 5;
        List<Integer> selectedIds = new ArrayList<>();
        for (int i = dtmsp.getRowCount() - 1; i >= 0; i--) {
            Boolean isChecked = (Boolean) dtmsp.getValueAt(i, columnIndexToDelete);
            if (isChecked != null && isChecked) {
                int id = (int) dtmsp.getValueAt(i, 2);
                System.out.println(id);
                selectedIds.add(id);
            }
        }
        if (!selectedIds.isEmpty() && JOptionPane.showConfirmDialog(this, "Bạn có muốn ngừng bán sản phẩm này không ?") == 0) {
            boolean isDelete = false;
            for (int id : selectedIds) {
                isDelete = sps.delete(id);
                sps.updateStatus(0, 0);
                if (isDelete) {
                    System.out.println(isDelete);

                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại");
                    break;
                }
            }
            showSanPham(sps.showSPTheoTrangThai(1));
            showData(ctsps.getAll());
            showDataDelete(ctsps.getAllDelete());
            if (isDelete) {
                JOptionPane.showMessageDialog(this, "Xóa thành công");
            }
        }
    }//GEN-LAST:event_btnBanActionPerformed

    private void txtSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoLuongActionPerformed

    private void cbbCpuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbCpuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbCpuActionPerformed

    private void tableTongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTongMouseClicked
        // TODO add your handling code here:
        showSanPham(sps.getTenSanPham());
        showData(ctsps.getAll());
        showDataDelete(ctsps.getAllDelete());
        btnBan.setVisible(false);
        btnNgung.setVisible(false);
    }//GEN-LAST:event_tableTongMouseClicked

    private void txtTenSanPhamKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenSanPhamKeyReleased
        // TODO add your handling code here:
        System.out.println("Key SanPham");
        String keyword = txtTenSanPham.getText().trim();
        List<SanPhamResponse> searchResults;
        if (keyword.isEmpty()) {
            showSanPham(sps.getTenSanPham());
        } else {
            searchResults = sps.keyPressed(keyword);
            System.out.println(keyword);
            showSanPham(searchResults);
        }

    }//GEN-LAST:event_txtTenSanPhamKeyReleased

    private void btnDelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete1ActionPerformed
        // TODO add your handling code here:
        try {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showSaveDialog(this);
            File saveFile = jFileChooser.getSelectedFile();
            if (saveFile != null) {
                saveFile = new File(saveFile.toString() + ".xlsx");
                Workbook wb = new XSSFWorkbook();
                Sheet sheet = wb.createSheet("Account");

                Row rowCol = sheet.createRow(0);
                for (int i = 0; i < tableCtspDelete.getColumnCount(); i++) {
                    Cell cell = rowCol.createCell(i);
                    cell.setCellValue(tableCtspDelete.getColumnName(i));
                }
                for (int j = 0; j < tableCtspDelete.getRowCount(); j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < tableCtspDelete.getColumnCount(); k++) {
                        Cell cell = row.createCell(k);
                        if (tableCtspDelete.getValueAt(j, k) != null) {
                            cell.setCellValue(tableCtspDelete.getValueAt(j, k).toString());
                        }
                    }
                }
                FileOutputStream out = new FileOutputStream(new File(saveFile.toString()));
                wb.write(out);
                wb.close();
                out.close();
                EventStream.openFile(saveFile.toPath());
                JOptionPane.showMessageDialog(this, "Export succesfully!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnDelete1ActionPerformed

    private void txtTimKiemQrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemQrActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemQrActionPerformed

    private void txtTimKiemQrKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemQrKeyReleased
        // TODO add your handling code here:
        System.out.println("Key Qr");
        String keyword = txtTimKiemQr.getText().trim();
        List<SanPhamChiTietResponse> searchResults;
        searchResults = ctsps.searchQr(keyword);
        System.out.println(keyword);
        showQrRender(searchResults);
    }//GEN-LAST:event_txtTimKiemQrKeyReleased

    private void btnQuetQrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuetQrActionPerformed
        // TODO add your handling code here:
        ViewQr viewQr = new ViewQr(null, true);
        viewQr.setVisible(true);
        viewQr.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("window closing");
                List<SanPhamChiTietResponse> searchResults;
                searchResults = ctsps.searchQr(ViewQr.imeiiii);
                showQrRender(searchResults);
            }
        });
    }//GEN-LAST:event_btnQuetQrActionPerformed

    private void tableSearchImeiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSearchImeiMouseClicked
        // TODO add your handling code here:
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Bạn phải chọn dòng để xem chi tiết");
            return;
        }
        if (evt.getClickCount() == 2) {
            sp = tableSearchImei.getValueAt(index, 0).toString();
            cl = tableSearchImei.getValueAt(index, 1).toString();
            cpu = tableSearchImei.getValueAt(index, 2).toString();
            dl = tableSearchImei.getValueAt(index, 3).toString();
            gpu = tableSearchImei.getValueAt(index, 4).toString();
            hdh = tableSearchImei.getValueAt(index, 5).toString();
            mh = tableSearchImei.getValueAt(index, 6).toString();
            ms = tableSearchImei.getValueAt(index, 7).toString();
            nsx = tableSearchImei.getValueAt(index, 8).toString();
            ram = tableSearchImei.getValueAt(index, 9).toString();
            giaa = ctsps.getAll().get(index).getGia();
            trongluongg = ctsps.getAll().get(index).getTrongLuong();
            EditChiTiet edit = new EditChiTiet(null, true, sp, nsx, dl, ms, hdh, gpu, ram, cpu, mh, cl, trongluongg, giaa);
            edit.setVisible(true);
        }
    }//GEN-LAST:event_tableSearchImeiMouseClicked
    public Boolean isChatLieuExisted(String tenThuocTinh) {
        for (ChatLieuResponse tt : cls.getTenChatLieu()) {
            if (tenThuocTinh.equals(tt.getTenChatLieu())) {
                return true;
            }
        }
        return false;
    }

    public Boolean isCpuExisted(String tenThuocTinh) {
        for (CpuResponse excpu : cpus.getLoaiCpu()) {
            if (tenThuocTinh.equals(excpu.getLoaiCPU())) {
                return true;
            }
        }
        return false;
    }

    public Boolean isDungLuongExisted(String tenThuocTinh) {
        for (DungLuongResponse exdl : dls.getDungLuongS()) {
            if (tenThuocTinh.equals(exdl.getDungLuong())) {
                return true;
            }
        }
        return false;
    }

    public Boolean isGpuExisted(String tenThuocTinh) {
        for (GpuResponse exgpu : gpus.getGpu()) {
            if (tenThuocTinh.equals(exgpu.getLoaiGPU())) {
                return true;
            }
        }
        return false;
    }

    public Boolean isHeDieuHanhExisted(String tenThuocTinh) {
        for (HeDieuHanhResponse exhdh : hdhs.getTenHeDieuHanh()) {
            if (tenThuocTinh.equals(exhdh.getHeDieuHanh())) {
                return true;
            }
        }
        return false;
    }

    public Boolean isImeiExisted(String maimei) {
        for (ImeiResponse exim : ims.getMaImei()) {
            if (maimei.equals(exim.getMaImei())) {
                return true;
            }
        }
        return false;
    }

    public Boolean isManHinhExisted(String tenThuocTinh) {
        for (ManHinhResponse exmh : mhs.getLoaiManHinh()) {
            if (tenThuocTinh.equals(exmh.getLoaiManHinh())) {
                return true;
            }
        }
        return false;
    }

    public Boolean isMauSacExisted(String tenThuocTinh) {
        for (MauSacResponse exms : mssu.getTenMauSac()) {
            if (tenThuocTinh.equals(exms.getTenMauSac())) {
                return true;
            }
        }
        return false;
    }

    public Boolean isNhaSanXuatExisted(String tenThuocTinh) {
        for (NhaSanXuatResponse exnsx : nsxs.getTenNhaSanXuat()) {
            if (tenThuocTinh.equals(exnsx.getTenNhaSanXuat())) {
                return true;
            }
        }
        return false;
    }

    public Boolean isRamExisted(String tenThuocTinh) {
        for (RamResponse exrm : rams.getDungLuongRam()) {
            if (tenThuocTinh.equals(exrm.getDungLuongRam())) {
                return true;
            }
        }
        return false;
    }

    public Boolean isSanPhamExisted(String tenThuocTinh) {
        for (SanPhamResponse exsp : sps.getTenSanPham()) {
            if (tenThuocTinh.equals(exsp.getTenSanPham())) {
                return true;
            }
        }
        return false;
    }

    public Boolean validateTT() {
        if (txtTenThuocTinh.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn phải nhập tên thuộc tính");
            return false;
        }
        if (!txtTenThuocTinh.getText().matches("[a-zA-Z0-9\\sàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễđòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳỹỷýÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄĐÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲỸỶÝíìịỉĩIÍỈỊÌ]+")) {
            JOptionPane.showMessageDialog(this, "Tên thuộc tính chỉ được chứa số, chữ và khoảng trắng");
            return false;
        }
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddTenSP;
    private javax.swing.JButton btnBan;
    private javax.swing.JButton btnCl;
    private javax.swing.JButton btnCu;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDelete1;
    private javax.swing.JButton btnDl;
    private javax.swing.JButton btnDowloadQR;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnGu;
    private javax.swing.JButton btnHdh;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLamMoiTT;
    private javax.swing.JButton btnMh;
    private javax.swing.JButton btnMs;
    private javax.swing.JButton btnNgung;
    private javax.swing.JButton btnNsx;
    private javax.swing.JButton btnQuetQr;
    private javax.swing.JButton btnRm;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnSuaTT;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemSP;
    private javax.swing.JButton btnThemTT;
    private javax.swing.JButton btnXoaTT;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cbbChatLieu;
    private javax.swing.JComboBox<String> cbbCpu;
    private javax.swing.JComboBox<String> cbbDungLuong;
    private javax.swing.JComboBox<String> cbbGpu;
    private javax.swing.JComboBox<String> cbbHeDieuHanh;
    private javax.swing.JComboBox<String> cbbManHinh;
    private javax.swing.JComboBox<String> cbbMauSac;
    private javax.swing.JComboBox<String> cbbNhaSanXuat;
    private javax.swing.JComboBox<String> cbbRam;
    private javax.swing.JComboBox<String> cbbSanPham;
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
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JLabel labelSlideDLRam;
    private javax.swing.JButton nextButton;
    private javax.swing.JButton prevButton;
    private javax.swing.JRadioButton rdChatLieu;
    private javax.swing.JRadioButton rdCpu;
    private javax.swing.JRadioButton rdDangBan;
    private javax.swing.JRadioButton rdDungLuong;
    private javax.swing.JRadioButton rdGpu;
    private javax.swing.JRadioButton rdHeDieuHanh;
    private javax.swing.JRadioButton rdManHinh;
    private javax.swing.JRadioButton rdMauSac;
    private javax.swing.JRadioButton rdNgungBan;
    private javax.swing.JRadioButton rdNhaSanXuat;
    private javax.swing.JRadioButton rdRam;
    private javax.swing.JRadioButton rdTatCa;
    private javax.swing.JSlider sliderGia;
    private javax.swing.JPanel tab1;
    private javax.swing.JPanel tab2;
    private javax.swing.JPanel tab3;
    private javax.swing.JTable tableChiTietSanPham;
    private javax.swing.JTable tableCtspDelete;
    private javax.swing.JTable tableSanPham;
    private javax.swing.JTable tableSearchImei;
    private javax.swing.JTable tableThuocTinh;
    private javax.swing.JTabbedPane tableTong;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtImei;
    private javax.swing.JTextField txtMaThuocTinh;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenSanPham;
    private javax.swing.JTextField txtTenThuocTinh;
    private javax.swing.JTextField txtTimKiemQr;
    private javax.swing.JTextField txtTrongLuong;
    // End of variables declaration//GEN-END:variables
}
