package com.sososhopping.server.service.auth;

import com.sososhopping.server.auth.AuthMember;
import com.sososhopping.server.entity.member.Admin;
import com.sososhopping.server.entity.member.Owner;
import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.repository.member.AdminRepository;
import com.sososhopping.server.repository.member.OwnerRepository;
import com.sososhopping.server.repository.member.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails loadUserByUsername(char memberType, String id) throws UsernameNotFoundException {
        if (memberType == 'A') {
            Admin admin = adminRepository.getById(Long.parseLong(id));
            return new AuthMember('A', admin.getId(), null, admin.getPassword());
        } else if(memberType == 'U'){
            User user = userRepository.getById(Long.parseLong(id));
            return new AuthMember('U', user.getId(), user.getActive(), user.getPassword());
        } else if(memberType == 'O'){
            Owner owner = ownerRepository.getById(Long.parseLong(id));
            return new AuthMember('O', owner.getId(), owner.getActive(), owner.getPassword());
        } else {
            //임시 오류
            throw new RuntimeException();
        }
    }
}
