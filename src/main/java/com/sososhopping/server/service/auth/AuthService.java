package com.sososhopping.server.service.auth;

import com.sososhopping.server.auth.JwtTokenProvider;
import com.sososhopping.server.common.dto.auth.request.*;
import com.sososhopping.server.common.error.Api400Exception;
import com.sososhopping.server.common.error.Api401Exception;
import com.sososhopping.server.entity.member.AccountStatus;
import com.sososhopping.server.entity.member.Admin;
import com.sososhopping.server.entity.member.Owner;
import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.repository.member.AdminRepository;
import com.sososhopping.server.repository.member.OwnerRepository;
import com.sososhopping.server.repository.member.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final AdminRepository adminRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * 점주 관련 인증
     */
    //점주 이메일 중복 확인
    @Transactional
    public boolean ownerSignUpValidation(String email){
        if(ownerRepository.existsByEmail(email))
            return false;
        else
            return true;
    }

    //점주 회원가입
    @Transactional
    public void ownerSignUp(OwnerSignUpRequestDto dto) {
        if(ownerRepository.existsByEmail(dto.getEmail())){
            throw new Api400Exception("중복된 아이디입니다");
        }

        //계정 저장
        Owner owner = Owner.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .phone(dto.getPhone())
                .active(AccountStatus.ACTIVE)
                .build();

        ownerRepository.save(owner);
    }

    //점주 로그인
    @Transactional
    public String ownerLogin(OwnerLoginRequestDto dto) {
        Owner owner = ownerRepository.findByEmail(dto.getEmail()).orElseThrow(() ->
                new Api401Exception("올바르지 않은 아이디입니다"));

        if(!passwordEncoder.matches(dto.getPassword(), owner.getPassword()))
            throw new Api401Exception("올바르지 않은 비밀번호입니다");

        return jwtTokenProvider.createToken("O", owner.getId());
    }

    /**
     * 고객 관련 인증
     */
    //고객 이메일 중복 확인
    @Transactional
    public boolean userSignUpValidation(String email) {
        if(userRepository.existsByEmail(email))
            return false;
        else
            return true;
    }

    //고객 닉네임 중복 확인
    @Transactional
    public boolean isDuplicateNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    //고객 회원가입
    @Transactional
    public void userSignUp(UserSignUpRequestDto dto) {
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new Api400Exception("중복된 아이디입니다");
        }

        //계정 저장
        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .phone(dto.getPhone())
                .nickname(dto.getNickname())
                .streetAddress(dto.getStreet())
                .detailedAddress(dto.getDetail())
                .active(AccountStatus.ACTIVE)
                .build();

        userRepository.save(user);
    }

    //고객 로그인
    @Transactional
    public String userLogin(UserLoginRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(() ->
                new Api401Exception("올바르지 않은 아이디입니다"));

        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword()))
            throw new Api401Exception("올바르지 않은 비밀번호입니다");

        return jwtTokenProvider.createToken("U", user.getId());
    }

    /**
     * 관리자 관련 인증
     */

    //관리자 회원가입
    @Transactional
    public void adminSignUp(AdminAuthRequestDto dto) {
        if(adminRepository.existsByNickname(dto.getNickname()))
            throw new Api400Exception("중복된 닉네임입니다");

        //계정 저장
        Admin admin = Admin.builder()
                .nickname(dto.getNickname())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        adminRepository.save(admin);
    }

    //관리자 로그인
    @Transactional
    public String adminLogin(AdminAuthRequestDto dto) {
        Admin admin = adminRepository.findByNickname(dto.getNickname()).orElseThrow(() ->
                new Api401Exception("올바르지 않은 닉네임입니다"));

        if (!passwordEncoder.matches(dto.getPassword(), admin.getPassword()))
            throw new Api401Exception("올바르지 않은 비밀번호입니다");

        return jwtTokenProvider.createToken("A", admin.getId());
    }
}
