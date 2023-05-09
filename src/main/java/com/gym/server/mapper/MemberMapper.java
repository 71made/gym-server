package com.gym.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gym.server.model.po.member.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;


/**
 * @Author: 71made
 * @Date: 2023/05/06 15:35
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@Mapper
@Repository
public interface MemberMapper extends BaseMapper<Member> {

    @Update("UPDATE `member` SET `amount` = `amount` + #{amount} WHERE `id` = #{member_id}")
    int calcMemberAmount(@Param("member_id") Integer memberId, @Param("amount") BigDecimal amount);

    @Select("SELECT `amount` FROM `member` WHERE `id` = #{member_id} FOR UPDATE")
    BigDecimal selectMemberAmountForUpdate(@Param("member_id") Integer memberId);
}
