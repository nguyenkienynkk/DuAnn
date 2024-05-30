/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.raven.form;

import com.raven.main.Main;
import com.raven.services.NhanVienService;
import com.raven.services.UserService;
import com.raven.services.impl.NhanVienServiceImpl;
import com.raven.services.impl.UserServiceIplm;
import com.raven.utils.Auth;
import com.raven.viewmodels.User;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Login extends javax.swing.JFrame {
    NhanVienService sv = new NhanVienServiceImpl();
    public Login() {
        initComponents();
        txtUserName.setBackground(new Color(0, 0, 0, 1));
        txtPassWord.setBackground(new Color(0, 0, 0, 1));
        txtUserName.setText("0973728589");
        txtPassWord.setText("LGdqzAAdv");
        UserService us = new UserServiceIplm();
        
    }
    
    Boolean getFrom() {
        String username = txtUserName.getText().trim();
        String password = new String(txtPassWord.getPassword());
        
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username không được để trống");
            txtUserName.requestFocus();
            return false;
        }
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Password không đuợc để trống không được để trống");
            txtUserName.requestFocus();
            return false;
        }
        return true;
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnl2 = new javax.swing.JPanel();
        lblExit = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        txtUserName = new javax.swing.JTextField();
        txtPassWord = new javax.swing.JPasswordField();
        disible = new javax.swing.JLabel();
        show = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblSingup1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/backgroudlaptop2.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnl2.setBackground(new java.awt.Color(128, 0, 0));
        pnl2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblExit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblExit.setForeground(new java.awt.Color(255, 255, 255));
        lblExit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblExit.setText("x");
        lblExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblExitMouseClicked(evt);
            }
        });
        pnl2.add(lblExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(417, 0, 30, 31));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("LOGIN");
        jLabel2.setToolTipText("");
        pnl2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 440, 36));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("to you experience, from laptops is our top priority.");
        pnl2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 455, 24));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("      Username");
        pnl2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 350, 22));

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("      ___________________________________________________________________________");
        pnl2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 394, 20));
        pnl2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 140, 50, 20));

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("     Password");
        pnl2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, 380, 22));

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("      ___________________________________________________________________________");
        pnl2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, 394, 30));

        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(0, 102, 255));
        btnLogin.setText("LOGIN");
        btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLoginMouseClicked(evt);
            }
        });
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        pnl2.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 360, 140, 35));

        txtUserName.setFont(txtUserName.getFont().deriveFont(txtUserName.getFont().getSize()+2f));
        txtUserName.setForeground(new java.awt.Color(255, 255, 255));
        txtUserName.setBorder(null);
        pnl2.add(txtUserName, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, 360, 30));

        txtPassWord.setFont(txtPassWord.getFont().deriveFont(txtPassWord.getFont().getSize()+2f));
        txtPassWord.setForeground(new java.awt.Color(255, 255, 255));
        txtPassWord.setBorder(null);
        pnl2.add(txtPassWord, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, 360, 30));

        disible.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                disibleMouseClicked(evt);
            }
        });
        pnl2.add(disible, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 230, 50, 50));

        show.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showMouseClicked(evt);
            }
        });
        pnl2.add(show, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 230, -1, -1));

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Our laptops bring the best quality ");
        pnl2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 455, 24));

        lblSingup1.setForeground(new java.awt.Color(255, 255, 255));
        lblSingup1.setText("I don't remember which account my salary was deducted from");
        pnl2.add(lblSingup1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 420, 340, 20));

        getContentPane().add(pnl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(597, 0, 470, 550));

        setSize(new java.awt.Dimension(1069, 551));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseClicked
        // TODO add your handling code here:
        String userLogin = txtUserName.getText();
        String passLogin = Arrays.toString(txtPassWord.getPassword());
        if (userLogin.isEmpty() || passLogin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống", "Login", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }//GEN-LAST:event_btnLoginMouseClicked

    private void disibleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_disibleMouseClicked
        // TODO add your handling code here:
        txtPassWord.setEchoChar((char) 0);
        disible.setVisible(false);
        disible.setVisible(false);
        show.setEnabled(true);
        show.setEnabled(true);
    }//GEN-LAST:event_disibleMouseClicked

    private void showMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showMouseClicked
        // TODO add your handling code here:
        txtPassWord.setEchoChar((char) 8226);
        disible.setVisible(true);
        disible.setVisible(true);
        show.setEnabled(false);
        show.setEnabled(false);
    }//GEN-LAST:event_showMouseClicked

    private void lblExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_lblExitMouseClicked

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        List<User> listUser = new ArrayList<>();
        UserService ser = new UserServiceIplm();
        listUser = ser.getAllUser();
        String username = txtUserName.getText().trim();
        String password = new String(txtPassWord.getPassword());
        boolean found = false;
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username không được để trống");
            txtUserName.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Password không đuợc để trống");
            txtPassWord.requestFocus();
            return;
        }
        for (User user : listUser) {
            if (user.getUsername().equals(username) && user.getPaswword().equals(password)) {
                found = true;
                break;
            }
            
        }
        if (found) {
            JOptionPane.showMessageDialog(this, "Đăng nhập hệ thống thành công");
            new Main().setVisible(true);
            new Voucher().setVisible(false);
            Auth.user = sv.getOne(username);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Thông tin tài khoản hoặc mật khẩu không chính xác");
        }
        

    }//GEN-LAST:event_btnLoginActionPerformed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        JFrame frame = new JFrame();
        frame.setVisible(true);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel disible;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblSingup1;
    private javax.swing.JPanel pnl2;
    private javax.swing.JLabel show;
    private javax.swing.JPasswordField txtPassWord;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}
