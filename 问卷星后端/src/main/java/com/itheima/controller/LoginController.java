package com.itheima.controller;

import com.itheima.pojo.Admin;
import com.itheima.pojo.Login;
import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import com.itheima.service.AdminService;
import com.itheima.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @PostMapping("/users/login")
    public Result loginUser(@RequestBody Login login){
        log.info("用户登陆: {}",login);
        return userService.login(login);
    }

    @PostMapping("/admins/login")
    public Result loginAdmin(@RequestBody Admin admin){
        log.info("管理员登陆: {}",admin);
        return adminService.login(admin);
    }
}
