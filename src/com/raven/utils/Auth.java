/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.utils;

import com.raven.model.ModelNhanVien;
import com.raven.viewmodels.NhanVienResponse;

/**
 *
 * @author vinhd
 */
public class Auth {

    public static NhanVienResponse user = null;

    public static void clear() {
        Auth.user = null;
    }

    public static boolean isLogin() {
        return Auth.user != null;
    }

    public static boolean isManager() {
        return Auth.isLogin() && user.isVaiTro();
    }
}
