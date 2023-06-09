package com.sahmyook.attendance.config.auth;
//시큐리티가 /login 주소 요청이 오면 낚아채서 로그인 진행
//로그인 진행 완료 되면 시큐리티 session 만들어주어야 함. (Security ContextHolder)
// 오브젝트 => Authentication 타입 객체이고 User 정보가 있어야 된다
// User 오브젝트 타입 => UserDetails 타입 객체

//Security Session => Authentication => UserDetails

import com.sahmyook.attendance.admin.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private User user;
    public PrincipalDetails(User user){
        this.user=user;
    }
    //해당 User의 권한을 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<GrantedAuthority>();
        collect.add(()->{return user.getRole();});
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getUserPw();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    }

    public Long getId(){
        return user.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;

    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
