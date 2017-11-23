/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.piggymetrics.auth.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.piggymetrics.auth.service.security.CustomUserDetailsService;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration implements AuthorizationServerConfigurer {
	
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
	
	@Autowired
	private DataSource dataSource;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	//@Autowired
	//private Environment env;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		// TODO persist clients details

		// @formatter:off
		//clients.inMemory()
		clients.inMemory()
			.withClient("browser")
			.secret("1234")
			.authorizedGrantTypes("refresh_token", "password")
			.scopes("ui")
		.and()
			.withClient("account-service")
			.secret("1234")
			.authorizedGrantTypes("client_credentials", "refresh_token")
			.scopes("server")
		.and()
			.withClient("statistics-service")
			.secret("1234")
			.authorizedGrantTypes("client_credentials", "refresh_token")
			.scopes("server")
		.and()
			.withClient("notification-service")
			.secret("1234")
			.authorizedGrantTypes("client_credentials", "refresh_token")
			.scopes("server")
		.and()
			.withClient("admin-server")
			.secret("1234")
			.authorizedGrantTypes("authorization_code", "refresh_token", "password")
			.scopes("openid");
		// @formatter:on
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
		// @formatter:off
        endpoints.tokenStore(tokenStore)
            .authenticationManager(authenticationManager)
            .userDetailsService(userDetailsService);
        // @formatter:on
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		// @formatter:off
        oauthServer.tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()");
        // @formatter:on
	}

}
