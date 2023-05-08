package com.gym.server.model.dto.member;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gym.server.model.dto.BaseDTO;
import com.gym.server.model.dto.POConvertor;
import com.gym.server.model.po.member.Member;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 71made
 * @Date: 2023/05/06 16:27
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@Getter
public class MemberRegisterDTO extends BaseDTO implements POConvertor<Member> {
    @JsonProperty("password")
    private String password;
    @JsonProperty("name")
    private String name;
//    @JsonProperty("status")
//    private int status;
//    @JsonProperty("type")
//    private int type;
    @JsonProperty("phone")
    private String phone;

    @Override
    public boolean verifyParameters() {
        return !StringUtils.isAnyBlank(password, name, phone);
    }

    @Override
    public Member convertToPO() {
        Member member = new Member();
        member.setPhone(this.phone);
        member.setName(this.name);
        member.setCardNumber(this.phone);
        member.setPassword(this.password);
        member.setType(Member.Type.NORMAL);
        member.setStatus(Member.Status.NORMAL);
        member.setAmount(BigDecimal.ZERO);
        member.setPeriod(BigDecimal.ZERO);
        member.setCreateTime(new Date());
        member.setUpdateTime(new Date());
        return member;
    }
}
