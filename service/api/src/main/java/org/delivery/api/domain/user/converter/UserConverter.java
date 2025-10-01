package org.delivery.api.domain.user.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Converter;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.model.User;
import org.delivery.db.user.UserEntity;

import java.util.Optional;

@Converter
@RequiredArgsConstructor
public class UserConverter {

    public UserEntity toEntity(UserRegisterRequest request){

        /**
         * request ApiException 발생
         */
        return Optional.ofNullable(request)
                .map(it->{
                    // 엔티티로 변환
                    return UserEntity.builder()
                            .name(request.getName())
                            .email(request.getEmail())
                            .password(request.getPassword())
                            .address(request.getAddress())
                            .build();
                }).orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT, "User registerRequest Null"));
    }

    public UserResponse toResponse(UserEntity userEntity) {
        return Optional.ofNullable(userEntity)
                .map(it->{
                    // Response로 변환
                    return  UserResponse.builder()
                            .id(userEntity.getId())
                            .name(userEntity.getName())
                            .email(userEntity.getEmail())
                            .status(userEntity.getStatus())
                            .address(userEntity.getAddress())
                            .registeredAt(userEntity.getRegisteredAt())
                            .unregisteredAt(userEntity.getUnregisteredAt())
                            .lastLoginAt(userEntity.getLastLoginAt())
                            .build();

                }).orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT, "user_toResponse에 userEntity Null"));

    }

    public UserResponse toResponse(User user) {
        return Optional.ofNullable(user)
                .map(it->{
                    // Response로 변환
                    return  UserResponse.builder()
                            .id(user.getId())
                            .name(user.getName())
                            .email(user.getEmail())
                            .status(user.getStatus())
                            .address(user.getAddress())
                            .registeredAt(user.getRegisteredAt())
                            .unregisteredAt(user.getUnregisteredAt())
                            .lastLoginAt(user.getLastLoginAt())
                            .build();

                }).orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT, "user_toResponse에 userEntity Null"));

    }
}
