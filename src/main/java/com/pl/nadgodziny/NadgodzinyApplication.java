package com.pl.nadgodziny;

import com.pl.nadgodziny.overtime.OvertimeConsoleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NadgodzinyApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(NadgodzinyApplication.class, args);
		OvertimeConsoleService overtimeConsoleService = context.getBean(OvertimeConsoleService.class);
		overtimeConsoleService.runApp();


	}
}
