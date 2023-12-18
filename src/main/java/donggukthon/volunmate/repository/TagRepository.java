package donggukthon.volunmate.repository;

import donggukthon.volunmate.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
