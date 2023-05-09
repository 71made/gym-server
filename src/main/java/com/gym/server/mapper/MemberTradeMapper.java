package com.gym.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gym.server.model.po.member.MemberTrade;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @Author: 71made
 * @Date: 2023/05/08 20:26
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@Mapper
@Repository
public interface MemberTradeMapper extends BaseMapper<MemberTrade> {
}
