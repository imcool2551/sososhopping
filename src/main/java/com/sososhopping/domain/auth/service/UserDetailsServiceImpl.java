package com.sososhopping.domain.auth.service;

import com.sososhopping.common.exception.Api500Exception;
import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.common.security.AuthMember;
import com.sososhopping.domain.auth.repository.AdminAuthRepository;
import com.sososhopping.domain.auth.repository.OwnerAuthRepository;
import com.sososhopping.domain.auth.repository.UserAuthRepository;
import com.sososhopping.entity.admin.Admin;
import com.sososhopping.entity.owner.Owner;
import com.sososhopping.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserAuthRepository userRepository;
    private final OwnerAuthRepository ownerRepository;
    private final AdminAuthRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails loadUserByUsername(String memberType, String id) throws UsernameNotFoundException {
        if (memberType.equals("A")) {
            Admin admin = adminRepository.findById(Long.parseLong(id))
                    .orElseThrow(UnAuthorizedException::new);

            return new AuthMember("A", admin.getId(), null, admin.getPassword());
        } else if (memberType.equals("U")){
            User user = userRepository.findById(Long.parseLong(id))
                    .orElseThrow(UnAuthorizedException::new);

            return new AuthMember("U", user.getId(), user.getActive(), user.getPassword());
        } else if (memberType.equals("O")){
            Owner owner = ownerRepository.findById(Long.parseLong(id))
                    .orElseThrow(UnAuthorizedException::new);

            return new AuthMember("O", owner.getId(), owner.getActive(), owner.getPassword());
        } else {
            throw new Api500Exception("권한 부여 오류");
        }
    }
}
