package com.sososhopping.server.auth;

import com.sososhopping.server.common.error.Api500Exception;
import com.sososhopping.server.service.auth.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${spring.jwt.secret-key}")
    private String secretKey;

    //토큰 기한 60분
    private long tokenValidTime = 1000L * 60 * 60;

    private final UserDetailsServiceImpl userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String memberType, Long pk) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(pk));
        claims.put("memberType", memberType);

        List<String> roles = new ArrayList<>();

        if (memberType.equals("A")) {
            roles.add("ROLE_ADMIN");
        } else if(memberType.equals("U")){
            roles.add("ROLE_USER");
        } else if(memberType.equals("O")){
            roles.add("ROLE_OWNER");
        } else {
            throw new Api500Exception("권한에 문제가 있습니다");
        }

        claims.put("roles", roles);

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    //인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getMemberType(token), this.getMemberPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //토큰에서 회원 id 추출
    public String getMemberPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    //토큰에서 회원 타입 추출
    public String getMemberType(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("memberType").toString();
    }

    //요청의 헤더에서 token 값 가져오기
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("TOKEN");
    }

    //토큰 유효성 확인 메소드
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
