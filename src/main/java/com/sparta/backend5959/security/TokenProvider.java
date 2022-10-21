package com.sparta.backend5959.security;

import com.sparta.backend5959.dto.TokenDto;
import com.sparta.backend5959.entity.Member;
import com.sparta.backend5959.repository.MemberRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000*60*30; // 밀리세컨드 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000*60*60*24*7; // 7일

    // DB에서 토큰 사용자를 확인할 예정이므로 멤버 리포지토리 인젝션함
    private final MemberRepository memberRepository;

    // 암호화된 키를 담을 객체 선언
    private final Key key;

    @Autowired
    public TokenProvider(@Value("#{environment['secret.key']}") String secretKey, MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        // 현재 정한 sercret_key는 BASE64 encoding으로 암호화 시켜놓은 상태이므로 풀어야함
        // Keys 메소드를 사용해 안전하게 암호화를 할 것이므로 byte[] 로 되어 있어야 함
        // io.jsonWebtoken.io.Decoders
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        // Keys : SecretKeys 및 KeyPairs를 안전하게 생성하기 위한 유틸리티 클래스
        // io.jsonwebtoken.security.Keys; 키 바이트 배열 길이가 256비트(32바이트) 미만인 경우 @throws WeakKeyException;
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 권한을 실어나를 토큰 Dto
    public TokenDto generateTokenDto(Authentication authentication) {
        String authorities = authentication
                .getAuthorities()
                        .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        // 인증 정보에서 권한들 꺼내와서 스트림으로 만든담에 (GrantedAuthority.s)-> s.getAuthority() 로 변형
        // authorities 문자열에 , 로 나눠담음

        long nowTime = new Date().getTime();

        // 토큰 구성 { Header.Payload.signature_sign }
        // 여기서 payload 부분 세팅 후 암호화

        // access token 설정
        Date accessTokenExpires = new Date(nowTime + ACCESS_TOKEN_EXPIRE_TIME);
        String acessToken = Jwts.builder()
                // paload 부분에 필드, 값 넣기
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(accessTokenExpires)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // refresh token 설정
        Date refreshTokenExpires = new Date(nowTime + REFRESH_TOKEN_EXPIRE_TIME);
        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(refreshTokenExpires)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(acessToken)
                .accessTokenExpiresIn(accessTokenExpires.getTime())
                .refreshToken(refreshToken)
                .build();

    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    // 복호화를 위한 키 입력
                    .build()
                    // 빌드
                    .parseClaimsJws(accessToken)
                    // payLoad 복호화 (xxxxx.yyyyy.zzzzz) -> (xxxxx.복호화.zzzzz)
                    // JWS (Json Web Signature) - 서버에서 인증을 근거로 인증정보를 서버의 private key로 서명 한것을 토큰화 한것
                    // 서버 시크릿 키 넣고 푸는 부분
                    .getBody();
                    // 풀었으면 가져옴
        } catch (ExpiredJwtException e) {
            return e.getClaims();
            // 토큰 기한이 만료되었을 경우
        }
    }

    // Jwt 필터를 통과했으면 Security라는 경비원이 지키고 있으므로 통과시켜줄 허가증을 발급받을 서비스 메소드가 핋요함
    // 복호화된 payLoad에서 유저 정보 획득 & DB랑 대조해서 맞으면 Security 인증용 토큰(허가증) 발급
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        // PAYLOAD에 키값 중에 "auth"가 없으면
        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다");
        }

        // UserDetails 인터페이스의 구현명세를 친절하게 구현한 User를 안쓰고 굳이 Members를 사용하기로 했기 때문에 확실히 다 바꿔줘야함
        // (심지어 빌더 패턴으로 유저 계정 정지, 조회, 잠금, 비밀번호 암호화(PasswordEncoding)까지 전부 구현되어있음. 노션 자료의 의도를 알 수가 없다)
        String username = claims.getSubject();
        // Controller 보다 훨씬 앞선 필터 최전선에서 Transaction이 일어나고 있는데 이게 맞나 싶기도
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + username ));
        // AccessToken PAYLOAD에서 가져온 이름과 DB에서 가져온 멤버 이름이 일치하면
        MemberDetails memberDetails = new MemberDetails(member);
        // Security의 UsernamePasswordAuthenticationToken이 구현 명세로 인정하는(Object principal) UserDetails 인터페이스의 양식에 맞춰
        return new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());
        // MemberDetails 객체로 만들어서 Security 인증용 Authentication 리턴
    }

    public boolean validateToken(String jwt) {
        try {
            Jwts.parserBuilder()
                    // jwts 빌더 분석
                    .setSigningKey(key)
                    // HMAC-SHA algorithm으로 암호화 시켰던 서버 시크릿 키 값 넣어주고
                    .build()
                    // 다시 필드 값 세팅
                    .parseClaimsJws(jwt);
                    // 세팅된 시크릿 키로 가장 겉껍데기 복호화
                    return true;
                    // 예외처리 안뜨고 복호화가 된거면 일단 뭐가 들어있긴 한것이므로 true 리턴

            // 예외처리 뜨면 Jwts에서 잦됬음을 감지해서 알려준 것이므로 에러 문구를 커스텀한다
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("JWT가 올바르게 구성되지 않았습니다");
        } catch (ExpiredJwtException e) {
            log.info("JWT의 유효시간이 초과되었습니다");
        } catch (UnsupportedJwtException e) {
            log.info("JWT의 형식이 일치 하지 않습니다");
        } catch (PrematureJwtException e) {
            log.info("이 토큰은 아직 유효한 토큰이 아닙니다. 활성화 시기를 확인해 주십시오");
        } catch (ClaimJwtException e) {
            log.info("Jwts의 PAYLOAD 분석에 실패했습니다");
        }
        return false;
    }
}
