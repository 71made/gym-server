package com.gym.server.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gym.server.mapper.AdminMapper;
import com.gym.server.mapper.MemberMapper;
import com.gym.server.model.po.admin.Admin;
import com.gym.server.model.po.member.Member;
import com.gym.server.service.admin.AdminService;
import com.gym.utils.http.Result;
import com.gym.utils.http.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @Author: 71made
 * @Date: 2023/05/07 12:06
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    MemberMapper memberMapper;

    @Override
    public Result login(String account, String password) {
        // 查询管理员
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq(Admin.Columns.ACCOUNT, account);
        Admin admin = adminMapper.selectOne(wrapper);

        // 如果不存在, 则返回
        if (null == admin) return Results.successWithStatus(Results.Status.RECORD_NOT_EXIST, "该管理员不存在");

        // 验证密码
        if (!password.equals(admin.getPassword()))
            return Results.failureWithStatus(Results.Status.LOGIN_ERROR, "管理员密码错误");
        return Results.successWithData(Results.Status.LOGIN_SUCCESS, admin);
    }

    @Override
    public Result memberAll() {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.ne(Member.Columns.STATUS, Member.Status.DELETE);
        return Results.successWithData(memberMapper.selectList(wrapper));
    }

    @Override
    public Result update(Integer memberId, Member.Status status) {
        if (status.equals(Member.Status.UNKNOWN)) return Results.failure("更新状态失败");
        UpdateWrapper<Member> wrapper = new UpdateWrapper<>();
        wrapper.eq(Member.Columns.ID, memberId).set(Member.Columns.STATUS, status.getValue());
        if (memberMapper.update(null, wrapper) == 0) return Results.failure("更新状态失败");
        return Results.success("更新成功");
    }
}
