package app.tasksmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaRepositories
@EnableAsync
@EnableCaching
public class TasksMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TasksMicroserviceApplication.class, args);
	}

}
