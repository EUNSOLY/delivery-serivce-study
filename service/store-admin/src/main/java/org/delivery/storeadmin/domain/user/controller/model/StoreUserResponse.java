package org.delivery.storeadmin.domain.user.controller.model;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.db.storeuser.enums.StoreUserRole;
import org.delivery.db.storeuser.enums.StoreUserStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// 가맹점 유저 반환 정보
public class StoreUserResponse {
    private UserResponse user;
    private StoreResponse store;


    // 이너클레스 static으로 StoreUserResponse.으로 접근할수있도록 적용
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserResponse {
        private Long id;

        private String email;

        private StoreUserStatus status;

        private StoreUserRole role;

        private LocalDateTime registeredAt;

        private LocalDateTime unregisteredAt;

        private LocalDateTime lastLoginAt;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StoreResponse {
        private Long id;

        private String name;

    }
}


