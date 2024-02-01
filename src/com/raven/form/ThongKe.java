package com.raven.form;

import com.raven.chart.ModelChart;
import com.raven.model.ModelCard;
import com.raven.services.ThongKeService;
import com.raven.services.impl.ThongKeServiceIplm;
import com.raven.swing.icon.GoogleMaterialDesignIcons;
import com.raven.swing.icon.IconFontSwing;
import com.raven.viewmodels.ThongKeDayInMonth;
import com.raven.viewmodels.ThongKeDayToDay;
import com.raven.viewmodels.ThongKeGetYear;
import com.raven.viewmodels.ThongKeMonthInYear;
import com.raven.viewmodels.ThongKeReponse;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import jdk.jfr.consumer.EventStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author nguye
 */
public class ThongKe extends javax.swing.JPanel {

    List<ThongKeReponse> listThongKe = new ArrayList<>();
    List<ThongKeGetYear> listThongKeByYear = new ArrayList<>();
    List<ThongKeDayInMonth> listThongKeDayInMonth = new ArrayList<>();
    List<ThongKeMonthInYear> listMonthInYear = new ArrayList<>();
    List<ThongKeDayToDay> listThongKeDayToDay = new ArrayList<>();
    ThongKeService ser = new ThongKeServiceIplm();
    DefaultTableModel modelTongQuan = new DefaultTableModel();
    DefaultTableModel modelThongKeYear = new DefaultTableModel();
    DefaultTableModel modelThongKeMonthInYear = new DefaultTableModel();
    DefaultTableModel modelThongKeDayInMonth = new DefaultTableModel();
    DefaultTableModel modelThongKeDayToDay = new DefaultTableModel();
    DecimalFormat decimalFormat = new DecimalFormat("###,###.##");
    DefaultComboBoxModel<ThongKeGetYear> comboboxPnl1 = new DefaultComboBoxModel<>();

    public ThongKe() {
        initComponents();
        init();

    }

    void init() {
        intCardThongKe();
        modelTongQuan = (DefaultTableModel) tblDoanhThu8Day.getModel();
        modelThongKeYear = (DefaultTableModel) tblThongKeByYear.getModel();
        modelThongKeMonthInYear = (DefaultTableModel) tblThongKeMonthInYear.getModel();
        modelThongKeDayInMonth = (DefaultTableModel) tblThongKeDayInMonth.getModel();
        modelThongKeDayToDay = (DefaultTableModel) tblThongKeDayToDay.getModel();
        showData(ser.getThongKeTop8());
        loadComBoBoxPanel1(listThongKeByYear = ser.loadComBoBoxYear());
        showDataByYear(ser.getThongKeYear());
        loadComBoBoxMonthInYear(listThongKeByYear);
        showDataMonthInYear(ser.getThongKeMonthInYear());
        showDataDayInMonth(ser.getThongKeDayInMonth());
        showDataDayToDay(ser.getThongKeDayToDay());
        EventEditByDateStart();
        EventEditByDateEnd();
        eventFindMonthByYear();
        EventEditByDayInMoth();
        chartLoadTongQuan();
        chartStatisticalByYear();
        chartStatiscalMonthInYear();
        chartStatisMonthInYear();
        chartStatisDayToDay();

    }

    void chartLoadTongQuan() {
        chartTongQuan.addLegend("Doanh thu", Color.yellow, Color.black);

        for (int i = 0; i < tblDoanhThu8Day.getRowCount(); i++) {
            Object value = tblDoanhThu8Day.getValueAt(i, 0);
            String doanhThuStr = tblDoanhThu8Day.getValueAt(i, 1).toString().replaceAll("[^\\d]", ""); // Loại bỏ tất cả trừ chữ số

            try {
                double doanhThu = Double.parseDouble(doanhThuStr);
                chartTongQuan.addData(new ModelChart(String.valueOf(value), new double[]{doanhThu, 200, 80, 89}));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Doanh thu phải là số");
            }
        }
        chartTongQuan.start();

    }

    void chartStatisticalByYear() {
        chartYear.addLegend("Doanh thu", Color.yellow, Color.black);
        for (int i = 0; i < tblThongKeByYear.getRowCount(); i++) {
            Object year = tblThongKeByYear.getValueAt(i, 0).toString();
            System.out.println(" year :" + year);
            String doanhThuStr = tblThongKeByYear.getValueAt(i, 1).toString().replaceAll("[^\\d]", "");
            try {
                double doanhThu = Double.parseDouble(doanhThuStr);
                chartYear.addData(new ModelChart(String.valueOf(year), new double[]{doanhThu, 200, 80, 100}));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Doanh thu phải là số");
            }

        }
        chartYear.start();
    }

