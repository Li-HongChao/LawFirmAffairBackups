package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Legal;
import com.example.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LegalMapper extends BaseMapper<Legal> {
}
