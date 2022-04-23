package com.sososhopping.service.auth;

import com.sososhopping.security.AuthMember;
import com.sososhopping.common.error.Api400Exception;
import com.sososhopping.common.error.Api500Exception;
import com.sososhopping.entity.member.Admin;
import com.sososhopping.entity.member.Owner;
import com.sososhopping.entity.user.User;
import com.sososhopping.repository.member.AdminRepository;
import com.sososhopping.domain.auth.repository.OwnerAuthRepository;
import com.sososhopping.domain.auth.repository.UserAuthRepository;
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
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails loadUserByUsername(String memberType, String id) throws UsernameNotFoundException {
        if (memberType.equals("A")) {
            Admin admin = adminRepository.findById(Long.parseLong(id)).orElseThrow(() -> new
                    Api400Exception("해당하는 사용자가 없습니다."));
            return new AuthMember("A", admin.getId(), null, admin.getPassword());
        } else if(memberType.equals("U")){
            User user = userRepository.findById(Long.parseLong(id)).orElseThrow(() -> new
                    Api400Exception("해당하는 사용자가 없습니다."));
            return new AuthMember("U", user.getId(), user.getActive(), user.getPassword());
        } else if(memberType.equals("O")){
            Owner owner = ownerRepository.findById(Long.parseLong(id)).orElseThrow(() -> new
                    Api400Exception("해당하는 사용자가 없습니다."));
            return new AuthMember("O", owner.getId(), owner.getActive(), owner.getPassword());
        } else {
            throw new Api500Exception("권한 부여 오류");
        }
    }
}
