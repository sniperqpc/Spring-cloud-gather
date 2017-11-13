/*
 * Copyright @ 1998-2017 Shenzhen Kingdom Technology CO.,LTD.
 * All Rights Reserved
 *
 *      http://www.szkingdom.com/
 *      http://www.szrhtj.com/
 *
 */

package com.piggymetrics.account.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.piggymetrics.account.domain.User;
import com.piggymetrics.account.repository.mybatis.UserMapper;
import com.piggymetrics.account.service.UserService;


@Service
public class UserServiceImpl implements UserService {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public List<User> getAllUser() {
        return userMapper.selectAll();
    }

    @Override
    public User getUserbyName(String username) {
        User record = new User();
        record.setUsername(username);
        return userMapper.selectOne(record);
    }

    @Override
    public User createUser(User record) {
        User existing = userMapper.selectOne(record);
        
        Assert.isNull(existing, "账户已存在: " + record.getUsername());
        
        int result = userMapper.insert(record);
        
        Assert.isTrue(result == 1, "保存失败");
        
        return record;
    }

    @Override
    public List<User> getUserByPage(int page, int limit) {
        int offset = (page - 1) * limit;
        RowBounds rowBounds = new RowBounds(offset, limit);
        return userMapper.selectByRowBounds(null, rowBounds);
    }

    @Override
    public PageInfo<User> getUserPageInfo(int page, int limit) {
        PageInfo<User> pageInfo = PageHelper.startPage(page, limit).doSelectPageInfo(new ISelect() {

            @Override
            public void doSelect() {
                int offset = (page - 1) * limit;
                RowBounds rowBounds = new RowBounds(offset, limit);
                userMapper.selectByRowBounds(null, rowBounds);
                
            }
            
        });
        
        logger.info("Page info ==> {}", pageInfo);
        return pageInfo;
    }

}
