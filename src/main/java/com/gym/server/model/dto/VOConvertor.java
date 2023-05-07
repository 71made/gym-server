package com.gym.server.model.dto;

/**
 * @Author: 71made
 * @Date: 2023/05/04 00:13
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
public interface VOConvertor<VO> {
    /**
     * @Method: convertToVO
     * @Author: 71made
     * @Date: 2023-05-04 00:21
     * @Params: []
     * @Return: VO
     * @Description: 对应 VO 的转换器转换方法
     */
    VO convertToVO();
}
