/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.services.impl;

import com.raven.repositories.impl.ChatLieuRepositoryImpl;
import com.raven.services.ChatLieuService;
import com.raven.viewmodels.ChatLieuResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public class ChatLieuServiceImpl implements ChatLieuService {

    private ChatLieuRepositoryImpl clsi = new ChatLieuRepositoryImpl();

    @Override
    public List<ChatLieuResponse> getAll() {
        return clsi.getAll();
    }

    @Override
    public Boolean add(ChatLieuResponse cl) {
        return clsi.add(cl);
    }

    @Override
    public Boolean update(ChatLieuResponse cl, int id) {
        return clsi.update(cl, id);
    }

    @Override
    public List<ChatLieuResponse> getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ChatLieuResponse> getTenChatLieu() {
        return clsi.getTenChatLieu();
    }

    @Override
    public Boolean delete(int id) {
     return clsi.delete(id);
    }

}
