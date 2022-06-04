package ifce.tjw.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ifce.tjw.spring.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
