package com.gym.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gym.server.model.po.member.MemberCourse;
import com.gym.server.model.vo.MemberCourseVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: 71made
 * @Date: 2023/05/08 16:22
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@Mapper
@Repository
public interface MemberCourseMapper extends BaseMapper<MemberCourse> {

    @Select("SELECT IFNULL(SUM(`period`), 0.0) FROM `member_course` " +
            "RIGHT JOIN `course` ON `member_course`.`member_id` = `course`.`id` " +
            "WHERE `member_course`.`member_id` = #{member_id} " +
            "AND `course`.`status` != 2 " +
            "AND `course`.`end_time` > NOW()")
    BigDecimal selectPeriodByMemberId(@Param("member_id") Integer memberId);

    @Results({
            @Result(column = "staff_id", property = "staffId"),
            @Result(column = "course_id", property = "courseId"),
            @Result(column = "staff_id", property = "staffName", one = @One(select = "com.gym.server.mapper.StaffMapper.selectStaffNameById")),
            @Result(column = "name", property = "courseName"),
            @Result(column = "start_time", property = "startTime"),
            @Result(column = "end_time", property = "endTime")
    })
    @Select("SELECT `member_course`.*, `course`.`name`, `course`.`start_time`, `course`.`end_time` FROM `member_course` " +
            "LEFT JOIN `course` ON `course`.`id` = `member_course`.`course_id` " +
            "WHERE `member_id` = #{member_id} " +
            "AND `course`.`status` != 2 AND `member_course`.`status` != 1 " +
            "ORDER BY `member_course`.`create_time` DESC")
    List<MemberCourseVO> selectVOsByMemberId(@Param("member_id") Integer memberId);



}
