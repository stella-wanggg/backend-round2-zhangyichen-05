package com.example.pojo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @NotBlank(message = "用户名不能为空") // 指示牌!姓名密码要非空!
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
}
