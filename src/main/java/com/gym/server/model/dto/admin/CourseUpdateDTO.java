package com.gym.server.model.dto.admin;

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
 * @Date: 2023/05/08 15:56
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@Getter
public class CourseUpdateDTO extends BaseDTO implements POConvertor<Course> {

    @JsonProperty("id")
    private Integer id;

    /**
     * 教练 id
     */
    @JsonProperty("staff_id")
    private Integer staffId;

    @JsonProperty("status")
    private int status;

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
                && (null != amount && !amount.equals(BigDecimal.ZERO)) && null != staffId && status >= 0;
    }

    @Override
    public Course convertToPO() {
        Course course = new Course();
        course.setId(this.id);
        course.setName(this.name);
        course.setStaffId(this.staffId);
        course.setStatus(Course.Status.parse(this.status));
        course.setPeriod(this.period);
        course.setAmount(this.amount);
        course.setStartTime(Dates.parseDate(this.startTime));
        course.setEndTime(Dates.parseDate(this.endTime));
        course.setUpdateTime(new Date());
        return course;
    }
}