    void chartStatiscalMonthInYear() {
        chartThongKeMonthInYear.addLegend("Doanh thu", Color.yellow, Color.black);
        for (int i = 0; i < tblThongKeMonthInYear.getRowCount(); i++) {
            Object month = tblThongKeMonthInYear.getValueAt(i, 0).toString();
            String doanhThuStr = tblThongKeMonthInYear.getValueAt(i, 2).toString().replaceAll("[^\\d]", "");
            try {
                double doanhThu = Double.parseDouble(doanhThuStr);
                chartThongKeMonthInYear.addData(new ModelChart(String.valueOf(month), new double[]{doanhThu, 200, 100, 80}));
            } catch (Exception e) {
                System.out.println("Doanh thu phải là số");
            }

        }
        chartThongKeMonthInYear.start();
    }

    void chartStatisMonthInYear() {
        chartDayInMonth.addLegend("Doanh thu ", Color.yellow, Color.black);
        for (int i = 0; i < tblThongKeDayInMonth.getRowCount(); i++) {
            String day = tblThongKeDayInMonth.getValueAt(i, 0).toString();
            String month = tblThongKeDayInMonth.getValueAt(i, 1).toString();
            String year = tblThongKeDayInMonth.getValueAt(i, 2).toString();
            String doanhThuStr = tblThongKeDayInMonth.getValueAt(i, 3).toString().replaceAll("[^\\d]", "");
            try {
                double doanhThu = Double.parseDouble(doanhThuStr);
                chartDayInMonth.addData(new ModelChart(day + "/" + month + "/" + year, new double[]{doanhThu, 200, 100, 80}));
            } catch (Exception e) {
                System.out.println("Doanh thu phải là số");
            }

        }
        chartDayInMonth.start();
    }

    void chartStatisDayToDay() {
        chartDayToDay.addLegend("Doanh thu ", Color.yellow, Color.black);
        for (int i = 0; i < tblThongKeDayToDay.getRowCount(); i++) {
            String day = tblThongKeDayToDay.getValueAt(i, 0).toString();
            String doanhThuStr = tblThongKeDayToDay.getValueAt(i, 1).toString().replaceAll("[^\\d]", "");
            try {
                double doanhThu = Double.parseDouble(doanhThuStr);
                chartDayToDay.addData(new ModelChart(day, new double[]{doanhThu, 200, 100, 80}));
            } catch (Exception e) {
            }

        }
        chartDayToDay.start();
    }

    void intCardThongKe() {
        int soLuongLaptop = ser.getSoLuongLaptop();
        int soLuongCustomer = ser.getSoLuongKhachHang();
        int soLuongNhanVien = ser.getSoLuongNhanVien();
        float tongDoanhThu = ser.getTongDoanhThu();

        Color colorCardSanPham = new Color(0, 128, 0); // Màu xanh lá cây
        Color colorCardNhanVien = new Color(106, 13, 173); // Màu tím
        Color colorCardKhachHang = new Color(255, 100, 0);
        Color colorCardDoanhThu = new Color(0, 0, 255);
        Color colorWhite = new Color(255, 255, 255); // Màu trắng

        cardSanPham.setBackground(colorCardSanPham);
        cardNhanVien.setBackground(colorCardNhanVien);
        cardKhachHang.setBackground(colorCardKhachHang);
        cardDoanhThu.setBackground(colorCardDoanhThu);

        Icon icon1 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.LAPTOP, 60, colorWhite, colorWhite);
        cardSanPham.setData(new ModelCard("Sản phẩm", soLuongLaptop, 20, icon1));
        cardSanPham.setForeground(colorWhite);

        Icon icon2 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.BUSINESS_CENTER, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        cardNhanVien.setData(new ModelCard("Nhân viên", soLuongNhanVien, 60, icon2));
        cardNhanVien.setForeground(colorWhite);

        Icon icon3 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.SHOPPING_BASKET, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        cardKhachHang.setData(new ModelCard("Khách hàng", soLuongCustomer, 80, icon3));
        cardKhachHang.setForeground(colorWhite);

