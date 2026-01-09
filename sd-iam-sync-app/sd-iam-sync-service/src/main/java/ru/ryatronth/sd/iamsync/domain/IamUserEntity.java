package ru.ryatronth.sd.iamsync.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "iam_user")
public class IamUserEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "username", nullable = false, unique = true, length = 128)
    private String username;

    @Column(name = "email", length = 256)
    private String email;

    @Column(name = "phone", length = 64)
    private String phone;

    @Column(name = "first_name", length = 128)
    private String firstName;

    @Column(name = "last_name", length = 128)
    private String lastName;

    @Column(name = "patronymic", length = 128)
    private String patronymic;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "department_code", length = 128)
    private String departmentCode;

    @Column(name = "workplace_code", length = 128)
    private String workplaceCode;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "iam_user_role",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    @Column(name = "role", nullable = false, length = 128)
    private Set<String> roles = new HashSet<>();

}
