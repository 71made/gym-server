package com.gym.server.service.member.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gym.exception.ServiceException;
import com.gym.server.mapper.CourseMapper;
import com.gym.server.mapper.MemberCourseMapper;
import com.gym.server.mapper.MemberMapper;
import com.gym.server.mapper.MemberTradeMapper;
import com.gym.server.model.dto.member.MemberCourseAddDTO;
import com.gym.server.model.po.admin.Course;
import com.gym.server.model.po.member.MemberCourse;
import com.gym.server.model.po.member.MemberTrade;
import com.gym.server.service.member.MemberCourseService;
import com.gym.utils.http.Result;
import com.gym.utils.http.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: 71made
 * @Date: 2023/05/08 18:28
 * @ProductName: IntelliJ IDEA
 * @Description:
 */

@Service
@Slf4j
public class MemberCourseServiceImpl implements MemberCourseService {

    @Autowired
    MemberCourseMapper memberCourseMapper;

    @Autowired
    MemberTradeMapper memberTradeMapper;

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    CourseMapper courseMapper;

    @Override
    public Result my(Integer memberId) {
        return Results.successWithData(memberCourseMapper.selectVOsByMemberId(memberId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result add(MemberCourseAddDTO memberCourseDTO) throws ServiceException {
        // 查询课程和余额
        Course course = courseMapper.selectById(memberCourseDTO.getCourseId());
        if (null == course) return Results.failure("加课失败, 课程不存在");

        if (course.getStatus().equals(Course.Status.DELETE) || course.getStatus().equals(Course.Status.STOPPING)) {
            return Results.failure("加课失败, 课程已停止授课");
        }
        if (course.getEndTime().compareTo(new Date()) <= 0) {
            return Results.failure("加课失败, 课程已结束");
        }
        // 查询是否有课程冲突
        if (1 == courseMapper.selectCrushCourseByMemberId(memberCourseDTO.getMemberId(),
                course.getStartTime(), course.getEndTime())) {
            return Results.failure("加课失败, 课程时间冲突");
        }
        BigDecimal memberAmount = memberMapper.selectMemberAmountForUpdate(memberCourseDTO.getMemberId());



        if (memberAmount.compareTo(BigDecimal.ZERO) <= 0) return Results.failure("加课失败, 余额不足");

        // 先构建支出记录
        MemberTrade memberTrade = new MemberTrade();
        memberTrade.setMemberId(memberCourseDTO.getMemberId());
        memberTrade.setLastAmount(memberAmount);
        memberTrade.setAmount(course.getAmount());
        memberTrade.setNotes("加入/购买《" + course.getName() + "》课程");
        memberTrade.setType(MemberTrade.Type.CAST);
        memberTrade.setCreateTime(new Date());

        if (0 == memberMapper.calcMemberAmount(memberCourseDTO.getMemberId(), course.getAmount().negate())) {
            throw new ServiceException("加课失败, 更新余额异常");
        }
        if (0 == memberTradeMapper.insert(memberTrade)) {
            throw new ServiceException("加课失败, 插入交易记录异常");
        }
        if (0 == courseMapper.calcMemberCountByCourseId(memberCourseDTO.getCourseId(), 1)) {
            throw new ServiceException("加课失败, 增加课程人数异常");
        }

        // 查询加课记录
        QueryWrapper<MemberCourse> memberCourseQueryWrapper = new QueryWrapper<>();
        memberCourseQueryWrapper.eq(MemberCourse.Columns.COURSE_ID, memberCourseDTO.getCourseId())
                .eq(MemberCourse.Columns.MEMBER_ID, memberCourseDTO.getMemberId());
        MemberCourse memberCourse = memberCourseMapper.selectOne(memberCourseQueryWrapper);
        // 若存在加课记录, 则更新
        if (null != memberCourse) {
            if (memberCourse.getStatus().equals(MemberCourse.Status.SIGN)) return Results.failure("不可以重复加入课程");
            memberCourse.setStatus(MemberCourse.Status.SIGN);
            if (0 == memberCourseMapper.updateById(memberCourse)) throw new ServiceException("加课失败");
        } else {
            memberCourse = memberCourseDTO.convertToPO();
            memberCourse.setStaffId(course.getStaffId());
            if (0 == memberCourseMapper.insert(memberCourse)) throw new ServiceException("加课失败");
        }
        return Results.success("加课成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result cancel(Integer memberId, Integer memberCourseId) throws ServiceException {
        // 查询加课记录
        QueryWrapper<MemberCourse> memberCourseQueryWrapper = new QueryWrapper<>();
        memberCourseQueryWrapper.eq(MemberCourse.Columns.ID, memberCourseId).eq(MemberCourse.Columns.MEMBER_ID, memberId);
        MemberCourse memberCourse = memberCourseMapper.selectOne(memberCourseQueryWrapper);
        // 若存在加课记录, 则更新
        if (null != memberCourse) {
            // 查询课程和余额
            Course course = courseMapper.selectById(memberCourse.getCourseId());
            BigDecimal memberAmount = memberMapper.selectMemberAmountForUpdate(memberCourse.getMemberId());

            // 先构建退款记录
            MemberTrade memberTrade = new MemberTrade();
            memberTrade.setMemberId(memberCourse.getMemberId());
            memberTrade.setLastAmount(memberAmount);
            memberTrade.setAmount(course.getAmount());
            memberTrade.setNotes("退出《" + course.getName() + "》课程");
            memberTrade.setType(MemberTrade.Type.INCOME);
            memberTrade.setCreateTime(new Date());

            if (0 == memberMapper.calcMemberAmount(memberCourse.getMemberId(), course.getAmount())) {
                throw new ServiceException("退课失败, 更新余额异常");
            }
            if (0 == memberTradeMapper.insert(memberTrade)) {
                throw new ServiceException("退课失败, 插入交易记录异常");
            }
            if (0 == courseMapper.calcMemberCountByCourseId(memberCourse.getCourseId(), -1)) {
                throw new ServiceException("退课失败, 减少课程人数异常");
            }

            // 更新状态
            memberCourse.setStatus(MemberCourse.Status.SIGN_OUT);
            if (0 == memberCourseMapper.updateById(memberCourse)) throw new ServiceException("退课失败");
        } else return Results.success("并没有加入此课程");
        return Results.success("退课成功");
    }
}
