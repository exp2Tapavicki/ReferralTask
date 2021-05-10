package referral.task.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column (name = "email")
    private String email;
    @Column (name = "password")
    private String password;
    @Column (name = "external_id")
    private String externalId;
    @Column (name = "referral_link")
    private String referralLink;
    @Column(name = "credit")
    private Long credit = 0L;


    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}
