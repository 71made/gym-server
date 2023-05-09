package com.gym.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gym.server.model.po.admin.Course;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: 71made
 * @Date: 2023/05/08 15:44
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@Mapper
@Repository
public interface CourseMapper extends BaseMapper<Course> {

    @Select("SELECT `name` FROM `course` WHERE `id` = #{course_id}")
    String selectCourseNameById(@Param("course_id") Integer id);

    @Select("SELECT IFNULL(" +
            "(SELECT COUNT(1) FROM `course` " +
            "RIGHT JOIN `member_course` ON `member_course`.`course_id` = `course`.`id` " +
            "WHERE `member_course`.`member_id` = #{member_id} " +
            "AND ( " +
            "(`course`.`start_time` >= #{start_time} AND `course`.`start_time` <= #{end_time} ) " +
            "OR (`course`.`end_time` >= #{start_time} AND `course`.`end_time` <= #{end_time} ) " +
            "OR (`course`.`start_time` <= #{start_time} AND `course`.`end_time` >= #{end_time} ) " +
            ")), 0)")
    int selectCrushCourseByMemberId(@Param("member_id") Integer memberId, @Param("start_time") Date startTime, @Param("end_time") Date endTime);

    @Update("UPDATE `course` SET `member_count` = `member_count` + #{count} WHERE `course`.`id` = #{course_id}")
    int calcMemberCountByCourseId(@Param("course_id") Integer courseId, @Param("count") Integer count);
}
