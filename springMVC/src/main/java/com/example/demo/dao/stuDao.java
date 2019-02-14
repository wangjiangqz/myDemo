package com.example.demo.dao;

import com.example.demo.entity.stu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by wangjiang on 2019/1/29.
 */
@Mapper
public interface  stuDao
{
    List<stu> findList();
}
