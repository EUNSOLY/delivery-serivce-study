package org.delivery.api.domain.user.model;

import lombok.*;
import org.delivery.db.user.enums.UserStatus;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class User {

    private Long id;

    private String name;

    private String email;

    private String password;

    private UserStatus status;

    private String address;

    private LocalDateTime registeredAt;

    private LocalDateTime unregisteredAt;

    private LocalDateTime lastLoginAt;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserStatus getStatus() {
        return status;
    }

    public String getAddress() {
        return address;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public LocalDateTime getUnregisteredAt() {
        return unregisteredAt;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    public void setUnregisteredAt(LocalDateTime unregisteredAt) {
        this.unregisteredAt = unregisteredAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }
}
