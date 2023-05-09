package com.gym.server.model.dto.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gym.server.model.dto.BaseDTO;
import com.gym.server.model.dto.POConvertor;
import com.gym.server.model.po.admin.LostItem;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @Author 43901
 * @Date 2023/5/8 12:07
 * @Description
 */
@Getter
public class LostItemAddDTO extends BaseDTO implements POConvertor<LostItem> {

    @JsonProperty("name")
    private String name;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("location")
    private String location;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("pickup_time")
    private Date pickupTime;
    @Override
    public boolean verifyParameters() {
        return !StringUtils.isAnyBlank(name);
    }

    @Override
    public LostItem convertToPO() {
        LostItem lostItem = new LostItem();
        if (!this.verifyParameters()) return lostItem;
        lostItem.setName(this.name);
        lostItem.setLocation(this.location);
        lostItem.setNotes(this.notes);
        lostItem.setPhone(this.phone);
        lostItem.setPickupTime(this.pickupTime);
        lostItem.setCreateTime(new Date());
        lostItem.setUpdateTime(new Date());
        lostItem.setStatus(LostItem.Status.LOST);
        return lostItem;
    }
}
