package referral.task.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import referral.task.repository.UserReferralRepository;
import referral.task.repository.UserRepositoryCustom;
import referral.task.rest.model.User;
import referral.task.rest.model.UserReferral;

import java.time.Instant;

class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @Autowired
    private UserReferralRepository userReferralRepository;

    @Override
    public UserReferral  saveUserReferral(User user, User referralUser) {
        UserReferral userReferral = new UserReferral();
        userReferral.setUserId(user.getId());
        userReferral.setReferralUserId(referralUser.getId());
        userReferral.setCreated(Instant.now().toEpochMilli()/1000);
        return userReferralRepository.save(userReferral);
    }
}
