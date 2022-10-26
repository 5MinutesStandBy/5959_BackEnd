//package com.sparta.backend5959.controller;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.sparta.backend5959.service.GoogleUserService;
//import com.sparta.backend5959.service.KakaoUserService;
//import com.sparta.backend5959.service.NaverUserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//public class AOuthController {
//
//    private final KakaoUserService kakaoUserService;
//    private final GoogleUserService googleUserService;
//    private final NaverUserService naverUserService;
//
//    // 카카오 로그인
//    @GetMapping("/user/kakao/callback")
//    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
//        kakaoUserService.kakaoLogin(code);
//        return "redirect:/";
//    }
//
//    // 구글 로그인
//    @GetMapping ("/user/google/callback")
//    public String googleLogin(@RequestParam String code) throws JsonProcessingException {
//        googleUserService.googleLogin(code);
//        return "redirect:/";
//    }
//
//    // 네이버 로그인
//    @GetMapping("/user/naver/callback")
//    public String naverLogin(@RequestParam String code) throws JsonProcessingException {
//        naverUserService.naverLogin(code);
//        return "redirect:/";
//    }
//
//    @GetMapping("/")
//    public String testAouth() {
//        return "index.html";
//    }
//
//}
