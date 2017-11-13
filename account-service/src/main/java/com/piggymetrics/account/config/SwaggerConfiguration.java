/*
 * Copyright @ 1998-2017 Shenzhen Kingdom Technology CO.,LTD.
 * All Rights Reserved
 *
 *      http://www.szkingdom.com/
 *      http://www.szrhtj.com/
 *
 */

package com.piggymetrics.account.config;

import java.util.ArrayList;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.ClientCredentialsGrant;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Value("${uaa.oauthServerUri}")
    private String oauthServerUri;

    @Value("${swagger2.title}")
    private String title;

    @Value("${swagger2.description}")
    private String description;

    @Value("${swagger2.termsOfServiceUrl}")
    private String termsOfServiceUrl;

    @Value("${swagger2.contact.name}")
    private String contactName;

    @Value("${swagger2.contact.url}")
    private String contactUrl;

    @Value("${swagger2.contact.email}")
    private String contactEmail;

    @Value("${swagger2.version}")
    private String version;

    @Bean
    public Docket createRestApi() {
        // @formatter:off
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(this.oAuth())
                .apiInfo(apiInfo())
                .select()
                .paths(postPaths())
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))  
                .paths(springBootActuatorJmxPaths())
                //.apis(RequestHandlerSelectors.basePackage("com.piggymetrics.account.controller"))
                .paths(PathSelectors.any())
                .build();
        // @formatter:on
    }

    private Predicate<String> postPaths() {
        return regex("/.*");
    }

    private Predicate<String> springBootActuatorJmxPaths() {
        return regex("^/(?!env|restart|pause|resume|refresh).*$");
    }

    private ApiInfo apiInfo() {
        // @formatter:off
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(termsOfServiceUrl)
                .contact(new Contact(contactName, contactUrl, contactEmail))
                .version(version)
                .build();
        // @formatter:on
    }

    private List<SecurityScheme> oAuth() {
        // browser
        List<AuthorizationScope> scopes1 = new ArrayList<AuthorizationScope>();
        scopes1.add(new AuthorizationScope("ui", "browser"));

        List<GrantType> gTypes1 = new ArrayList<GrantType>();
        //gTypes1.add(new GrantType("refresh_token"));
        gTypes1.add(new ResourceOwnerPasswordCredentialsGrant(oauthServerUri));

        OAuth oAuth1 = new OAuth("browser", scopes1, gTypes1);

        // Server
        List<AuthorizationScope> scopes2 = new ArrayList<AuthorizationScope>();
        scopes2.add(new AuthorizationScope("server", "server"));

        List<GrantType> gTypes2 = new ArrayList<GrantType>();
        gTypes2.add(new ClientCredentialsGrant(oauthServerUri));
        //gTypes2.add(new GrantType("refresh_token"));

        OAuth oAuth2 = new OAuth("account-service", scopes2, gTypes2);

        List<SecurityScheme> list = new ArrayList<SecurityScheme>();
        list.add(oAuth1);
        list.add(oAuth2);

        return list;
    }
}
