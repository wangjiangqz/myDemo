package com.example.demo.entity;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by wangjiang on 2019/1/30.
 */
@Data
public class Role
{
    private Integer rid;
    private String rname;
    private Set<User> users = new HashSet<>();
    private Set<Module> modules = new HashSet<>();
}
