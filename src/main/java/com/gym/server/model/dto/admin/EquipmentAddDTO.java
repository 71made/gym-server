package com.gym.server.model.dto.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gym.server.model.dto.BaseDTO;
import com.gym.server.model.dto.POConvertor;
import com.gym.server.model.po.admin.Equipment;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @Author: 71made
 * @Date: 2023/05/07 22:54
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@Getter
public class EquipmentAddDTO extends BaseDTO implements POConvertor<Equipment> {

    @JsonProperty("name")
    private String name;

    @JsonProperty("location")
    private String location;
    @Override
    public boolean verifyParameters() {
        return !StringUtils.isAnyBlank(name, location);
    }

    @Override
    public Equipment convertToPO() {
        Equipment equipment = new Equipment();
        if (!this.verifyParameters()) return equipment;
        equipment.setName(this.name);
        equipment.setLocation(this.location);
        equipment.setStatus(Equipment.Status.USING);
        equipment.setCreateTime(new Date());
        equipment.setUpdateTime(new Date());
        return equipment;
    }
}
