package com.gym.server.model.po.member;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gym.server.model.po.admin.Course;
import com.gym.utils.date.Dates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
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
 * @Date: 2023/05/08 20:26
 * @ProductName: IntelliJ IDEA
 * @Description:
 */

@Data
public class MemberTrade {

    @TableId(value = Columns.ID, type = IdType.AUTO)
    private Integer id;

    @TableField(Columns.MEMBER_ID)
    private Integer memberId;

    @TableField(value = Columns.TYPE, typeHandler = Type.TypeHandler.class)
    private Type type;

    @TableField(Columns.AMOUNT)
    private BigDecimal amount;

    @TableField(Columns.LAST_AMOUNT)
    @JsonProperty("last_amount")
    private BigDecimal lastAmount;

    @TableField(Columns.NOTES)
    private String notes;

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
    public enum Type {
        /**
         * 收入
         */
        INCOME(0),
        /**
         * 支出
         */
        CAST(1),
        UNKNOWN(null);

        private final Integer value;

        public static Type parse(Integer code) {
            if (null == code || (values().length - 1) <= code) return UNKNOWN;
            return values()[code];
        }

        @MappedJdbcTypes(JdbcType.INTEGER)
        @MappedTypes(Type.class)
        public static class TypeHandler extends BaseTypeHandler<Type> {

            @Override
            public void setNonNullParameter(PreparedStatement ps, int i, Type parameter, JdbcType jdbcType) throws SQLException {
                ps.setInt(i, parameter.value);
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
     * 映射数据库字段
     */
    public static final class Columns {
        public static final String ID = "id";
        public static final String MEMBER_ID = "member_id";
        public static final String LAST_AMOUNT = "last_amount";
        public static final String AMOUNT = "amount";
        public static final String TYPE = "type";
        public static final String NOTES = "notes";
        public static final String CREATE_TIME = "create_time";
        public static final String UPDATE_TIME = "update_time";
    }

}
