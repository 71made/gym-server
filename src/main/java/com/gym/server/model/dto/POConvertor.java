package com.gym.server.model.dto;

/**
 * @Author: 71made
 * @Date: 2023/05/04 00:08
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
public interface POConvertor<PO> {

    /**
     * @Method: convertToPO
     * @Author: 71made
     * @Date: 2023-05-04 00:22
     * @Params: []
     * @Return: PO
     * @Description: 对应 PO 的转换器转换方法
     */
    PO convertToPO();

}
