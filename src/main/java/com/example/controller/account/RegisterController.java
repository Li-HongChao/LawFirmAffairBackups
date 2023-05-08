package com.example.controller.account;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.R;
import com.example.entity.Admin;
import com.example.entity.Lawyer;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.service.impl.AdminServiceImpl;
import com.example.service.impl.LawyerServiceImpl;
import com.example.service.impl.RoleServiceImpl;
import com.example.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    //角色对象
    private RoleServiceImpl roleService;
    @Autowired
    //用户对象
    private UserServiceImpl userService;
    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private LawyerServiceImpl lawyerService;

    //普通用户注册
    @PostMapping("/user")
    private R<User> registerUser(@RequestBody User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName, user.getName());
        User one = userService.getOne(queryWrapper);



        if (one != null && roleIsNull(user.getName())) {
            return R.error("该用户已存在");
        } else {
            userService.save(user);
            Role role = new Role();
            role.setUsername(user.getName());
            role.setPassword(user.getPassword());
            role.setCategory("3");
            roleService.save(role);
            return R.success(user);
        }
    }

    //管理员用户注册
    @PostMapping("/admin")
    private R<Admin> registerAdmin(@RequestBody Admin admin) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername, admin.getUsername());
        Admin one = adminService.getOne(queryWrapper);
        if (one != null  && roleIsNull(admin.getUsername())) {
            return R.error("该用户已存在");
        } else {
            log.info(admin.toString());
            adminService.save(admin);
            Role role = new Role();
            role.setUsername(admin.getUsername());
            role.setPassword(admin.getPassword());
            role.setCategory("1");
            roleService.save(role);
            return R.success(admin);
        }
    }

    //律师用户注册
    @PostMapping("/lawyer")
    private R<Lawyer> registerAdmin(@RequestBody Lawyer lawyer) {
        LambdaQueryWrapper<Lawyer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Lawyer::getName, lawyer.getName());
        Lawyer one = lawyerService.getOne(queryWrapper);
        if (one != null && roleIsNull(lawyer.getName())) {
            return R.error("该用户已存在");
        } else {
            log.info(lawyer.toString());
            if (lawyer.getLawFirm().isEmpty()) {
                lawyer.setLawFirm("未填写");
            }
            if (lawyer.getWorking().isEmpty()) {
                lawyer.setWorking("未填写");
            }
            lawyerService.save(lawyer);
            Role role = new Role();
            role.setUsername(lawyer.getName());
            role.setPassword(lawyer.getPassword());
            role.setCategory("2");
            roleService.save(role);
            return R.success(lawyer);
        }
    }

    private Boolean roleIsNull(String name){
        LambdaQueryWrapper<Role> roles = new LambdaQueryWrapper<>();
        roles.eq(Role::getUsername,name);
        Role one = roleService.getOne(roles);
        return one == null;
    }


    @Value("${myPath.iconPath}")
    private String path;

    //图片上传
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        long time = new Date().getTime();
        String url =path +"icon\\"+ time + ".jpg";
        File dest = new File(url);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            log.info("当前文件已经存储:"+url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success("icon\\"+ time + ".jpg");
    }
}
