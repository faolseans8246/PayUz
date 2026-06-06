package com.example.payuz.security;

import com.example.payuz.entity.UserBase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class CustomerUserDetails implements UserDetails {

    private final UserBase userBase;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority(
                        "ROLE_" + userBase.getRole().name()
                )
        );
    }

    @Override
    public String getPassword() {
        return userBase.getPassword();
    }

    @Override
    public String getUsername() {
        return userBase.getPhoneNumber();
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
        return userBase.isPhoneVerified();
    }

    public UserBase getUserBase() {
        return userBase;
    }
}
