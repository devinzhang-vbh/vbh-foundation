package com.foundation.manage;

import cn.stylefeng.guns.generator.config.GeneratorAutoConfiguration;
import cn.stylefeng.roses.core.config.WebAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author 70719
 */
@SpringBootApplication(exclude = {WebAutoConfiguration.class, GeneratorAutoConfiguration.class})
public class FoundationApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoundationApplication.class, args);
	}

}

