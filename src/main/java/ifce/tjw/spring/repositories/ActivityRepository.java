package ifce.tjw.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ifce.tjw.spring.entity.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long>{

}
