package app.data_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
public class DataMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataMicroserviceApplication.class, args);
	}

}
