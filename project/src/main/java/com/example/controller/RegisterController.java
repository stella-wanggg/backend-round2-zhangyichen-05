package com.example.controller;

import com.example.pojo.User;
import com.example.result.Result;
import com.example.service.Register;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RegisterController {
    @Autowired
    private Register register; // 引入实现类

    @PostMapping("/register")
    // @Valid提示进行非空检验
    public Result register(@Valid @RequestBody User user){ // 获取json格式的信息
        log.info("{}成功注册",user.getUsername());// 打印日志
        register.register(user);
        /* 返回Result,第一遍把Result写到了实现类里,但实际上返回结果这部分应该是controller的工作,
           [第一版]return register.register(user);
           导致controller这部分代码怪怪的 ↑↑ ,现在看过去也不知道自己当时怎么想的(汗)
         */
        return Result.success();
    }
}
