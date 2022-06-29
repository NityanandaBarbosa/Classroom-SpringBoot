package ifce.tjw.spring.repositories;

import ifce.tjw.spring.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
