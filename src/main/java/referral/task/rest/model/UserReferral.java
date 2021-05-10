package referral.task.rest.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "user_referral")
public class UserReferral {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column (name = "user_id")
    private Long userId;
    @Column (name = "referral_user_id")
    private Long referralUserId;
    @Column(name = "created")
    private Long created;
}
