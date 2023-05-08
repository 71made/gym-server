package com.gym.server.model.po.admin;

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

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * @Author: 71made
 * @Date: 2023/05/08 11:07
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@Data
@TableName("`staff`")
public class Staff {

    /**
     * id
     */
    @TableId(value = Columns.ID, type = IdType.AUTO)
    private Integer id;

    /**
     * 员工名称
     */
    @TableField(Columns.NAME)
    private String name;

    /**
     * 员工性别
     */
    @TableField(value = Columns.SEX, typeHandler = Sex.TypeHandler.class)
    private Sex sex;

    /**
     * 状态: 0-在职 1-已离职 2-删除
     */
    @TableField(value = Columns.STATUS, typeHandler = Status.TypeHandler.class)
    private Status status;

    /**
     * 身份证
     */
    @TableField(Columns.ID_CARD)
    @JsonProperty("id_card")
    private String idCard;

    @TableField(value = Columns.POSITION, typeHandler = Position.TypeHandler.class)
    private Position position;

    /**
     * 出生日期
     */
    @TableField(Columns.BIRTHDAY_TIME)
    @JsonProperty("birthday_time")
    @JsonFormat(pattern = Dates.Pattern.DATETIME, timezone = Dates.TIMEZONE)
    private Date birthdayTime;

    /**
     * 入职时间
     */
    @TableField(Columns.ENTRY_TIME)
    @JsonProperty("entry_time")
    @JsonFormat(pattern = Dates.Pattern.DATETIME, timezone = Dates.TIMEZONE)
    private Date entryTime;

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
         * 在职
         */
        WORKING(0),
        /**
         * 已离职
         */
        LEFT(1),
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

    @Getter
    @AllArgsConstructor
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    public enum Sex {
        MALE(0),
        FEMALE(1),
        UNKNOWN(null);

        private final Integer value;

        public static Sex parse(Integer code) {
            if (null == code || (values().length - 1) <= code) return UNKNOWN;
            return values()[code];
        }

        @MappedJdbcTypes(JdbcType.INTEGER)
        @MappedTypes(Sex.class)
        public static class TypeHandler extends BaseTypeHandler<Sex> {

            @Override
            public void setNonNullParameter(PreparedStatement ps, int i, Sex parameter, JdbcType jdbcType) throws SQLException {
                ps.setInt(i, parameter.value);
            }

            @Override
            public Sex getNullableResult(ResultSet rs, String columnName) throws SQLException {
                return Sex.parse(rs.getInt(columnName));
            }

            @Override
            public Sex getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
                return Sex.parse(rs.getInt(columnIndex));
            }

            @Override
            public Sex getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
                return Sex.parse(cs.getInt(columnIndex));
            }
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum Position {
        ADMIN(0, "管理员"),
        TRAINER(1, "教练"),
        RECEPTIONIST(2, "前台"),
        CLEANER(3, "保洁"),
        UNKNOWN(null, "非法职位");

        private final Integer code;

        private final  String msg;

        public static Position parse(Integer code) {
            if (null == code || (values().length - 1) <= code) return UNKNOWN;
            return values()[code];
        }

        @MappedJdbcTypes(JdbcType.INTEGER)
        @MappedTypes(Position.class)
        public static class TypeHandler extends BaseTypeHandler<Position> {

            @Override
            public void setNonNullParameter(PreparedStatement ps, int i, Position parameter, JdbcType jdbcType) throws SQLException {
                ps.setInt(i, parameter.code);
            }

            @Override
            public Position getNullableResult(ResultSet rs, String columnName) throws SQLException {
                return Position.parse(rs.getInt(columnName));
            }

            @Override
            public Position getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
                return Position.parse(rs.getInt(columnIndex));
            }

            @Override
            public Position getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
                return Position.parse(cs.getInt(columnIndex));
            }
        }
    }


    /**
     * 映射字段名称
     */
    public static final class Columns{
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String STATUS = "status";
        public static final String SEX = "sex";
        public static final String ID_CARD = "id_card";
        public static final String POSITION = "position";
        public static final String BIRTHDAY_TIME = "birthday_time";
        public static final String ENTRY_TIME = "entry_time";
        public static final String CREATE_TIME = "create_time";
        public static final String UPDATE_TIME = "update_time";
    }
}
