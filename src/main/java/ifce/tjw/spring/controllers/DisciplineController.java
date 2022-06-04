package ifce.tjw.spring.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import ifce.tjw.spring.config.JWTAuthFilter;
import ifce.tjw.spring.dto.DisciplineGetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ifce.tjw.spring.dto.DisciplineCreateDTO;
import ifce.tjw.spring.dto.DisciplineDTO;
import ifce.tjw.spring.services.DisciplineService;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/discipline")
public class DisciplineController {
	@Autowired
	DisciplineService service;

	@PostMapping
	public ResponseEntity<DisciplineDTO> createDiscipline(@RequestBody DisciplineCreateDTO dto, @RequestHeader(name="Authorization") String token) {
		try {
			DisciplineDTO discipline = service.createDiscipline(dto);
			
			if(discipline != null) {
				return new ResponseEntity<> (discipline, HttpStatus.CREATED);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public String getToken(String token) {
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String[] chunks = token.split("\\.");
		String payload = new String(decoder.decode(chunks[1]));
		return  payload;
	}

	@GetMapping
	public ResponseEntity<List<DisciplineGetDTO>> getAll(@RequestHeader (name="Authorization") String token) {
		System.out.println(getToken(token));
		try {
			List<DisciplineGetDTO> list =  service.getAll();
			return new ResponseEntity<> (list, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<DisciplineDTO> deleteUser(@PathVariable Long id) {
		try {
			DisciplineDTO dto =  service.delete(id);
			return new ResponseEntity<> (dto, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
