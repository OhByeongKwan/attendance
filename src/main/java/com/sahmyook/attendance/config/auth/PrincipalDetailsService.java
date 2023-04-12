package com.sahmyook.attendance.config.auth;

import com.sahmyook.attendance.admin.dao.UserRepository;
import com.sahmyook.attendance.admin.domain.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

//시큐리티 설정에서 loginProcessingUrl("/login");
//login 요청이 오면 자동으로 UserDetailsService 타임인 loadUserByUsername 함수가 실행
@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userId = username;
        User userEntity = userRepository.findByUserId(userId);

        if(userEntity != null){
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}
