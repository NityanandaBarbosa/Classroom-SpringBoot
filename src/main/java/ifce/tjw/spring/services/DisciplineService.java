package ifce.tjw.spring.services;

import java.util.Optional;

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
	@Autowired
	private DisciplineRepository repo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ModelMapper mapper;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public DisciplineDTO createDiscipline(DisciplineCreateDTO dto) {
		Discipline discipline = new Discipline();
		discipline.setName(dto.getName());
		Optional<User> owner = userRepo.findById(dto.getOwner_id());
		if(owner != null) {
			User owner_true = owner.get();
			discipline.setOwner(owner_true);
			repo.save(discipline);
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
}