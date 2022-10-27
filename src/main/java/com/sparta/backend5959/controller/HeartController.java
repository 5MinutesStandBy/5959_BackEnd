package com.sparta.backend5959.controller;

import com.sparta.backend5959.security.MemberDetails;
import com.sparta.backend5959.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HeartController {

    private final HeartService heartService;

    @PostMapping("/heart/{board_id}")
    public void clickHeart(@PathVariable("board_id") Long boardId,
                           @AuthenticationPrincipal MemberDetails memberDetails) {
        heartService.heart(boardId, memberDetails.getMember());
    }
}