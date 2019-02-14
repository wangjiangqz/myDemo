package com.example.demo.service;

import com.example.demo.dao.stuDao;
import com.example.demo.entity.stu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wangjiang on 2019/1/29.
 */

@Service("userService")
public class stuServiceImp implements stuService
{
    @Resource
    private stuDao studao;

    public List<stu> findList(){
        return studao.findList();
    }
}
