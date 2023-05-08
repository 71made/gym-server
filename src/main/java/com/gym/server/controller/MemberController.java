package com.gym.server.controller;

import com.gym.server.model.dto.member.MemberRegisterDTO;
import com.gym.server.model.po.member.Member;
import com.gym.server.service.member.MemberService;
import com.gym.utils.http.Result;
import com.gym.utils.http.Results;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author: 71made
 * @Date: 2023/05/06 15:31
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@RestController
@RequestMapping("/member")
@Api(value = "member-controller", tags = "会员控制层")
@Slf4j
public class MemberController {

    @Autowired
    MemberService memberService;

    @PostMapping("/login")
    @ApiOperation(value = "member login", notes = "会员登陆")
    public Result login(@ApiIgnore HttpServletRequest request,
                        @RequestParam("card_number") @ApiParam(value = "会员卡号", required = true) String cardNumber,
                        @RequestParam("password") @ApiParam(value = "密码", required = true) String password) {
        if (StringUtils.isAnyBlank(cardNumber, password)) return Results.failureWithStatus(Results.Status.BAD_REQUEST);
        Result result = memberService.login(cardNumber, password);
        if (result.isSuccess()) {
            HttpSession session = request.getSession(true);
            // session 存储会员 id
            session.setAttribute("member_id", ((Member) result.getData()).getId());
        }
        return result;
    }

    @PostMapping("/register")
    @ApiOperation(value = "member register", notes = "会员注册")
    public Result register(@RequestBody @ApiParam(value = "会员注册信息", required = true) MemberRegisterDTO registerDTO) {
        if (!registerDTO.verifyParameters()) return Results.failureWithStatus(Results.Status.BAD_REQUEST);
        return memberService.register(registerDTO);
    }

    @GetMapping("/logout")
    @ApiOperation(value = "member logout", notes = "会员登出")
    public Result logout(@ApiIgnore HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.removeAttribute("member_id");
        return Results.success("登出成功");
    }

    @GetMapping("/info")
    @ApiOperation(value = "member info", notes = "会员信息")
    public Result info(@ApiIgnore HttpServletRequest request,
                       @RequestParam(value = "member_id", required = false) @ApiParam(value = "会员 id") Integer memberId) {
        HttpSession session = request.getSession();
        String thisMemberIdStr = null != session ? (String) session.getAttribute("member_id") : null;
        memberId = null == memberId && null != thisMemberIdStr ? Integer.valueOf(thisMemberIdStr) : memberId;
        if (null == memberId) return Results.failureWithStatus(Results.Status.BAD_REQUEST);
        return memberService.info(memberId);
    }
}
