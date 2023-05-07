package com.gym.server.model.dto;

/**
 * @Author: 71made
 * @Date: 2023/05/03 02:30
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
public abstract class BaseDTO {
    /**
     * @Method: verifyParameters
     * @Author: 71made
     * @Date: 2023-05-03 02:34
     * @Params: []
     * @Return: boolean
     * @Description: 校验自身参数, 由子类实现
     */
    public abstract boolean verifyParameters();
}
