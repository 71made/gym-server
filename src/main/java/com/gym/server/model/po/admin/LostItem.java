package com.gym.server.model.po.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * <p>
 *
 * </p>
 *
 * @author 作者
 * @since 2023-05-07
 */
@Data
public class LostItem {

    /**
     * id
     */
    @TableId(value = Columns.ID, type = IdType.AUTO)
    private Integer id;

    /**
     * 遗失物品名称
     */
    @TableField(Columns.NAME)
    private String name;

    /**
     * 状态: 0-认领中 1-已认领 2-删除
     */
    @TableField(Columns.STATUS)
    private Status status;

    /**
     * 备注
     */
    @TableField(Columns.NOTES)
    private String notes;

    /**
     * 地点位置
     */
    @TableField(Columns.LOCATION)
    private String location;

    /**
     * 捡拾人电话
     */
    @TableField(Columns.PHONE)
    private String phone;

    /**
     * 捡拾时间
     */
    @TableField(Columns.PICKUP_TIME)
    @JsonProperty("pickup_time")
    @JsonFormat(pattern = Dates.Pattern.DATETIME, timezone = Dates.TIMEZONE)
    private Date pickupTime;

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
         * 丢失中
         */
        LOST(0),
        /**
         * 已找到
         */
        FOUND(1),
        /**
         * 已删除
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


    public static final class Columns {
        public static final String ID="id";
        public static final String NAME="name";
        public static final String STATUS="status";
        public static final String NOTES="notes";
        public static final String LOCATION="location";
        public static final String PHONE="phone";
        public static final String PICKUP_TIME="pickup_time";
        public static final String CREATE_TIME="create_time";
        public static final String UPDATE_TIME="update_time";
    }


}
