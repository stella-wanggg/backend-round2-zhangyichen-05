package com.example.service.impl;

import com.example.exception.BusinessException;
import com.example.pojo.User;
import com.example.service.Register;
import com.example.util.PasswordUtil;
import com.example.yaml.useryaml;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class RegisterImpl implements Register {
    @Autowired
    private useryaml user_info;

    public static final List<User> users = new ArrayList<>();

    @Autowired
    private ObjectMapper mapper; // 自带的JSON格式转换器

    @PostConstruct
    public void init() {
        loadData();
    }
    @Override
    public /*Result*/void register(User user) {
        String pattern = user_info.getPassword().getPattern();
//        if(user.getName() == null || user.getName().trim().equals("")) {
//            throw new BusinessException("用户名不能为空");
//        }
//        if(user.getPassword() == null || user.getPassword().trim().equals("")) {
//            throw new BusinessException("密码不能为空");
//        }
//      有了非空校验,这块可以省略
        if (user.getUsername().length() > user_info.getUsername().getMax_length()){ // 判名长度是否合适
//            return Result.error("用户名过长");
            throw new BusinessException("用户名过长");
        }
        if (user.getPassword().length() < user_info.getPassword().getMin_length()){ // 判密码长度是否合适
//            return Result.error("密码位数过短");
            throw new BusinessException("密码位数过短");
        }
        if(!user.getPassword().matches(pattern)){
            throw new BusinessException("密码不符合要求");
        }
        for(User user1:users) {
            if (user.getUsername().equals(user1.getUsername())) { // 判是否重名
//                return Result.error("用户名已被占用");
                throw new BusinessException("用户名已被占用");
            }
        }
        String passwordplus = PasswordUtil.Password(user.getPassword());// 密码加密
        user.setPassword(passwordplus); // 重置加密密码
        users.add(user); // users list中加入新注册的用户
//        return Result.success();
        saveData();
    }
    private void loadData(){
        String path = user_info.getPath();
        File file = new File(path); // 获取文件地址
        if(!file.exists()) {
            log.info("文件不存在");
            return;
        }
        try{ // 防止出现格式错误等引发的异常
            // 这是要把JSON格式的文件中的数据加入List
            List<User> content = mapper.readValue(file, new TypeReference<List<User>>() {});
            users.clear(); // 先把原先list数据清空
            users.addAll(content); // 重新把数据注入
            log.info("数据成功加载");
        }catch (Exception e){
            throw new RuntimeException("数据加载失败");
        }
    }
    private void saveData(){
        String path = user_info.getPath();
        File file = new File(path);
        File parentFile = file.getParentFile();
        if(!parentFile.exists()){
             parentFile.mkdirs(); // 判父类路径是否存在,不存在新建
        }
        try{ // 依旧防止出现异常
             mapper.writeValue(file, users); // 先判文件存在不存在,不存在先新建,然后把list数据写进文件
        } catch (Exception e){
            throw new BusinessException("注册失败");
        }
    }
}



