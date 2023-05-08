package com.gym.server.controller;

import com.gym.server.model.dto.admin.EquipmentAddDTO;
import com.gym.server.model.dto.admin.EquipmentUpdateDTO;
import com.gym.server.model.po.admin.Admin;
import com.gym.server.model.po.member.Member;
import com.gym.server.service.admin.AdminService;
import com.gym.server.service.admin.EquipmentService;
import com.gym.server.service.member.MemberService;
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

/**
 * @Author: 71made
 * @Date: 2023/05/06 15:31
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@RestController
@RequestMapping("/admin")
@Api(value = "admin-controller", tags = "管理员控制层")
@Slf4j
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    MemberService memberService;

    @Autowired
    EquipmentService equipmentService;

    @PostMapping("/login")
    @ApiOperation(value = "admin login", notes = "管理员登陆")
    public Result login(@ApiIgnore HttpServletRequest request,
                        @RequestParam("account") @ApiParam(value = "管理员账号", required = true) String account,
                        @RequestParam("password") @ApiParam(value = "密码", required = true) String password) {
        if (StringUtils.isAnyBlank(account, password)) return Results.failureWithStatus(Results.Status.BAD_REQUEST);
        Result result = adminService.login(account, password);
        if (result.isSuccess()) {
            HttpSession session = request.getSession(true);
            // session 存储会员 id
            session.setAttribute("admin_id", ((Admin) result.getData()).getId());
        }
        return result;
    }

    @GetMapping("/member/all")
    @ApiOperation(value = "admin member all", notes = "获取会员列表")
    public Result memberAll(@ApiIgnore HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Integer adminId = (Integer) session.getAttribute("admin_id");
        if (null == adminId) return Results.failureWithStatus(Results.Status.LOGIN_MISSING);
        return memberService.all();
    }

    @GetMapping("/member/update")
    @ApiOperation(value = "admin member update", notes = "更新会员状态")
    public Result memberUpdate(@ApiIgnore HttpServletRequest request,
                            @RequestParam("member_id") @ApiParam(value = "会员 id", required = true) Integer memberId,
                            @RequestParam("status") @ApiParam(value = "会员状态", required = true) Integer status) {
        HttpSession session = request.getSession(true);
        Integer adminId = (Integer) session.getAttribute("admin_id");
        if (null == adminId) return Results.failureWithStatus(Results.Status.LOGIN_MISSING);
        if (null == memberId || null == status) return Results.failureWithStatus(Results.Status.BAD_REQUEST);
        return memberService.update(memberId, status);
    }

    @GetMapping("/equipment/all")
    @ApiOperation(value = "admin equipment all", notes = "获取器材列表")
    public Result equipmentAll(@ApiIgnore HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Integer adminId = (Integer) session.getAttribute("admin_id");
        if (null == adminId) return Results.failureWithStatus(Results.Status.LOGIN_MISSING);
        return equipmentService.all();
    }

    @PostMapping("/equipment/add")
    @ApiOperation(value = "admin equipment add", notes = "增加器材")
    public Result equipmentAdd(@ApiIgnore HttpServletRequest request,
                               @RequestBody @ApiParam(value = "器材信息", required = true) EquipmentAddDTO equipmentDTO) {
        HttpSession session = request.getSession();
        Integer adminId = (Integer) session.getAttribute("admin_id");
        if (null == adminId) return Results.failureWithStatus(Results.Status.LOGIN_MISSING);
        if (null == equipmentDTO || !equipmentDTO.verifyParameters()) return Results.failureWithStatus(Results.Status.BAD_REQUEST);
        return equipmentService.add(equipmentDTO);
    }

    @PostMapping("/equipment/update")
    @ApiOperation(value = "admin equipment update", notes = "更新器材")
    public Result equipmentUpdate(@ApiIgnore HttpServletRequest request,
                               @RequestBody @ApiParam(value = "器材信息", required = true) EquipmentUpdateDTO equipmentDTO) {
        HttpSession session = request.getSession();
        Integer adminId = (Integer) session.getAttribute("admin_id");
        if (null == adminId) return Results.failureWithStatus(Results.Status.LOGIN_MISSING);
        if (null == equipmentDTO || !equipmentDTO.verifyParameters()) return Results.failureWithStatus(Results.Status.BAD_REQUEST);
        return equipmentService.update(equipmentDTO);
    }

}
