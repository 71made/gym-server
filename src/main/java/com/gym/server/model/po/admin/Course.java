package com.gym.server.model.po.admin;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gym.utils.date.Dates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;

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
    @TableField(value = Columns.STATUS, typeHandler = Status.TypeHandler.class)
    private Status status;

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
    @TableField(value = Columns.AMOUNT, fill = FieldFill.UPDATE)
    private BigDecimal amount;

    /**
     * 开始时间
     */
    @TableField(Columns.START_TIME)
    @JsonProperty("start_time")
    @JsonFormat(pattern = Dates.Pattern.DATETIME, timezone = Dates.TIMEZONE)
    private Date startTime;

    /**
     * 结束时间
     */
    @TableField(Columns.END_TIME)
    @JsonProperty("end_time")
    @JsonFormat(pattern = Dates.Pattern.DATETIME, timezone = Dates.TIMEZONE)
    private Date endTime;

    /**
     * 创建时间
     */
    @TableField(Columns.CREATE_TIME)
    @JsonProperty("create_time")
    @JsonFormat(pattern = Dates.Pattern.DATETIME, timezone = Dates.TIMEZONE)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(Columns.UPDATE_TIME)
    @JsonProperty("update_time")
    @JsonFormat(pattern = Dates.Pattern.DATETIME, timezone = Dates.TIMEZONE)
    private Date updateTime;

    @Getter
    @AllArgsConstructor
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    public enum Status {
        /**
         * 正常授课
         */
        WORKING(0),
        /**
         * 停止授课
         */
        STOPPING(1),
        /**
         * 删除
         */
        DELETE(2),
        UNKNOWN(null);

        private final Integer value;

        public static Status parse(Integer code) {
            if (null == code || (values().length - 1) <= code) return UNKNOWN;
            return values()[code];
        }

        @MappedJdbcTypes(JdbcType.INTEGER)
        @MappedTypes(Status.class)
        public static class TypeHandler extends BaseTypeHandler<Status> {

            @Override
            public void setNonNullParameter(PreparedStatement ps, int i, Status parameter, JdbcType jdbcType) throws SQLException {
                ps.setInt(i, parameter.value);
            }

            @Override
            public Status getNullableResult(ResultSet rs, String columnName) throws SQLException {
                return Status.parse(rs.getInt(columnName));
            }

            @Override
            public Status getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
                return Status.parse(rs.getInt(columnIndex));
            }

            @Override
            public Status getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
                return Status.parse(cs.getInt(columnIndex));
            }
        }
    }

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
