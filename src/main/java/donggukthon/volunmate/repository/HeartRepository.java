package donggukthon.volunmate.repository;

import donggukthon.volunmate.domain.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Long> {
}
