package com.gym.server.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gym.server.mapper.StaffMapper;
import com.gym.server.model.dto.admin.StaffAddDTO;
import com.gym.server.model.dto.admin.StaffUpdateDTO;
import com.gym.server.model.dto.admin.StaffAddDTO;
import com.gym.server.model.dto.admin.StaffUpdateDTO;
import com.gym.server.model.po.admin.Staff;
import com.gym.server.service.admin.StaffService;
import com.gym.utils.http.Result;
import com.gym.utils.http.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 71made
 * @Date: 2023/05/08 11:44
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@Service
@Slf4j
public class StaffServiceImpl implements StaffService {

    @Autowired
    StaffMapper staffMapper;

    @Override
    public Result all() {
        QueryWrapper<Staff> wrapper = new QueryWrapper<>();
        wrapper.ne(Staff.Columns.STATUS, Staff.Status.DELETE.getValue());
        return Results.successWithData(staffMapper.selectList(wrapper));
    }

    @Override
    public Result add(StaffAddDTO staffDTO) {
        Staff staff = staffDTO.convertToPO();
        if (0 == staffMapper.insert(staff)) {
            return Results.failure("新增失败");
        }
        return Results.success("新增成功");
    }

    @Override
    public Result update(StaffUpdateDTO staffDTO) {
        Staff staff = staffDTO.convertToPO();
        if (0 == staffMapper.updateById(staff)) {
            return Results.failure("更新失败");
        }
        return Results.success("更新成功");
    }
}
