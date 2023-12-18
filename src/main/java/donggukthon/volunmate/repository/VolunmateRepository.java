package donggukthon.volunmate.repository;

import donggukthon.volunmate.domain.Volunmate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunmateRepository extends JpaRepository<Volunmate, Long> {
}
