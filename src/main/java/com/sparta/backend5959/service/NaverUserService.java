//package com.sparta.backend5959.service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sparta.backend5959.entity.Member;
//import com.sparta.backend5959.repository.MemberRepository;
//import com.sparta.backend5959.security.Authority;
//import com.sparta.backend5959.security.MemberDetails;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.UUID;
//
//@Service
//public class NaverUserService {
//    private final PasswordEncoder passwordEncoder;
//    private final MemberRepository memberRepository;
//
//    @Autowired
//    public NaverUserService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
//        this.memberRepository = memberRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    public void naverLogin(String code) throws JsonProcessingException {
//        // 1. "인가 코드"로 "액세스 토큰" 요청
//        String accessToken = getAccessToken(code);
//
//        // 2. 토큰으로 카카오 API 호출
//        naverUserInfoDto naverUserInfo = getnaverUserInfo(accessToken);
//
//        // 3. 필요시에 회원가입
//        Member NaverUsers = registerNaverUserIfNeeded(naverUserInfo);
//
//        // 4. 강제 로그인 처리
//        forceLogin(NaverUsers);
//    }
//
//    private String getAccessToken(String code) throws JsonProcessingException {
//        // HTTP Header 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//        // HTTP Body 생성
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("grant_type", "authorization_code");
//        // 카카오 RestAPI
//        body.add("client_id", "");
//        body.add("redirect_uri", "");
//        body.add("code", code);
//
//        // HTTP 요청 보내기
//        HttpEntity<MultiValueMap<String, String>> naverTokenRequest =
//                new HttpEntity<>(body, headers);
//        RestTemplate rt = new RestTemplate();
//        ResponseEntity<String> response = rt.exchange(
//                "https://kauth.kakao.com/oauth/token",
//                HttpMethod.POST,
//                naverTokenRequest,
//                String.class
//        );
//
//        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
//        String responseBody = response.getBody();
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(responseBody);
//        return jsonNode.get("access_token").asText();
//    }
//
//    private naverUserInfoDto getnaverUserInfo(String accessToken) throws JsonProcessingException {
//        // HTTP Header 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + accessToken);
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        // HTTP 요청 보내기
//        HttpEntity<MultiValueMap<String, String>> naverUserInfoRequest = new HttpEntity<>(headers);
//        RestTemplate rt = new RestTemplate();
//        ResponseEntity<String> response = rt.exchange(
//                "https://kapi.kakao.com/v2/user/me",
//                HttpMethod.POST,
//                naverUserInfoRequest,
//                String.class
//        );
//
//        // 여기를 통해 카카오 JSON 데이터를 꺼내옴 = 네이버 구글도 여기만 바꾸면 정보 가져올 수 있음
//        String responseBody = response.getBody();
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(responseBody);
//        Long id = jsonNode.get("id").asLong();
//        String nickname = jsonNode.get("properties")
//                .get("nickname").asText();
//        String email = jsonNode.get("kakao_account")
//                .get("email").asText();
//
//        System.out.println("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);
//        return new naverUserInfoDto(id, nickname, email);
//    }
//
//    private Member registerNaverUserIfNeeded(naverUserInfoDto naverUserInfo) {
//        // DB 에 중복된 Kakao Id 가 있는지 확인
//        Long kakaoId = naverUserInfo.getId();
//        Member naverMember = memberRepository.findByKakaoId(kakaoId)
//                .orElse(null);
//        if (naverMember == null) {
//            // 카카오 사용자 이메일과 동일한 이메일을 가진 회원이 있는지 확인
//            String kakaoEmail = naverUserInfo.getEmail();
//            Member sameEmailMember = memberRepository.findByEmail(kakaoEmail).orElse(null);
//            if (sameEmailMember != null) {
//                naverMember = sameEmailMember;
//                // 기존 회원정보에 카카오 Id 추가
//                naverMember.setKakaoId(kakaoId);
//            } else {
//                // 신규 회원가입
//                // username: kakao nickname
//                String nickname = naverUserInfo.getNickname();
//
//                // password: random UUID
//                String password = UUID.randomUUID().toString();
//                String encodedPassword = passwordEncoder.encode(password);
//
//                // email: kakao email
//                String email = naverUserInfo.getEmail();
//                // role: 일반 사용자
//                Authority role = Authority.ROLE_USER;
//
//                naverMember = new Member(nickname, encodedPassword, role, kakaoId);
//            }
//
//            memberRepository.save(naverMember);
//        }
//        return naverMember;
//    }
//
//    private void forceLogin(Member naverMember) {
//        MemberDetails memberDetails = new MemberDetails(naverMember);
//        Authentication authentication = new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }
//}
