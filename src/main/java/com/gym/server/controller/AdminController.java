package com.gym.server.controller;

import com.gym.server.model.dto.admin.*;
import com.gym.server.model.po.admin.Admin;
import com.gym.server.service.admin.*;
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

    @Autowired
    StaffService staffService;

    @Autowired
    CourseService courseService;

    @Autowired
    LostItemService lostItemService;

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

    @GetMapping("/logout")
    @ApiOperation(value = "admin logout", notes = "管理员登出")
    public Result login(@ApiIgnore HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.removeAttribute("admin_id");
        return Results.success("登出成功");
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

    @GetMapping("/member/upgrade")
    @ApiOperation(value = "admin member upgrade", notes = "升级会员类型")
    public Result memberUpgrade(@ApiIgnore HttpServletRequest request,
                               @RequestParam("member_id") @ApiParam(value = "会员 id", required = true) Integer memberId) {
        HttpSession session = request.getSession();
        Integer adminId = (Integer) session.getAttribute("admin_id");
        if (null == adminId) return Results.failureWithStatus(Results.Status.LOGIN_MISSING);
        if (null == memberId) return Results.failureWithStatus(Results.Status.BAD_REQUEST);
        return memberService.upgrade(memberId);
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

    @GetMapping("/staff/all")
    @ApiOperation(value = "admin staff all", notes = "获取员工列表")
    public Result staffAll(@ApiIgnore HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Integer adminId = (Integer) session.getAttribute("admin_id");
        if (null == adminId) return Results.failureWithStatus(Results.Status.LOGIN_MISSING);
        return staffService.all();
    }

    @PostMapping("/staff/add")
    @ApiOperation(value = "admin staff add", notes = "增加员工")
    public Result staffAdd(@ApiIgnore HttpServletRequest request,
                               @RequestBody @ApiParam(value = "员工信息", required = true) StaffAddDTO staffDTO) {
        HttpSession session = request.getSession();
        Integer adminId = (Integer) session.getAttribute("admin_id");
        if (null == adminId) return Results.failureWithStatus(Results.Status.LOGIN_MISSING);
        if (null == staffDTO || !staffDTO.verifyParameters()) return Results.failureWithStatus(Results.Status.BAD_REQUEST);
        return staffService.add(staffDTO);
    }

    @PostMapping("/staff/update")
    @ApiOperation(value = "admin staff update", notes = "更新员工")
    public Result staffUpdate(@ApiIgnore HttpServletRequest request,
                                  @RequestBody @ApiParam(value = "员工信息", required = true) StaffUpdateDTO staffDTO) {
        HttpSession session = request.getSession();
        Integer adminId = (Integer) session.getAttribute("admin_id");
        if (null == adminId) return Results.failureWithStatus(Results.Status.LOGIN_MISSING);
        if (null == staffDTO || !staffDTO.verifyParameters()) return Results.failureWithStatus(Results.Status.BAD_REQUEST);
        return staffService.update(staffDTO);
    }

    @GetMapping("/course/all")
    @ApiOperation(value = "admin course all", notes = "获取课程列表")
    public Result courseAll(@ApiIgnore HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Integer adminId = (Integer) session.getAttribute("admin_id");
        if (null == adminId) return Results.failureWithStatus(Results.Status.LOGIN_MISSING);
        return courseService.all();
    }

    @PostMapping("/course/add")
    @ApiOperation(value = "admin course add", notes = "增加课程")
    public Result courseAdd(@ApiIgnore HttpServletRequest request,
                           @RequestBody @ApiParam(value = "课程信息", required = true) CourseAddDTO courseDTO) {
        HttpSession session = request.getSession();
        Integer adminId = (Integer) session.getAttribute("admin_id");
        if (null == adminId) return Results.failureWithStatus(Results.Status.LOGIN_MISSING);
        if (null == courseDTO || !courseDTO.verifyParameters()) return Results.failureWithStatus(Results.Status.BAD_REQUEST);
        return courseService.add(courseDTO);
    }

    @PostMapping("/course/update")
    @ApiOperation(value = "admin course update", notes = "更新课程")
    public Result courseUpdate(@ApiIgnore HttpServletRequest request,
                              @RequestBody @ApiParam(value = "课程信息", required = true) CourseUpdateDTO courseDTO) {
        HttpSession session = request.getSession();
        Integer adminId = (Integer) session.getAttribute("admin_id");
        if (null == adminId) return Results.failureWithStatus(Results.Status.LOGIN_MISSING);
        if (null == courseDTO || !courseDTO.verifyParameters()) return Results.failureWithStatus(Results.Status.BAD_REQUEST);
        try {
            return courseService.update(courseDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return Results.failure(e.getMessage());
        }
    }

    @GetMapping("/lost_item/all")
    @ApiOperation(value = "admin lost item all", notes = "获取遗失物品列表")
    public Result lostItemAll(@ApiIgnore HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Integer adminId = (Integer) session.getAttribute("admin_id");
        if (null == adminId) return Results.failureWithStatus(Results.Status.LOGIN_MISSING);
        return lostItemService.all();
    }

    @PostMapping("/lost_item/add")
    @ApiOperation(value = "admin lost item add", notes = "增加遗失物品")
    public Result lostItemAdd(@ApiIgnore HttpServletRequest request,
                              @RequestBody @ApiParam(value = "遗失物品信息", required = true)LostItemAddDTO lostItemAddDTO) {
        HttpSession session = request.getSession();
        Integer adminId = (Integer) session.getAttribute("admin_id");
        if (null == adminId) return Results.failureWithStatus(Results.Status.LOGIN_MISSING);
        if (null == lostItemAddDTO || !lostItemAddDTO.verifyParameters()) return Results.failureWithStatus(Results.Status.BAD_REQUEST);
        return lostItemService.add(lostItemAddDTO);
    }
    @PostMapping("/lost_item/update")
    @ApiOperation(value = "admin lost item update", notes = "更新遗失物品")
    public Result lostItemUpdate(@ApiIgnore HttpServletRequest request,
                                 @RequestBody @ApiParam(value = "遗失物品信息", required = true)LostItemUpdateDTO lostItemUpdateDTO) {
        HttpSession session = request.getSession();
        Integer adminId = (Integer) session.getAttribute("admin_id");
        if (null == adminId) return Results.failureWithStatus(Results.Status.LOGIN_MISSING);
        if (null == lostItemUpdateDTO || !lostItemUpdateDTO.verifyParameters()) return Results.failureWithStatus(Results.Status.BAD_REQUEST);
        return lostItemService.update(lostItemUpdateDTO);
    }

}
