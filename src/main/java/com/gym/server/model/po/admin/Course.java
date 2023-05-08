package com.gym.server.model.po.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gym.utils.date.Dates;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("`course`")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = Columns.ID, type = IdType.AUTO)
    private Integer id;

    /**
     * 教练 id
     */
    @TableField(Columns.STAFF_ID)
    @JsonProperty("staff_id")
    private Integer staffId;

    /**
     * 课程名称
     */
    @TableField(Columns.NAME)
    private String name;

    /**
     * 状态: 0-进行中 1-停课/删除
     */
    @TableField(Columns.STATUS)
    private Boolean status;

    /**
     * 报名人数
     */
    @TableField(Columns.MEMBER_COUNT)
    @JsonProperty("member_count")
    private Integer memberCount;

    /**
     * 课程学时
     */
    @TableField(Columns.PERIOD)
    private BigDecimal period;

    /**
     * 报名金额
     */
    @TableField(Columns.AMOUNT)
    private BigDecimal amount;

    /**
     * 开始时间
     */
    @TableField(Columns.START_TIME)
    @JsonProperty("start_time")
    @JsonFormat(pattern = Dates.Pattern.DATETIME, timezone = Dates.TIMEZONE)
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @TableField(Columns.END_TIME)
    @JsonProperty("end_time")
    @JsonFormat(pattern = Dates.Pattern.DATETIME, timezone = Dates.TIMEZONE)
    private LocalDateTime endTime;

    /**
     * 创建时间
     */
    @TableField(Columns.CREATE_TIME)
    @JsonProperty("create_time")
    @JsonFormat(pattern = Dates.Pattern.DATETIME, timezone = Dates.TIMEZONE)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(Columns.UPDATE_TIME)
    @JsonProperty("update_time")
    @JsonFormat(pattern = Dates.Pattern.DATETIME, timezone = Dates.TIMEZONE)
    private LocalDateTime updateTime;

    /**
     * 映射字段名称
     */
    public static final class Columns {
        public static final String ID = "id";
        public static final String STAFF_ID = "staff_id";
        public static final String NAME = "name";
        public static final String STATUS = "status";
        public static final String MEMBER_COUNT = "member_count";
        public static final String PERIOD = "period";
        public static final String AMOUNT = "amount";
        public static final String START_TIME = "start_time";
        public static final String END_TIME = "end_time";
        public static final String CREATE_TIME = "create_time";
        public static final String UPDATE_TIME = "update_time";
    }


}
