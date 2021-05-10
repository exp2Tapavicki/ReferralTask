package referral.task.service;

import referral.task.rest.model.UserReferral;
import referral.task.rest.model.User;

public interface UserService extends ServiceInterface<User>  {
    User findByEmail(String email);
    boolean checkExternalIdUnique(String externalId);
    User findByExternalId(String externalId);
    UserReferral saveUserReferral(User user, User referralUser);
}
