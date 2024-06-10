package dominik.nadgodziny;

import dominik.nadgodziny.infrastructure.overtime.console.OvertimeMainControlLoop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class NadgodzinyApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(NadgodzinyApplication.class, args);
		OvertimeMainControlLoop overtimeMainControlLoop = context.getBean(OvertimeMainControlLoop.class);
		Environment env = context.getEnvironment();
		String version = env.getProperty("application.version");
		System.out.println(version);
		overtimeMainControlLoop.runAppMain();
	}
}
