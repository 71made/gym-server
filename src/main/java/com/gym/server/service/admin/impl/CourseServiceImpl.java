package com.gym.server.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gym.exception.ServiceException;
import com.gym.server.mapper.*;
import com.gym.server.model.dto.admin.CourseAddDTO;
import com.gym.server.model.dto.admin.CourseUpdateDTO;
import com.gym.server.model.po.admin.Course;
import com.gym.server.model.po.member.MemberCourse;
import com.gym.server.model.po.member.MemberTrade;
import com.gym.server.model.vo.CourseVO;
import com.gym.server.service.admin.CourseService;
import com.gym.server.service.member.MemberCourseService;
import com.gym.utils.http.Result;
import com.gym.utils.http.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author: 71made
 * @Date: 2023/05/08 15:42
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@Service
@Slf4j
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    StaffMapper staffMapper;

    @Autowired
    MemberCourseMapper memberCourseMapper;

    @Autowired
    MemberTradeMapper memberTradeMapper;

    @Autowired
    MemberMapper memberMapper;

    @Override
    public Result all() {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.ne(Course.Columns.STATUS, Course.Status.DELETE.getValue());
        List<Course> courses = courseMapper.selectList(wrapper);
        List<CourseVO> courseVOs = new ArrayList<>();
        for (Course course : courses) {
            CourseVO vo = new CourseVO();
            vo = vo.createVO(course);
            vo.setStaffName(staffMapper.selectStaffNameById(course.getId()));
            courseVOs.add(vo);
        }
        return Results.successWithData(courseVOs);
    }

    @Override
    public Result add(CourseAddDTO courseDTO) {
        Course course = courseDTO.convertToPO();
        if (0 == courseMapper.insert(course)) {
            return Results.failure("新增失败");
        }
        return Results.success("新增成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result update(CourseUpdateDTO courseDTO) throws ServiceException {
        Course oldCourse = courseMapper.selectById(courseDTO.getId());
        // 转换为 PO 准备对课程信息更新
        Course course = courseDTO.convertToPO();

        // 对于 WORKING -> DELETE/STOPPING 状态转换的情况需要对会员退课处理
        if (oldCourse.getStatus().equals(Course.Status.WORKING)) {
            // 更新状态时, 对会员退课, 只对还上课的课程退课
            if ((Course.Status.parse(courseDTO.getStatus()).equals(Course.Status.DELETE)
                    || Course.Status.parse(courseDTO.getStatus()).equals(Course.Status.STOPPING))
                    && oldCourse.getEndTime().before(new Date())) {
                // 查询还在课中的会员 id
                QueryWrapper<MemberCourse> queryWrapper = new QueryWrapper<>();
                queryWrapper.select(MemberCourse.Columns.MEMBER_ID)
                        .eq(MemberCourse.Columns.COURSE_ID, oldCourse.getId())
                        .ne(MemberCourse.Columns.STATUS, MemberCourse.Status.SIGN_OUT);
                List<Object> memberIds = memberCourseMapper.selectObjs(queryWrapper);
                // 退课
                for (Object memberId : memberIds) {
                    // 查询课程和余额
                    BigDecimal memberAmount = memberMapper.selectMemberAmountForUpdate((Integer) memberId);

                    // 先构建退款记录
                    MemberTrade memberTrade = new MemberTrade();
                    memberTrade.setMemberId((Integer) memberId);
                    memberTrade.setLastAmount(memberAmount);
                    memberTrade.setAmount(oldCourse.getAmount());
                    memberTrade.setNotes("退出《" + oldCourse.getName() + "》课程");
                    memberTrade.setType(MemberTrade.Type.INCOME);
                    memberTrade.setCreateTime(new Date());

                    if (0 == memberMapper.calcMemberAmount((Integer) memberId, oldCourse.getAmount())) {
                        throw new ServiceException("修改状态失败, 更新会员余额异常");
                    }
                    if (0 == memberTradeMapper.insert(memberTrade)) {
                        throw new ServiceException("修改状态失败, 插入会员交易记录异常");
                    }
                }
                // 批量更新状态
                UpdateWrapper<MemberCourse> updateWrapper = new UpdateWrapper<>();
                updateWrapper.set(MemberCourse.Columns.STATUS, MemberCourse.Status.SIGN_OUT)
                        .eq(MemberCourse.Columns.COURSE_ID, oldCourse.getId());
                if (0 == memberCourseMapper.update(null, updateWrapper)) throw new ServiceException("修改状态失败");
                // 设置选课人数为零
                course.setMemberCount(0);
            }
        }
        if (0 == courseMapper.updateById(course)) {
            return Results.failure("更新失败");
        }
        return Results.success("更新成功");
    }
}
