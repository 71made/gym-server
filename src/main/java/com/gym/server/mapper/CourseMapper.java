package com.gym.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gym.server.model.po.admin.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * @Author: 71made
 * @Date: 2023/05/08 15:44
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@Mapper
@Repository
public interface CourseMapper extends BaseMapper<Course> {
}
