/*
 * Copyright @ 1998-2017 Shenzhen Kingdom Technology CO.,LTD.
 * All Rights Reserved
 *
 *      http://www.szkingdom.com/
 *      http://www.szrhtj.com/
 *
 */

package com.piggymetrics.account.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.piggymetrics.account.domain.User;
import com.piggymetrics.account.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    
    @ApiOperation(value="查询用户列表", notes="根据开始结束查询列表")
    @GetMapping("/user-list/{page}/{limit}")
    public List<User> getUserByPage(@PathVariable("page") int page, @PathVariable("limit") int limit) {
        return userService.getUserByPage(page, limit);
    }
    
    @ApiOperation(value="查询用户列表分页信息", notes="根据开始结束查询列表分布信息")
    @GetMapping("/user-lists/{page}/{limit}")
    public PageInfo<User> getUserPageInfo(@PathVariable("page") int page, @PathVariable("limit") int limit) {
        return userService.getUserPageInfo(page, limit);
    }
    
    @ApiOperation(value="获取所有用户", notes="所有用户")
    @GetMapping("/user")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }
    
    @ApiOperation(value="创建用户", notes="创建用户")
    @PostMapping("/user")
    public User create(User user) {
        return userService.createUser(user);
    }
}
