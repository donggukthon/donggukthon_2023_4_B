package donggukthon.volunmate.repository;

import donggukthon.volunmate.domain.Help;
import donggukthon.volunmate.domain.User;
import donggukthon.volunmate.domain.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HelpRepository extends JpaRepository<Help, Long> {
    @Query("select count(h) from Help h where h.user = :user")
    Integer countByUser(@Param("user") User user);

    @Query("select h from Help h where h.user = :user")
    List<Help> findByUser(User user);

    @Query("SELECT h FROM Help h " +
            "ORDER BY ST_Distance_Sphere(" +
            "POINT(h.longitude,h.latitude),POINT(:longitude,:latitude)"+
            ")")
    List<Help> getHelpList(@Param("latitude") Double latitude, @Param("longitude") Double longitude);

    @Query("SELECT h FROM Help h " +
            "WHERE h.emergency = true "+
            "ORDER BY ST_Distance_Sphere(" +
            "POINT(h.longitude,h.latitude),POINT(:longitude,:latitude)"+
            ")")
    List<Help> getEmergencyHelpList(@Param("latitude") Double latitude, @Param("longitude") Double longitude);
}
