package com.gym.server.model.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gym.server.model.dto.BaseDTO;
import com.gym.server.model.dto.POConvertor;
import com.gym.server.model.po.member.MemberCourse;
import lombok.Getter;

import java.util.Date;

/**
 * @Author: 71made
 * @Date: 2023/05/08 20:46
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@Getter
public class MemberCourseAddDTO extends BaseDTO implements POConvertor<MemberCourse> {

    @JsonProperty(value = "course_id", required = true)
    private Integer courseId;

    @JsonProperty(value = "member_id", required = true)
    private Integer memberId;

    @Override
    public boolean verifyParameters() {
        return courseId != null && memberId != null;
    }

    @Override
    public MemberCourse convertToPO() {
        MemberCourse memberCourse = new MemberCourse();
        if (!this.verifyParameters()) return memberCourse;
        memberCourse.setCourseId(this.courseId);
        memberCourse.setMemberId(this.memberId);
        memberCourse.setStatus(MemberCourse.Status.SIGN);
        memberCourse.setCreateTime(new Date());
        memberCourse.setUpdateTime(new Date());
        return memberCourse;
    }
}
