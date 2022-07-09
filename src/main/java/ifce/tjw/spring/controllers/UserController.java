package ifce.tjw.spring.controllers;

import ifce.tjw.spring.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import ifce.tjw.spring.dto.UserCreateDTO;
import ifce.tjw.spring.dto.UserDTO;

@Controller
@RequestMapping("/auth")
public class UserController {
	private final UserService service;

	public UserController(UserService service) {
		this.service = service;
	}

	@GetMapping("/sing-up")
	public String createUser() {
		return "sing-up.html";
	}

	@PostMapping("/sing-up")
	public String createUser(UserCreateDTO dto) {
		try {
			if (dto.getEmail().isEmpty() || dto.getEmail().isEmpty() || dto.getNome().isEmpty()) {
				return "sing-up-error.html";
			} else {
				UserDTO user = service.createUser(dto);
				if (user != null) {
					return "login.html";
				}
			}
		} catch (Exception e) {
			return "sing-up-error.html";
		}
		return "sing-up-error.html";
	}
}
