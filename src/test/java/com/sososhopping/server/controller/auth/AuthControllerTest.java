package com.sososhopping.server.controller.auth;

import com.sososhopping.common.error.Api400Exception;
import com.sososhopping.common.error.Api401Exception;
import com.sososhopping.controller.auth.AuthController;
import com.sososhopping.entity.member.Owner;
import com.sososhopping.common.dto.auth.request.OwnerLoginRequestDto;
import com.sososhopping.common.dto.auth.request.OwnerSignUpRequestDto;
import com.sososhopping.common.dto.auth.response.LoginResponseDto;
import com.sososhopping.repository.member.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthControllerTest {

    @Autowired EntityManager em;
    @Autowired
    AuthController authController;
    @Autowired OwnerRepository ownerRepository;

    @Test
    void ownerSignUp() {
        OwnerSignUpRequestDto dto = new OwnerSignUpRequestDto("1234@naver.com", "1234", "아무개", "01012345678");
        ResponseEntity response = authController.ownerSignUp(dto);

        em.flush();
        em.clear();

        Owner findOwner = ownerRepository.findByEmail(dto.getEmail()).orElseThrow(()->new RuntimeException("없는 엔티티입니다."));

        assertThat(findOwner.getEmail()).isEqualTo(dto.getEmail());
        System.out.println("findOwner.getEmail() = " + findOwner.getEmail());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void ownerSignUpFailed() {
        OwnerSignUpRequestDto dto1 = new OwnerSignUpRequestDto("5234@naver.com", "1234", "아무개", "01052345678");
        OwnerSignUpRequestDto dto2 = new OwnerSignUpRequestDto("5234@naver.com", "1234", "아무개", "01052345678");
        ResponseEntity response1 = authController.ownerSignUp(dto1);

        Api400Exception error = org.junit.jupiter.api.Assertions.assertThrows(Api400Exception.class, () ->
        {
            authController.ownerSignUp(dto2);
        });

        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(error.getMessage()).isEqualTo("중복된 아이디입니다");
    }

    @Test
    void ownerLogin() {
        OwnerSignUpRequestDto signUpDto = new OwnerSignUpRequestDto("3234@naver.com", "1234", "아무개", "01032345678");
        authController.ownerSignUp(signUpDto);

        OwnerLoginRequestDto loginDto = new OwnerLoginRequestDto("3234@naver.com", "1234");
        ResponseEntity<LoginResponseDto> response = authController.ownerLogin(loginDto);

        LoginResponseDto loginResponseDto = response.getBody();

        System.out.println("loginResponseDto = " + loginResponseDto.getToken());
    }

    @Test
    void ownerLoginFailed() {
        OwnerSignUpRequestDto signUpDto = new OwnerSignUpRequestDto("2234@naver.com", "1234", "아무개", "01022345678");
        authController.ownerSignUp(signUpDto);

        OwnerLoginRequestDto loginDto = new OwnerLoginRequestDto("223@naver.com", "1234");

        Api401Exception error = org.junit.jupiter.api.Assertions.assertThrows(Api401Exception.class, () ->
        {
        authController.ownerLogin(loginDto);
        });

    }
}