package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wangjiang on 2019/1/30.
 */
@Data
public class User implements Serializable
{
    private Integer uid;
    private String username;
    private String password;
    private Set<Role> roles = new HashSet<>();
}
