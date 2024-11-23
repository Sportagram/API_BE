package com.Sportagram.sportagram;

import com.Sportagram.sportagram.config.SecurityConfig;
import com.Sportagram.sportagram.service.CustomOAuth2UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(
		scanBasePackageClasses = {
		SportagramApplication.class,
		CustomOAuth2UserService.class,
		SecurityConfig.class
})
public class SportagramApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportagramApplication.class, args);
	}

}
