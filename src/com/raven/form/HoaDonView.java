/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.form;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFontFactory.EmbeddingStrategy;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.raven.dialog.HoaDon;
import com.raven.form.frameViewCtsp.QrHoaDon;
import com.raven.model.ModelBill;
import com.raven.services.BillService;
import com.raven.viewmodels.ModelBillDetailTable;
import com.raven.viewmodels.ModelBillTable;
import com.raven.viewmodels.ModelHistoryTable;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import jdk.jfr.consumer.EventStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 *
 * @author nguye
 */
public class HoaDonView extends javax.swing.JPanel {

    BillService billService = new BillService();
    DefaultTableModel dtm = new DefaultTableModel();
    int index = -1;
    int soTrangHoaDon = billService.selectAllHoaDon().size();
    int soTrangHDCT = 1;
    int soTrangLichSu = 1;
    int trangHD = 1;
    int trangHDCT = 1;
    int trangLichSu = 1;

    public HoaDonView() {
        initComponents();
        setOpaque(false);
        this.countTrang();
        this.fillTableHoaDon(billService.fillHoaDon(1));
        lblTrangHDCT.setText(trangHDCT + "/" + soTrangHDCT);
        lblTrangLichSu.setText(trangLichSu + "/" + soTrangLichSu);
        addPlacehoder(txtTimKiem);
        addPlacehoder(txtTimKiem1);
        jdcEnd.setDateFormatString("yyyy-MM-dd");
        jdcStart.setDateFormatString("yyyy-MM-dd");
        editByDateEnd();
        editByDateStart();
    }

    

