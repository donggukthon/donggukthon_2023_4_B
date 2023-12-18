package donggukthon.volunmate.repository;

import donggukthon.volunmate.domain.Volunmate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunmateRepository extends JpaRepository<Volunmate, Long> {
}
