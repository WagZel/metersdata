package com.waggi.metersdata.data.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.waggi.metersdata.Constants;
import com.waggi.metersdata.converters.UserSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;


@Entity
@Table(name = "user_info")
@JsonSerialize(using = UserSerializer.class)
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    private Long id;

    @Version
    private Long version;

    @Column(unique = true, nullable = false, length = 32)
    @NotNull @Size(min = 3, max = 32, message = "user name can not be shorter than 3 symbols")
    private String username;

    @Column(nullable = false, length = 128)
    @NotNull @Size(min = 6, max = 128)
    private String password;

    @Basic(optional = false)
    @NotNull
    private boolean enabled;

    @Basic(optional = false)
    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @NotNull
    private Set<UserRole> userRoles;

    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
    @NotNull
    protected Set<MetersData>  metersData;

    public enum UserRole {
        ROLE_ADMIN,
        ROLE_USER
    }

    public Long getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public Set<MetersData> getMetersData() {
        return metersData;
    }

    public void setMetersData(Set<MetersData> metersData) {
        this.metersData = metersData;
    }
}