    public static byte[] createQrCodeFromText(String text) {
        try {
            // Tạo một đối tượng BitMatrix từ chuỗi bằng cách sử dụng thư viện ZXing
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            hints.put(EncodeHintType.MARGIN, 1);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, 200, 200, hints);

            // Chuyển đổi đối tượng BitMatrix thành một đối tượng BufferedImage
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // Chuyển đổi đối tượng BufferedImage thành một mảng byte
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            javax.imageio.ImageIO.write(bufferedImage, "png", baos);
            baos.flush();
            byte[] imageBytes = baos.toByteArray();
            baos.close();
            return imageBytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void convertImageBytesToPdfBytes(byte[] imgBytes) {
        try {
            // Tạo một tài liệu pdf với tên qr_code.pdf và lưu ở đường dẫn D:\\PDF_HOADON\\
            PdfWriter writer = new PdfWriter("D:\\PDF_HOADON\\qr_code5.pdf");
            PdfDocument pdfDoc = new PdfDocument(writer);

            // Tạo một đối tượng document để thêm các thành phần vào tài liệu pdf
            Document document = new Document(pdfDoc);

            // Tạo một đối tượng imagedata từ mảng byte
            ImageData data = ImageDataFactory.create(imgBytes);

            // Tạo một đối tượng image từ imagedata
            Image img = new Image(data);

            // Thêm image vào document
            document.add(img);

            // Đóng document
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void countTrang() {
        if (soTrangHoaDon < 8) {
            soTrangHoaDon = 1;
        } else if (soTrangHoaDon % 8 == 0) {
            soTrangHoaDon = soTrangHoaDon / 8;
        } else if (soTrangHoaDon / 8 != 0) {
            soTrangHoaDon = soTrangHoaDon / 8 + 1;
        }

        if (soTrangHDCT < 8) {
            soTrangHDCT = 1;
        } else if (soTrangHDCT / 8 == 1) {
            soTrangHDCT = soTrangHDCT / 8;
        } else if (soTrangHDCT / 8 != 0) {
            soTrangHDCT = soTrangHDCT / 8 + 1;
        }

        if (soTrangLichSu < 8) {
            soTrangLichSu = 1;
        } else if (soTrangLichSu / 8 == 1) {
            soTrangLichSu = soTrangLichSu / 8;
        } else if (soTrangLichSu / 8 != 0) {
            soTrangLichSu = soTrangLichSu / 8 + 1;
        }
    }

    public void fillTableHoaDon(List<ModelBillTable> listModelBillTable) {
        dtm = (DefaultTableModel) tblHoaDon.getModel();
        dtm.setRowCount(0);
        int i = 0;
        for (ModelBillTable modelBillTable : listModelBillTable) {
            i++;
            dtm.addRow(modelBillTable.toDataRow(i));
        }
        lblTrangHoaDon.setText(trangHD + "/" + soTrangHoaDon);
    }

    public void fillTableHoaDonChiTiet(List<ModelBillDetailTable> listModelBillDetailTable) {
        dtm = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        dtm.setRowCount(0);
        int a = 0;
        for (ModelBillDetailTable modelBillDetailTable : listModelBillDetailTable) {
            a++;
            dtm.addRow(modelBillDetailTable.toDataRow(a));
        }
        lblTrangHDCT.setText(trangHDCT + "/" + soTrangHDCT);
    }

    public void fillTableHoaDonChiTiet1(List<ModelBillDetailTable> listModelBillDetailTable) {
        dtm = (DefaultTableModel) tblHDCT1.getModel();
        dtm.setRowCount(0);
        int a = 0;
        for (ModelBillDetailTable modelBillDetailTable : listModelBillDetailTable) {
            a++;
            dtm.addRow(modelBillDetailTable.toDataRow(a));
        }
        lblTrangHDCT.setText(trangLichSu + "/" + soTrangLichSu);
    }

    public void fillTableLichSu(List<ModelHistoryTable> listHistory) {
        dtm = (DefaultTableModel) tblLichSuDonHang.getModel();
        dtm.setRowCount(0);
        int i = 0;
        for (ModelHistoryTable modelHistory : listHistory) {
            i++;
            dtm.addRow(modelHistory.toDataRow(i));
        }
//        lblTrangHoaDon.setText(trangHD + "/" + soTrangHoaDon);
    }

    public void addPlacehoder(JTextField txt) {
        Font font = txt.getFont();
        font = font.deriveFont(Font.ITALIC);
        txt.setFont(font);
        txt.setForeground(Color.gray);
    }

    public void removePlacehoder(JTextField txt) {
        Font font = txt.getFont();
        font = font.deriveFont(Font.PLAIN | Font.BOLD);
        txt.setFont(font);
        txt.setForeground(Color.black);
    }
    
    void editByDateStart() {
        jdcEnd.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                String trangThai = null;
                if (cboTrangThai.getSelectedItem().equals("Tất cả")) {
                    trangThai = "";
                } else if (cboTrangThai.getSelectedItem().equals("Đã thanh toán")) {
                    trangThai = "Đã thanh toán";
                } else if (cboTrangThai.getSelectedItem().equals("Chờ thanh toán")) {
                    trangThai = "Chờ thanh toán";
                } else if (cboTrangThai.getSelectedItem().equals("Đã hủy")) {
                    trangThai = "Đã hủy";
                }
                String loaiHoaDon = null;
                if (cboLoaiHoaDon.getSelectedItem().equals("Tất cả")) {
                    loaiHoaDon = "";
                } else if (cboLoaiHoaDon.getSelectedItem().equals("Bán tại quầy")) {
                    loaiHoaDon = "Bán tại quầy";
                } else if (cboLoaiHoaDon.getSelectedItem().equals("Khách đặt hàng")) {
                    loaiHoaDon = "Khách đặt hàng";
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String str = "1900-01-01";
                Date start = jdcStart.getDate();
                Date end = jdcEnd.getDate();
                String startStr = null;
                String endStr = null;
                if (start == null || end == null) {
                    try {
                        start = sdf.parse(str);
                        end = new Date();
                    } catch (Exception e) {
                    }
                }
                startStr = sdf.format(start);
                endStr = sdf.format(end);
                String text = txtTimKiem.getText().trim();
                dtm = (DefaultTableModel) tblHoaDonChiTiet.getModel();
                dtm.setRowCount(0);
                if (text.isEmpty()) {
                    soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
                    countTrang();
                    fillTableHoaDon(billService.fillHoaDon(1));
                } else if (text.equals("Mã hóa đơn - Mã nhân viên - Tên khách hàng")) {
                    text = "";
                    soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
                    countTrang();
                    if (trangHD > soTrangHoaDon) {
                        trangHD = soTrangHoaDon;
                    }
                    fillTableHoaDon(billService.timKiem(trangHD, text, loaiHoaDon, trangThai, startStr, endStr));
                } else {
                    soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
                    countTrang();
                    if (trangHD > soTrangHoaDon) {
                        trangHD = soTrangHoaDon;
                    }
                    fillTableHoaDon(billService.timKiem(trangHD, text, loaiHoaDon, trangThai, startStr, endStr));
                }
            }
        });
    }

    void editByDateEnd() {
        jdcStart.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                 String trangThai = null;
                if (cboTrangThai.getSelectedItem().equals("Tất cả")) {
                    trangThai = "";
                } else if (cboTrangThai.getSelectedItem().equals("Đã thanh toán")) {
                    trangThai = "Đã thanh toán";
                } else if (cboTrangThai.getSelectedItem().equals("Chờ thanh toán")) {
                    trangThai = "Chờ thanh toán";
                } else if (cboTrangThai.getSelectedItem().equals("Đã hủy")) {
                    trangThai = "Đã hủy";
                }
                String loaiHoaDon = null;
                if (cboLoaiHoaDon.getSelectedItem().equals("Tất cả")) {
                    loaiHoaDon = "";
                } else if (cboLoaiHoaDon.getSelectedItem().equals("Bán tại quầy")) {
                    loaiHoaDon = "Bán tại quầy";
                } else if (cboLoaiHoaDon.getSelectedItem().equals("Khách đặt hàng")) {
                    loaiHoaDon = "Khách đặt hàng";
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String str = "1900-01-01";
                Date start = jdcStart.getDate();
                Date end = jdcEnd.getDate();
                String startStr = null;
                String endStr = null;
                if (start == null || end == null) {
                    try {
                        start = sdf.parse(str);
                        end = new Date();
                    } catch (Exception e) {
                    }
                }
                startStr = sdf.format(start);
                endStr = sdf.format(end);
                String text = txtTimKiem.getText().trim();
                dtm = (DefaultTableModel) tblHoaDonChiTiet.getModel();
                dtm.setRowCount(0);
                if (text.isEmpty()) {
                    soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
                    countTrang();
                    fillTableHoaDon(billService.fillHoaDon(1));
                } else if (text.equals("Mã hóa đơn - Mã nhân viên - Tên khách hàng")) {
                    text = "";
                    soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
                    countTrang();
                    if (trangHD > soTrangHoaDon) {
                        trangHD = soTrangHoaDon;
                    }
                    fillTableHoaDon(billService.timKiem(trangHD, text, loaiHoaDon, trangThai, startStr, endStr));
                } else {
                    soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
                    countTrang();
                    if (trangHD > soTrangHoaDon) {
                        trangHD = soTrangHoaDon;
                    }
                    fillTableHoaDon(billService.timKiem(trangHD, text, loaiHoaDon, trangThai, startStr, endStr));
                }
            }
        });
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cboTrangThai = new javax.swing.JComboBox<>();
        cboLoaiHoaDon = new javax.swing.JComboBox<>();
        btnNextAllHoaDon = new javax.swing.JButton();
        lblTrangHoaDon = new javax.swing.JLabel();
        btnPreAllHoaDon = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        btnXemChiTiet = new javax.swing.JButton();
        btnPreHoaDon = new javax.swing.JButton();
        btnNextHoaDon = new javax.swing.JButton();
        btnXuatExcel = new javax.swing.JButton();
        jdcStart = new com.toedter.calendar.JDateChooser();
        jdcEnd = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        btnInHoaDon = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHoaDonChiTiet = new javax.swing.JTable();
        lblTrangHDCT = new javax.swing.JLabel();
        btnNextHDCT = new javax.swing.JButton();
        btnPreHDCT = new javax.swing.JButton();
        btnNextAllHDCT = new javax.swing.JButton();
        btnPreAllHDCT = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLichSuDonHang = new javax.swing.JTable();
        btnPreLichSu = new javax.swing.JButton();
        lblTrangLichSu = new javax.swing.JLabel();
        btnPreAllLichSu = new javax.swing.JButton();
        btnNextAllLichSu = new javax.swing.JButton();
        btnNextLichSu = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        txtTimKiem1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtMaHoaDon = new javax.swing.JTextField();
        txtTenNhanVien = new javax.swing.JTextField();
        txtTenKhachHang = new javax.swing.JTextField();
        txtTenNguoiNhan = new javax.swing.JTextField();
        txtSoDienThoai = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        txtTienShip = new javax.swing.JTextField();
        txtTongTien = new javax.swing.JTextField();
        txtMaVoucher = new javax.swing.JTextField();
        txtTrangThai = new javax.swing.JTextField();
        txtNgayTao = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtTienMat = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtLoaiHoaDon = new javax.swing.JTextField();
        txtTienChuyenKhoan = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblHDCT1 = new javax.swing.JTable();
        btnPreHDCT1 = new javax.swing.JButton();
        btnPreAllHDCT1 = new javax.swing.JButton();
        lblTrangHDCT1 = new javax.swing.JLabel();
        btnNextAllHDCT1 = new javax.swing.JButton();
        btnNextHDCT1 = new javax.swing.JButton();
        btnQR = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1058, 700));
        setMinimumSize(new java.awt.Dimension(1058, 700));
        setPreferredSize(new java.awt.Dimension(1058, 700));

        jTabbedPane1.setMaximumSize(new java.awt.Dimension(1058, 700));
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(1058, 700));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1058, 700));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(102, 102, 255))); // NOI18N

        jLabel1.setText("Tìm kiếm");

        txtTimKiem.setText("Mã hóa đơn - Mã nhân viên - Tên khách hàng");
        txtTimKiem.setToolTipText("");
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

        jLabel2.setText("Loại hóa đơn");

        jLabel3.setText("Trạng thái");

        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Đã thanh toán", "Chờ thanh toán", "Đã hủy" }));
        cboTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTrangThaiActionPerformed(evt);
            }
        });

        cboLoaiHoaDon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Bán tại quầy", "Khách đặt hàng" }));
        cboLoaiHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLoaiHoaDonActionPerformed(evt);
            }
        });

        btnNextAllHoaDon.setBackground(new java.awt.Color(255, 51, 51));
        btnNextAllHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNextAllHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnNextAllHoaDon.setText(">>");
        btnNextAllHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextAllHoaDonActionPerformed(evt);
            }
        });

        lblTrangHoaDon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTrangHoaDon.setText("1");

        btnPreAllHoaDon.setBackground(new java.awt.Color(255, 51, 51));
        btnPreAllHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPreAllHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnPreAllHoaDon.setText("<<");
        btnPreAllHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreAllHoaDonActionPerformed(evt);
            }
        });

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "MÃ HÓA ĐƠN", "MÃ NHÂN VIÊN", "TÊN KHÁCH HÀNG", "LOẠI HÓA DƠN", "HÌNH THỨC TT", "MÃ GIAO DỊCH", "NGÀY TẠO", "TRẠNG THÁI"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblHoaDon);
        if (tblHoaDon.getColumnModel().getColumnCount() > 0) {
            tblHoaDon.getColumnModel().getColumn(0).setMinWidth(40);
            tblHoaDon.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblHoaDon.getColumnModel().getColumn(0).setMaxWidth(40);
            tblHoaDon.getColumnModel().getColumn(1).setPreferredWidth(50);
            tblHoaDon.getColumnModel().getColumn(2).setPreferredWidth(50);
            tblHoaDon.getColumnModel().getColumn(4).setPreferredWidth(60);
            tblHoaDon.getColumnModel().getColumn(7).setMinWidth(80);
            tblHoaDon.getColumnModel().getColumn(7).setPreferredWidth(80);
            tblHoaDon.getColumnModel().getColumn(7).setMaxWidth(80);
            tblHoaDon.getColumnModel().getColumn(8).setPreferredWidth(50);
        }

        btnXemChiTiet.setBackground(new java.awt.Color(255, 51, 51));
        btnXemChiTiet.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXemChiTiet.setForeground(new java.awt.Color(255, 255, 255));
        btnXemChiTiet.setText("Xem chi tiết");
        btnXemChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemChiTietActionPerformed(evt);
            }
        });

        btnPreHoaDon.setBackground(new java.awt.Color(255, 51, 51));
        btnPreHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPreHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnPreHoaDon.setText("<");
        btnPreHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreHoaDonActionPerformed(evt);
            }
        });

        btnNextHoaDon.setBackground(new java.awt.Color(255, 51, 51));
        btnNextHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNextHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnNextHoaDon.setText(">");
        btnNextHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextHoaDonActionPerformed(evt);
            }
        });

        btnXuatExcel.setBackground(new java.awt.Color(255, 51, 51));
        btnXuatExcel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXuatExcel.setForeground(new java.awt.Color(255, 255, 255));
        btnXuatExcel.setText("Xuất excel");
        btnXuatExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatExcelActionPerformed(evt);
            }
        });

        jdcStart.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jdcStartKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jdcStartKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jdcStartKeyTyped(evt);
            }
        });

        jdcEnd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jdcEndKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jdcEndKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jdcEndKeyTyped(evt);
            }
        });

        jLabel5.setText("Từ ngày");

        jLabel20.setText("Đến ngày");

        btnInHoaDon.setBackground(new java.awt.Color(255, 51, 51));
        btnInHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnInHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnInHoaDon.setText("In hóa đơn");
        btnInHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInHoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnPreAllHoaDon)
                        .addGap(18, 18, 18)
                        .addComponent(btnPreHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblTrangHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNextHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNextAllHoaDon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnInHoaDon)
                        .addGap(18, 18, 18)
                        .addComponent(btnXuatExcel)
                        .addGap(18, 18, 18)
                        .addComponent(btnXemChiTiet))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(cboLoaiHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel20)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jdcEnd, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                            .addComponent(jdcStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(222, 222, 222)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5))
                            .addComponent(jdcStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(cboLoaiHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jdcEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNextAllHoaDon)
                    .addComponent(lblTrangHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPreAllHoaDon)
                    .addComponent(btnXemChiTiet)
                    .addComponent(btnPreHoaDon)
                    .addComponent(btnNextHoaDon)
                    .addComponent(btnXuatExcel)
                    .addComponent(btnInHoaDon))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách hóa đơn chi tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(102, 102, 255))); // NOI18N

        tblHoaDonChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "STT", "TÊN SẢN PHẨM", "SỐ SERIAL", "GIÁ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblHoaDonChiTiet);
        if (tblHoaDonChiTiet.getColumnModel().getColumnCount() > 0) {
            tblHoaDonChiTiet.getColumnModel().getColumn(0).setMinWidth(40);
            tblHoaDonChiTiet.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblHoaDonChiTiet.getColumnModel().getColumn(0).setMaxWidth(40);
            tblHoaDonChiTiet.getColumnModel().getColumn(2).setMinWidth(110);
            tblHoaDonChiTiet.getColumnModel().getColumn(2).setPreferredWidth(110);
            tblHoaDonChiTiet.getColumnModel().getColumn(2).setMaxWidth(110);
            tblHoaDonChiTiet.getColumnModel().getColumn(3).setMinWidth(80);
            tblHoaDonChiTiet.getColumnModel().getColumn(3).setPreferredWidth(80);
            tblHoaDonChiTiet.getColumnModel().getColumn(3).setMaxWidth(80);
        }

        lblTrangHDCT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTrangHDCT.setText("1");

        btnNextHDCT.setBackground(new java.awt.Color(255, 51, 51));
        btnNextHDCT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNextHDCT.setForeground(new java.awt.Color(255, 255, 255));
        btnNextHDCT.setText(">");

        btnPreHDCT.setBackground(new java.awt.Color(255, 51, 51));
        btnPreHDCT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPreHDCT.setForeground(new java.awt.Color(255, 255, 255));
        btnPreHDCT.setText("<");

        btnNextAllHDCT.setBackground(new java.awt.Color(255, 51, 51));
        btnNextAllHDCT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNextAllHDCT.setForeground(new java.awt.Color(255, 255, 255));
        btnNextAllHDCT.setText(">>");

        btnPreAllHDCT.setBackground(new java.awt.Color(255, 51, 51));
        btnPreAllHDCT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPreAllHDCT.setForeground(new java.awt.Color(255, 255, 255));
        btnPreAllHDCT.setText("<<");
        btnPreAllHDCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreAllHDCTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnPreAllHDCT)
                        .addGap(18, 18, 18)
                        .addComponent(btnPreHDCT, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblTrangHDCT, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNextHDCT, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNextAllHDCT)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNextAllHDCT)
                    .addComponent(lblTrangHDCT, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPreAllHDCT)
                    .addComponent(btnPreHDCT)
                    .addComponent(btnNextHDCT))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lịch sử đơn hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(102, 102, 255))); // NOI18N

        tblLichSuDonHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "THỜI GIAN", "NGÀY", "HÀNH ĐỘNG", "NGƯỜI XÁC NHẬN"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblLichSuDonHang);
        if (tblLichSuDonHang.getColumnModel().getColumnCount() > 0) {
            tblLichSuDonHang.getColumnModel().getColumn(0).setMinWidth(40);
            tblLichSuDonHang.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblLichSuDonHang.getColumnModel().getColumn(0).setMaxWidth(40);
            tblLichSuDonHang.getColumnModel().getColumn(1).setMinWidth(65);
            tblLichSuDonHang.getColumnModel().getColumn(1).setPreferredWidth(65);
            tblLichSuDonHang.getColumnModel().getColumn(1).setMaxWidth(65);
            tblLichSuDonHang.getColumnModel().getColumn(2).setMinWidth(70);
            tblLichSuDonHang.getColumnModel().getColumn(2).setPreferredWidth(70);
            tblLichSuDonHang.getColumnModel().getColumn(2).setMaxWidth(70);
            tblLichSuDonHang.getColumnModel().getColumn(4).setMinWidth(130);
            tblLichSuDonHang.getColumnModel().getColumn(4).setPreferredWidth(130);
            tblLichSuDonHang.getColumnModel().getColumn(4).setMaxWidth(130);
        }

        btnPreLichSu.setBackground(new java.awt.Color(255, 51, 51));
        btnPreLichSu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPreLichSu.setForeground(new java.awt.Color(255, 255, 255));
        btnPreLichSu.setText("<");

        lblTrangLichSu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTrangLichSu.setText("1");

        btnPreAllLichSu.setBackground(new java.awt.Color(255, 51, 51));
        btnPreAllLichSu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPreAllLichSu.setForeground(new java.awt.Color(255, 255, 255));
        btnPreAllLichSu.setText("<<");
        btnPreAllLichSu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreAllLichSuActionPerformed(evt);
            }
        });

        btnNextAllLichSu.setBackground(new java.awt.Color(255, 51, 51));
        btnNextAllLichSu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNextAllLichSu.setForeground(new java.awt.Color(255, 255, 255));
        btnNextAllLichSu.setText(">>");

        btnNextLichSu.setBackground(new java.awt.Color(255, 51, 51));
        btnNextLichSu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNextLichSu.setForeground(new java.awt.Color(255, 255, 255));
        btnNextLichSu.setText(">");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btnPreAllLichSu)
                        .addGap(18, 18, 18)
                        .addComponent(btnPreLichSu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblTrangLichSu, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNextLichSu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNextAllLichSu)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNextAllLichSu)
                    .addComponent(lblTrangLichSu, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPreAllLichSu)
                    .addComponent(btnPreLichSu)
                    .addComponent(btnNextLichSu))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Hóa đơn", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        txtTimKiem1.setText("Mã hóa đơn");
        txtTimKiem1.setToolTipText("");
        txtTimKiem1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTimKiem1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTimKiem1FocusLost(evt);
            }
        });
        txtTimKiem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiem1ActionPerformed(evt);
            }
        });
        txtTimKiem1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiem1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addComponent(txtTimKiem1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiem1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setText("Mã hóa đơn");

        jLabel7.setText("Tên nhân viên");

        jLabel8.setText("Tên khách hàng");

        jLabel9.setText("Tên người nhận");

        jLabel10.setText("Số điện thoại");

        jLabel11.setText("Địa chỉ");

        jLabel12.setText("Tiền ship");

        jLabel13.setText("Tổng tiền");

        jLabel14.setText("Mã voucher");

        jLabel15.setText("Trạng thái");

        jLabel16.setText("Ngày tạo");

        txtMaHoaDon.setEditable(false);

        txtTenNhanVien.setEditable(false);

        txtTenKhachHang.setEditable(false);
        txtTenKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenKhachHangActionPerformed(evt);
            }
        });

        txtTenNguoiNhan.setEditable(false);

        txtSoDienThoai.setEditable(false);

        txtDiaChi.setEditable(false);
        txtDiaChi.setColumns(20);
        txtDiaChi.setRows(5);
        jScrollPane4.setViewportView(txtDiaChi);

        txtTienShip.setEditable(false);

        txtTongTien.setEditable(false);

        txtMaVoucher.setEditable(false);

        txtTrangThai.setEditable(false);

        txtNgayTao.setEditable(false);

        jLabel17.setText("Tiền mặt");

        txtTienMat.setEditable(false);

        jLabel18.setText("Tiền chuyển khoản");

        jLabel19.setText("Loại hóa đơn");

        txtLoaiHoaDon.setEditable(false);

        txtTienChuyenKhoan.setEditable(false);

        tblHDCT1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "TÊN SẢN PHẨM", "SỐ SERIAL", "GIÁ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tblHDCT1);

        btnPreHDCT1.setBackground(new java.awt.Color(255, 51, 51));
        btnPreHDCT1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPreHDCT1.setForeground(new java.awt.Color(255, 255, 255));
        btnPreHDCT1.setText("<");

        btnPreAllHDCT1.setBackground(new java.awt.Color(255, 51, 51));
        btnPreAllHDCT1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPreAllHDCT1.setForeground(new java.awt.Color(255, 255, 255));
        btnPreAllHDCT1.setText("<<");
        btnPreAllHDCT1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreAllHDCT1ActionPerformed(evt);
            }
        });

        lblTrangHDCT1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTrangHDCT1.setText("1");

        btnNextAllHDCT1.setBackground(new java.awt.Color(255, 51, 51));
        btnNextAllHDCT1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNextAllHDCT1.setForeground(new java.awt.Color(255, 255, 255));
        btnNextAllHDCT1.setText(">>");

        btnNextHDCT1.setBackground(new java.awt.Color(255, 51, 51));
        btnNextHDCT1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNextHDCT1.setForeground(new java.awt.Color(255, 255, 255));
        btnNextHDCT1.setText(">");

        btnQR.setBackground(new java.awt.Color(255, 51, 51));
        btnQR.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnQR.setForeground(new java.awt.Color(255, 255, 255));
        btnQR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/14.png"))); // NOI18N
        btnQR.setText("Quét QR");
        btnQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQRActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel10)))
                                .addGap(24, 24, 24)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtSoDienThoai)
                                    .addComponent(txtTenNguoiNhan, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTenKhachHang, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTenNhanVien, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMaHoaDon, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel12)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel17)
                                            .addComponent(jLabel18)
                                            .addComponent(jLabel19))
                                        .addGap(26, 26, 26)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtTienChuyenKhoan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                                            .addComponent(txtTienMat, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtTongTien, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtTienShip, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtLoaiHoaDon)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(38, 38, 38)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel15)
                                            .addComponent(jLabel14)
                                            .addComponent(jLabel16))
                                        .addGap(24, 24, 24)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(txtTrangThai, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                                                .addComponent(txtMaVoucher))
                                            .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(298, 298, 298)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnQR, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnPreAllHDCT1)
                        .addGap(18, 18, 18)
                        .addComponent(btnPreHDCT1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblTrangHDCT1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNextHDCT1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNextAllHDCT1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnQR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtTenNguoiNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jScrollPane4)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtTienShip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtTienMat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtTienChuyenKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txtLoaiHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtMaVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNextAllHDCT1)
                    .addComponent(lblTrangHDCT1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPreAllHDCT1)
                    .addComponent(btnPreHDCT1)
                    .addComponent(btnNextHDCT1))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Tìm kiếm", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnPreAllHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreAllHoaDonActionPerformed
        // TODO add your handling code here:
        dtm = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        dtm.setRowCount(0);
        String trangThai = null;
        if (cboTrangThai.getSelectedItem().equals("Tất cả")) {
            trangThai = "";
        } else if (cboTrangThai.getSelectedItem().equals("Đã thanh toán")) {
            trangThai = "Đã thanh toán";
        } else if (cboTrangThai.getSelectedItem().equals("Chờ thanh toán")) {
            trangThai = "Chờ thanh toán";
        } else if (cboTrangThai.getSelectedItem().equals("Đã hủy")) {
            trangThai = "Đã hủy";
        }
        String loaiHoaDon = null;
        if (cboLoaiHoaDon.getSelectedItem().equals("Tất cả")) {
            loaiHoaDon = "";
        } else if (cboLoaiHoaDon.getSelectedItem().equals("Bán tại quầy")) {
            loaiHoaDon = "Bán tại quầy";
        } else if (cboLoaiHoaDon.getSelectedItem().equals("Khách đặt hàng")) {
            loaiHoaDon = "Khách đặt hàng";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "1900-01-01";
        Date start = jdcStart.getDate();
        Date end = jdcEnd.getDate();
        String startStr = null;
        String endStr = null;
        if (start == null || end == null) {
            try {
                start = sdf.parse(str);
                end = new Date();
            } catch (Exception e) {
            }
        }
        startStr = sdf.format(start);
        endStr = sdf.format(end);
        String text = txtTimKiem.getText().trim();
        if (text.isEmpty()) {
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
        } else if (text.equals("Mã hóa đơn - Mã nhân viên - Tên khách hàng")) {
            text = "";
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
        } else {
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
        }
        if (trangHD > 1) {
            trangHD = 1;
            fillTableHoaDon(billService.timKiem(trangHD, text, loaiHoaDon, trangThai, startStr, endStr));
            lblTrangHoaDon.setText(trangHD + "/" + soTrangHoaDon);
        }
    }//GEN-LAST:event_btnPreAllHoaDonActionPerformed

    private void btnXemChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemChiTietActionPerformed
        // TODO add your handling code here:
        index = tblHoaDon.getSelectedRow();
        int id = Integer.parseInt(tblHoaDon.getValueAt(index, 1).toString());
        ModelBill modelBill = billService.selectHoaDonById(id);
        new HoaDon(null, true).showData(modelBill.getId(), modelBill.getTenNhanVien(),
                modelBill.getTenKhachHang(), modelBill.getMaVoucher(), modelBill.getTrangThai(),
                modelBill.getDiaChi(), modelBill.getTenNguoiNhan(), modelBill.getSoDienThoai(),
                modelBill.getLoaiHoaDon(), modelBill.getTienShip(),
                modelBill.getTongTien(), modelBill.getTienMat(),
                modelBill.getTienChuyenKhoan(), modelBill.getNgayTao());
    }//GEN-LAST:event_btnXemChiTietActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        // TODO add your handling code here:
        index = tblHoaDon.getSelectedRow();
        int idHoaDon = Integer.parseInt(tblHoaDon.getValueAt(index, 1).toString());
        soTrangHDCT = billService.fillHoaDonChiTiet(idHoaDon, soTrangHDCT).size();
        countTrang();
        fillTableHoaDonChiTiet(billService.fillHoaDonChiTiet(idHoaDon, soTrangHDCT));
        soTrangLichSu = billService.fillLichSu(idHoaDon, soTrangLichSu).size();
        countTrang();
        fillTableLichSu(billService.fillLichSu(idHoaDon, soTrangLichSu));
        if (evt.getClickCount() == 2) {
            index = tblHoaDon.getSelectedRow();
            int id = Integer.parseInt(tblHoaDon.getValueAt(index, 1).toString());
            ModelBill modelBill = billService.selectHoaDonById(id);
            new HoaDon(null, true).showData(modelBill.getId(), modelBill.getTenNhanVien(),
                    modelBill.getTenKhachHang(), modelBill.getMaVoucher(), modelBill.getTrangThai(),
                    modelBill.getDiaChi(), modelBill.getTenNguoiNhan(), modelBill.getSoDienThoai(),
                    modelBill.getLoaiHoaDon(), modelBill.getTienShip(),
                    modelBill.getTongTien(), modelBill.getTienMat(),
                    modelBill.getTienChuyenKhoan(), modelBill.getNgayTao());
        }
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void txtTimKiemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusGained
        if (txtTimKiem.getText().equals("Mã hóa đơn - Mã nhân viên - Tên khách hàng")) {
            txtTimKiem.setText(null);
            txtTimKiem.requestFocus();
            removePlacehoder(txtTimKiem);
        }
    }//GEN-LAST:event_txtTimKiemFocusGained

    private void txtTimKiemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusLost
        if (txtTimKiem.getText().length() == 0) {
            addPlacehoder(txtTimKiem);
            txtTimKiem.setText("Mã hóa đơn - Mã nhân viên - Tên khách hàng");
        }
    }//GEN-LAST:event_txtTimKiemFocusLost

    private void btnPreAllHDCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreAllHDCTActionPerformed
        // TODO add your handling code here:
        if (trangHD > 1) {
            trangHD = 1;
            index = tblHoaDon.getSelectedRow();
            int idHoaDon = Integer.parseInt(tblHoaDon.getValueAt(index, 1).toString());
            fillTableHoaDonChiTiet(billService.fillHoaDonChiTiet(idHoaDon, soTrangHDCT));
            lblTrangHoaDon.setText(trangHD + "/" + soTrangHDCT);
        }
    }//GEN-LAST:event_btnPreAllHDCTActionPerformed

    private void btnPreAllLichSuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreAllLichSuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPreAllLichSuActionPerformed

    private void btnNextAllHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextAllHoaDonActionPerformed
        // TODO add your handling code here:
        dtm = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        dtm.setRowCount(0);
        String trangThai = null;
        if (cboTrangThai.getSelectedItem().equals("Tất cả")) {
            trangThai = "";
        } else if (cboTrangThai.getSelectedItem().equals("Đã thanh toán")) {
            trangThai = "Đã thanh toán";
        } else if (cboTrangThai.getSelectedItem().equals("Chờ thanh toán")) {
            trangThai = "Chờ thanh toán";
        } else if (cboTrangThai.getSelectedItem().equals("Đã hủy")) {
            trangThai = "Đã hủy";
        }
        String loaiHoaDon = null;
        if (cboLoaiHoaDon.getSelectedItem().equals("Tất cả")) {
            loaiHoaDon = "";
        } else if (cboLoaiHoaDon.getSelectedItem().equals("Bán tại quầy")) {
            loaiHoaDon = "Bán tại quầy";
        } else if (cboLoaiHoaDon.getSelectedItem().equals("Khách đặt hàng")) {
            loaiHoaDon = "Khách đặt hàng";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "1900-01-01";
        Date start = jdcStart.getDate();
        Date end = jdcEnd.getDate();
        String startStr = null;
        String endStr = null;
        if (start == null || end == null) {
            try {
                start = sdf.parse(str);
                end = new Date();
            } catch (Exception e) {
            }
        }
        startStr = sdf.format(start);
        endStr = sdf.format(end);
        String text = txtTimKiem.getText().trim();
        if (text.isEmpty()) {
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
        } else if (text.equals("Mã hóa đơn - Mã nhân viên - Tên khách hàng")) {
            text = "";
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
        } else {
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
        }
        if (trangHD < soTrangHoaDon) {
            trangHD = soTrangHoaDon;
            fillTableHoaDon(billService.timKiem(trangHD, text, loaiHoaDon, trangThai, startStr, endStr));
            lblTrangHoaDon.setText(trangHD + "/" + soTrangHoaDon);
        }
    }//GEN-LAST:event_btnNextAllHoaDonActionPerformed

    private void btnPreHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreHoaDonActionPerformed
        // TODO add your handling code here:
        dtm = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        dtm.setRowCount(0);
        String trangThai = null;
        if (cboTrangThai.getSelectedItem().equals("Tất cả")) {
            trangThai = "";
        } else if (cboTrangThai.getSelectedItem().equals("Đã thanh toán")) {
            trangThai = "Đã thanh toán";
        } else if (cboTrangThai.getSelectedItem().equals("Chờ thanh toán")) {
            trangThai = "Chờ thanh toán";
        } else if (cboTrangThai.getSelectedItem().equals("Đã hủy")) {
            trangThai = "Đã hủy";
        }
        String loaiHoaDon = null;
        if (cboLoaiHoaDon.getSelectedItem().equals("Tất cả")) {
            loaiHoaDon = "";
        } else if (cboLoaiHoaDon.getSelectedItem().equals("Bán tại quầy")) {
            loaiHoaDon = "Bán tại quầy";
        } else if (cboLoaiHoaDon.getSelectedItem().equals("Khách đặt hàng")) {
            loaiHoaDon = "Khách đặt hàng";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "1900-01-01";
        Date start = jdcStart.getDate();
        Date end = jdcEnd.getDate();
        String startStr = null;
        String endStr = null;
        if (start == null || end == null) {
            try {
                start = sdf.parse(str);
                end = new Date();
            } catch (Exception e) {
            }
        }
        startStr = sdf.format(start);
        endStr = sdf.format(end);
        String text = txtTimKiem.getText().trim();
        if (text.isEmpty()) {
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
        } else if (text.equals("Mã hóa đơn - Mã nhân viên - Tên khách hàng")) {
            text = "";
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
        } else {
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
        }

        if (trangHD > 1) {
            trangHD--;
            fillTableHoaDon(billService.timKiem(trangHD, text, loaiHoaDon, trangThai, startStr, endStr));
            lblTrangHoaDon.setText(trangHD + "/" + soTrangHoaDon);
        }
    }//GEN-LAST:event_btnPreHoaDonActionPerformed

    private void btnNextHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextHoaDonActionPerformed
        // TODO add your handling code here:
        dtm = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        dtm.setRowCount(0);
        String trangThai = null;
        if (cboTrangThai.getSelectedItem().equals("Tất cả")) {
            trangThai = "";
        } else if (cboTrangThai.getSelectedItem().equals("Đã thanh toán")) {
            trangThai = "Đã thanh toán";
        } else if (cboTrangThai.getSelectedItem().equals("Chờ thanh toán")) {
            trangThai = "Chờ thanh toán";
        } else if (cboTrangThai.getSelectedItem().equals("Đã hủy")) {
            trangThai = "Đã hủy";
        }
        String loaiHoaDon = null;
        if (cboLoaiHoaDon.getSelectedItem().equals("Tất cả")) {
            loaiHoaDon = "";
        } else if (cboLoaiHoaDon.getSelectedItem().equals("Bán tại quầy")) {
            loaiHoaDon = "Bán tại quầy";
        } else if (cboLoaiHoaDon.getSelectedItem().equals("Khách đặt hàng")) {
            loaiHoaDon = "Khách đặt hàng";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "1900-01-01";
        Date start = jdcStart.getDate();
        Date end = jdcEnd.getDate();
        String startStr = null;
        String endStr = null;
        if (start == null || end == null) {
            try {
                start = sdf.parse(str);
                end = new Date();
            } catch (Exception e) {
            }
        }
        startStr = sdf.format(start);
        endStr = sdf.format(end);
        String text = txtTimKiem.getText().trim();
        if (text.isEmpty()) {
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
        } else if (text.equals("Mã hóa đơn - Mã nhân viên - Tên khách hàng")) {
            text = "";
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
        } else {
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
        }
        if (trangHD < soTrangHoaDon) {
            trangHD++;
            fillTableHoaDon(billService.timKiem(trangHD, text, loaiHoaDon, trangThai, startStr, endStr));
            lblTrangHoaDon.setText(trangHD + "/" + soTrangHoaDon);
        }
    }//GEN-LAST:event_btnNextHoaDonActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void cboTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTrangThaiActionPerformed
        String trangThai = null;
        if (cboTrangThai.getSelectedItem().equals("Tất cả")) {
            trangThai = "";
        } else if (cboTrangThai.getSelectedItem().equals("Đã thanh toán")) {
            trangThai = "Đã thanh toán";
        } else if (cboTrangThai.getSelectedItem().equals("Chờ thanh toán")) {
            trangThai = "Chờ thanh toán";
        } else if (cboTrangThai.getSelectedItem().equals("Đã hủy")) {
            trangThai = "Đã hủy";
        }
        String loaiHoaDon = null;
        if (cboLoaiHoaDon.getSelectedItem().equals("Tất cả")) {
            loaiHoaDon = "";
        } else if (cboLoaiHoaDon.getSelectedItem().equals("Bán tại quầy")) {
            loaiHoaDon = "Bán tại quầy";
        } else if (cboLoaiHoaDon.getSelectedItem().equals("Khách đặt hàng")) {
            loaiHoaDon = "Khách đặt hàng";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "1900-01-01";
        Date start = jdcStart.getDate();
        Date end = jdcEnd.getDate();
        String startStr = null;
        String endStr = null;
        if (start == null || end == null) {
            try {
                start = sdf.parse(str);
                end = new Date();
            } catch (Exception e) {
            }
        }
        startStr = sdf.format(start);
        endStr = sdf.format(end);
        String text = txtTimKiem.getText().trim();
        dtm = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        dtm.setRowCount(0);
        if (text.isEmpty()) {
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
            this.fillTableHoaDon(billService.fillHoaDon(1));
        } else if (text.equals("Mã hóa đơn - Mã nhân viên - Tên khách hàng")) {
            text = "";
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
            if (trangHD > soTrangHoaDon) {
                trangHD = soTrangHoaDon;
            }
            fillTableHoaDon(billService.timKiem(trangHD, text, loaiHoaDon, trangThai, startStr, endStr));
        } else {
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
            if (trangHD > soTrangHoaDon) {
                trangHD = soTrangHoaDon;
            }
            fillTableHoaDon(billService.timKiem(trangHD, text, loaiHoaDon, trangThai, startStr, endStr));
        }
    }//GEN-LAST:event_cboTrangThaiActionPerformed

    private void txtTimKiem1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiem1FocusGained
        // TODO add your handling code here:
        if (txtTimKiem1.getText().equals("Mã hóa đơn")) {
            txtTimKiem1.setText(null);
            txtTimKiem1.requestFocus();
            removePlacehoder(txtTimKiem1);
        }
    }//GEN-LAST:event_txtTimKiem1FocusGained

    private void txtTimKiem1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiem1FocusLost
        // TODO add your handling code here:
        if (txtTimKiem1.getText().length() == 0) {
            addPlacehoder(txtTimKiem1);
            txtTimKiem1.setText("Mã hóa đơn");
        }
    }//GEN-LAST:event_txtTimKiem1FocusLost

    private void txtTimKiem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiem1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtTimKiem1ActionPerformed

    private void txtTenKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenKhachHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenKhachHangActionPerformed

    private void btnPreAllHDCT1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreAllHDCT1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPreAllHDCT1ActionPerformed

    private void btnXuatExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExcelActionPerformed
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
                for (int i = 0; i < tblHoaDon.getColumnCount(); i++) {
                    Cell cell = rowCol.createCell(i);
                    cell.setCellValue(tblHoaDon.getColumnName(i));
                }
                for (int j = 0; j < tblHoaDon.getRowCount(); j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < tblHoaDon.getColumnCount(); k++) {
                        Cell cell = row.createCell(k);
                        if (tblHoaDon.getValueAt(j, k) != null) {
                            cell.setCellValue(tblHoaDon.getValueAt(j, k).toString());
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
    }//GEN-LAST:event_btnXuatExcelActionPerformed

    private void cboLoaiHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLoaiHoaDonActionPerformed
        String trangThai = null;
        if (cboTrangThai.getSelectedItem().equals("Tất cả")) {
            trangThai = "";
        } else if (cboTrangThai.getSelectedItem().equals("Đã thanh toán")) {
            trangThai = "Đã thanh toán";
        } else if (cboTrangThai.getSelectedItem().equals("Chờ thanh toán")) {
            trangThai = "Chờ thanh toán";
        } else if (cboTrangThai.getSelectedItem().equals("Đã hủy")) {
            trangThai = "Đã hủy";
        }
        String loaiHoaDon = null;
        if (cboLoaiHoaDon.getSelectedItem().equals("Tất cả")) {
            loaiHoaDon = "";
        } else if (cboLoaiHoaDon.getSelectedItem().equals("Bán tại quầy")) {
            loaiHoaDon = "Bán tại quầy";
        } else if (cboLoaiHoaDon.getSelectedItem().equals("Khách đặt hàng")) {
            loaiHoaDon = "Khách đặt hàng";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "1900-01-01";
        Date start = jdcStart.getDate();
        Date end = jdcEnd.getDate();
        String startStr = null;
        String endStr = null;
        if (start == null || end == null) {
            try {
                start = sdf.parse(str);
                end = new Date();
            } catch (Exception e) {
            }
        }
        startStr = sdf.format(start);
        endStr = sdf.format(end);
        String text = txtTimKiem.getText().trim();
        dtm = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        dtm.setRowCount(0);
        if (text.isEmpty()) {
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
            this.fillTableHoaDon(billService.fillHoaDon(1));
        } else if (text.equals("Mã hóa đơn - Mã nhân viên - Tên khách hàng")) {
            text = "";
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
            if (trangHD > soTrangHoaDon) {
                trangHD = soTrangHoaDon;
            }
            fillTableHoaDon(billService.timKiem(trangHD, text, loaiHoaDon, trangThai, startStr, endStr));
        } else {
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
            if (trangHD > soTrangHoaDon) {
                trangHD = soTrangHoaDon;
            }
            fillTableHoaDon(billService.timKiem(trangHD, text, loaiHoaDon, trangThai, startStr, endStr));
        }
    }//GEN-LAST:event_cboLoaiHoaDonActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
        String trangThai = null;
        if (cboTrangThai.getSelectedItem().equals("Tất cả")) {
            trangThai = "";
        } else if (cboTrangThai.getSelectedItem().equals("Đã thanh toán")) {
            trangThai = "Đã thanh toán";
        } else if (cboTrangThai.getSelectedItem().equals("Chờ thanh toán")) {
            trangThai = "Chờ thanh toán";
        } else if (cboTrangThai.getSelectedItem().equals("Đã hủy")) {
            trangThai = "Đã hủy";
        }
        String loaiHoaDon = null;
        if (cboLoaiHoaDon.getSelectedItem().equals("Tất cả")) {
            loaiHoaDon = "";
        } else if (cboLoaiHoaDon.getSelectedItem().equals("Bán tại quầy")) {
            loaiHoaDon = "Bán tại quầy";
        } else if (cboLoaiHoaDon.getSelectedItem().equals("Khách đặt hàng")) {
            loaiHoaDon = "Khách đặt hàng";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "1900-01-01";
        Date start = jdcStart.getDate();
        Date end = jdcEnd.getDate();
        String startStr = null;
        String endStr = null;
        if (start == null || end == null) {
            try {
                start = sdf.parse(str);
                end = new Date();
            } catch (Exception e) {
            }
        }
        startStr = sdf.format(start);
        endStr = sdf.format(end);
        String text = txtTimKiem.getText().trim();
        dtm = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        dtm.setRowCount(0);
        if (text.isEmpty()) {
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
            this.fillTableHoaDon(billService.fillHoaDon(1));
        } else if (text.equals("Mã hóa đơn - Mã nhân viên - Tên khách hàng")) {
            text = "";
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
            if (trangHD > soTrangHoaDon) {
                trangHD = soTrangHoaDon;
            }
            fillTableHoaDon(billService.timKiem(trangHD, text, loaiHoaDon, trangThai, startStr, endStr));
        } else {
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
            if (trangHD > soTrangHoaDon) {
                trangHD = soTrangHoaDon;
            }
            fillTableHoaDon(billService.timKiem(trangHD, text, loaiHoaDon, trangThai, startStr, endStr));
        }
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void jdcStartKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jdcStartKeyReleased
        // TODO add your handling code here:
        String trangThai = null;
        if (cboTrangThai.getSelectedItem().equals("Tất cả")) {
            trangThai = "";
        } else if (cboTrangThai.getSelectedItem().equals("Đã thanh toán")) {
            trangThai = "Đã thanh toán";
        } else if (cboTrangThai.getSelectedItem().equals("Chờ thanh toán")) {
            trangThai = "Chờ thanh toán";
        } else if (cboTrangThai.getSelectedItem().equals("Đã hủy")) {
            trangThai = "Đã hủy";
        }
        String loaiHoaDon = null;
        if (cboLoaiHoaDon.getSelectedItem().equals("Tất cả")) {
            loaiHoaDon = "";
        } else if (cboLoaiHoaDon.getSelectedItem().equals("Bán tại quầy")) {
            loaiHoaDon = "Bán tại quầy";
        } else if (cboLoaiHoaDon.getSelectedItem().equals("Khách đặt hàng")) {
            loaiHoaDon = "Khách đặt hàng";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "1900-01-01";
        Date start = jdcStart.getDate();
        Date end = jdcEnd.getDate();
        String startStr = null;
        String endStr = null;
        if (start == null || end == null) {
            try {
                start = sdf.parse(str);
                end = new Date();
            } catch (Exception e) {
            }
        }
        startStr = sdf.format(start);
        endStr = sdf.format(end);
        String text = txtTimKiem.getText().trim();
        dtm = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        dtm.setRowCount(0);
        if (text.isEmpty()) {
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
            this.fillTableHoaDon(billService.fillHoaDon(1));
        } else if (text.equals("Mã hóa đơn - Mã nhân viên - Tên khách hàng")) {
            text = "";
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
            fillTableHoaDon(billService.timKiem(trangHD, text, loaiHoaDon, trangThai, startStr, endStr));
        } else {
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
            fillTableHoaDon(billService.timKiem(trangHD, text, loaiHoaDon, trangThai, startStr, endStr));
        }
    }//GEN-LAST:event_jdcStartKeyReleased

    private void jdcEndKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jdcEndKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_jdcEndKeyReleased

    private void jdcStartKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jdcStartKeyPressed
        // TODO add your handling code here:
        String trangThai = null;
        if (cboTrangThai.getSelectedItem().equals("Tất cả")) {
            trangThai = "";
        } else if (cboTrangThai.getSelectedItem().equals("Đã thanh toán")) {
            trangThai = "Đã thanh toán";
        } else if (cboTrangThai.getSelectedItem().equals("Chờ thanh toán")) {
            trangThai = "Chờ thanh toán";
        } else if (cboTrangThai.getSelectedItem().equals("Đã hủy")) {
            trangThai = "Đã hủy";
        }
        String loaiHoaDon = null;
        if (cboLoaiHoaDon.getSelectedItem().equals("Tất cả")) {
            loaiHoaDon = "";
        } else if (cboLoaiHoaDon.getSelectedItem().equals("Bán tại quầy")) {
            loaiHoaDon = "Bán tại quầy";
        } else if (cboLoaiHoaDon.getSelectedItem().equals("Khách đặt hàng")) {
            loaiHoaDon = "Khách đặt hàng";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "1900-01-01";
        Date start = jdcStart.getDate();
        Date end = jdcEnd.getDate();
        String startStr = null;
        String endStr = null;
        if (start == null || end == null) {
            try {
                start = sdf.parse(str);
                end = new Date();
            } catch (Exception e) {
            }
        }
        startStr = sdf.format(start);
        endStr = sdf.format(end);
        String text = txtTimKiem.getText().trim();
        dtm = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        dtm.setRowCount(0);
        if (text.isEmpty()) {
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
            this.fillTableHoaDon(billService.fillHoaDon(1));
        } else if (text.equals("Mã hóa đơn - Mã nhân viên - Tên khách hàng")) {
            text = "";
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
            fillTableHoaDon(billService.timKiem(trangHD, text, loaiHoaDon, trangThai, startStr, endStr));
        } else {
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
            fillTableHoaDon(billService.timKiem(trangHD, text, loaiHoaDon, trangThai, startStr, endStr));
        }
    }//GEN-LAST:event_jdcStartKeyPressed

    private void jdcEndKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jdcEndKeyPressed
        // TODO add your handling code here:
        String trangThai = null;
        if (cboTrangThai.getSelectedItem().equals("Tất cả")) {
            trangThai = "";
        } else if (cboTrangThai.getSelectedItem().equals("Đã thanh toán")) {
            trangThai = "Đã thanh toán";
        } else if (cboTrangThai.getSelectedItem().equals("Chờ thanh toán")) {
            trangThai = "Chờ thanh toán";
        } else if (cboTrangThai.getSelectedItem().equals("Đã hủy")) {
            trangThai = "Đã hủy";
        }
        String loaiHoaDon = null;
        if (cboLoaiHoaDon.getSelectedItem().equals("Tất cả")) {
            loaiHoaDon = "";
        } else if (cboLoaiHoaDon.getSelectedItem().equals("Bán tại quầy")) {
            loaiHoaDon = "Bán tại quầy";
        } else if (cboLoaiHoaDon.getSelectedItem().equals("Khách đặt hàng")) {
            loaiHoaDon = "Khách đặt hàng";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "1900-01-01";
        Date start = jdcStart.getDate();
        Date end = jdcEnd.getDate();
        String startStr = null;
        String endStr = null;
        if (start == null || end == null) {
            try {
                start = sdf.parse(str);
                end = new Date();
            } catch (Exception e) {
            }
        }
        startStr = sdf.format(start);
        endStr = sdf.format(end);
        String text = txtTimKiem.getText().trim();
        dtm = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        dtm.setRowCount(0);
        if (text.isEmpty()) {
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
            this.fillTableHoaDon(billService.fillHoaDon(1));
        } else if (text.equals("Mã hóa đơn - Mã nhân viên - Tên khách hàng")) {
            text = "";
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
            fillTableHoaDon(billService.timKiem(trangHD, text, loaiHoaDon, trangThai, startStr, endStr));
        } else {
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
            fillTableHoaDon(billService.timKiem(trangHD, text, loaiHoaDon, trangThai, startStr, endStr));
        }
    }//GEN-LAST:event_jdcEndKeyPressed

    private void jdcStartKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jdcStartKeyTyped
        // TODO add your handling code here:
        String trangThai = null;
        if (cboTrangThai.getSelectedItem().equals("Tất cả")) {
            trangThai = "";
        } else if (cboTrangThai.getSelectedItem().equals("Đã thanh toán")) {
            trangThai = "Đã thanh toán";
        } else if (cboTrangThai.getSelectedItem().equals("Chờ thanh toán")) {
            trangThai = "Chờ thanh toán";
        } else if (cboTrangThai.getSelectedItem().equals("Đã hủy")) {
            trangThai = "Đã hủy";
        }
        String loaiHoaDon = null;
        if (cboLoaiHoaDon.getSelectedItem().equals("Tất cả")) {
            loaiHoaDon = "";
        } else if (cboLoaiHoaDon.getSelectedItem().equals("Bán tại quầy")) {
            loaiHoaDon = "Bán tại quầy";
        } else if (cboLoaiHoaDon.getSelectedItem().equals("Khách đặt hàng")) {
            loaiHoaDon = "Khách đặt hàng";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "1900-01-01";
        Date start = jdcStart.getDate();
        Date end = jdcEnd.getDate();
        String startStr = null;
        String endStr = null;
        if (start == null || end == null) {
            try {
                start = sdf.parse(str);
                end = new Date();
            } catch (Exception e) {
            }
        }
        startStr = sdf.format(start);
        endStr = sdf.format(end);
        String text = txtTimKiem.getText().trim();
        dtm = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        dtm.setRowCount(0);
        if (text.isEmpty()) {
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
            this.fillTableHoaDon(billService.fillHoaDon(1));
        } else if (text.equals("Mã hóa đơn - Mã nhân viên - Tên khách hàng")) {
            text = "";
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
            fillTableHoaDon(billService.timKiem(trangHD, text, loaiHoaDon, trangThai, startStr, endStr));
        } else {
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
            fillTableHoaDon(billService.timKiem(trangHD, text, loaiHoaDon, trangThai, startStr, endStr));
        }
    }//GEN-LAST:event_jdcStartKeyTyped

    private void jdcEndKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jdcEndKeyTyped
        // TODO add your handling code here:
        String trangThai = null;
        if (cboTrangThai.getSelectedItem().equals("Tất cả")) {
            trangThai = "";
        } else if (cboTrangThai.getSelectedItem().equals("Đã thanh toán")) {
            trangThai = "Đã thanh toán";
        } else if (cboTrangThai.getSelectedItem().equals("Chờ thanh toán")) {
            trangThai = "Chờ thanh toán";
        } else if (cboTrangThai.getSelectedItem().equals("Đã hủy")) {
            trangThai = "Đã hủy";
        }
        String loaiHoaDon = null;
        if (cboLoaiHoaDon.getSelectedItem().equals("Tất cả")) {
            loaiHoaDon = "";
        } else if (cboLoaiHoaDon.getSelectedItem().equals("Bán tại quầy")) {
            loaiHoaDon = "Bán tại quầy";
        } else if (cboLoaiHoaDon.getSelectedItem().equals("Khách đặt hàng")) {
            loaiHoaDon = "Khách đặt hàng";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "1900-01-01";
        Date start = jdcStart.getDate();
        Date end = jdcEnd.getDate();
        String startStr = null;
        String endStr = null;
        if (start == null || end == null) {
            try {
                start = sdf.parse(str);
                end = new Date();
            } catch (Exception e) {
            }
        }
        startStr = sdf.format(start);
        endStr = sdf.format(end);
        String text = txtTimKiem.getText().trim();
        dtm = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        dtm.setRowCount(0);
        if (text.isEmpty()) {
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
            this.fillTableHoaDon(billService.fillHoaDon(1));
        } else if (text.equals("Mã hóa đơn - Mã nhân viên - Tên khách hàng")) {
            text = "";
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
            fillTableHoaDon(billService.timKiem(trangHD, text, loaiHoaDon, trangThai, startStr, endStr));
        } else {
            soTrangHoaDon = billService.countHoaDon(text, loaiHoaDon, trangThai, startStr, endStr).size();
            this.countTrang();
            fillTableHoaDon(billService.timKiem(trangHD, text, loaiHoaDon, trangThai, startStr, endStr));
        }
    }//GEN-LAST:event_jdcEndKeyTyped

    private void btnInHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInHoaDonActionPerformed
        // TODO add your handling code here:
        index = tblHoaDon.getSelectedRow();
        int id = Integer.parseInt(tblHoaDon.getValueAt(index, 1).toString());
        ModelBill modelBill = billService.selectHoaDonById(id);
        byte[] qrCodeBytes = createQrCodeFromText("     " + String.valueOf(id) + "    ");
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn file để lưu");
            fileChooser.setCurrentDirectory(new File("D:\\PDF_HOADON"));
            int result = fileChooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);
                Paragraph paragraph = new Paragraph();
                paragraph.add("Mã hóa đơn:                    " + modelBill.getId() + "\n");
                paragraph.add("Mã nhân viên:                " + modelBill.getTenNhanVien() + "\n");
                paragraph.add("Tên khách hàng:             " + modelBill.getTenKhachHang() + "\n");
                paragraph.add("Tên người nhận:             " + modelBill.getTenNguoiNhan() + "\n");
                paragraph.add("Số điện thoại:                 " + modelBill.getSoDienThoai() + "\n");
                paragraph.add("Địa chỉ:                           " + modelBill.getDiaChi() + "\n");
                String tongTien = "Tổng tiền:                       " + String.valueOf(String.format("%,.0f", modelBill.getTongTien()));
                String tienMat = "Tiền mặt:                        " + String.valueOf(String.format("%,.0f", modelBill.getTienMat()));
                String tienChuyenKhoan = "Tiền chuyển khoản:       " + String.valueOf(String.format("%,.0f", modelBill.getTienChuyenKhoan()));
                paragraph.add("Tiền ship:                       " + String.format("%,.0f", modelBill.getTienShip()) + "\n");
                paragraph.add(tongTien + "\n");
                paragraph.add(tienMat + "\n");
                paragraph.add(tienChuyenKhoan + "\n");
                paragraph.add("Loại hóa đơn:                 " + modelBill.getLoaiHoaDon() + "\n");
                paragraph.add("Mã voucher:                   " + modelBill.getMaVoucher() + "\n");
                paragraph.add("Trạng thái:                     " + modelBill.getTrangThai() + "\n");
                paragraph.add("Ngày tạo:                       " + modelBill.getNgayTao());
                paragraph.setFont(PdfFontFactory.createFont("D:\\Library\\arial-unicode-ms.ttf",
                        PdfEncodings.IDENTITY_H, EmbeddingStrategy.PREFER_EMBEDDED));
                ImageData data = ImageDataFactory.create(qrCodeBytes);
                Image img = new Image(data);
                document.setLeftMargin(170);
                document.add(paragraph);
                document.add(img);
                document.close();
                JOptionPane.showMessageDialog(this, "Tạo file hóa đơn thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Tạo file hóa đơn thất bại");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnInHoaDonActionPerformed

    private void txtTimKiem1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiem1KeyReleased
        // TODO add your handling code here:

        if (!txtTimKiem1.getText().trim().isEmpty()) {
            int id = Integer.parseInt(txtTimKiem1.getText());
            ModelBill modelBill = billService.selectHoaDonById(id);
            txtDiaChi.setText(modelBill.getDiaChi());
            txtLoaiHoaDon.setText(modelBill.getLoaiHoaDon());
            txtMaVoucher.setText(modelBill.getMaVoucher());
            txtSoDienThoai.setText(modelBill.getSoDienThoai());
            txtTenKhachHang.setText(modelBill.getTenKhachHang());
            txtTrangThai.setText(modelBill.getTrangThai());
            txtTenNhanVien.setText(modelBill.getTenNhanVien() + "");
            txtMaHoaDon.setText(id + "");
            txtNgayTao.setText(modelBill.getNgayTao() + "");
            txtTenNguoiNhan.setText(modelBill.getTenNguoiNhan());
            txtTienChuyenKhoan.setText(String.format("%,.0f", modelBill.getTienChuyenKhoan()));
            txtTienMat.setText(String.format("%,.0f", modelBill.getTienMat()));
            txtTienShip.setText(String.format("%,.0f", modelBill.getTienShip()));
            txtTongTien.setText(String.format("%,.0f", modelBill.getTongTien()));
            fillTableHoaDonChiTiet1(billService.fillHoaDonChiTiet(id, trangHDCT));
        }


    }//GEN-LAST:event_txtTimKiem1KeyReleased

    private void btnQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQRActionPerformed
        // TODO add your handling code here:
        QrHoaDon viewQr = new QrHoaDon(null, true);
        viewQr.setVisible(true);
        viewQr.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (QrHoaDon.mahd != null) {
                    ModelBill modelBill = billService.selectHoaDonById(Integer.parseInt(QrHoaDon.mahd.trim()));
                    txtDiaChi.setText(modelBill.getDiaChi());
                    txtLoaiHoaDon.setText(modelBill.getLoaiHoaDon());
                    txtMaVoucher.setText(modelBill.getMaVoucher());
                    txtSoDienThoai.setText(modelBill.getSoDienThoai());
                    txtTenKhachHang.setText(modelBill.getTenKhachHang());
                    txtTrangThai.setText(modelBill.getTrangThai());
                    txtTenNhanVien.setText(modelBill.getTenNhanVien() + "");
                    txtMaHoaDon.setText(modelBill.getId() + "");
                    txtNgayTao.setText(modelBill.getNgayTao() + "");
                    txtTenNguoiNhan.setText(modelBill.getTenNguoiNhan());
                    txtTienChuyenKhoan.setText(String.format("%,.0f", modelBill.getTienChuyenKhoan()));
                    txtTienMat.setText(String.format("%,.0f", modelBill.getTienMat()));
                    txtTienShip.setText(String.format("%,.0f", modelBill.getTienShip()));
                    txtTongTien.setText(String.format("%,.0f", modelBill.getTongTien()));
                    fillTableHoaDonChiTiet1(billService.fillHoaDonChiTiet(Integer.parseInt(QrHoaDon.mahd.trim()), trangHDCT));
                }
            
            }
        });
    }//GEN-LAST:event_btnQRActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInHoaDon;
    private javax.swing.JButton btnNextAllHDCT;
    private javax.swing.JButton btnNextAllHDCT1;
    private javax.swing.JButton btnNextAllHoaDon;
    private javax.swing.JButton btnNextAllLichSu;
    private javax.swing.JButton btnNextHDCT;
    private javax.swing.JButton btnNextHDCT1;
    private javax.swing.JButton btnNextHoaDon;
    private javax.swing.JButton btnNextLichSu;
    private javax.swing.JButton btnPreAllHDCT;
    private javax.swing.JButton btnPreAllHDCT1;
    private javax.swing.JButton btnPreAllHoaDon;
    private javax.swing.JButton btnPreAllLichSu;
    private javax.swing.JButton btnPreHDCT;
    private javax.swing.JButton btnPreHDCT1;
    private javax.swing.JButton btnPreHoaDon;
    private javax.swing.JButton btnPreLichSu;
    private javax.swing.JButton btnQR;
    private javax.swing.JButton btnXemChiTiet;
    private javax.swing.JButton btnXuatExcel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboLoaiHoaDon;
    private javax.swing.JComboBox<String> cboTrangThai;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.toedter.calendar.JDateChooser jdcEnd;
    private com.toedter.calendar.JDateChooser jdcStart;
    private javax.swing.JLabel lblTrangHDCT;
    private javax.swing.JLabel lblTrangHDCT1;
    private javax.swing.JLabel lblTrangHoaDon;
    private javax.swing.JLabel lblTrangLichSu;
    private javax.swing.JTable tblHDCT1;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblHoaDonChiTiet;
    private javax.swing.JTable tblLichSuDonHang;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txtLoaiHoaDon;
    private javax.swing.JTextField txtMaHoaDon;
    private javax.swing.JTextField txtMaVoucher;
    private javax.swing.JTextField txtNgayTao;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTenKhachHang;
    private javax.swing.JTextField txtTenNguoiNhan;
    private javax.swing.JTextField txtTenNhanVien;
    private javax.swing.JTextField txtTienChuyenKhoan;
    private javax.swing.JTextField txtTienMat;
    private javax.swing.JTextField txtTienShip;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTimKiem1;
    private javax.swing.JTextField txtTongTien;
    private javax.swing.JTextField txtTrangThai;
    // End of variables declaration//GEN-END:variables
}
