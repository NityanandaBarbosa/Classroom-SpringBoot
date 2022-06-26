package ifce.tjw.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ifce.tjw.spring.entity.Discipline;

import java.util.List;

public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
    List<Discipline> getAllByStudentsId(Long id);

    List<Discipline> getAllByOwnerId(Long id);
}
