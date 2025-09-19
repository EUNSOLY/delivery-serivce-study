package org.delivery.storeadmin.domain.authorization;

import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.store.service.StoreService;
import org.delivery.storeadmin.domain.user.service.StoreUserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements UserDetailsService {
    private final StoreUserService storeUserService;
    private final StoreService storeService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var storeUserEntity = storeUserService.getRegisterUser(username);
        var storeEntity = storeService.getStoreWithThrow(storeUserEntity.get().getStoreId());

        return  storeUserEntity.map(it->{

            var userSession = UserSession.builder()
                    .userId(it.getId())
                    .email(it.getEmail())
                    .password(it.getPassword())
                    .status(it.getStatus())
                    .role(it.getRole())
                    .registeredAt(it.getRegisteredAt())
                    .unregisteredAt(it.getUnregisteredAt())
                    .storeId(storeEntity.getId())
                    .storeName(storeEntity.getName())
                    .build();

            return userSession;
        }).orElseThrow(()->new UsernameNotFoundException(username));
    }
}
