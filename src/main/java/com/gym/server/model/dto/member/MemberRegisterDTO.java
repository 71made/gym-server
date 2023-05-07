package com.gym.server.model.dto.member;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gym.server.model.dto.BaseDTO;
import com.gym.server.model.po.member.Member;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * @Author: 71made
 * @Date: 2023/05/06 16:27
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@Getter
public class MemberRegisterDTO extends BaseDTO {
    @JsonProperty("password")
    private String password;
    @JsonProperty("status")
    private int status;
    @JsonProperty("type")
    private int type;
    @JsonProperty("phone")
    private String phone;

    @Override
    public boolean verifyParameters() {
        return !(password.trim().isEmpty() || phone.trim().isEmpty());
    }
}
