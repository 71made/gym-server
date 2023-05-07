package com.gym.server.service.admin;

import com.gym.server.model.po.member.Member;
import com.gym.utils.http.Result;

/**
 * @Author: 71made
 * @Date: 2023/05/07 12:05
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
public interface AdminService {

    /**
     * @Method: login
     * @Author: 71made
     * @Date: 2023-05-07 12:06
     * @Params: [account, password]
     * @Return: com.gym.utils.http.Result
     * @Description: 管理员登陆
     */
    Result login(String account, String password);

    /**
     * @Method: memberAll
     * @Author: 71made
     * @Date: 2023-05-07 17:24
     * @Params: []
     * @Return: com.gym.utils.http.Result
     * @Description: 获取全部会员信息
     */
    Result memberAll();

    /**
     * @Method: update
     * @Author: 71made
     * @Date: 2023-05-07 21:16
     * @Params: [memberId, status]
     * @Return: com.gym.utils.http.Result
     * @Description: 更新会员 status
     */
    Result update(Integer memberId, Member.Status status);
}
