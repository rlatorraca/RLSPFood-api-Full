package ca.com.rlsp.rlspfoodapi.domain.model;

import ca.com.rlsp.rlspfoodapi.domain.repository.CustomJpaRepository;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tbl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(name = "user_email", nullable = false)
    private String email;

    @Column(name = "user_password", nullable = true)
    private String password;

    @CreationTimestamp
    @Column(name = "user_created", nullable = true, columnDefinition = "datetime")
    private OffsetDateTime createdClient;

    @UpdateTimestamp
    @Column(name = "user_last_modified", nullable = false, columnDefinition = "datetime")
    private OffsetDateTime lastModifiedClient;

    @ManyToMany
    @JoinTable(name = "tbl_user_group",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns =@JoinColumn(name = "group_id")
    )
    private Set<Group> groups = new HashSet<>();

    /**
     * No more used.
     * There is Bcrypt matcher to check the password
    public boolean passwordMatches(String password) {

        return getPassword().equals(password);
    }

    public boolean passwordNotMatches(String password) {
        return !passwordMatches(password);
    }
    */


    public boolean detachGroup(Group group) {
        return getGroups().remove(group);
    }

    public boolean attachGroup(Group group) {
        return getGroups().add(group);
    }

    public boolean isNewUser() {
        return this.getId() == null;
    }

    }
