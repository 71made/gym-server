package com.gym.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gym.server.model.po.admin.Staff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Author: 71made
 * @Date: 2023/05/08 11:47
 * @ProductName: IntelliJ IDEA
 * @Description:
 */

@Mapper
@Repository
public interface StaffMapper extends BaseMapper<Staff> {

    @Select("select `name` from `staff` where `id` = #{staff_id}")
    String selectStaffNameById(@Param("staff_id") Integer id);
}
