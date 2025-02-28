package com.vi5hnu.gobetrotter_api.security;

import com.vi5hnu.gobetrotter_api.Entity.user.UserModel;
import com.vi5hnu.gobetrotter_api.services.user.UserRepository;
import com.vi5hnu.gobetrotter_api.specifications.UserSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserModel userModel=userRepository.findOne(UserSpecifications.activeUserById(userId)).orElseThrow(()->new UsernameNotFoundException("user does not exists."));
        return new SecurityUserDetails(userModel);
    }
}