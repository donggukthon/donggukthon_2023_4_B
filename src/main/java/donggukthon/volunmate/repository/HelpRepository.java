package donggukthon.volunmate.repository;

import donggukthon.volunmate.domain.Help;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelpRepository extends JpaRepository<Help, Long> {
}
