package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/users")
public class LogoutController {
    @Autowired
    private UserService userService;
    @GetMapping("/logout")
    public Result logout(Boolean autoLogin){
        String jwt=userService.logout(autoLogin);
        return Result.success(jwt);
    }
}
