package com.gym.server.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gym.server.mapper.EquipmentMapper;
import com.gym.server.model.dto.admin.EquipmentAddDTO;
import com.gym.server.model.dto.admin.EquipmentUpdateDTO;
import com.gym.server.model.po.admin.Equipment;
import com.gym.server.service.admin.EquipmentService;
import com.gym.utils.http.Result;
import com.gym.utils.http.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 71made
 * @Date: 2023/05/07 22:59
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@Service
@Slf4j
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    EquipmentMapper equipmentMapper;

    @Override
    public Result all() {
        QueryWrapper<Equipment> wrapper = new QueryWrapper<>();
        wrapper.ne(Equipment.Columns.STATUS, Equipment.Status.DELETE.getValue());
        return Results.successWithData(equipmentMapper.selectList(wrapper));
    }

    @Override
    public Result add(EquipmentAddDTO equipmentDTO) {
        Equipment equipment = equipmentDTO.convertToPO();
        if (0 == equipmentMapper.insert(equipment)) {
            return Results.failure("新增失败");
        }
        return Results.success("新增成功");
    }

    @Override
    public Result update(EquipmentUpdateDTO equipmentDTO) {
        Equipment equipment = equipmentDTO.convertToPO();
        if (0 == equipmentMapper.updateById(equipment)) {
            return Results.failure("更新失败");
        }
        return Results.success("更新成功");
    }
}
