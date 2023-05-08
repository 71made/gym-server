package com.gym.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: 71made
 * @Date: 2023/05/06 15:25
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@Controller
@RequestMapping(value = "/pages")
public class PagesController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/admin/login")
    public String adminLogin() {
        return "pages/admin/login";
    }

    @GetMapping("/admin/member")
    public String adminMember() {
        return "pages/admin/member";
    }

    @GetMapping("/admin/equipment")
    public String adminEquipment() {
        return "pages/admin/equipment";
    }

    @GetMapping("/admin/staff")
    public String adminStaff() {
        return "pages/admin/staff";
    }
}
