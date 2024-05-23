package dominik.nadgodziny;

import dominik.nadgodziny.infrastructure.console.OvertimeMainControlLoop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class NadgodzinyApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(NadgodzinyApplication.class, args);
		OvertimeMainControlLoop overtimeMainControlLoop = context.getBean(OvertimeMainControlLoop.class);
		System.out.println("Version 1.1.4");
		overtimeMainControlLoop.runAppMain();
	}
}
