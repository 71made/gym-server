package com.gym.server.service.admin;

import com.gym.server.model.dto.admin.EquipmentAddDTO;
import com.gym.server.model.dto.admin.EquipmentUpdateDTO;
import com.gym.utils.http.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: 71made
 * @Date: 2023/05/07 22:56
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
public interface EquipmentService {

    /**
     * @Method: all
     * @Author: 71made
     * @Date: 2023-05-07 23:18
     * @Params: []
     * @Return: com.gym.utils.http.Result
     * @Description: 获取器材列表
     */
    Result all();

    /**
     * @Method: add
     * @Author: 71made
     * @Date: 2023-05-07 22:57
     * @Params: [equipmentDTO]
     * @Return: com.gym.utils.http.Result
     * @Description: 新增器材
     */
    Result add(EquipmentAddDTO equipmentDTO);

    /**
     * @Method: update
     * @Author: 71made
     * @Date: 2023-05-07 22:58
     * @Params: [equipmentDTO]
     * @Return: com.gym.utils.http.Result
     * @Description: 更新器材信息
     */
    Result update(EquipmentUpdateDTO equipmentDTO);
}
