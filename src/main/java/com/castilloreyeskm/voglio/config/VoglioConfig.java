package com.castilloreyeskm.voglio.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VoglioConfig {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
