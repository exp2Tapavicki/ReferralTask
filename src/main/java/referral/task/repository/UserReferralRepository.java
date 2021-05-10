package referral.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import referral.task.rest.model.UserReferral;

public interface UserReferralRepository extends JpaRepository<UserReferral, Long> {


}
