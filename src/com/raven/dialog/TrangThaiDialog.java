/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.raven.dialog;

import com.raven.services.BillService;

/**
 *
 * @author vinhd
 */
public class TrangThaiDialog extends javax.swing.JDialog {

    BillService billService = new BillService();

    public TrangThaiDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public void updateDaThanhToan(int id) {
        billService.updateStatus("Đã thanh toán", id);
    }

    public void updateChoThanhToan(int id) {
        billService.updateStatus("CHờ thanh toán", id);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnDaThanhToan = new javax.swing.JButton();
        btnChoThanhToan = new javax.swing.JButton();
        btnDaHuy = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("Cập nhật trạng thái thành ?");

        btnDaThanhToan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/icons8-payment-30.png"))); // NOI18N
        btnDaThanhToan.setText("Đã thanh toán");
        btnDaThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDaThanhToanActionPerformed(evt);
            }
        });

        btnChoThanhToan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/icons8-return-30.png"))); // NOI18N
        btnChoThanhToan.setText("Chờ thanh toán");

        btnDaHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/delete.png"))); // NOI18N
        btnDaHuy.setText("Đã hủy");
        btnDaHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDaHuyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(btnDaThanhToan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(btnChoThanhToan)
                .addGap(18, 18, 18)
                .addComponent(btnDaHuy)
                .addGap(18, 18, 18))
            .addGroup(layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1)
                .addGap(83, 83, 83)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChoThanhToan)
                    .addComponent(btnDaThanhToan)
                    .addComponent(btnDaHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(125, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDaHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDaHuyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDaHuyActionPerformed

    private void btnDaThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDaThanhToanActionPerformed
        // TODO add your handling code here:
        this.updateDaThanhToan(WIDTH);
    }//GEN-LAST:event_btnDaThanhToanActionPerformed

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
            java.util.logging.Logger.getLogger(TrangThaiDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrangThaiDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrangThaiDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrangThaiDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TrangThaiDialog dialog = new TrangThaiDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnChoThanhToan;
    private javax.swing.JButton btnDaHuy;
    private javax.swing.JButton btnDaThanhToan;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
