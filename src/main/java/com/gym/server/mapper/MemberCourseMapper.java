package com.gym.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * @Author: 71made
 * @Date: 2023/05/08 16:22
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@Mapper
@Repository
public interface MemberCourseMapper extends BaseMapper<MemberMapper> {

    @Select("SELECT IFNULL(SUM(`period`), 0.0) FROM `member_course` " +
            "RIGHT JOIN `course` ON `member_course`.`member_id` = `course`.`id` " +
            "WHERE `member_course`.`member_id` = #{member_id} " +
            "AND `course`.`status` != 2 " +
            "AND `course`.`end_time` > NOW()")
    BigDecimal selectPeriodByMemberId(@Param("member_id") Integer memberId);

}
