package referral.task.repository;

import referral.task.rest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    @Query(value = "SELECT user.* " +
            "FROM " +
            "user " +
            " where user.email = :email", nativeQuery = true)
    User getUserByEmail(@Param("email") String email);


    @Query("SELECT DISTINCT user FROM User user " +
            "WHERE user.externalId = :externalId")
    User findByExternalId(@Param("externalId") String externalId);

    @Query("SELECT count(userReferral) FROM UserReferral userReferral " +
            "WHERE userReferral.referralUserId = :referralUserId")
    Long countReferralsRegistration(@Param("referralUserId") Long referralUserId);

}
