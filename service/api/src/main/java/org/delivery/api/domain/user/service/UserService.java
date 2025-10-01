package org.delivery.api.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.error.UserErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.db.user.UserEntity;
import org.delivery.db.user.UserRepository;
import org.delivery.db.user.enums.UserStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * User 도메인 로직을 처리하는 서비스
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserEntity register(UserEntity userEntity) {
        return Optional.ofNullable(userEntity)
                .map(it -> {
                    userEntity.setStatus(UserStatus.REGISTERED);
                    userEntity.setRegisteredAt(LocalDateTime.now());
                    return userRepository.save(userEntity);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "userService_회원가입 중 오류 발생(가입정보없음)"));

    }

    public UserEntity login(
            String email, String password
    ) {
        return getUserWithThrow(email, password);
    }

    ;
    /**
     * 존재하지않는다면 ApiException 발생할 것으로 반환 타입은 UserEntity로 작성
     *
     * @param email
     * @param password
     * @return
     */
    public UserEntity getUserWithThrow(String email, String password) {
        return Optional.ofNullable(userRepository.findFirstByEmailAndPasswordAndStatusOrderByIdDesc(
                        email,
                        password,
                        UserStatus.REGISTERED
                ))
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND, "유저가 존재하지 않습니다"));

    }

    public UserEntity getUserWithThrow(Long userId) {
        return Optional.ofNullable(userRepository.findFirstByIdAndStatusOrderByIdDesc(
                        userId,
                        UserStatus.REGISTERED
                ))
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND, "유저가 존재하지 않습니다"));
    }
}
