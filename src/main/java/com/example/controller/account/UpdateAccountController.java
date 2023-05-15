package com.example.controller.account;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.until.R;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/update")
@Slf4j
public class UpdateAccountController {
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

    @PutMapping("/admin")
    private R<String> UpDateAdmin(@RequestBody Admin admin) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getId, admin.getId());
        //原本的管理员姓名，以此来改变角色表中姓名
        Admin admins = adminService.getBaseMapper().selectOne(queryWrapper);

        if (Objects.equals(admins.getUsername(),admin.getUsername()) ||roleIsNull(admin.getUsername())) {
            updateRole(admins.getUsername(), admin.getUsername(), admin.getPassword());
            //更新本表
            adminService.updateById(admin);
            return R.success("更新成功");
        } else {
            return R.error("与其他人名字冲突或其他错误，请联系开发人员解决");
        }
    }

    @PutMapping("/lawyer")
    private R<String> UpDateLawyer(@RequestBody Lawyer lawyer) {
        LambdaQueryWrapper<Lawyer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Lawyer::getId, lawyer.getId());
        //原本的管理员姓名，以此来改变角色表中姓名
        Lawyer lawyers = lawyerService.getBaseMapper().selectOne(queryWrapper);

        log.info("原名：{}，更改后的名字：{}",lawyer.getName(),lawyers.getName());
        if (Objects.equals(lawyer.getName(), lawyers.getName()) ||roleIsNull(lawyer.getName())) {
            updateRole(lawyers.getName(), lawyer.getName(), lawyer.getPassword());
            //更新本表
            lawyerService.updateById(lawyer);
            return R.success("更新成功");
        } else {
            return R.error("与其他人名字冲突或其他错误，请联系开发人员解决");
        }
    }

    @PutMapping("/user")
    private R<String> UpDateUser(@RequestBody User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, user.getId());

        //原本的管理员姓名，以此来改变角色表中姓名
        User users = userService.getBaseMapper().selectOne(queryWrapper);

        if (Objects.equals(users.getName(), user.getName()) ||roleIsNull(user.getName())) {
            updateRole(users.getName(), user.getName(), user.getPassword());
            //更新本表
            userService.updateById(user);
            return R.success("更新成功");
        } else {
            return R.error("与其他人名字冲突或其他错误，请联系开发人员解决");
        }
    }

    //判断姓名是否存在角色表中
    private Boolean roleIsNull(String name) {
        LambdaQueryWrapper<Role> roles = new LambdaQueryWrapper<>();
        roles.eq(Role::getUsername, name);
        return roleService.count(roles) < 1;
    }

    private void updateRole(String beforName, String afterName, String afterPws) {
        LambdaQueryWrapper<Role> queryWrappers = new LambdaQueryWrapper<>();
        queryWrappers.eq(Role::getUsername, beforName);
        Role role = roleService.getBaseMapper().selectOne(queryWrappers);
        role.setUsername(afterName);
        role.setPassword(afterPws);
        boolean b = roleService.updateById(role);
        log.info("更新" + b);
    }
}
