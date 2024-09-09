package com.capstone.controller;


import com.capstone.dto.ItemResponseDto;
import com.capstone.dto.MemberSessionDto;
import com.capstone.dto.MyPageResponseDto;
import com.capstone.exception.ErrorCode;
import com.capstone.exception.ErrorException;
import com.capstone.service.MyPageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.capstone.SessionFactory.SESSION_KEY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/mypage")
    public MyPageResponseDto getMyPage(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Object recentProducts = session.getAttribute("recentProducts");
        List<ItemResponseDto> findRecentProducts = (List<ItemResponseDto>) recentProducts;
        MemberSessionDto dto = (MemberSessionDto) session.getAttribute(SESSION_KEY);
        return myPageService.getMyPage(dto.getId(), findRecentProducts);
    }
}
