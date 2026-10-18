package com.example.controller;

import com.example.pojo.User;
import com.example.result.Result;
import com.example.service.Login;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {
    @Autowired
    private Login login; // 引入实现类

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        log.info("{} 成功登录",user.getUsername());
        login.login(user); // 调用实现类的方法
        return Result.success(); // 返回结果
    }
}
