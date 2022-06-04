package ifce.tjw.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ifce.tjw.spring.dto.UserCreateDTO;
import ifce.tjw.spring.dto.UserDTO;
import ifce.tjw.spring.repositories.UserRepository;
import ifce.tjw.spring.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	UserService service;
	
	@PostMapping
	public ResponseEntity<UserDTO> createUser(@RequestBody UserCreateDTO dto) {
		try {
			if(dto.getEmail().isEmpty() || dto.getEmail().isEmpty() || dto.getNome().isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}else {
				UserDTO user = service.createUser(dto);
				
				if(user != null) {
					return new ResponseEntity<> (user, HttpStatus.CREATED);
				}
			}
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id) {
		try {
			UserDTO dto =  service.delete(id);
			return new ResponseEntity<> (dto, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
