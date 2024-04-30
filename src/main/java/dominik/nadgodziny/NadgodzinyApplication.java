package dominik.nadgodziny;

import dominik.nadgodziny.infrastructure.console.OvertimeMainLoop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
//@EnableScheduling1
public class NadgodzinyApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(NadgodzinyApplication.class, args);
		OvertimeMainLoop overtimeMainLoop = context.getBean(OvertimeMainLoop.class);
		overtimeMainLoop.runApp();
	}
}
