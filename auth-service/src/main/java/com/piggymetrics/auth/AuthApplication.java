
package com.piggymetrics.auth;

import com.piggymetrics.auth.service.security.MongoUserDetailsService;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableResourceServer
@EnableDiscoveryClient
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    @Configuration
    @EnableWebMvc
    protected static class WebMvcConfig extends WebMvcConfigurerAdapter {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            // @formatter:off
            registry.addMapping("/oauth/token")
                .allowedOrigins("http://127.0.0.1:6100")
                .allowedMethods("GET", "POST")
                //.allowedHeaders("header1", "header2", "header3")
                //.exposedHeaders("header1", "header2")
                .allowCredentials(false)
                .maxAge(3600);
            // @formatter:on
        }
    }

    @Configuration
    @EnableWebSecurity
    protected static class webSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private MongoUserDetailsService userDetailsService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // @formatter:off
			http
			    .cors()
			.and()
				.authorizeRequests()
				.anyRequest()
				.authenticated()
			.and()
				.csrf()
				.disable();
			// @formatter:on
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
        }

        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

        private TokenStore tokenStore = new InMemoryTokenStore();
        
        @Autowired
        private DataSource dataSource;

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Autowired
        private MongoUserDetailsService userDetailsService;

        @Autowired
        private Environment env;

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

            // TODO persist clients details

            // @formatter:off
			//clients.inMemory()
			clients.jdbc(dataSource)
				.withClient("browser")
				.secret("public-secret")
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
				.scopes("server");
			// @formatter:on
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
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
}
