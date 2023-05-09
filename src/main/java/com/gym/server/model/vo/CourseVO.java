package com.gym.server.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gym.server.model.po.admin.Course;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: 71made
 * @Date: 2023/05/09 14:22
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseVO extends Course implements VOCreator<Course, CourseVO>{

    @JsonProperty("staff_name")
    private String staffName;

    @Override
    public CourseVO createVO(Course obj) {
        this.setId(obj.getId());
        this.setName(obj.getName());
        this.setAmount(obj.getAmount());
        this.setPeriod(obj.getPeriod());
        this.setMemberCount(obj.getMemberCount());
        this.setStaffId(obj.getStaffId());
        this.setStatus(obj.getStatus());
        this.setStartTime(obj.getStartTime());
        this.setEndTime(obj.getEndTime());
        this.setCreateTime(obj.getCreateTime());
        this.setUpdateTime(obj.getUpdateTime());
        return this;
    }
}
