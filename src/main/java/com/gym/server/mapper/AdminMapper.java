package com.gym.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gym.server.model.po.admin.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: 71made
 * @Date: 2023/05/06 15:34
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@Mapper
@Repository
public interface AdminMapper extends BaseMapper<Admin> {
}
