package com.gym.server.service.member.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gym.server.mapper.MemberMapper;
import com.gym.server.model.dto.member.MemberRegisterDTO;
import com.gym.server.model.po.member.Member;
import com.gym.server.service.member.MemberService;
import com.gym.utils.http.Result;
import com.gym.utils.http.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;

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
        member = new Member();
        member.setPhone(registerDTO.getPhone());
        member.setName(registerDTO.getName());
        // MD5 摘要处理密码
        member.setPassword(DigestUtils.md5DigestAsHex(registerDTO.getPassword().getBytes()));
        member.setCardNumber(registerDTO.getPhone());
        member.setType(Member.Type.parse(registerDTO.getType()));
        member.setStatus(Member.Status.parse(registerDTO.getType()));
        member.setAmount(BigDecimal.ZERO);
        member.setPeriod(BigDecimal.ZERO);

        // 插入
        if (0 == memberMapper.insert(member)) return Results.failure("会员注册失败, 请重试");
        return Results.success("会员注册成功");
    }

    @Override
    public Result info(Integer memberId) {
        // 查询会员
        Member member = memberMapper.selectById(memberId);
        if (null == member || member.getStatus().equals(Member.Status.DISABLE)) return Results.failureWithStatus(Results.Status.RECORD_NOT_EXIST);
        return Results.successWithData(member);
    }

    @Override
    public Result all() {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.ne(Member.Columns.STATUS, Member.Status.DELETE);
        return Results.successWithData(memberMapper.selectList(wrapper));
    }

    @Override
    public Result update(Integer memberId, int status) {
        if (Member.Status.parse(status).equals(Member.Status.UNKNOWN)) return Results.failure("更新状态失败");
        UpdateWrapper<Member> wrapper = new UpdateWrapper<>();
        wrapper.eq(Member.Columns.ID, memberId).set(Member.Columns.STATUS, status);
        if (memberMapper.update(null, wrapper) == 0) return Results.failure("更新状态失败");
        return Results.success("更新成功");
    }
}
