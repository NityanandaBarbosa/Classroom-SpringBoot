package ifce.tjw.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ifce.tjw.spring.entity.Discipline;

public interface DisciplineRepository extends JpaRepository<Discipline, Long>{

}
