/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.raven.form.attribute;

import com.raven.domainmodels.ModelDiaChi;
import com.raven.form.KhachHang;
import com.raven.repositories.impl.DiaChiRepositoryIplm;
import com.raven.services.DiaChiService;
import com.raven.services.impl.DiaChiServiceIplm;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class DiaChi extends javax.swing.JDialog {

    List<ModelDiaChi> listDC = new ArrayList<>();
    DefaultTableModel modelDC = new DefaultTableModel();
    DefaultComboBoxModel<ModelDiaChi> modelCbo = new DefaultComboBoxModel<>();
    DiaChiService ser = new DiaChiServiceIplm();
    public static String diaChi;

    public DiaChi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initDC();
        btnChon.setEnabled(false);
//        int index = tblDiaChi.getSelectedRow();
//        if (index <= 0) {
//            btnChon.setEnabled(true);
//        }

    }

    void initDC() {
        listDC = ser.getAllDiaChi();
//        modelDC = (DefaultTableModel) tblDiaChi.getModel();
        showData(listDC);
        loadComBoBox();
    }

    void showData(List<ModelDiaChi> list) {
        modelDC.setRowCount(0);
        for (ModelDiaChi dc : list) {
            modelDC.addRow(new Object[]{
                dc.getID(),
                dc.getDiaChiCuThe()
            });

        }
    }

    void loadComBoBox() {
        Set<String> setcbo = new HashSet<>();
        for (ModelDiaChi dc : listDC) {
            setcbo.add(dc.toString());

        }
        modelCbo = (DefaultComboBoxModel) new DefaultComboBoxModel<>(setcbo.toArray());
        cbo.setModel((DefaultComboBoxModel) modelCbo);

    }

//    void showDetail() {
//        int index = tblDiaChi.getSelectedRow();
//        txtDiaChi.setText(tblDiaChi.getValueAt(index, 1).toString());
//
//    }
//    void clearFrom() {
//        txtDiaChi.setText("");
//    }
//


//    boolean validateDiaChi() {
//        String diaChi = txtDiaChi.getText().trim();
//        if (diaChi.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Địa chỉ không được để trống");
//            txtDiaChi.requestFocus();
//            return false;
//        }
//
//        if (diaChi.length() <= 5) {
//            JOptionPane.showMessageDialog(this, "Địa chỉ phải lớn hơn 5 ký tự");
//            txtDiaChi.requestFocus();
//            return false;
//        }
//
//        return true;
//    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbo = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDeleted = new javax.swing.JButton();
        btnChon = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("ĐỊA CHỈ");

        cbo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(161, 161, 161))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 43, Short.MAX_VALUE)
                .addComponent(cbo, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cbo, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức năng"));

        btnThem.setBackground(new java.awt.Color(255, 51, 0));
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(255, 51, 0));
        btnUpdate.setText("Sửa");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDeleted.setBackground(new java.awt.Color(255, 51, 0));
        btnDeleted.setText("Xóa");
        btnDeleted.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletedActionPerformed(evt);
            }
        });

        btnChon.setBackground(new java.awt.Color(255, 51, 0));
        btnChon.setText("Chọn");
        btnChon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnChon)
                    .addComponent(btnDeleted)
                    .addComponent(btnUpdate)
                    .addComponent(btnThem))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDeleted)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnChon)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
//        

    }//GEN-LAST:event_btnThemActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
//        if (validateDiaChi()) {
//            int chek = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa địa chỉ không ?", "update", JOptionPane.YES_NO_OPTION);
//            if (chek != JOptionPane.YES_NO_OPTION) {
//                return;
//            }
//            int index = tblDiaChi.getSelectedRow();
//            if (index < 0) {
//                JOptionPane.showMessageDialog(this, "Bạn hãy chọn dòng để thực hiện");
//                return;
//            } else {
//                ModelDiaChi dc = getFrom();
//                String id = tblDiaChi.getValueAt(index, 0).toString();
//                int idINT = Integer.parseInt(id);
//                dc.setID(idINT);
//
//                try {
//                    if (ser.updateDiaChi(dc) != null) {
//                        JOptionPane.showMessageDialog(this, "Sửa địa chỉ thành công");
//                        showData(ser.getAllDiaChi());
//                    }
//                } catch (Exception e) {
//                }
//            }
//        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeletedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletedActionPerformed
//        int chek = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa địa chỉ không ?", "delete", JOptionPane.YES_NO_OPTION);
//        if (chek != JOptionPane.YES_NO_OPTION) {
//            return;
//        }
//        int index = tblDiaChi.getSelectedRow();
//        if (index < 0) {
//            JOptionPane.showMessageDialog(this, "Bạn hãy chọn dòng để thực hiện");
//            return;
//        } else {
//            try {
//                ModelDiaChi dc = getFrom();
//                String id = tblDiaChi.getValueAt(index, 0).toString();
//                int idINT = Integer.parseInt(id);
//                if (ser.deleteDiaChi(idINT) != null) {
//                    JOptionPane.showMessageDialog(this, "Xóa địa chỉ thành công");
//                    showData(ser.getAllDiaChi());
//                    clearFrom();
//                }
//            } catch (Exception e) {
//            }
//        }
    }//GEN-LAST:event_btnDeletedActionPerformed

    private void btnChonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonActionPerformed
//        int index = tblDiaChi.getSelectedRow();
//        String diaChiTable = tblDiaChi.getValueAt(index, 1).toString();
//        diaChi = diaChiTable;
        this.dispose();
    }//GEN-LAST:event_btnChonActionPerformed

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
            java.util.logging.Logger.getLogger(DiaChi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DiaChi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DiaChi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DiaChi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DiaChi dialog = new DiaChi(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnChon;
    private javax.swing.JButton btnDeleted;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cbo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
