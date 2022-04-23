package com.sososhopping.controller.auth;

import com.sososhopping.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

//    @PostMapping("/api/v1/admin/auth/login")
//    public ResponseEntity adminLogin(@ModelAttribute AdminLoginDto dto) {
//        String token = authService.adminLogin(dto);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(new LoginResponseDto(token, null));
//    }

}

