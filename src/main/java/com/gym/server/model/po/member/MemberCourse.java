package com.gym.server.model.po.member;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class MemberCourse {

    /**
     * id
     */
    @TableId(value = Columns.ID, type = IdType.AUTO)
    private Integer id;

    /**
     * 课程 id
     */
    @TableField(Columns.COURSE_ID)
    @JsonProperty("course_id")
    private Integer courseId;

    /**
     * 教练 id
     */
    @TableField(Columns.STAFF_ID)
    @JsonProperty("staff_id")
    private Integer staffId;

    /**
     * 会员 id
     */
    @TableField(Columns.MEMBER_ID)
    @JsonProperty("member_id")
    private Integer memberId;

    /**
     * 状态: 0-报名 1-取消报名
     */
    @TableField(Columns.STATUS)
    private Status status;

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
         * 报名
         */
        SIGN(0),
        /**
         * 取消报名
         */
        SIGN_OUT(1),
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
     * 映射数据库字段
     */
    public static final class Columns {
        public static final String ID = "id";
        public static final String COURSE_ID = "course_id";
        public static final String COURSE_NAME = "course_name";
        public static final String MEMBER_ID = "member_id";
        public static final String STAFF_ID = "staff_id";
        public static final String STAFF_NAME = "staff_name";
        public static final String STATUS = "status";
        public static final String CREATE_TIME = "create_time";
        public static final String UPDATE_TIME = "update_time";
    }

}