        Icon icon4 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.MONETIZATION_ON, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        cardDoanhThu.setData(new ModelCard("Doanh thu", tongDoanhThu, 95, icon4));
        cardDoanhThu.setForeground(colorWhite);

    }

    void showData(List<ThongKeReponse> listThongKe) {
        modelTongQuan.setRowCount(0);
        for (ThongKeReponse tk : listThongKe) {
            modelTongQuan.addRow(new Object[]{
                tk.getNgay(),
                decimalFormat.format(tk.getTongTien()) + " VNĐ"
            });

        }
    }

    void showDataByYear(List<ThongKeGetYear> listThongKe) {
        modelThongKeYear.setRowCount(0);
        for (ThongKeGetYear tk : listThongKe) {
            modelThongKeYear.addRow(new Object[]{
                tk.getYear(),
                decimalFormat.format(tk.getDoanhThu()) + " VNĐ"
            });

        }
    }

    void loadToTableByCboYear() {
        String year = cboYearInPane1.getSelectedItem().toString();
        int yearINT = Integer.parseInt(year);
        System.out.println(yearINT);
        listThongKeByYear = ser.getThongKeByYear(yearINT);
        modelThongKeYear.setRowCount(0);
        for (ThongKeGetYear tk : listThongKeByYear) {
            modelThongKeYear.addRow(new Object[]{
                tk.getYear(),
                decimalFormat.format(tk.getDoanhThu()) + " VNĐ"
            });

        }

    }

    void showDataMonthInYear(List<ThongKeMonthInYear> listThongKe) {
        modelThongKeMonthInYear.setRowCount(0);
        for (ThongKeMonthInYear tk : listThongKe) {
            modelThongKeMonthInYear.addRow(new Object[]{
                tk.getMonth(),
                tk.getYear(),
                decimalFormat.format(tk.getDoanhThu()) + " VNĐ"
            });
        }
    }

    void FinMonthByYear(List<ThongKeMonthInYear> listThongKe) {
        int selectedYear = Integer.parseInt(cboMonthInYear.getSelectedItem().toString());
        modelThongKeMonthInYear.setRowCount(0);
        for (ThongKeMonthInYear tk : listThongKe) {
            modelThongKeMonthInYear.addRow(new Object[]{
                tk.getMonth(),
                selectedYear,
                decimalFormat.format(tk.getDoanhThu()) + " VNĐ"
            });
        }
    }

    void showDataDayInMonth(List<ThongKeDayInMonth> listThongKe) {
        modelThongKeDayInMonth.setRowCount(0);
        for (ThongKeDayInMonth tk : listThongKe) {
            modelThongKeDayInMonth.addRow(new Object[]{
                tk.getDay(),
                tk.getMonth(),
                tk.getYear(),
                decimalFormat.format(tk.getDoanhThu()) + " VNĐ"
            }
            );
        }
    }

    void showDataDayToDay(List<ThongKeDayToDay> listThongKe) {
        modelThongKeDayToDay.setRowCount(0);
        for (ThongKeDayToDay tk : listThongKe) {
            modelThongKeDayToDay.addRow(new Object[]{
                tk.getNgayTao(),
                decimalFormat.format(tk.getDoanhThu()) + " VNĐ"
            }
            );

        }

    }

    void loadComBoBoxPanel1(List<ThongKeGetYear> listYear) {
        Set<String> setYear = new HashSet<>();

        for (ThongKeGetYear y : listYear) {
            setYear.add(y.toString());
        }
        comboboxPnl1 = (DefaultComboBoxModel) new DefaultComboBoxModel<>(setYear.toArray());
        cboYearInPane1.setModel((DefaultComboBoxModel) comboboxPnl1);

    }

    void loadComBoBoxMonthInYear(List<ThongKeGetYear> listYear) {
        Set<String> setYear = new HashSet<>();

        for (ThongKeGetYear y : listYear) {
            setYear.add(y.toString());
        }
        comboboxPnl1 = (DefaultComboBoxModel) new DefaultComboBoxModel<>(setYear.toArray());
        cboMonthInYear.setModel((DefaultComboBoxModel) comboboxPnl1);
    }

    void editByDate() {
        java.util.Date utildateStart = dateNgayBatdau.getDate();
        java.util.Date utildateEnd = dateNgayKetThuc.getDate();
        if (utildateStart != null && utildateEnd != null) {
            java.sql.Date ngayBatDauDate = new java.sql.Date(utildateStart.getTime());
            java.sql.Date ngayKetThucDate = new java.sql.Date(utildateEnd.getTime());
            try {
                listThongKeDayToDay = ser.getThongKeByDayToDay(ngayBatDauDate, ngayKetThucDate);
                showDataDayToDay(listThongKeDayToDay);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    void EventEditByDateStart() {
        dateNgayBatdau.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                editByDate();

            }
        });
    }

    void EventEditByDateEnd() {
        dateNgayKetThuc.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                editByDate();

            }
        });
    }

