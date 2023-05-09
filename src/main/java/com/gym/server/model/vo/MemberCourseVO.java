package com.gym.server.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gym.server.model.po.admin.Course;
import com.gym.server.model.po.member.MemberCourse;
import com.gym.utils.date.Dates;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @Author: 71made
 * @Date: 2023/05/08 22:17
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MemberCourseVO extends MemberCourse {

    @JsonProperty("staff_name")
    private String StaffName;

    @JsonProperty("course_name")
    private String courseName;


    /**
     * 开始时间
     */
    @JsonProperty("start_time")
    @JsonFormat(pattern = Dates.Pattern.DATETIME, timezone = Dates.TIMEZONE)
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonProperty("end_time")
    @JsonFormat(pattern = Dates.Pattern.DATETIME, timezone = Dates.TIMEZONE)
    private Date endTime;
}
