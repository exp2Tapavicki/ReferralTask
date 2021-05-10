package referral.task.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import referral.task.rest.model.User;
import referral.task.service.UserService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> registerUser(@RequestBody @NotNull User user) {
        Set<ConstraintViolation<Object>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            return ResponseEntity.badRequest().body(violations.toString());
        } else {
            if (userService.findByEmail(user.getEmail()) != null) {
                return ResponseEntity.badRequest().body("Email already registered");
            }
            String externalId = UUID.randomUUID().toString();
            while (!userService.checkExternalIdUnique(externalId)) {
                externalId = UUID.randomUUID().toString();
            }
            user.setExternalId(externalId);
            user.setReferralLink(ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/referral_register?referrerExternalId=" + externalId);
            if (userService.save(user) != null ) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
    }

    @GetMapping("")
    public ResponseEntity<User> getUserByEmail(@RequestParam(name = "userEmail", required = true) String userEmail) {
        User user = userService.findByEmail(userEmail);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/referral_link")
    public ResponseEntity<String> getUserReferralLinkByEmail(@RequestParam(name = "userEmail", required = true) String userEmail) {
        User user = userService.findByEmail(userEmail);
        if (user != null) {
            return ResponseEntity.ok(user.getReferralLink());
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @PostMapping(path = "/referral_register", consumes = "application/json")
    public ResponseEntity<?> registerFromReferral(@RequestBody @NotNull User user, @RequestParam(name = "referrerExternalId", required = true) String referrerExternalId) {
        Set<ConstraintViolation<Object>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            return ResponseEntity.badRequest().body(violations.toString());
        } else {
            if (userService.findByEmail(user.getEmail()) != null) {
                return ResponseEntity.badRequest().body("Email already registered");
            }
            User referralUser = userService.findByExternalId(referrerExternalId);
            if (referralUser == null) {
                return ResponseEntity.badRequest().body("User doesn't exist for external id");
            }
            String externalId = UUID.randomUUID().toString();
            while (!userService.checkExternalIdUnique(externalId)) {
                externalId = UUID.randomUUID().toString();
            }
            user.setExternalId(externalId);
            user.setReferralLink(ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/referral_register?referrerExternalId=" + externalId);
            if (userService.saveUserReferral(user, referralUser) != null ) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
    }

}