//    void loadToTableByMonthInYear() {
//        int selectedMonth = dateMonth.getMonth() + 1;
//        String selectedYear = cboMonthInYear.getSelectedItem().toString();
//        int selectedYearInt = Integer.parseInt(selectedYear);
//        try {
//            listMonthInYear = ser.getThongKeByMonthInYear(selectedMonth, selectedYearInt);
//            showDataMonthInYear(listMonthInYear);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("Selected Month: " + selectedMonth);
//        System.out.println("Selected Year: " + selectedYearInt);
//    }
    void loadToTableByDayInMonth() {
        Date selectedDate = dateDayInMonth.getDate();

        if (selectedDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectedDate);

            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);

            try {
                listThongKeDayInMonth = ser.getThongKeByDayToMonth(day, month, year);
                showDataDayInMonth(listThongKeDayInMonth);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Day :" + day);
            System.out.println("Month :" + month);
        }
    }

    void EventEditByDayInMoth() {
        dateDayInMonth.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                loadToTableByDayInMonth();
            }
        });
    }

    void loadTableFindMothByYear() {
        int yearStr = Integer.parseInt(cboMonthInYear.getSelectedItem().toString());
        FinMonthByYear(ser.FinMonthByYear(yearStr));

    }

    void eventFindMonthByYear() {
        cboMonthInYear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTableFindMothByYear();
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        tabTong = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        cardSanPham = new com.raven.component.Card();
        cardNhanVien = new com.raven.component.Card();
        cardKhachHang = new com.raven.component.Card();
        cardDoanhThu = new com.raven.component.Card();
        chartTongQuan = new com.raven.chart.Chart();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDoanhThu8Day = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        tabDoanhThu = new javax.swing.JTabbedPane();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblThongKeByYear = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        cboYearInPane1 = new javax.swing.JComboBox<>();
        chartYear = new com.raven.chart.Chart();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblThongKeMonthInYear = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        cboMonthInYear = new javax.swing.JComboBox<>();
        btnExportMothInYear = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        chartThongKeMonthInYear = new com.raven.chart.Chart();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblThongKeDayInMonth = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        btnExportDayInMonnth = new javax.swing.JButton();
        dateDayInMonth = new com.toedter.calendar.JDateChooser();
        jLabel17 = new javax.swing.JLabel();
        chartDayInMonth = new com.raven.chart.Chart();
        jPanel14 = new javax.swing.JPanel();
        dateNgayBatdau = new com.toedter.calendar.JDateChooser();
        dateNgayKetThuc = new com.toedter.calendar.JDateChooser();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblThongKeDayToDay = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        btnExportDayToDay = new javax.swing.JButton();
        chartDayToDay = new com.raven.chart.Chart();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("THỐNG KÊ");

        tabTong.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Doanh thu 8 ngày gần nhất"));

        tblDoanhThu8Day.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Ngày", "Doanh thu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblDoanhThu8Day);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1037, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(chartTongQuan, javax.swing.GroupLayout.PREFERRED_SIZE, 1053, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cardSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cardNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(cardKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(cardDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cardSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(chartTongQuan, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 1074, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabTong.addTab("Tổng quan", jPanel6);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        tblThongKeByYear.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Năm", "Doanh thu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblThongKeByYear);

        jButton1.setBackground(new java.awt.Color(51, 153, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/icons8-change-50 (2).png"))); // NOI18N
        jButton1.setText("Khôi phục");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(51, 153, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/expoert.png"))); // NOI18N
        jButton2.setText("Export Excel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel18.setText("Năm");

        cboYearInPane1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboYearInPane1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboYearInPane1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chartYear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboYearInPane1, 0, 151, Short.MAX_VALUE)
                        .addGap(573, 573, 573)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1076, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel11Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton1, jButton2});

        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton2))
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cboYearInPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chartYear, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(185, 185, 185))
        );

        jPanel11Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton2});

        tabDoanhThu.addTab("Thống kê theo năm", jPanel11);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        tblThongKeMonthInYear.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Tháng", "Năm", "Doanh thu"
            }
        ));
        tblThongKeMonthInYear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThongKeMonthInYearMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblThongKeMonthInYearMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tblThongKeMonthInYear);

        jLabel14.setText("Chọn năm thống kê");

        cboMonthInYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboMonthInYear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboMonthInYearMouseClicked(evt);
            }
        });
        cboMonthInYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMonthInYearActionPerformed(evt);
            }
        });

        btnExportMothInYear.setBackground(new java.awt.Color(102, 153, 0));
        btnExportMothInYear.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnExportMothInYear.setForeground(new java.awt.Color(255, 255, 255));
        btnExportMothInYear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/expoert.png"))); // NOI18N
        btnExportMothInYear.setText("Export Excel");
        btnExportMothInYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportMothInYearActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(51, 153, 0));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/icons8-change-50 (2).png"))); // NOI18N
        jButton3.setText("Khôi phục");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(chartThongKeMonthInYear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboMonthInYear, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 461, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(btnExportMothInYear)))
                .addContainerGap())
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel12Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3)
                    .addContainerGap()))
        );

        jPanel12Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnExportMothInYear, jButton3});

        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboMonthInYear, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(btnExportMothInYear)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chartThongKeMonthInYear, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(365, Short.MAX_VALUE))
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel12Layout.createSequentialGroup()
                    .addGap(319, 319, 319)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(104, Short.MAX_VALUE)))
        );

        jPanel12Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnExportMothInYear, jButton3});

        tabDoanhThu.addTab("Thống kê từng tháng trong năm", jPanel12);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        tblThongKeDayInMonth.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Ngày", "Tháng", "Năm", "Doanh thu"
            }
        ));
        jScrollPane4.setViewportView(tblThongKeDayInMonth);

        jButton6.setBackground(new java.awt.Color(51, 153, 0));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/icons8-change-50 (2).png"))); // NOI18N
        jButton6.setText("Khôi phục");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        btnExportDayInMonnth.setBackground(new java.awt.Color(51, 153, 0));
        btnExportDayInMonnth.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnExportDayInMonnth.setForeground(new java.awt.Color(255, 255, 255));
        btnExportDayInMonnth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/expoert.png"))); // NOI18N
        btnExportDayInMonnth.setText("Export Excel");
        btnExportDayInMonnth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportDayInMonnthActionPerformed(evt);
            }
        });

        jLabel17.setText("Chọn ngày trong tháng");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dateDayInMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 427, Short.MAX_VALUE)
                .addComponent(jButton6)
                .addGap(18, 18, 18)
                .addComponent(btnExportDayInMonnth)
                .addContainerGap())
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(chartDayInMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 1053, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel13Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane4)
                    .addContainerGap()))
        );

        jPanel13Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnExportDayInMonnth, jButton6});

        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnExportDayInMonnth)
                        .addComponent(jButton6))
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(dateDayInMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17)))
                .addGap(48, 48, 48)
                .addComponent(chartDayInMonth, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                .addGap(324, 324, 324))
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel13Layout.createSequentialGroup()
                    .addGap(407, 407, 407)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(19, Short.MAX_VALUE)))
        );

        jPanel13Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnExportDayInMonnth, jButton6});

        tabDoanhThu.addTab("Thống kê từng ngày trong tháng", jPanel13);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        dateNgayBatdau.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                dateNgayBatdauKeyReleased(evt);
            }
        });

        dateNgayKetThuc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                dateNgayKetThucKeyReleased(evt);
            }
        });

        jLabel15.setText("Từ ngày");

        tblThongKeDayToDay.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Ngày tạo", "Doanh thu"
            }
        ));
        jScrollPane5.setViewportView(tblThongKeDayToDay);

        jLabel16.setText("Đến ngày");

        jButton4.setBackground(new java.awt.Color(51, 153, 0));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/icons8-change-50 (2).png"))); // NOI18N
        jButton4.setText("Khôi phục");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        btnExportDayToDay.setBackground(new java.awt.Color(51, 153, 0));
        btnExportDayToDay.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnExportDayToDay.setForeground(new java.awt.Color(255, 255, 255));
        btnExportDayToDay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/expoert.png"))); // NOI18N
        btnExportDayToDay.setText("Export Excel");
        btnExportDayToDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportDayToDayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(chartDayToDay, javax.swing.GroupLayout.PREFERRED_SIZE, 1053, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateNgayBatdau, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addGap(18, 18, 18)
                        .addComponent(btnExportDayToDay)))
                .addGap(29, 29, 29))
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel14Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane5)
                    .addContainerGap()))
        );

        jPanel14Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnExportDayToDay, jButton4});

        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton4)
                        .addComponent(btnExportDayToDay, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel16)
                        .addComponent(dateNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dateNgayBatdau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chartDayToDay, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                .addGap(360, 360, 360))
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel14Layout.createSequentialGroup()
                    .addGap(368, 368, 368)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(104, Short.MAX_VALUE)))
        );

        jPanel14Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnExportDayToDay, jButton4});

        tabDoanhThu.addTab("Thống kê từ ngày đến ngày", jPanel14);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabDoanhThu)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(tabDoanhThu))
        );

        tabTong.addTab("Doanh thu", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tabTong))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(478, 478, 478)
                        .addComponent(jLabel2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabTong, javax.swing.GroupLayout.PREFERRED_SIZE, 732, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(90, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboYearInPane1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboYearInPane1ActionPerformed
        // TODO add your handling code here:
        loadToTableByCboYear();
    }//GEN-LAST:event_cboYearInPane1ActionPerformed

    private void dateNgayBatdauKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dateNgayBatdauKeyReleased

    }//GEN-LAST:event_dateNgayBatdauKeyReleased

    private void dateNgayKetThucKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dateNgayKetThucKeyReleased

    }//GEN-LAST:event_dateNgayKetThucKeyReleased

    private void cboMonthInYearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboMonthInYearMouseClicked
