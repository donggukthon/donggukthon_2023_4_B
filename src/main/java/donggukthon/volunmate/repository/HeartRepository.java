package donggukthon.volunmate.repository;

import donggukthon.volunmate.domain.Heart;
import donggukthon.volunmate.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {
    @Query("select count(h) from Heart h where h.user = :user")
    Integer countByUser(@Param("user") User user);

    @Query("select h from Heart h where h.user = :user")
    List<Heart> findByUser(User user);
}
