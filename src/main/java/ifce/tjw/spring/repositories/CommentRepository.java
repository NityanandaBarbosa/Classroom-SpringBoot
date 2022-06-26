package ifce.tjw.spring.repositories;

import ifce.tjw.spring.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> getAllByActivityId(Long id);
}
