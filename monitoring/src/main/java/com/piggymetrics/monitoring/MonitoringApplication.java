
package com.piggymetrics.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream;

@SpringBootApplication
@EnableTurbineStream
//@EnableDiscoveryClient
//@EnableHystrixDashboard
public class MonitoringApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringApplication.class, args);
	}

	/**
	@Configuration
	public class HystrixDashboardAutoConfiguration extends WebMvcConfigurerAdapter {
	
	    @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("/hystrix/**").addResourceLocations("classpath:/static/hystrix/");
	    }
	
	}*/
}
