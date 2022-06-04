package ifce.tjw.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ifce.tjw.spring.entity.User;
import ifce.tjw.spring.repositories.UserRepository;

@RestController
public class UserController {
	@Autowired
	UserRepository userRepo;
	
	@RequestMapping("/users")
	public List<User> getUsers(){
		return userRepo.findAll();
	}
}
