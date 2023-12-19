package donggukthon.volunmate.repository;

import donggukthon.volunmate.domain.Help;
import donggukthon.volunmate.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HelpRepository extends JpaRepository<Help, Long> {
    @Query("select count(h) from Help h where h.user = :user")
    Integer countByUser(@Param("user") User user);

    @Query("select h from Help h where h.user = :user")
    List<Help> findByUser(User user);
}
