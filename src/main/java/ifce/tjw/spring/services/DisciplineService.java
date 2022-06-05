package ifce.tjw.spring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ifce.tjw.spring.dto.DisciplineGetDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
	private final UserRepository userRepo;
	@Autowired
	private DisciplineRepository repo;
	@Autowired
	private ModelMapper mapper;

	public DisciplineService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public DisciplineDTO createDiscipline(DisciplineCreateDTO dto, Long userId) {
		Discipline discipline = new Discipline();
		discipline.setName(dto.getName());
		Optional<User> owner = userRepo.findById(userId);
		if(owner != null) {
			User owner_true = owner.get();
			owner_true.getClasses().add(discipline);
			discipline.setOwner(owner_true);
			repo.save(discipline);
			userRepo.save(owner_true);
			return mapper.map(discipline, DisciplineDTO.class);
		}else {
			System.out.println("ERROR");
		}
		return null;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public DisciplineDTO delete(Long id) {
		Optional<Discipline> optDiscipline = repo.findById(id);
		if(optDiscipline != null) {
			Discipline discipline = optDiscipline.get();
			repo.deleteById(id);
			return mapper.map(discipline, DisciplineDTO.class);
		}
		return null;
	}
	public List<DisciplineGetDTO> getAllByOwnerId(Long id) {
		List<Discipline> disciplines = (List<Discipline>) userRepo.findById(id).get().getClasses();
		List<DisciplineGetDTO> listDTO = new ArrayList<>();
		disciplines.forEach((disciplineObj -> listDTO.add(mapper.map(disciplineObj, DisciplineGetDTO.class))));
		return listDTO;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public DisciplineGetDTO joinClass(Long classId, Long userId){
		User user = userRepo.findById(userId).get();
		Discipline discipline = repo.findById(classId).get();
		discipline.getUser().add(user);
		user.getClasses().add(discipline);
		repo.save(discipline);
		userRepo.save(user);
		return mapper.map(discipline, DisciplineGetDTO.class);
	}

}
