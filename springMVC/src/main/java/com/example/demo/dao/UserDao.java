package com.example.demo.dao;

import com.example.demo.entity.User;

/**
 * Created by wangjiang on 2019/1/30.
 */
public interface UserDao
{
    public User findByUserName(String username);

    public User get(String id);
}
