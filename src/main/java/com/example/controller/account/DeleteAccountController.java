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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delete")
@Slf4j
public class DeleteAccountController {

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

    @DeleteMapping("/admin")
    private R<String> deleteAdmin(@RequestParam("id") Long id) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getId, id);
        Admin admins = adminService.getBaseMapper().selectOne(queryWrapper);
        //在角色表中删除
        delete(admins.getUsername());
        adminService.removeById(id);
        //从管理员表中删除
        return R.success("删除成功");
    }

    @DeleteMapping("/lawyer")
    private R<String> deleteLawyer(@RequestParam("id") Long id) {
        LambdaQueryWrapper<Lawyer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Lawyer::getId, id);
        Lawyer lawyer = lawyerService.getBaseMapper().selectOne(queryWrapper);
        //在角色表中删除
        delete(lawyer.getName());
        lawyerService.removeById(id);
        //从管理员表中删除
        return R.success("删除成功");
    }

    @DeleteMapping("/user")
    private R<String> deleteUser(@RequestParam("id") Long id) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, id);
        User user = userService.getBaseMapper().selectOne(queryWrapper);
        //在角色表中删除
        delete(user.getName());
        userService.removeById(id);
        //从管理员表中删除
        return R.success("删除成功");
    }

    private void delete(String name) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getUsername, name);
        roleService.remove(queryWrapper);
        //从角色表中删除
    }
}
