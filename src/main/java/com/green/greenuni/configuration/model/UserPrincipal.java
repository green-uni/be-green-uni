package com.green.greenuni.configuration.model;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {
    private final JwtMember jwtMember;

    public long getLoginUserId() {
        return jwtMember.getLoginUserId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return List.of(); }

    @Override
    public @Nullable String getPassword() { return ""; }

    @Override
    public String getUsername() { return ""; }
}
