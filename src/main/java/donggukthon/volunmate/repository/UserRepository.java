package donggukthon.volunmate.repository;

import donggukthon.volunmate.domain.User;
import donggukthon.volunmate.type.ELoginProvider;
import donggukthon.volunmate.type.EUserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u.socialId as id, u.userType as userType, u.password as password from User u where u.socialId = :socialId")
    Optional<UserSecurityForm> findBySecurityFormBySocialId(String socialId);

    Optional<User> findBySocialIdAndRefreshToken(String socialId, String refreshToken);

    Optional<User> findBySocialIdAndProvider(String socialId, ELoginProvider provider);

    Optional<User> findBySocialIdAndIsLogin(String socialId, boolean isLogin);

    interface UserSecurityForm {
        String getId();
        EUserType getType();
        String getPassword();
    }
}
