package com.example.springboot_aws_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
//@EnableJpaRepositories("com.example.springboot_aws_demo.dao")
@EntityScan("com.example.springboot_aws_demo.vo")
public class SpringbootAwsDemoApplication  {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootAwsDemoApplication.class, args);
	}

	/*@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

	}
*/
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
