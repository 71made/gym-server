package com.gym.server.service.member.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gym.exception.ServiceException;
import com.gym.server.mapper.MemberCourseMapper;
import com.gym.server.mapper.MemberMapper;
import com.gym.server.mapper.MemberTradeMapper;
import com.gym.server.model.dto.member.MemberRegisterDTO;
import com.gym.server.model.po.member.Member;
import com.gym.server.model.po.member.MemberTrade;
import com.gym.server.service.member.MemberService;
import com.gym.utils.http.Result;
import com.gym.utils.http.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: 71made
 * @Date: 2023/05/06 16:23
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@Service
@Slf4j
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    MemberCourseMapper memberCourseMapper;

    @Autowired
    MemberTradeMapper memberTradeMapper;

    @Override
    public Result login(String cardNumber, String password) {
        // 查询会员
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq(Member.Columns.CARD_NUMBER, cardNumber);
        Member member = memberMapper.selectOne(wrapper);

        // 如果不存在, 则返回
        if (null == member) return Results.successWithStatus(Results.Status.RECORD_NOT_EXIST, "该会员不存在");

        // 验证密码
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(member.getPassword()))
            return Results.failureWithStatus(Results.Status.LOGIN_ERROR, "会员密码错误");
        member.setPeriod(memberCourseMapper.selectPeriodByMemberId(member.getId()));
        return Results.successWithData(Results.Status.LOGIN_SUCCESS, member);
    }

    @Override
    public Result register(MemberRegisterDTO registerDTO) {
        // 查询是否已存在会员
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        // 手机号作为会员号
        wrapper.eq(Member.Columns.CARD_NUMBER, registerDTO.getPhone())
                .or()
                .eq(Member.Columns.PHONE, registerDTO.getPhone());
        Member member = memberMapper.selectOne(wrapper);

        // 如果存在, 则返回
        if (null != member) return Results.successWithStatus(Results.Status.REGISTER_REPEAT, "该手机号已被使用");

        // 构建新会员
        member = registerDTO.convertToPO();

        // MD5 摘要处理密码
        member.setPassword(DigestUtils.md5DigestAsHex(registerDTO.getPassword().getBytes()));

        // 插入
        if (0 == memberMapper.insert(member)) return Results.failure("会员注册失败, 请重试");
        return Results.success("会员注册成功");
    }

    @Override
    public Result info(Integer memberId) {
        // 查询会员
        Member member = memberMapper.selectById(memberId);
        if (null == member || member.getStatus().equals(Member.Status.DISABLE)) return Results.failureWithStatus(Results.Status.RECORD_NOT_EXIST);
        member.setPeriod(memberCourseMapper.selectPeriodByMemberId(member.getId()));
        return Results.successWithData(member);
    }

    @Override
    public Result all() {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.ne(Member.Columns.STATUS, Member.Status.DELETE);
        // 遍历查询
        List<Member> members = memberMapper.selectList(wrapper);
        for (Member member : members) {
            BigDecimal period = memberCourseMapper.selectPeriodByMemberId(member.getId());
            member.setPeriod(period);
        }
        return Results.successWithData(members);
    }

    @Override
    public Result update(Integer memberId, int status) {
        if (Member.Status.parse(status).equals(Member.Status.UNKNOWN)) return Results.failure("更新状态失败");
        UpdateWrapper<Member> wrapper = new UpdateWrapper<>();
        wrapper.eq(Member.Columns.ID, memberId).set(Member.Columns.STATUS, status);
        if (memberMapper.update(null, wrapper) == 0) return Results.failure("更新状态失败");
        return Results.success("更新成功");
    }

    @Override
    public Result upgrade(Integer memberId) {
        Member member = memberMapper.selectById(memberId);
        if (member.getType().equals(Member.Type.NORMAL)) {
            member.setType(Member.Type.SILVER);
        } else if (member.getType().equals(Member.Type.SILVER)) {
            member.setType(Member.Type.GOLD);
        } else if (member.getType().equals(Member.Type.GOLD)) {
            return Results.failure("已经升级为最高级");
        }
        if (0 == memberMapper.updateById(member)) return Results.failure("更新失败");
        return Results.success("升级成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result recharge(Integer memberId, BigDecimal amount) throws ServiceException {
        BigDecimal lastAmount = memberMapper.selectMemberAmountForUpdate(memberId);
        // 构建充值记录
        MemberTrade memberTrade = new MemberTrade();
        memberTrade.setMemberId(memberId);
        memberTrade.setLastAmount(lastAmount);
        memberTrade.setAmount(amount);
        memberTrade.setNotes("会员卡充值");
        memberTrade.setType(MemberTrade.Type.INCOME);
        memberTrade.setCreateTime(new Date());
        if (0 == memberTradeMapper.insert(memberTrade)) throw new ServiceException("充值失败");
        if (0 == memberMapper.calcMemberAmount(memberId, amount)) throw new ServiceException("充值失败");
        return Results.success("充值成功");
    }
}
