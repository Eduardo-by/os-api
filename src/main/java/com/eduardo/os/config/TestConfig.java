package com.eduardo.os.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.eduardo.os.service.DBService;

@Configuration
@Profile("Test")
public class TestConfig {
	
	@Autowired
	private DBService  dbService;

	@Bean
	public void instaciaDB() {
		this.dbService.instaciaDB();
		

	}

}
