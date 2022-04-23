package com.sososhopping.security;

import com.sososhopping.entity.member.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Component
@Getter @Builder
public class AuthMember implements UserDetails {

    private String memberType;
    private Long id;
    private AccountStatus accountStatus;
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority authority;

        if (this.memberType.equals("A")) {
            authority = new SimpleGrantedAuthority("ROLE_ADMIN");
        } else if(this.memberType.equals("U")){
            authority = new SimpleGrantedAuthority("ROLE_USER");
        } else if(this.memberType.equals("O")){
            authority = new SimpleGrantedAuthority("ROLE_OWNER");
        } else {
            throw new RuntimeException();
        }

        authorities.add(authority);
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return String.valueOf(id);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (accountStatus != null){
            if( accountStatus == AccountStatus.ACTIVE) {
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isAccountNonLocked();
    }
}
