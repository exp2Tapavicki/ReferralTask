package referral.task.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import referral.task.repository.UserRepository;
import referral.task.rest.model.UserReferral;
import referral.task.rest.model.User;
import referral.task.service.UserService;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    public UserServiceImpl() {
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User save(User object) {
        return userRepository.save(object);
    }

    @Override
    public void update(User object) {
        userRepository.save(object);
    }

    @Override
    public void delete(Long id, Long ordinalNumber) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public boolean checkExternalIdUnique(String externalId) {
        return userRepository.findByExternalId(externalId) == null;
    }

    @Override
    public User findByExternalId(String externalId) {
        return userRepository.findByExternalId(externalId);
    }

    @Transactional
    @Override
    public UserReferral saveUserReferral(User user, User referralUser) {
        UserReferral userReferral = null;
        user.setCredit(user.getCredit() + 10);
        user = userRepository.save(user);
        if (user.getId() != null) {
            userReferral = userRepository.saveUserReferral(user, referralUser);
            if (userReferral != null) {
                Long count = userRepository.countReferralsRegistration(referralUser.getId());
                if (count != 0 && count % 5 == 0) {
                    referralUser.setCredit(referralUser.getCredit() + 10);
                    userRepository.save(referralUser);
                }
            }
        }
        return userReferral;
    }
}
