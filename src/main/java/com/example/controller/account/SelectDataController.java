package com.example.controller.account;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.until.R;
import com.example.entity.Admin;
import com.example.entity.Lawyer;
import com.example.entity.User;
import com.example.service.impl.AdminServiceImpl;
import com.example.service.impl.LawyerServiceImpl;
import com.example.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/select")
public class SelectDataController {
    @Autowired
    //用户对象
    private UserServiceImpl userService;
    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private LawyerServiceImpl lawyerService;

    @GetMapping("/userAll")
    public R<List<User>> getAllUser() {
        return R.success(userService.getBaseMapper().selectList(null));
    }
    @GetMapping("/adminAll")
    public R<List<Admin>> getAllAdmin() {
        return R.success(adminService.selectAll());
    }
    @GetMapping("/lawyerAll")
    public R<List<Lawyer>> getAllLawyer() {
        LambdaQueryWrapper<Lawyer> wrapper = new LambdaQueryWrapper<>();
        //状态为1，则审核通过
        wrapper.eq(Lawyer::getStatus,1);
        wrapper.orderByDesc(Lawyer::getRating);
        return R.success(lawyerService.getBaseMapper().selectList(wrapper));
    }

    @GetMapping("/lawyerDecideLawyer")
    public R<List<Lawyer>> getDecideLawyer() {
        LambdaQueryWrapper<Lawyer> wrapper = new LambdaQueryWrapper<>();
        //状态为1，则审核通过
        wrapper.eq(Lawyer::getStatus,0);
        wrapper.orderByDesc(Lawyer::getRating);
        return R.success(lawyerService.getBaseMapper().selectList(wrapper));
    }

    @GetMapping("/lawyerPut")
    public R<List<Lawyer>> getPutLawyer() {
        LambdaQueryWrapper<Lawyer> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Lawyer::getRating);
        wrapper.eq(Lawyer::getStatus,1);
        List<Lawyer> lawyers = lawyerService.getBaseMapper().selectList(wrapper);
        if (lawyers.size()>8){
           lawyers = lawyers.subList(0,7);
        }
        return R.success(lawyers);
    }

    @GetMapping("/byId/admin")
    public R<Admin> getAdmin(Long id) {
        return R.success(adminService.getById(id));
    }
    @GetMapping("/byId/user")
    public R<User> getUser(Long id) {
        return R.success(userService.getById(id));
    }

    @GetMapping("/byId/lawyer")
    public R<Lawyer> getLawyer(Long id) {
        log.info("id在这"+id);
        return R.success(lawyerService.getById(id));
    }

    @GetMapping("/admin")
    public R<Admin> getAdmin(String name) {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUsername,name);
        return R.success(adminService.getBaseMapper().selectOne(wrapper));
    }
    @GetMapping("/user")
    public R<User> getUser(String name) {
        log.info("所穿参数为："+name);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getName,name);
        return R.success(userService.getBaseMapper().selectOne(wrapper));
    }

    @GetMapping("/lawyer")
    public R<Lawyer> getLawyer(String name) {
        LambdaQueryWrapper<Lawyer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Lawyer::getName,name);
        return R.success(lawyerService.getBaseMapper().selectOne(wrapper));
    }
}
