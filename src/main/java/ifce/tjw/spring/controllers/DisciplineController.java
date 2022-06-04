package ifce.tjw.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ifce.tjw.spring.dto.DisciplineCreateDTO;
import ifce.tjw.spring.dto.DisciplineDTO;
import ifce.tjw.spring.services.DisciplineService;

@RestController
@RequestMapping("/discipline")
public class DisciplineController {
	@Autowired
	DisciplineService service;
	
	@PostMapping
	public ResponseEntity<DisciplineDTO> createDiscipline(@RequestBody DisciplineCreateDTO dto) {
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
