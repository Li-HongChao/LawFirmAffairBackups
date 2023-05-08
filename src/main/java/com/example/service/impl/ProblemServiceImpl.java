package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Admin;
import com.example.entity.Problem;
import com.example.mapper.AdminMapper;
import com.example.mapper.ProblemMapper;
import com.example.service.IAdminService;
import com.example.service.IProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem>implements IProblemService {
}
