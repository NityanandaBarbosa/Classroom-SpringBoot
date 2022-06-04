package ifce.tjw.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ifce.tjw.spring.entity.User;
import ifce.tjw.spring.repositories.UserRepository;

@SpringBootApplication
public class Application {
	
//	@Autowired
//	UserRepository userRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
//	@Bean
//	CommandLineRunner runner() {
//		return args -> {
//			List<User> users = userRepo.findAll();
//			users.forEach(user -> System.out.println(user.getNome()));
//		};
//	}
}
