package donggukthon.volunmate.repository;

import donggukthon.volunmate.domain.User;
import donggukthon.volunmate.domain.Volunteer;
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

    @Query("select v from Volunteer v join v.volunmates vm where vm.id = :volunmateId")
    Optional<Volunteer> findByVolunmateId(Long volunmateId);

    @Query("SELECT v FROM Volunteer v " +
            "WHERE v.curCount < v.volunCounet AND v.dueDate > :now " +
            "ORDER BY SQRT(" +
            "    (v.latitude - :latitude) * (v.latitude - :latitude) + " +
            "    (v.longitude - :longitude) * (v.longitude - :longitude)" +
            ")")
    List<Volunteer> getVolunteers(@Param("latitude") Double latitude, @Param("longitude") Double longitude);
}
