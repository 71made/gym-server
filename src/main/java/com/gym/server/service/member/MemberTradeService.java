package com.gym.server.service.member;

import com.gym.utils.http.Result;

/**
 * @Author: 71made
 * @Date: 2023/05/08 22:33
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
public interface MemberTradeService {

    /**
     * @Method: all
     * @Author: 71made
     * @Date: 2023-05-08 22:34
     * @Params: [memberId]
     * @Return: com.gym.utils.http.Result
     * @Description: 获取缴费记录
     */
    Result all(Integer memberId);
}
