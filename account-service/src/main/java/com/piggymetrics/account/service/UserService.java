/*
 * Copyright @ 1998-2017 Shenzhen Kingdom Technology CO.,LTD.
 * All Rights Reserved
 *
 *      http://www.szkingdom.com/
 *      http://www.szrhtj.com/
 *
 */

package com.piggymetrics.account.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.piggymetrics.account.domain.User;

public interface UserService {

    List<User> getAllUser();
    
    User getUserbyName(String username);
    
    User createUser(User user);
    
    List<User> getUserByPage(int page, int limit);
    
    PageInfo<User> getUserPageInfo(int page, int limit);
}
