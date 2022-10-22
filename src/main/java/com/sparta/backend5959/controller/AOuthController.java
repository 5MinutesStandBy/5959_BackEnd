package com.sparta.backend5959.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.backend5959.service.KakaoUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AOuthController {

    private final KakaoUserService kakaoUserService;

    // 카카오 로그인
    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        kakaoUserService.kakaoLogin(code);
        return "redirect:/";
    }

    @GetMapping("/")
    public String testAouth() {
        return "index.html";
    }
    
}
