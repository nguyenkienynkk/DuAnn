/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.services.impl;

import com.raven.repositories.impl.UserRepositoryImpl;
import com.raven.services.UserService;
import com.raven.viewmodels.User;
import java.util.List;

/**
 *
 * @author Admin
 */
public class UserServiceIplm implements UserService {

    UserRepositoryImpl repo = new UserRepositoryImpl();

    @Override
    public List<User> getAllUser() {
        return repo.getAllUser();

    }

    @Override
    public User getUserByID(int id) {
        return repo.getUserByID(id);
    }

    @Override
    public int getRoleByUseAndPass(String user, String pass) {
        return repo.getRoleByUseAndPass(user, pass);
    }

    @Override
    public Integer addUser(User u) {
        return repo.addUser(u);
    }

    @Override
    public Integer updateUser(User u) {
        return repo.updateUser(u);
    }

    @Override
    public int getIDBySDT(String sdt) {
        return repo.getIDBySDT(sdt);
    }

}
