package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.R;
import com.example.entity.Lawyer;
import com.example.entity.Legal;
import com.example.service.impl.LegalServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/legal")
@Slf4j
public class LegalController {
    @Autowired
    LegalServiceImpl legalService;

    @PostMapping
    public R<String> addLegal(@RequestBody Legal legal){
        LambdaQueryWrapper<Legal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Legal::getTitle, legal.getTitle());
        Legal one = legalService.getOne(queryWrapper);
        if (one!=null){
            return R.error("该用户已存在");
        }else {
            legalService.save(legal);
            return R.success("新增成功");
        }
    }

    @DeleteMapping
    public R<String> deleteLegal(@RequestParam("id") Long id){
        if (legalService.removeById(id)){
            return R.success("删除成功");
        }else {
            return R.error("未知错误，请联系开发人员");
        }
    }

    @PutMapping
    public R<String> upDataLegal(@RequestBody Legal legal){
        if (legalService.updateById(legal)){
            return R.success("更新成功");
        }else {
            return R.error("未知错误，请联系开发人员");
        }
    }

    @GetMapping("/select")
    public R<Legal> selectLegal(Long id){
        Legal legal = legalService.getById(id);
        if (legal!=null){
            return R.success(legal);
        }else {
            return R.error("未知错误，请联系开发人员");
        }
    }

    @GetMapping("/selectALL")
    public R<List<Legal>> selectAllLegal(){
        List<Legal> legals = legalService.getBaseMapper().selectList(null);
        if (legals!=null){
            return R.success(legals);
        }else {
            return R.error("未知错误，请联系开发人员");
        }
    }

    @GetMapping("/selectPut")
    public R<List<Legal>> selectPut(){
        LambdaQueryWrapper<Legal> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Legal::getTime);
        List<Legal> legals = legalService.getBaseMapper().selectList(wrapper);
        if (legals.size()>6){
            legals = legals.subList(0,5);
        }
        return R.success(legals);
    }
}
