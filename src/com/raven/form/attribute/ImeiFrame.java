/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.raven.form.attribute;

import com.raven.services.ImeiService;
import com.raven.services.impl.ImeiServiceImpl;
import com.raven.viewmodels.ImeiResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.List;
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
public class ImeiFrame extends javax.swing.JFrame {

    /**
     * Creates new form ImeiDialog
     */
    public static String imeii;
    private ImeiService service = new ImeiServiceImpl();
    private DefaultTableModel dtm = new DefaultTableModel();

    public ImeiFrame() {
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dtm = (DefaultTableModel) tableMaImei.getModel();
    }

    void showData(List<ImeiResponse> imr) {
        dtm.setRowCount(0);
        for (ImeiResponse im : imr) {
            dtm.addRow(new Object[]{
                im.getMaImei()
            });
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

        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtImei = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableMaImei = new javax.swing.JTable();
        btnThemImei = new javax.swing.JButton();
        btnSuaImei = new javax.swing.JButton();
        btnExportImei = new javax.swing.JButton();
        btnImportImei = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Mã Imei");

        jLabel2.setText("New Imei:");

        txtImei.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtImeiActionPerformed(evt);
            }
        });

        tableMaImei.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Imei", "Xóa"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableMaImei.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMaImeiMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tableMaImei);

        btnThemImei.setText("Thêm");
        btnThemImei.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemImeiActionPerformed(evt);
            }
        });

        btnSuaImei.setText("Sửa");
        btnSuaImei.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaImeiActionPerformed(evt);
            }
        });

        btnExportImei.setText("Export");
        btnExportImei.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportImeiActionPerformed(evt);
            }
        });

        btnImportImei.setText("Import");
        btnImportImei.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportImeiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnThemImei, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnSuaImei, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnImportImei, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnExportImei, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtImei, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 27, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(166, 166, 166)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel3)
                .addGap(27, 27, 27)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtImei, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemImei)
                    .addComponent(btnSuaImei)
                    .addComponent(btnExportImei)
                    .addComponent(btnImportImei))
                .addGap(22, 22, 22))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtImeiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtImeiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtImeiActionPerformed

    private void tableMaImeiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMaImeiMouseClicked
        // TODO add your handling code here:
        int row = tableMaImei.getSelectedRow();
        ImeiResponse imr = service.getAll().get(row);
        setForm(imr);

    }//GEN-LAST:event_tableMaImeiMouseClicked
    void setForm(ImeiResponse im) {
        txtImei.setText(im.getMaImei());
    }
    private void btnThemImeiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemImeiActionPerformed
        // TODO add your handling code here:
        if (validateForm()) {
            String ten = txtImei.getText();
            if (isImeiExisted(ten)) {
                JOptionPane.showMessageDialog(this, "Tên thuộc tính đã tồn tại");
                return;
            }
            imeii = txtImei.getText();
            System.out.println(imeii);
            ImeiResponse imr = getForm();
            if (service.add(imr)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                showData(Collections.singletonList(imr));
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại");
            }
        }

    }//GEN-LAST:event_btnThemImeiActionPerformed
    ImeiResponse getForm() {
        imeii = txtImei.getText();
        return new ImeiResponse(imeii);
    }

    public Boolean isImeiExisted(String maImei) {
        for (ImeiResponse exim : service.getMaImei()) {
            if (maImei.equals(exim.getMaImei())) {
                return true;
            }
        }
        return false;
    }

    boolean validateForm() {
        if (txtImei.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn cần nhập mã imei");
            return false;
        }
        String regex = "^+[0-9]$";
        if (txtImei.getText().length() != 15) {
            JOptionPane.showMessageDialog(this, "Độ dài phải đúng 15 ký tự");
            return false;
        } else if (txtImei.getText().matches(regex)) {
            JOptionPane.showMessageDialog(this, "Mã imei phải là số");
            return false;
        }

        return true;
    }
    private void btnExportImeiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportImeiActionPerformed
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
                for (int i = 0; i < tableMaImei.getColumnCount(); i++) {
                    Cell cell = rowCol.createCell(i);
                    cell.setCellValue(tableMaImei.getColumnName(i));
                }
                for (int j = 0; j < tableMaImei.getRowCount(); j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < tableMaImei.getColumnCount(); k++) {
                        Cell cell = row.createCell(k);
                        if (tableMaImei.getValueAt(j, k) != null) {
                            cell.setCellValue(tableMaImei.getValueAt(j, k).toString());
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
    }//GEN-LAST:event_btnExportImeiActionPerformed

    private void btnSuaImeiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaImeiActionPerformed
        // TODO add your handling code here:
        int row = tableMaImei.getSelectedRow();
        int ma = (int) tableMaImei.getValueAt(row, 0);
        ImeiResponse kh = getForm();
        if (service.update(kh, ma)) {
            JOptionPane.showMessageDialog(this, "Thành công");
            showData(service.getAll());
        } else {
            JOptionPane.showMessageDialog(this, "Thất bại");
        }
    }//GEN-LAST:event_btnSuaImeiActionPerformed

    private void btnImportImeiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportImeiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnImportImeiActionPerformed

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
            java.util.logging.Logger.getLogger(ImeiFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ImeiFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ImeiFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ImeiFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ImeiFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportImei;
    private javax.swing.JButton btnImportImei;
    private javax.swing.JButton btnSuaImei;
    private javax.swing.JButton btnThemImei;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable tableMaImei;
    private javax.swing.JTextField txtImei;
    // End of variables declaration//GEN-END:variables
}
