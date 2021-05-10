package referral.task.repository;

import referral.task.rest.model.User;
import referral.task.rest.model.UserReferral;

public interface UserRepositoryCustom {
    UserReferral saveUserReferral(User user, User referralUser);
}