//        loadToTableByMonthInYear();
    }//GEN-LAST:event_cboMonthInYearMouseClicked

    private void cboMonthInYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMonthInYearActionPerformed
//        loadToTableByMonthInYear();

    }//GEN-LAST:event_cboMonthInYearActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
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
                for (int i = 0; i < tblThongKeByYear.getColumnCount(); i++) {
                    Cell cell = rowCol.createCell(i);
                    cell.setCellValue(tblThongKeByYear.getColumnName(i));
                }
                for (int j = 0; j < tblThongKeByYear.getRowCount(); j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < tblThongKeByYear.getColumnCount(); k++) {
                        Cell cell = row.createCell(k);
                        if (tblThongKeByYear.getValueAt(j, k) != null) {
                            cell.setCellValue(tblThongKeByYear.getValueAt(j, k).toString());
                        }
                    }
                }
                FileOutputStream out = new FileOutputStream(new File(saveFile.toString()));
                wb.write(out);
                wb.close();
                out.close();
                EventStream.openFile(saveFile.toPath());
                JOptionPane.showMessageDialog(this, "Export Excel thành công ");
                showDataByYear(ser.getThongKeYear());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnExportMothInYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportMothInYearActionPerformed
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
                for (int i = 0; i < tblThongKeMonthInYear.getColumnCount(); i++) {
                    Cell cell = rowCol.createCell(i);
                    cell.setCellValue(tblThongKeMonthInYear.getColumnName(i));
                }
                for (int j = 0; j < tblThongKeMonthInYear.getRowCount(); j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < tblThongKeMonthInYear.getColumnCount(); k++) {
                        Cell cell = row.createCell(k);
                        if (tblThongKeMonthInYear.getValueAt(j, k) != null) {
                            cell.setCellValue(tblThongKeMonthInYear.getValueAt(j, k).toString());
                        }
                    }
                }
                FileOutputStream out = new FileOutputStream(new File(saveFile.toString()));
                wb.write(out);
                wb.close();
                out.close();
                EventStream.openFile(saveFile.toPath());
                JOptionPane.showMessageDialog(this, "Export Excel thành công ");
                showDataMonthInYear(ser.getThongKeMonthInYear());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnExportMothInYearActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        showDataByYear(ser.getThongKeYear());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        showDataMonthInYear(ser.getThongKeMonthInYear());
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        showDataDayInMonth(ser.getThongKeDayInMonth());
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        showDataDayToDay(ser.getThongKeDayToDay());
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnExportDayInMonnthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportDayInMonnthActionPerformed
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
                for (int i = 0; i < tblThongKeDayInMonth.getColumnCount(); i++) {
                    Cell cell = rowCol.createCell(i);
                    cell.setCellValue(tblThongKeDayInMonth.getColumnName(i));
                }
                for (int j = 0; j < tblThongKeDayInMonth.getRowCount(); j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < tblThongKeDayInMonth.getColumnCount(); k++) {
                        Cell cell = row.createCell(k);
                        if (tblThongKeDayInMonth.getValueAt(j, k) != null) {
                            cell.setCellValue(tblThongKeDayInMonth.getValueAt(j, k).toString());
                        }
                    }
                }
                FileOutputStream out = new FileOutputStream(new File(saveFile.toString()));
                wb.write(out);
                wb.close();
                out.close();
                EventStream.openFile(saveFile.toPath());
                JOptionPane.showMessageDialog(this, "Export Excel thành công ");
                showDataDayInMonth(ser.getThongKeDayInMonth());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnExportDayInMonnthActionPerformed

    private void btnExportDayToDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportDayToDayActionPerformed
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
                for (int i = 0; i < tblThongKeDayToDay.getColumnCount(); i++) {
                    Cell cell = rowCol.createCell(i);
                    cell.setCellValue(tblThongKeDayToDay.getColumnName(i));
                }
                for (int j = 0; j < tblThongKeDayToDay.getRowCount(); j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < tblThongKeDayToDay.getColumnCount(); k++) {
                        Cell cell = row.createCell(k);
                        if (tblThongKeDayToDay.getValueAt(j, k) != null) {
                            cell.setCellValue(tblThongKeDayToDay.getValueAt(j, k).toString());
                        }
                    }
                }
                FileOutputStream out = new FileOutputStream(new File(saveFile.toString()));
                wb.write(out);
                wb.close();
                out.close();
                EventStream.openFile(saveFile.toPath());
                JOptionPane.showMessageDialog(this, "Export Excel thành công ");
                showDataDayInMonth(ser.getThongKeDayInMonth());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnExportDayToDayActionPerformed

    private void tblThongKeMonthInYearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThongKeMonthInYearMouseClicked

    }//GEN-LAST:event_tblThongKeMonthInYearMouseClicked

    private void tblThongKeMonthInYearMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThongKeMonthInYearMousePressed
        if (evt.getClickCount() == 2) {
            int index = tblThongKeMonthInYear.getSelectedRow();
            int month = Integer.parseInt(tblThongKeMonthInYear.getValueAt(index, 0).toString());
            tabDoanhThu.setSelectedIndex(2);
            showDataDayInMonth(ser.getThongKeDayInMonthByMonth(month));
        }
    }//GEN-LAST:event_tblThongKeMonthInYearMousePressed
