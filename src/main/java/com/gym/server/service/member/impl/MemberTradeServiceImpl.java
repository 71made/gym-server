package com.gym.server.service.member.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gym.server.mapper.MemberTradeMapper;
import com.gym.server.model.po.member.MemberTrade;
import com.gym.server.service.member.MemberTradeService;
import com.gym.utils.http.Result;
import com.gym.utils.http.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 71made
 * @Date: 2023/05/08 22:34
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@Service
@Slf4j
public class MemberTradeServiceImpl implements MemberTradeService {

    @Autowired
    MemberTradeMapper memberTradeMapper;

    @Override
    public Result all(Integer memberId) {
        QueryWrapper<MemberTrade> wrapper = new QueryWrapper<>();
        wrapper.eq(MemberTrade.Columns.MEMBER_ID, memberId)
                .orderBy(true, false, MemberTrade.Columns.CREATE_TIME);
        return Results.successWithData(memberTradeMapper.selectList(wrapper));
    }
}
