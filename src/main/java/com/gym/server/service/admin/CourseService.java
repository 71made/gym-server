package com.gym.server.service.admin;


import com.gym.server.model.dto.admin.CourseAddDTO;
import com.gym.server.model.dto.admin.CourseUpdateDTO;
import com.gym.utils.http.Result;

/**
 * @Author: 71made
 * @Date: 2023/05/08 15:38
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
public interface CourseService {

    /**
     * @Method: all
     * @Author: 71made
     * @Date: 2023-05-07 23:18
     * @Params: []
     * @Return: com.gym.utils.http.Result
     * @Description: 获取课程列表
     */
    Result all();

    /**
     * @Method: add
     * @Author: 71made
     * @Date: 2023-05-07 22:57
     * @Params: [courseDTO]
     * @Return: com.gym.utils.http.Result
     * @Description: 新增课程
     */
    Result add(CourseAddDTO courseDTO);

    /**
     * @Method: update
     * @Author: 71made
     * @Date: 2023-05-07 22:58
     * @Params: [courseDTO]
     * @Return: com.gym.utils.http.Result
     * @Description: 更新课程信息
     */
    Result update(CourseUpdateDTO courseDTO);
}
