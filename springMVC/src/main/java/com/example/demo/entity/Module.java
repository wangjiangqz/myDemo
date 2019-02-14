package com.example.demo.entity;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by wangjiang on 2019/1/30.
 */
@Data
public class Module
{
    private Integer mid;
    private String mname;
    private Set<Role> roles = new HashSet<>();
}
