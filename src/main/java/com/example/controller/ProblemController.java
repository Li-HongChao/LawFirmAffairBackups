package com.example.controller;

import com.example.until.R;
import com.example.entity.Problem;
import com.example.service.impl.ProblemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/problem")
public class ProblemController {
    @Autowired
    private ProblemServiceImpl problemService;

    @PostMapping
    private R<String> addProblem(@RequestBody Problem problem){
        problemService.save(problem);
        return R.success("保存成功");
    }

    @PutMapping
    private R<String> upData(@RequestBody Problem problem){
        problemService.updateById(problem);
        return R.success("更新成功");
    }

    @DeleteMapping
    private R<String> delete(Long id){
        problemService.removeById(id);
        return R.success("删除成功");
    }

    @GetMapping("/getAll")
    private R<List<Problem>> getAll(){
        List<Problem> problems = problemService.getBaseMapper().selectList(null);
        return R.success(problems);
    }

    @GetMapping("/getById")
     private R<Problem> getById(Long id){
        Problem problem = problemService.getById(id);
        return R.success(problem);
    }

}
