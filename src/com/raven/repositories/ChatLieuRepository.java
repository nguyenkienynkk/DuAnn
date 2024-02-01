/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.raven.repositories;

import com.raven.viewmodels.ChatLieuResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
public interface ChatLieuRepository {

    List<ChatLieuResponse> getAll();

    Boolean add(ChatLieuResponse cl);

    Boolean update(ChatLieuResponse cl, int id);

    Boolean delete(int id);

    List<ChatLieuResponse> getOne(int id);

    List<ChatLieuResponse> getTenChatLieu();
}
