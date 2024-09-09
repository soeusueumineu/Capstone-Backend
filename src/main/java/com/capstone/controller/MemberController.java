package com.capstone.controller;

import com.capstone.domain.Item.Item;
import com.capstone.domain.Member;
import com.capstone.dto.*;
import com.capstone.exception.ErrorCode;
import com.capstone.exception.ErrorException;
import com.capstone.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.capstone.SessionFactory.SESSION_KEY;


@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signUp") //회원가입
    public ResponseEntity<Void> signUp(@RequestBody SignUpDto signUpDto){
        memberService.signUp(signUpDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ResponseBody
    @PostMapping("/login") //로그인 (request : loginId, password / response : (session)id, loginId, username)
    public LoginResponseDto login(@Valid @RequestBody LoginFormDto loginFormDto, HttpServletRequest request){
        MemberSessionDto memberSession = memberService.login(loginFormDto.getEmail(), loginFormDto.getPassword());
        List<ItemResponseDto> recentProducts = new ArrayList<>();
        HttpSession session = request.getSession();
        session.setAttribute(SESSION_KEY, memberSession);
        session.setAttribute("recentProducts", recentProducts);
        return new LoginResponseDto(memberSession.getId(), memberSession.getEmail(), memberSession.getUsername());
    }

    @GetMapping("/logout") //로그아웃
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(Objects.nonNull(session)){
            session.invalidate();
        }
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/update/{id}") //회원 정보 변경(이메일, 이름)
    public void updateMember(@RequestBody SignUpDto memberRequestDto, @PathVariable Long id){
        memberService.updateMember(
                id,
                memberRequestDto.getEmail(),
                memberRequestDto.getUsername()
        );
    }


    @GetMapping("/session-check")
    public MemberSessionDto sessionCheck(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (Objects.isNull(session) || Objects.isNull(session.getAttribute(SESSION_KEY))) {
            throw ErrorException.type(ErrorCode.AUTHENTICATION_USER);
        }

        return (MemberSessionDto) session.getAttribute(SESSION_KEY);
    }

}
