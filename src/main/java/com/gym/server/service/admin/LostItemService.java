package com.gym.server.service.admin;


import com.gym.server.model.dto.admin.LostItemAddDTO;
import com.gym.server.model.dto.admin.LostItemUpdateDTO;
import com.gym.utils.http.Result;

public interface LostItemService {

    Result all();

    Result add(LostItemAddDTO lostItemAddDTO);

    Result update(LostItemUpdateDTO lostItemUpdateDTO);
}
