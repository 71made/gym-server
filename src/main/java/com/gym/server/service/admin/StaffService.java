package com.gym.server.service.admin;

import com.gym.server.model.dto.admin.StaffAddDTO;
import com.gym.server.model.dto.admin.StaffUpdateDTO;
import com.gym.utils.http.Result;

/**
 * @Author: 71made
 * @Date: 2023/05/08 11:45
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
public interface StaffService {

    /**
     * @Method: all
     * @Author: 71made
     * @Date: 2023-05-07 23:18
     * @Params: []
     * @Return: com.gym.utils.http.Result
     * @Description: 获取员工列表
     */
    Result all();

    /**
     * @Method: add
     * @Author: 71made
     * @Date: 2023-05-07 22:57
     * @Params: [equipmentDTO]
     * @Return: com.gym.utils.http.Result
     * @Description: 新增员工
     */
    Result add(StaffAddDTO staffDTO);

    /**
     * @Method: update
     * @Author: 71made
     * @Date: 2023-05-07 22:58
     * @Params: [staffDTO]
     * @Return: com.gym.utils.http.Result
     * @Description: 更新员工信息
     */
    Result update(StaffUpdateDTO staffDTO);
}
