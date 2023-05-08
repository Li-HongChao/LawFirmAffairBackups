package com.example.controller.account;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.R;
import com.example.entity.Role;
import com.example.service.impl.RoleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private RoleServiceImpl adminService;

    //登录校验
    @PostMapping
    public R<Role> login(HttpServletRequest request,@RequestBody Role role){
        String password = role.getPassword();

        //数据库查询
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getUsername, role.getUsername());
        Role one = adminService.getOne(queryWrapper);

        //账号比对
        if (one == null) {
            return R.error("登录失败，用户不存在");
        }
        //密码比对
        if (!one.getPassword().equals(password)) {
            log.info(password);
            log.info(one.getPassword());
            return R.error("登录失败，密码错误");
        }

        return R.success(one);
    }

}
