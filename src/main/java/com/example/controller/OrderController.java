package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.R;
import com.example.entity.Lawyer;
import com.example.entity.OrderSort;
import com.example.entity.Orders;
import com.example.service.impl.LawyerServiceImpl;
import com.example.service.impl.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
@Slf4j
//预约管理类
public class OrderController {
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private LawyerServiceImpl lawyerService;

    @PostMapping
    public R<String> addOrder(@RequestBody Orders orders) {
        orderService.save(orders);
        return R.success("预约成功");
    }

    @PutMapping
    public R<String> completeOrder(@RequestBody Orders orders) {
        return R.success(orderService.updateById(orders) + "");
    }

    @GetMapping
    public R<List<Orders>> getByLawyerOrder(String username) {
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getLawyerName, username);
        List<Orders> orders = orderService.getBaseMapper().selectList(wrapper);
        //过滤出未解决的订单
        orders = orders.stream().filter(s -> s.getStatus() > 0).collect(Collectors.toList());
        log.info("查询订单：" + orders);
        return R.success(orders);
    }

    @GetMapping("/user")
    public R<List<Orders>> getByUserOrder(String username) {
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getUserName, username);
        List<Orders> orders = orderService.getBaseMapper().selectList(wrapper);
        //过滤出未解决的订单
        orders = orders.stream().filter(s -> s.getStatus() > 0).collect(Collectors.toList());
        log.info("查询订单：" + orders);
        return R.success(orders);
    }

    @GetMapping("/sort")
    private R<OrderSort> getSortOrder() {
        LambdaQueryWrapper<Lawyer> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Lawyer::getStatus,1);
        List<Lawyer> lawyers = lawyerService.getBaseMapper().selectList(lambdaQueryWrapper);
        //两个列表分别保存姓名和出现次数
        List<String> names = new ArrayList<>();
        List<Integer> sorts = new ArrayList<>();

        int count;
        for (Lawyer lawyer : lawyers) {
            LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Orders::getLawyerName, lawyer.getName());
            count = orderService.count(wrapper);
            //一侧是律师姓名，一侧是次数
            names.add(lawyer.getName());
            sorts.add(count);
        }
        OrderSort sort = new OrderSort(names, sorts);
        return R.success(sort);
    }
}
