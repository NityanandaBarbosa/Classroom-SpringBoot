package ifce.tjw.spring.services;

import java.util.ArrayList;
import java.util.List;

import ifce.tjw.spring.dto.DisciplineGetDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ifce.tjw.spring.dto.DisciplineCreateDTO;
import ifce.tjw.spring.dto.DisciplineDTO;
import ifce.tjw.spring.entity.Discipline;
import ifce.tjw.spring.entity.User;
import ifce.tjw.spring.repositories.DisciplineRepository;
import ifce.tjw.spring.repositories.UserRepository;

@Service
public class DisciplineService {
	private final UserRepository userRepository;
	private final DisciplineRepository repository;
	private final ModelMapper mapper;

	public DisciplineService(UserRepository userRepository, DisciplineRepository repository, ModelMapper mapper) {
		this.userRepository = userRepository;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public DisciplineDTO createDiscipline(DisciplineCreateDTO dto, Long userId) {
		Discipline discipline = new Discipline();
		discipline.setName(dto.getName());
		User owner = userRepository.getReferenceById(userId);
		if (owner != null) {
			discipline.setOwner(owner);
			repository.save(discipline);
			return mapper.map(discipline, DisciplineDTO.class);
		} else {
			System.out.println("ERROR");
		}
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public DisciplineDTO delete(Long id, Long ownerId) throws Exception {
		Discipline discipline = repository.getReferenceById(id);
		if (discipline != null) {
			if (discipline.getOwner().getId() == ownerId) {
				repository.deleteById(id);
				return mapper.map(discipline, DisciplineDTO.class);
			} else {
				throw new Exception("It's not your class to delete!");
			}
		}
		return null;
	}

	public List<DisciplineGetDTO> getAllByStudentsId(Long id) {
		List<Discipline> studentList = repository.getAllByStudentsId(id);
		List<Discipline> professorDisciplines = repository.getAllByOwnerId(id);
		List<DisciplineGetDTO> listDTO = new ArrayList<>();
		studentList.forEach((disciplineObj -> listDTO.add(mapper.map(disciplineObj, DisciplineGetDTO.class))));
		professorDisciplines.forEach((disciplineObj -> listDTO.add(mapper.map(disciplineObj, DisciplineGetDTO.class))));
		return listDTO;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public DisciplineGetDTO joinClass(Long classId, Long userId) throws Exception {
		User user = userRepository.findById(userId).get();
		Discipline discipline = repository.findById(classId).get();
		if (discipline.getOwner() == user) {
			throw new Exception("You are the owner");
		}
		if (discipline.getStudents().contains(user)) {
			throw new Exception("You already in class");
		}
		discipline.getStudents().add(user);
		repository.save(discipline);
		return mapper.map(discipline, DisciplineGetDTO.class);
	}

}
