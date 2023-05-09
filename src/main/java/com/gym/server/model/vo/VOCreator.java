package com.gym.server.model.vo;

/**
 * @Author: 71made
 * @Date: 2023/05/09 14:33
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
public interface VOCreator<PO, VO> {

    /**
     * @Method: createVO
     * @Author: 71made
     * @Date: 2023-05-09 14:34
     * @Params: [obj]
     * @Return: VO
     * @Description: 由 PO 构建 VO 非法
     */
    VO createVO(PO obj);
}
