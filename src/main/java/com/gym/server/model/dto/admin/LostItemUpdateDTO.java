package com.gym.server.model.dto.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gym.server.model.dto.BaseDTO;
import com.gym.server.model.dto.POConvertor;
import com.gym.server.model.po.admin.LostItem;
import com.gym.utils.date.Dates;

import java.util.Date;

/**
 * @Author 43901
 * @Date 2023/5/8 12:07
 * @Description
 */
public class LostItemUpdateDTO extends BaseDTO implements POConvertor<LostItem> {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("location")
    private String location;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("pickup_time")
    @JsonFormat(pattern = Dates.Pattern.DATETIME, timezone = Dates.TIMEZONE)
    private Date pickupTime;

    @JsonProperty("status")
    private int status;

    @Override
    public boolean verifyParameters() {
        return !(status < 0 || null == id);
    }

    @Override
    public LostItem convertToPO() {
        LostItem lostItem = new LostItem();
        if (!this.verifyParameters()) return lostItem;
        lostItem.setId(this.id);
        lostItem.setName(this.name);
        lostItem.setLocation(this.location);
        lostItem.setNotes(this.notes);
        lostItem.setPhone(this.phone);
        lostItem.setPickupTime(this.pickupTime);
        lostItem.setUpdateTime(new Date());
        lostItem.setStatus(LostItem.Status.parse(status));
        return lostItem;
    }
}
