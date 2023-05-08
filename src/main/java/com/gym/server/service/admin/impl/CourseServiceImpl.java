package com.gym.server.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gym.server.mapper.CourseMapper;
import com.gym.server.model.dto.admin.CourseAddDTO;
import com.gym.server.model.dto.admin.CourseUpdateDTO;
import com.gym.server.model.po.admin.Course;
import com.gym.server.service.admin.CourseService;
import com.gym.utils.http.Result;
import com.gym.utils.http.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 71made
 * @Date: 2023/05/08 15:42
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@Service
@Slf4j
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseMapper courseMapper;

    @Override
    public Result all() {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.ne(Course.Columns.STATUS, Course.Status.DELETE.getValue());
        return Results.successWithData(courseMapper.selectList(wrapper));
    }

    @Override
    public Result add(CourseAddDTO courseDTO) {
        Course course = courseDTO.convertToPO();
        if (0 == courseMapper.insert(course)) {
            return Results.failure("新增失败");
        }
        return Results.success("新增成功");
    }

    @Override
    public Result update(CourseUpdateDTO courseDTO) {
        Course course = courseDTO.convertToPO();
        if (0 == courseMapper.updateById(course)) {
            return Results.failure("更新失败");
        }
        return Results.success("更新成功");
    }
}
