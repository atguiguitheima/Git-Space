package com.itheima.service.impl;

import com.itheima.mapper.AdminMapper;
import com.itheima.pojo.Admin;
import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import com.itheima.service.AdminService;
import com.itheima.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public Result login(Admin admin) {
        Admin adm = adminMapper.selectAdmin(admin);
        if (adm!=null){
            Map map=new HashMap();
            map.put("username",adm.getAdminName());
            map.put("id",adm.getId());
            String jwt = JwtUtils.generateJWT(map);
            return Result.success(jwt);
        }else{return Result.error("登入失败,不存在此管理员账号或密码错误!");}
    }
}
