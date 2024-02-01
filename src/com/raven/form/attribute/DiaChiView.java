/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.raven.form.attribute;

import com.raven.domainmodels.ModelDiaChi;
import com.raven.services.DiaChiService;
import com.raven.services.KhachHangService;
import com.raven.services.impl.DiaChiServiceIplm;
import com.raven.services.impl.KhachHangServiceIplm;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class DiaChiView extends javax.swing.JFrame {

    DiaChiService serdiaChi = new DiaChiServiceIplm();
    KhachHangService serKh = new KhachHangServiceIplm();
    public static int lastAddedAddressId;
    List<ModelDiaChi> listDiaChi = new ArrayList<>();
    DefaultTableModel modelKH = new DefaultTableModel();

    public DiaChiView() {
        initComponents();
        listDiaChi = serdiaChi.getAllDiaChi();
        modelKH = (DefaultTableModel) tblDiaChi.getModel();
        showData(serdiaChi.getAllDiaChi());
    }

    void showData(List<ModelDiaChi> list) {
        modelKH.setRowCount(0);
        for (ModelDiaChi dc : list) {
            modelKH.addRow(new Object[]{
                dc.getID(),
                dc.getDiaChiCuThe()
            });

        }

    }

    ModelDiaChi getForm() {
        return new ModelDiaChi(txtDiaChiMacDinh.getText().trim());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAddDiaChiMacDinh = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDiaChiMacDinh = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDiaChi = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        btnAddDiaChiMacDinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/icons8-add.gif"))); // NOI18N
        btnAddDiaChiMacDinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDiaChiMacDinhActionPerformed(evt);
            }
        });

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        txtDiaChiMacDinh.setColumns(20);
        txtDiaChiMacDinh.setRows(5);
        jScrollPane3.setViewportView(txtDiaChiMacDinh);

        tblDiaChi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "Địa chỉ"
            }
        ));
        tblDiaChi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDiaChiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDiaChi);
        if (tblDiaChi.getColumnModel().getColumnCount() > 0) {
            tblDiaChi.getColumnModel().getColumn(0).setMinWidth(30);
            tblDiaChi.getColumnModel().getColumn(0).setMaxWidth(30);
        }

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Thêm nhanh địa chỉ");

        jLabel2.setText("Địa chỉ");

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setText("Thêm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setText("Sửa");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane3)
                                        .addGap(44, 44, 44))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(27, 27, 27)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton2});

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddDiaChiMacDinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDiaChiMacDinhActionPerformed


    }//GEN-LAST:event_btnAddDiaChiMacDinhActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (txtDiaChiMacDinh.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Địa chỉ không được để trống");
            txtDiaChiMacDinh.requestFocus();
            return;
        }
        int chek = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm địa chỉ không ?", "Thêm địa chỉ", JOptionPane.YES_NO_OPTION);
        if (chek != JOptionPane.YES_OPTION) {
            return;
        }

        String diaChiMacDinh = txtDiaChiMacDinh.getText().trim();
        ModelDiaChi dc = new ModelDiaChi();
        dc.setDiaChiCuThe(diaChiMacDinh);
        boolean dcTT = false;
        for (ModelDiaChi modelDiaChi : listDiaChi) {
            if (modelDiaChi.getDiaChiCuThe().trim().equals(diaChiMacDinh)) {
                dcTT = true;
                break;

            }
        }
        if (dcTT) {
            JOptionPane.showMessageDialog(this, "Địa chỉ đã tồn tại không thể thêm");
            txtDiaChiMacDinh.requestFocus();

        } else {
            int idDiaChi = serdiaChi.addDiaChiInteger(dc);
            try {
                if (idDiaChi != -1) {
                    JOptionPane.showMessageDialog(this, "Thêm địa chỉ mới thành công");
                    showData(serdiaChi.getAllDiaChi());

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (txtDiaChiMacDinh.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Địa chỉ không được để trống");
            txtDiaChiMacDinh.requestFocus();
            return;
        }

        int chek = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa địa chỉ không ?", "Sửa địa chỉ", JOptionPane.YES_NO_OPTION);
        if (chek != JOptionPane.YES_OPTION) {
            return;
        }

        int index = tblDiaChi.getSelectedRow();
        String diaChi = tblDiaChi.getValueAt(index, 1).toString();

        ModelDiaChi dc = getForm();
        dc.setID(serdiaChi.getAllDiaChi().get(index).getID());
        try {
            if (serdiaChi.updateDiaChi(dc) != null) {
                JOptionPane.showMessageDialog(this, "Sửa địa chỉ thành công");
                showData(serdiaChi.getAllDiaChi());
            }
        } catch (Exception e) {
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void tblDiaChiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDiaChiMouseClicked
        int index = tblDiaChi.getSelectedRow();
        txtDiaChiMacDinh.setText(tblDiaChi.getValueAt(index, 1).toString());
    }//GEN-LAST:event_tblDiaChiMouseClicked

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
            java.util.logging.Logger.getLogger(DiaChiView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DiaChiView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DiaChiView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DiaChiView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DiaChiView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddDiaChiMacDinh;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tblDiaChi;
    private javax.swing.JTextArea txtDiaChiMacDinh;
    // End of variables declaration//GEN-END:variables
}
