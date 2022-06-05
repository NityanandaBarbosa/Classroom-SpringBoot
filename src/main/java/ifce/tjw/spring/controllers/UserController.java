package ifce.tjw.spring.controllers;

import ifce.tjw.spring.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ifce.tjw.spring.dto.UserCreateDTO;
import ifce.tjw.spring.dto.UserDTO;

@RestController
@RequestMapping("/auth")
public class UserController {
	private final UserService service;

	public UserController(UserService service) {
		this.service = service;
	}

	@PostMapping("/sing-up")
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
	
//	@DeleteMapping(value = "/{id}")
//	public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id) {
//		try {
//			UserDTO dto =  service.delete(id);
//			return new ResponseEntity<> (dto, HttpStatus.OK);
//		}catch (Exception e) {
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
}
