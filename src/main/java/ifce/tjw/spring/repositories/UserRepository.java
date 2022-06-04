package ifce.tjw.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ifce.tjw.spring.entity.User;

@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User, Long> {
}