package com.sososhopping.server.service.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.sososhopping.server.auth.JwtTokenProvider;
import com.sososhopping.server.common.dto.AuthToken;
import com.sososhopping.server.common.dto.auth.request.*;
import com.sososhopping.server.common.error.Api400Exception;
import com.sososhopping.server.common.error.Api401Exception;
import com.sososhopping.server.common.error.Api404Exception;
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

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
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

    @Transactional
    public boolean isDuplicatePhone(String phone) {
        return userRepository.existsByPhone(phone);
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

        createFirebaseAccount("U" + user.getId(), user.getEmail(), user.getName());
    }

    //고객 로그인
    @Transactional
    public AuthToken userLogin(UserLoginRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(() ->
                new Api401Exception("올바르지 않은 아이디입니다"));

        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword()))
            throw new Api401Exception("올바르지 않은 비밀번호입니다");

        if (!user.isActive()) {
            throw new Api401Exception("이용이 정지된 사용자입니다");
        }

        String apiToken = jwtTokenProvider.createToken("U", user.getId());
        String firebaseToken = createFirebaseToken("U" + user.getId());

        return new AuthToken(apiToken, firebaseToken);
    }

    @Transactional
    public String findUserEmail(UserFindEmailDto dto) {
        User user = userRepository.findByNameAndPhone(dto.getName(), dto.getPhone())
                .orElseThrow(() -> new Api404Exception("존재하지 않는 유저입니다"));

        if (!user.isActive()) {
            throw new Api401Exception("이용이 정지된 사용자입니다");
        }

        return user.getEmail();
    }

    @Transactional
    public void findUserPassword(UserFindPasswordDto dto) {
        User user = userRepository.findByEmailAndNameAndPhone(dto.getEmail(), dto.getName(), dto.getPhone())
                .orElseThrow(() -> new Api404Exception("존재하지 않는 유저입니다"));

        if (!user.isActive()) {
            throw new Api401Exception("이용이 정지된 사용자입니다");
        }
    }

    @Transactional
    public void changeUserPassword(UserChangePasswordDto dto) {
        User user = userRepository.findByEmailAndNameAndPhone(dto.getEmail(), dto.getName(), dto.getPhone())
                .orElseThrow(() -> new Api404Exception("존재하지 않는 유저입니다"));

        if (!user.isActive()) {
            throw new Api401Exception("이용이 정지된 사용자입니다");
        }

        user.updatePassword(passwordEncoder.encode(dto.getPassword()));
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 유저입니다"));

        if (!user.withdrawable()) {
            throw new Api400Exception("진행중인 주문이 있습니다");
        }

        user.withdraw();
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