//    void eventMonth() {
//        dateMonth.addPropertyChangeListener(new PropertyChangeListener() {
//            @Override
//            public void propertyChange(PropertyChangeEvent evt) {
//                if ("month".equals(evt.getPropertyName())) {
//                    loadToTableByMonthInYear();
//                }
//            }
//        });
//    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportDayInMonnth;
    private javax.swing.JButton btnExportDayToDay;
    private javax.swing.JButton btnExportMothInYear;
    private com.raven.component.Card cardDoanhThu;
    private com.raven.component.Card cardKhachHang;
    private com.raven.component.Card cardNhanVien;
    private com.raven.component.Card cardSanPham;
    private javax.swing.JComboBox<String> cboMonthInYear;
    private javax.swing.JComboBox<String> cboYearInPane1;
    private com.raven.chart.Chart chartDayInMonth;
    private com.raven.chart.Chart chartDayToDay;
    private com.raven.chart.Chart chartThongKeMonthInYear;
    private com.raven.chart.Chart chartTongQuan;
    private com.raven.chart.Chart chartYear;
    private com.toedter.calendar.JDateChooser dateDayInMonth;
    private com.toedter.calendar.JDateChooser dateNgayBatdau;
    private com.toedter.calendar.JDateChooser dateNgayKetThuc;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane tabDoanhThu;
    private javax.swing.JTabbedPane tabTong;
    private javax.swing.JTable tblDoanhThu8Day;
    private javax.swing.JTable tblThongKeByYear;
    private javax.swing.JTable tblThongKeDayInMonth;
    private javax.swing.JTable tblThongKeDayToDay;
    private javax.swing.JTable tblThongKeMonthInYear;
    // End of variables declaration//GEN-END:variables
}
