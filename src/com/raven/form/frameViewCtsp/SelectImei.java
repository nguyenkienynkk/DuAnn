/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.raven.form.frameViewCtsp;

import com.raven.form.MainForm;
import com.raven.form.BanHang;
import com.raven.services.BanHangService;
import com.raven.services.ChiTietSanPhamService;
import com.raven.services.impl.ChiTietSanPhamServiceImpl;
import com.raven.viewmodels.GioHangModel;
import com.raven.viewmodels.HoaDoninBanHang;
import com.raven.viewmodels.ImeiResponse;
import com.raven.viewmodels.SanPhamChiTietResponse;
import com.raven.viewmodels.ToanCuc;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author vinhd
 */
public class SelectImei extends javax.swing.JDialog {

    BanHangService banHang = new BanHangService();
    DefaultTableModel dtm = new DefaultTableModel();
    ChiTietSanPhamService service = new ChiTietSanPhamServiceImpl();
    public int idHD;

    public SelectImei(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

// String tensp,chatlieu,cpuu,dungluong,gpu,hedieuhanh,manhinh,mausac,nhasanxuat,ram
    public SelectImei(java.awt.Frame parent, boolean modal, int row, String tensp, String chatlieu, String cpuu, String dungluong, String gpu, String hedieuhanh, String manhinh, String mausac, String nhasanxuat, int ram, int maHD) {
        super(parent, modal);
        initComponents();
        dtm = (DefaultTableModel) tableGetImei.getModel();
        List listressult = service.getImei(tensp, nhasanxuat, dungluong, mausac, hedieuhanh, gpu, ram, cpuu, manhinh, chatlieu, maHD);
        showData(listressult);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        idHD = maHD;
    }

    public void showData(List<ImeiResponse> list) {
        dtm.setRowCount(0);
        int sl = 0;
        for (ImeiResponse spct : list) {
            dtm.addRow(new Object[]{
                sl += 1,
                spct.getMaImei()
            });
        }
    }

    public void fillTableHoaDon(List<HoaDoninBanHang> list, DefaultTableModel dtmsp) {
        dtmsp.setRowCount(0);
        int i = 0;
        for (HoaDoninBanHang hd : list) {
            i++;
            dtmsp.addRow(hd.toDataRow(i));
        }
    }

    public void fillTableGioHang(List<GioHangModel> list, DefaultTableModel dtmsp) {
        dtmsp.setRowCount(0);
        int i = 0;
        for (GioHangModel hd : list) {
            i++;
            dtmsp.addRow(hd.toDataRow(i));
        }
    }

    public SelectImei(java.awt.Frame parent, boolean modal, int row) {
        super(parent, modal);
        initComponents();
        dtm = (DefaultTableModel) tableGetImei.getModel();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableGetImei = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Tìm kiếm:");

        jButton1.setText("Chọn tất");

        tableGetImei.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã Imei"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableGetImei.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableGetImeiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableGetImei);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tableGetImeiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableGetImeiMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            String imei = tableGetImei.getValueAt(tableGetImei.getSelectedRow(), 1).toString();
            banHang.addHDCT(idHD, banHang.getIDctsp(imei));
            this.dispose();

        }
    }//GEN-LAST:event_tableGetImeiMouseClicked

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
            java.util.logging.Logger.getLogger(SelectImei.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SelectImei.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SelectImei.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SelectImei.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SelectImei dialog = new SelectImei(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tableGetImei;
    // End of variables declaration//GEN-END:variables
}
