package com.gym.server.model.dto.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gym.server.model.dto.BaseDTO;
import com.gym.server.model.dto.POConvertor;
import com.gym.server.model.po.admin.Staff;
import com.gym.utils.date.Dates;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @Author: 71made
 * @Date: 2023/05/08 11:40
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
public class StaffUpdateDTO extends BaseDTO implements POConvertor<Staff> {

    @JsonProperty("name")
    private String name;
    @JsonProperty("id_card")
    private String idCard;
    @JsonProperty("position")
    private int position;
    @JsonProperty("status")
    private int status;
    @JsonProperty("entry_time")
    private String entryTime;
    @Override
    public boolean verifyParameters() {
        return !(StringUtils.isAnyBlank(name, idCard, entryTime) || position < 0 || status < 0);
    }

    @Override
    public Staff convertToPO() {
        Staff staff = new Staff();
        staff.setName(this.name);
        staff.setPosition(Staff.Position.parse(this.position));
        staff.setStatus(Staff.Status.parse(this.status));
        String dateStr = idCard.substring(6, 8);
        staff.setBirthdayTime(Dates.parseDate(dateStr, Dates.Pattern.NONE_DATE));
        staff.setEntryTime(Dates.parseDate(this.entryTime));
        staff.setStatus(Staff.Status.WORKING);
        staff.setIdCard(this.idCard);
        staff.setUpdateTime(new Date());
        return staff;
    }
}
