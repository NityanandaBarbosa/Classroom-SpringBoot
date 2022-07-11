package ifce.tjw.spring.controllers;

import ifce.tjw.spring.dto.DisciplineGetDTO;
import ifce.tjw.spring.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import ifce.tjw.spring.dto.DisciplineCreateDTO;
import ifce.tjw.spring.dto.DisciplineDTO;
import ifce.tjw.spring.services.DisciplineService;
import ifce.tjw.spring.utils.UserInfoToken;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/discipline")
public class DisciplineController {
	private final DisciplineService service;

	public DisciplineController(DisciplineService service, UserRepository userRepository) {
		this.service = service;
	}

	@PostMapping
	public String createDiscipline(DisciplineCreateDTO dto) {
		try {
			DisciplineDTO discipline = service.createDiscipline(dto);

			if (discipline != null) {
				return "redirect:/discipline";
			}
		} catch (Exception e) {
			return "redirect:/discipline";
		}
		return "redirect:/discipline";
	}

	@GetMapping(value = "/create-discipline")
	public String createDiscipline() {
		return "create-discipline.html";
	}

	@GetMapping
	public ModelAndView getAll() {
		ModelAndView mv = new ModelAndView("home.html");
		List<DisciplineGetDTO> list = service.getAllByStudentsId();
		mv.addObject("disciplines", list);
		return mv;
	}

	@PostMapping(value = "/join-class")
	public String joinClass(DisciplineCreateDTO dto) {
		try {
			DisciplineGetDTO dtoResponse = service.joinClass(dto);
			return "redirect:/discipline";
		} catch (Exception e) {
			System.out.println(e);
			return "redirect:/discipline";
		}
	}

	@GetMapping(value = "/join-class")
	public String joinClass() {
		return "join-discipline.html";
	}
}
