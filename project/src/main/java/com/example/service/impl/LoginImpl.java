package com.example.service.impl;

import com.example.exception.BusinessException;
import com.example.pojo.User;
import com.example.service.Login;
import com.example.util.PasswordUtil;
import org.springframework.stereotype.Service;


import static com.example.service.impl.RegisterImpl.users;


@Service
public class LoginImpl implements Login {

    @Override
    public /*Result*/void login(User user){
        for(User u :users){ // 遍历users
            if(u.getUsername().equals(user.getUsername())){ // 先确定有没有名字相同的
                if (PasswordUtil.checkPassword(user.getPassword(),u.getPassword())){ // 有的话再看看密码一不一样
//                  [第一版]  return Result.success();
                    return;
                }throw new BusinessException("密码错误"); // 第一遍忘了加
            }
        }
//      [第一版]  return Result.error("用户不存在");
        throw new BusinessException("用户不存在"); // 丢错误
    }
}

