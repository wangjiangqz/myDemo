package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangjiang on 2019/1/30.
 */
@Service("UserService")
public class UserServiceImp implements UserService
{
    @Autowired
    private UserDao userDao;

    @Override
    public User findByUserName(String username){
        return userDao.findByUserName(username);
    }

    @Override
    public User get(String id){
        return userDao.get(id);
    }
}
