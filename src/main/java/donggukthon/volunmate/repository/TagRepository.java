package donggukthon.volunmate.repository;

import donggukthon.volunmate.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByContent(String content);
}
