package com.gym.server.service.member;

import com.gym.server.model.dto.member.MemberRegisterDTO;
import com.gym.server.model.po.member.Member;
import com.gym.utils.http.Result;

/**
 * @Author: 71made
 * @Date: 2023/05/06 16:22
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
public interface MemberService {

    /**
     * @Method: login
     * @Author: 71made
     * @Date: 2023-05-06 16:24
     * @Params: [cardNumber, password]
     * @Return: com.gym.utils.http.Result
     * @Description: 会员登陆
     */
    Result login(String cardNumber, String password);

    /**
     * @Method: register
     * @Author: 71made
     * @Date: 2023-05-06 16:33
     * @Params: [registerDTO]
     * @Return: com.gym.utils.http.Result
     * @Description: 会员注册
     */
    Result register(MemberRegisterDTO registerDTO);

    /**
     * @Method: info
     * @Author: 71made
     * @Date: 2023-05-06 16:34
     * @Params: [memberId]
     * @Return: com.gym.utils.http.Result
     * @Description: 会员信息
     */
    Result info(Integer memberId);

    /**
     * @Method: memberAll
     * @Author: 71made
     * @Date: 2023-05-07 17:24
     * @Params: []
     * @Return: com.gym.utils.http.Result
     * @Description: 获取全部会员信息
     */
    Result all();

    /**
     * @Method: update
     * @Author: 71made
     * @Date: 2023-05-07 21:16
     * @Params: [memberId, status]
     * @Return: com.gym.utils.http.Result
     * @Description: 更新会员 status
     */
    Result update(Integer memberId, int status);
}
