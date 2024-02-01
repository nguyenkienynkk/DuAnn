/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raven.repositories;

import com.raven.viewmodels.User;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface UserRepository {

    List<User> getAllUser();

    User getUserByID(int id);

    Integer addUser(User u);

    int getIDBySDT(String sdt);

    Integer updateUser(User u);

    int getRoleByUseAndPass(String user, String pass);

}
