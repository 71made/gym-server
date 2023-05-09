package com.gym.server.service.member;

import com.gym.exception.ServiceException;
import com.gym.server.model.dto.member.MemberCourseAddDTO;
import com.gym.utils.http.Result;

/**
 * @Author: 71made
 * @Date: 2023/05/08 18:28
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
public interface MemberCourseService {

    /**
     * @Method: my
     * @Author: 71made
     * @Date: 2023-05-08 20:45
     * @Params: [memberId]
     * @Return: com.gym.utils.http.Result
     * @Description: 会员选课信息
     */
    Result my(Integer memberId);


    /**
     * @Method: add
     * @Author: 71made
     * @Date: 2023-05-08 20:48
     * @Params: [memberCourseDTO]
     * @Return: com.gym.utils.http.Result
     * @Description: 会员选买新课
     */
    Result add(MemberCourseAddDTO memberCourseDTO) throws ServiceException;

    /**
     * @Method: cancel
     * @Author: 71made
     * @Date: 2023-05-08 20:49
     * @Params: [memberCourseId]
     * @Return: com.gym.utils.http.Result
     * @Description: 会员取消选课
     */
    Result cancel(Integer memberId, Integer memberCourseId) throws ServiceException;
}
