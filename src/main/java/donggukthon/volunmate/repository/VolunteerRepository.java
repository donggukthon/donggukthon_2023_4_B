package donggukthon.volunmate.repository;

import donggukthon.volunmate.domain.User;
import donggukthon.volunmate.domain.Volunteer;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    @Query("select v from Volunteer v where v.user = :user")
    List<Volunteer> findByUser(User user);

    @Query("SELECT v FROM Volunteer v " +
            "WHERE v.curCount < v.volunCount AND v.dueDate > :now " +
            "ORDER BY ST_Distance_Sphere(" +
            "POINT(v.longitude,v.latitude),POINT(:longitude,:latitude)"+
            ")")
    List<Volunteer> getVolunteers(@Param("latitude") Double latitude, @Param("longitude") Double longitude,
                                  @Param("now")LocalDateTime now);
}
