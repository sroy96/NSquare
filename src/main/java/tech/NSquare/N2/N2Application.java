package tech.NSquare.N2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class N2Application {

	public static void main(String[] args) {
		SpringApplication.run(N2Application.class, args);
	}

}
