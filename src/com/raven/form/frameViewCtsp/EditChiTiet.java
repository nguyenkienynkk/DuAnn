/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.raven.form.frameViewCtsp;


import com.raven.services.ChatLieuService;
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
import com.raven.viewmodels.ManHinhResponse;
import com.raven.viewmodels.MauSacResponse;
import com.raven.viewmodels.NhaSanXuatResponse;
import com.raven.viewmodels.RamResponse;
import com.raven.viewmodels.SanPhamResponse;
import java.text.DecimalFormat;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nguye
 */
public final class EditChiTiet extends javax.swing.JDialog {

    private DefaultComboBoxModel dcmCpu, dcmDungLuong, dcmGpu, dcmHeDieuHanh, dcmManHinh, dcmMauSac, dcmNhaSanXuat, dcmImei, dcmRam, dcmSanPham, dcmChatLieu = new DefaultComboBoxModel<>();
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

    /**
     * Creates new form EditChiTiet
     */
    public EditChiTiet(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    private void setSelectedIndex(JComboBox cbo, String value) {
        for (int i = 0; i < cbo.getItemCount(); i++) {
            if (cbo.getItemAt(i).toString().equals(value)) {
                cbo.setSelectedIndex(i);
                return;
            }
        }
    }

    public EditChiTiet(java.awt.Frame parent, boolean modal, String sp, String nsx, String dl, String ms, String hdh, String gpu, String ram, String cpu, String mh, String cl, float trongluong, float gia) {
        super(parent, modal);
        initComponents();
        dcmCpu = (DefaultComboBoxModel) cbbCpu.getModel();
        dcmDungLuong = (DefaultComboBoxModel) cbbDungLuong.getModel();
        dcmGpu = (DefaultComboBoxModel) cbbGpu.getModel();
        dcmHeDieuHanh = (DefaultComboBoxModel) cbbHeDieuHanh.getModel();
        dcmManHinh = (DefaultComboBoxModel) cbbManHinh.getModel();
        dcmNhaSanXuat = (DefaultComboBoxModel) cbbNhaSanXuat.getModel();
        dcmRam = (DefaultComboBoxModel) cbbRam.getModel();
        dcmMauSac = (DefaultComboBoxModel) cbbMauSac.getModel();
        dcmChatLieu = (DefaultComboBoxModel) cbbChatLieu.getModel();
        loadDataCpu();
        loadDataDungLuong();
        loadDataGpu();
        loadDataHeDieuHanh();
        loadDataManHinh();
        loadDataMauSac();
        loadDataNhaSanXuat();
        loadDataRam();
        loadDataChatLieu();
        labelTenSP.setText(sp);
        DecimalFormat dcmf = new DecimalFormat();
        String fmgia = dcmf.format(gia);
        txtGia.setText(fmgia + "");
        txtTrongLuong.setText(trongluong + "");
        cbbCpu.setSelectedItem(cpu);
        cbbDungLuong.setSelectedItem(dl);
        cbbGpu.setSelectedItem(gpu);
        cbbHeDieuHanh.setSelectedItem(hdh);
        cbbManHinh.setSelectedItem(mh);
        cbbMauSac.setSelectedItem(ms);
        cbbNhaSanXuat.setSelectedItem(nsx);
        cbbRam.setSelectedItem(ram);
        setSelectedIndex(cbbChatLieu, cl);
        setSelectedIndex(cbbCpu, cpu);
        setSelectedIndex(cbbDungLuong, dl);
        setSelectedIndex(cbbGpu, gpu);
        setSelectedIndex(cbbHeDieuHanh, hdh);
        setSelectedIndex(cbbManHinh, mh);
        setSelectedIndex(cbbMauSac, ms);
        setSelectedIndex(cbbNhaSanXuat, nsx);
        setSelectedIndex(cbbRam, ram);

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

    void loadDataChatLieu() {
        cbbChatLieu.removeAllItems();
        for (ChatLieuResponse clr : cls.getTenChatLieu()) {
            dcmChatLieu.addElement(clr);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cbbChatLieu = new javax.swing.JComboBox<>();
        cbbGpu = new javax.swing.JComboBox<>();
        cbbHeDieuHanh = new javax.swing.JComboBox<>();
        cbbCpu = new javax.swing.JComboBox<>();
        cbbDungLuong = new javax.swing.JComboBox<>();
        cbbManHinh = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        labelTenSP = new javax.swing.JLabel();
        cbbMauSac = new javax.swing.JComboBox<>();
        cbbNhaSanXuat = new javax.swing.JComboBox<>();
        cbbRam = new javax.swing.JComboBox<>();
        txtGia = new javax.swing.JTextField();
        txtTrongLuong = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 255));
        jLabel1.setText("Chỉnh sửa chi tiết sản phẩm");

        jButton1.setBackground(new java.awt.Color(255, 0, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Cập nhật");

        labelTenSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelTenSP.setText("Tên sản phẩm :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cbbGpu, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(126, 126, 126)
                        .addComponent(cbbHeDieuHanh, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(140, 140, 140)
                        .addComponent(cbbManHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbbMauSac, 0, 246, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(126, 126, 126)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbbNhaSanXuat, 0, 270, Short.MAX_VALUE)
                                    .addComponent(txtTrongLuong))
                                .addGap(131, 131, 131)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbbRam, 0, 250, Short.MAX_VALUE)
                                    .addComponent(txtGia)))
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cbbChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(126, 126, 126)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                            .addComponent(cbbCpu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(140, 140, 140)
                        .addComponent(cbbDungLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(79, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbbChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbCpu, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbDungLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(103, 103, 103)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbbGpu, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbHeDieuHanh, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbManHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(104, 104, 104)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbbMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbNhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbRam, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTrongLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(19, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(EditChiTiet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditChiTiet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditChiTiet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditChiTiet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EditChiTiet dialog = new EditChiTiet(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<String> cbbChatLieu;
    private javax.swing.JComboBox<String> cbbCpu;
    private javax.swing.JComboBox<String> cbbDungLuong;
    private javax.swing.JComboBox<String> cbbGpu;
    private javax.swing.JComboBox<String> cbbHeDieuHanh;
    private javax.swing.JComboBox<String> cbbManHinh;
    private javax.swing.JComboBox<String> cbbMauSac;
    private javax.swing.JComboBox<String> cbbNhaSanXuat;
    private javax.swing.JComboBox<String> cbbRam;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelTenSP;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtTrongLuong;
    // End of variables declaration//GEN-END:variables

}
