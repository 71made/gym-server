package com.gym.server.controller;

import com.gym.server.model.dto.member.MemberCourseAddDTO;
import com.gym.server.model.dto.member.MemberRegisterDTO;
import com.gym.server.model.po.member.Member;
import com.gym.server.service.admin.CourseService;
import com.gym.server.service.member.MemberCourseService;
import com.gym.server.service.member.MemberService;
import com.gym.server.service.member.MemberTradeService;
import com.gym.utils.http.Result;
import com.gym.utils.http.Results;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

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

    @Autowired
    MemberCourseService memberCourseService;

    @Autowired
    MemberTradeService memberTradeService;

    @Autowired
    CourseService courseService;

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
        Integer thisMemberId = (Integer) session.getAttribute("member_id");
        memberId = null == memberId && null != thisMemberId ? thisMemberId : memberId;
        if (null == memberId) return Results.failureWithStatus(Results.Status.LOGIN_MISSING);
        return memberService.info(memberId);
    }

    @PostMapping("/recharge")
    @ApiOperation(value = "member recharge", notes = "会员信息")
    public Result recharge(@ApiIgnore HttpServletRequest request,
                       @RequestParam(value = "amount", required = false) @ApiParam(value = "充值金额") BigDecimal amount) {
        HttpSession session = request.getSession();
        Integer memberId = (Integer) session.getAttribute("member_id");
        if (null == memberId) return Results.failureWithStatus(Results.Status.LOGIN_MISSING);
        if (amount.equals(BigDecimal.ZERO)) return Results.failureWithStatus(Results.Status.BAD_REQUEST);
        try {
            return memberService.recharge(memberId, amount);
        } catch (Exception e) {
            return Results.failure(e.getMessage());
        }
    }

    @GetMapping("/course/all")
    @ApiOperation(value = "member course all", notes = "所有课程信息")
    public Result courseAll(@ApiIgnore HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer memberId = (Integer) session.getAttribute("member_id");
        if (null == memberId) return Results.failureWithStatus(Results.Status.LOGIN_MISSING);
        return courseService.all();
    }

    @GetMapping("/course/my")
    @ApiOperation(value = "member course my", notes = "会员课程信息")
    public Result courseMy(@ApiIgnore HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer memberId = (Integer) session.getAttribute("member_id");
        if (null == memberId) return Results.failureWithStatus(Results.Status.LOGIN_MISSING);
        return memberCourseService.my(memberId);
    }

    @PostMapping("/course/add")
    @ApiOperation(value = "member course add", notes = "会员加入新课程")
    public Result courseAdd(@ApiIgnore HttpServletRequest request,
                            @RequestBody @ApiParam(value = "加课信息", required = true) MemberCourseAddDTO memberCourseDTO) {
        HttpSession session = request.getSession();
        Integer memberId = (Integer) session.getAttribute("member_id");
        if (null == memberId) return Results.failureWithStatus(Results.Status.LOGIN_MISSING);
        if (!memberCourseDTO.verifyParameters()) return Results.failureWithStatus(Results.Status.BAD_REQUEST);
        if (!memberId.equals(memberCourseDTO.getMemberId())) return Results.failureWithStatus(Results.Status.BAD_REQUEST);
        try {
            return memberCourseService.add(memberCourseDTO);
        } catch (Exception e) {
            return Results.failure(e.getMessage());
        }
    }

    @PostMapping("/course/cancel")
    @ApiOperation(value = "member course cancel", notes = "会员退出课程")
    public Result courseCancel(@ApiIgnore HttpServletRequest request,
                            @RequestParam("member_course_id") @ApiParam(value = "加课信息", required = true) Integer memberCourseId) {
        HttpSession session = request.getSession();
        Integer memberId = (Integer) session.getAttribute("member_id");
        if (null == memberId) return Results.failureWithStatus(Results.Status.LOGIN_MISSING);
        if (null == memberCourseId) return Results.failureWithStatus(Results.Status.BAD_REQUEST);
        try {
            return memberCourseService.cancel(memberId, memberCourseId);
        } catch (Exception e) {
            return Results.failure(e.getMessage());
        }
    }

    @GetMapping("/trade/all")
    @ApiOperation(value = "member trade all", notes = "会员缴费信息")
    public Result tradeAll(@ApiIgnore HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer memberId = (Integer) session.getAttribute("member_id");
        if (null == memberId) return Results.failureWithStatus(Results.Status.LOGIN_MISSING);
        return memberTradeService.all(memberId);
    }
}
