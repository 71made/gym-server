package com.gym.server.model.po.member;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gym.server.mapper.MemberCourseMapper;
import com.gym.server.model.po.admin.Admin;
import com.gym.utils.date.Dates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * @Author: 71made
 * @Date: 2023/05/06 15:59
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@Data
public class Member {

    @TableId(value = Columns.ID, type = IdType.AUTO)
    private Integer id;

    @TableField(Columns.CARD_NUMBER)
    @JsonProperty("card_number")
    private String cardNumber;

    @TableField(Columns.NAME)
    private String name;

    @TableField(Columns.PASSWORD)
    @JsonIgnore
    private String password;

    @TableField(value = Columns.STATUS, typeHandler = Status.TypeHandler.class)
    private Status status;

    @TableField(Columns.AMOUNT)
    private BigDecimal amount;

    @TableField(exist = false)
    private BigDecimal period;

    @TableField(value = Columns.TYPE, typeHandler = Type.TypeHandler.class)
    private Type type;

    @TableField(Columns.PHONE)
    private String phone;

    @TableField(Columns.CREATE_TIME)
    @JsonProperty("create_time")
    @JsonFormat(pattern = Dates.Pattern.DATETIME, timezone = Dates.TIMEZONE)
    private Date createTime;

    @TableField(Columns.UPDATE_TIME)
    @JsonProperty("update_time")
    @JsonFormat(pattern = Dates.Pattern.DATETIME, timezone = Dates.TIMEZONE)
    private Date updateTime;

    @Getter
    @AllArgsConstructor
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    public enum Status {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 异常标记
         */
        ABNORMAL(1),
        /**
         * 禁用
         */
        DISABLE(2),
        /**
         * 删除
         */
        DELETE(3),
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
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum Type {
        NORMAL(0, "普通会员"),
        SILVER(1, "白银会员"),
        GOLD(2, "黄金会员"),
        UNKNOWN(null, "非法类型");

        private final Integer code;

        private final  String msg;

        public static Type parse(Integer code) {
            if (null == code || (values().length - 1) <= code) return UNKNOWN;
            return values()[code];
        }

        @MappedJdbcTypes(JdbcType.INTEGER)
        @MappedTypes(Type.class)
        public static class TypeHandler extends BaseTypeHandler<Type> {

            @Override
            public void setNonNullParameter(PreparedStatement ps, int i, Type parameter, JdbcType jdbcType) throws SQLException {
                ps.setInt(i, parameter.code);
            }

            @Override
            public Type getNullableResult(ResultSet rs, String columnName) throws SQLException {
                return Type.parse(rs.getInt(columnName));
            }

            @Override
            public Type getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
                return Type.parse(rs.getInt(columnIndex));
            }

            @Override
            public Type getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
                return Type.parse(cs.getInt(columnIndex));
            }
        }
    }

    /**
     * 映射字段名称
     */
    public static final class Columns {
        public static final String ID = "id";
        public static final String CARD_NUMBER = "card_number";
        public static final String PASSWORD = "password";
        public static final String NAME = "name";
        public static final String TYPE = "type";
        public static final String AMOUNT = "amount";
        public static final String PHONE = "phone";
        public static final String STATUS = "status";
        public static final String CREATE_TIME = "create_time";
        public static final String UPDATE_TIME = "update_time";
    }
}
