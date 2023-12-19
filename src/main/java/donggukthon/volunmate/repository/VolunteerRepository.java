package donggukthon.volunmate.repository;

import donggukthon.volunmate.domain.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    @Query("SELECT v FROM Volunteer v " +
            "WHERE v.curCount < v.volunCounet AND v.dueDate > :now " +
            "ORDER BY SQRT(" +
            "    (v.lattitude - :latitude) * (v.lattitude - :latitude) + " +
            "    (v.longitude - :longitude) * (v.longitude - :longitude)" +
            ")")
    List<Volunteer> getVolunteers(@Param("latitude") Double latitude,
                                  @Param("longitude") Double longitude, @Param("now") LocalDateTime now);



}
