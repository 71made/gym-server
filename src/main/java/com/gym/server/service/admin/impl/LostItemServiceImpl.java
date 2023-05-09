package com.gym.server.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gym.server.mapper.LostItemMapper;
import com.gym.server.model.dto.admin.LostItemAddDTO;
import com.gym.server.model.dto.admin.LostItemUpdateDTO;
import com.gym.server.model.po.admin.LostItem;
import com.gym.server.service.admin.LostItemService;
import com.gym.utils.http.Result;
import com.gym.utils.http.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 43901
 * @Date 2023/5/8 12:09
 * @Description
 */
@Service
@Slf4j
public class LostItemServiceImpl implements LostItemService {

    @Autowired
    LostItemMapper lostItemMapper;

    @Override
    public Result all() {
        QueryWrapper<LostItem> wrapper = new QueryWrapper<>();
        wrapper.eq(LostItem.Columns.STATUS, LostItem.Status.LOST.getValue());
        List<LostItem> lostItems = lostItemMapper.selectList(wrapper);
        return Results.successWithData(lostItems);
    }

    @Override
    public Result add(LostItemAddDTO lostItemAddDTO) {
        LostItem lostItem = lostItemAddDTO.convertToPO();
        if (0 == lostItemMapper.insert(lostItem)) {
            return Results.failure("新增失败");
        }
        return Results.success("新增成功");
    }

    @Override
    public Result update(LostItemUpdateDTO lostItemUpdateDTO) {
        LostItem lostItem = lostItemUpdateDTO.convertToPO();
        if (0 == lostItemMapper.updateById(lostItem)) {
            return Results.failure("更新失败");
        }
        return Results.success("更新成功");
    }
}
