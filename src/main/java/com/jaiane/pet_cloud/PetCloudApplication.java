package com.jaiane.pet_cloud;

import com.jaiane.pet_cloud.config.AzureAIConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication


public class PetCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetCloudApplication.class, args);
	}

}
