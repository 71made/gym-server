package com.gym.server.controller;

import com.gym.server.model.po.admin.Admin;
import com.gym.server.model.po.member.Member;
import com.gym.server.service.admin.AdminService;
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
}
