package com.example.demo.service;

import com.example.demo.entity.User;

/**
 * Created by wangjiang on 2019/1/30.
 */
public interface UserService
{
    public User findByUserName(String username);

    public User get(String id);
}
