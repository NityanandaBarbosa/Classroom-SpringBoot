package ifce.tjw.spring.repositories;

import ifce.tjw.spring.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
