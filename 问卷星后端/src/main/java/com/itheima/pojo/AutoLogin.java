package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutoLogin {
    private Integer autoLogin;
    private Integer rememberPassword;
    private String username;
    private String password;
}
