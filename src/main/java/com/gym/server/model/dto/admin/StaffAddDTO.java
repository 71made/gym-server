package com.gym.server.model.dto.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gym.server.model.dto.BaseDTO;
import com.gym.server.model.dto.POConvertor;
import com.gym.server.model.po.admin.Staff;
import com.gym.utils.date.Dates;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @Author: 71made
 * @Date: 2023/05/08 11:24
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@Getter
public class StaffAddDTO extends BaseDTO implements POConvertor<Staff> {

    @JsonProperty("name")
    private String name;
    @JsonProperty("id_card")
    private String idCard;
    @JsonProperty("position")
    private int position;
    @JsonProperty("sex")
    private int sex;
    @JsonProperty("entry_time")
    private String entryTime;
    @Override
    public boolean verifyParameters() {
        return !(StringUtils.isAnyBlank(name, idCard, entryTime) || position < 0 || sex < 0);
    }

    @Override
    public Staff convertToPO() {
        Staff staff = new Staff();
        staff.setName(this.name);
        staff.setSex(Staff.Sex.parse(this.sex));
        staff.setPosition(Staff.Position.parse(this.position));
        String dateStr = this.idCard.substring(6, 14);
        staff.setBirthdayTime(Dates.parseDate(dateStr, Dates.Pattern.NONE_DATE));
        staff.setEntryTime(Dates.parseDate(this.entryTime));
        staff.setStatus(Staff.Status.WORKING);
        staff.setIdCard(this.idCard);
        staff.setCreateTime(new Date());
        staff.setUpdateTime(new Date());
        return staff;
    }
}
