package com.gym.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gym.server.model.po.admin.Equipment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: 71made
 * @Date: 2023/05/07 22:45
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@Mapper
@Repository
public interface EquipmentMapper extends BaseMapper<Equipment> {
}
