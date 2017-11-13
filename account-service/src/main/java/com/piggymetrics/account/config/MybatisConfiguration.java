/*
 * Copyright @ 1998-2017 Shenzhen Kingdom Technology CO.,LTD.
 * All Rights Reserved
 *
 *      http://www.szkingdom.com/
 *      http://www.szrhtj.com/
 *
 */

package com.piggymetrics.account.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.piggymetrics.account.repository.mybatis")
public class MybatisConfiguration {

}
