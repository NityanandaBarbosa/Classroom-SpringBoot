package ifce.tjw.spring.controllers;

import ifce.tjw.spring.Application;
import ifce.tjw.spring.dto.DisciplineGetDTO;
import ifce.tjw.spring.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ifce.tjw.spring.dto.DisciplineCreateDTO;
import ifce.tjw.spring.dto.DisciplineDTO;
import ifce.tjw.spring.services.DisciplineService;
import ifce.tjw.spring.utils.UserInfoToken;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/discipline")
public class DisciplineController {
	private final DisciplineService service;

	public DisciplineController(DisciplineService service, UserRepository userRepository) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<DisciplineDTO> createDiscipline(@RequestBody DisciplineCreateDTO dto,
			@RequestHeader(name = "Authorization") String token) {
		Map<String, String> payload = UserInfoToken.getUserInfoFromToken(token);
		Long id = Long.parseLong(payload.get("id"));
		try {
			DisciplineDTO discipline = service.createDiscipline(dto, id);

			if (discipline != null) {
				return new ResponseEntity<>(discipline, HttpStatus.CREATED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping
	public ResponseEntity<List<DisciplineGetDTO>> getAll(@RequestHeader(name = "Authorization") String token) {
		Map<String, String> payload = UserInfoToken.getUserInfoFromToken(token);
		Long id = Long.parseLong(payload.get("id"));
		try {
			List<DisciplineGetDTO> list = service.getAllByOwnerId(id);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/{id}")
	public ResponseEntity<DisciplineGetDTO> joinClass(@PathVariable Long id,
			@RequestHeader(name = "Authorization") String token) {
		Map<String, String> payload = UserInfoToken.getUserInfoFromToken(token);
		Long userId = Long.parseLong(payload.get("id"));
		try {
			DisciplineGetDTO dto = service.joinClass(id, userId);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}
