package com.example.controller.account;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.R;
import com.example.entity.Lawyer;
import com.example.entity.Role;
import com.example.service.impl.LawyerServiceImpl;
import com.example.service.impl.RoleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private RoleServiceImpl adminService;
    @Autowired
    private LawyerServiceImpl lawyerService;

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

        log.info("律师登录："+one.toString());
        //查看是否是律师账户，是否通过审核
        if (Objects.equals(one.getCategory(), "2")){
            LambdaQueryWrapper<Lawyer> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Lawyer::getName,one.getUsername());
            Lawyer one1 = lawyerService.getOne(wrapper);
            log.info("律师登录："+one1.toString());
            if (one1.getStatus()==0){
                return R.error("登录失败，请等待审核通过！");
            }
        }

        return R.success(one);
    }

}
