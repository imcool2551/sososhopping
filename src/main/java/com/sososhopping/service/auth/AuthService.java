package com.sososhopping.service.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.sososhopping.domain.auth.repository.UserAuthRepository;
import com.sososhopping.common.dto.AuthToken;
import com.sososhopping.common.dto.auth.request.*;
import com.sososhopping.common.dto.auth.response.OwnerFindEmailResponseDto;
import com.sososhopping.common.error.Api400Exception;
import com.sososhopping.common.error.Api401Exception;
import com.sososhopping.common.error.Api404Exception;
import com.sososhopping.common.error.Api409Exception;
import com.sososhopping.entity.member.AccountStatus;
import com.sososhopping.entity.member.Admin;
import com.sososhopping.entity.member.Owner;
import com.sososhopping.repository.member.AdminRepository;
import com.sososhopping.repository.member.OwnerRepository;
import com.sososhopping.security.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserAuthRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final AdminRepository adminRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final FirebaseAuth firebaseAuth;

    /**
     * 점주 관련 인증
     */
    //점주 이메일 중복 확인
    @Transactional
    public boolean ownerSignUpValidation(String email){
        return !ownerRepository.existsByEmail(email);
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

        createFirebaseAccount("O" + owner.getId(), owner.getEmail(), owner.getName());
    }

    //점주 로그인
    @Transactional
    public AuthToken ownerLogin(OwnerLoginRequestDto dto) {
        Owner owner = ownerRepository.findByEmail(dto.getEmail()).orElseThrow(() ->
                new Api401Exception("올바르지 않은 아이디입니다"));

        if(!passwordEncoder.matches(dto.getPassword(), owner.getPassword()))
            throw new Api401Exception("올바르지 않은 비밀번호입니다");

        String apiToken = jwtTokenProvider.createToken("O", owner.getId());
        String firebaseToken = createFirebaseToken("O" + owner.getId());

        return new AuthToken(apiToken, firebaseToken);
    }

    @Transactional
    public OwnerFindEmailResponseDto findOwnerEmail(OwnerFindEmailRequestDto dto) {
        Owner owner = ownerRepository.findByNameAndPhone(dto.getName(), dto.getPhone())
                .orElseThrow(() -> new Api404Exception("일치하는 계정이 없습니다"));

        if(!owner.isActive()) {
            throw new Api400Exception("비활성화 계정입니다");
        }

        return new OwnerFindEmailResponseDto(owner.getEmail());
    }

    @Transactional
    public void findOwnerPassword(OwnerFindPasswordRequestDto dto) {
        Owner owner = ownerRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new Api404Exception("일치하는 계정이 없습니다"));

        if (!owner.credentialsMatch(dto.getName(), dto.getPhone())) {
            throw new Api404Exception("개인정보가 일치하지 않습니다");
        }

        if (!owner.isActive()) {
            throw new Api404Exception("비활성화 계정입니다");
        }
    }

    @Transactional
    public void changeOwnerPassword(OwnerChangePasswordRequestDto dto) {
        Owner owner = ownerRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new Api404Exception("일치하는 계정이 없습니다"));

        if (!owner.isActive()) {
            throw new Api404Exception("비활성화 계정입니다");
        }

        owner.changePassword(passwordEncoder.encode(dto.getPassword()));
    }

    @Transactional
    public void updateOwnerInfo(Long ownerId, OwnerUpdateInfoRequestDto dto) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new Api404Exception("일치하는 계정이 없습니다"));

        ownerRepository.findByPhone(dto.getPhone())
                .ifPresent(
                        existingOwner -> {
                            if (owner != existingOwner) {
                                throw new Api409Exception("이미 사용중인 번호입니다");
                            }
                        }
                );

        if (!passwordEncoder.matches(dto.getPassword(), owner.getPassword())) {
            throw new Api401Exception("비밀번호가 일치하지 않습니다");
        }

        owner.updateInfo(dto.getName(), dto.getPhone());
    }

    @Transactional
    public void updateOwnerPassword(Long ownerId, OwnerUpdatePasswordRequest dto) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new Api404Exception("일치하는 계정이 없습니다"));

        if (!passwordEncoder.matches(dto.getPassword(), owner.getPassword())) {
            throw new Api401Exception("비밀번호가 일치하지 않습니다");
        }

        String encodedNewPassword = passwordEncoder.encode(dto.getNewPassword());
        owner.updatePassword(encodedNewPassword);
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


    //Firebase 사용자 생성 및 저장
    private void createFirebaseAccount(String uid, String email, String name) {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setUid(uid)
                .setEmail(email)
                .setDisplayName(name);

        try {
            firebaseAuth.createUser(request);
        }catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
    }

    //Firebase 커스텀 토큰 생성 및 반환
    private String createFirebaseToken(String uid) {
        String firebaseToken = "";

        Map<String, Object> additionalClaims = new HashMap<>();
        additionalClaims.put("from", "sososhopApi");

        try {
            firebaseToken = firebaseAuth.createCustomToken(uid, additionalClaims);
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }

        return firebaseToken;
    }



}
