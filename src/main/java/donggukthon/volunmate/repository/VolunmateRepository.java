package donggukthon.volunmate.repository;

import donggukthon.volunmate.domain.User;
import donggukthon.volunmate.domain.Volunmate;
import donggukthon.volunmate.domain.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VolunmateRepository extends JpaRepository<Volunmate, Long> {
    @Query("select count(v) from Volunmate v where v.user= :user")
    Integer countByUser(@Param("user") User user);

    @Query("select v from Volunmate v where v.volunteer in :volunteers")
    List<Volunmate> findByVolunteersIn(List<Volunteer> volunteers);

    @Query("select v from Volunmate v where v.volunteer = :volunteer")
    List<Volunmate> findByVolunteer(Volunteer volunteer);
}
