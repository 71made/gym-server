package com.gym.server.model.dto.admin;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gym.server.model.dto.BaseDTO;
import com.gym.server.model.dto.POConvertor;
import com.gym.server.model.po.admin.Course;
import com.gym.utils.date.Dates;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 71made
 * @Date: 2023/05/08 15:46
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@Getter
public class CourseAddDTO extends BaseDTO implements POConvertor<Course> {

    /**
     * 教练 id
     */
    @JsonProperty("staff_id")
    private Integer staffId;

    /**
     * 课程名称
     */
    @JsonProperty("name")
    private String name;

    /**
     * 课程学时
     */
    @JsonProperty("period")
    private BigDecimal period;

    /**
     * 报名金额
     */
    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("start_time")
    private String startTime;

    @JsonProperty("end_time")
    private String endTime;

    @Override
    public boolean verifyParameters() {
        return !StringUtils.isAnyBlank(name) && (null != period && !period.equals(BigDecimal.ZERO))
                && (null != amount && !amount.equals(BigDecimal.ZERO)) && null != staffId;
    }

    @Override
    public Course convertToPO() {
        Course course = new Course();
        course.setName(this.name);
        course.setStaffId(this.staffId);
        course.setStatus(Course.Status.WORKING);
        course.setMemberCount(0);
        course.setPeriod(this.period);
        course.setAmount(this.amount);
        course.setStartTime(Dates.parseDate(this.startTime));
        course.setEndTime(Dates.parseDate(this.endTime));
        course.setCreateTime(new Date());
        course.setUpdateTime(new Date());
        return course;
    }
}
