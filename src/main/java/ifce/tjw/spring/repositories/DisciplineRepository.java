package ifce.tjw.spring.repositories;

import ifce.tjw.spring.dto.DisciplineGetDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import ifce.tjw.spring.entity.Discipline;

import java.util.Collection;
import java.util.List;

public interface DisciplineRepository extends JpaRepository<Discipline, Long>{
    List<Discipline> getAllByOwnerId(Long id);
}
