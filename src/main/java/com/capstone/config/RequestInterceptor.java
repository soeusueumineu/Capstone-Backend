package com.capstone.config;

import com.capstone.dto.MemberSessionDto;
import com.capstone.exception.ErrorCode;
import com.capstone.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Objects;

import static com.capstone.SessionFactory.SESSION_KEY;
import static com.capstone.exception.ErrorCode.AUTHENTICATION_USER;


@Slf4j
public class RequestInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        String requestURI = request.getRequestURI();
        log.info("요청 URI : {}", requestURI);

        HttpSession session = request.getSession(false);

        if (Objects.isNull(session) || Objects.isNull(session.getAttribute(SESSION_KEY))) {
            handleUnauthenticatedUser(response, objectMapper, HttpStatus.UNAUTHORIZED, AUTHENTICATION_USER);
            return false;
        }

        MemberSessionDto currentMember = (MemberSessionDto) session.getAttribute(SESSION_KEY);


        // URI에서 userId 또는 itemId를 추출
        Long id = null;
        if (requestURI.startsWith("/api/mypage/")) {
            id = extractIdFromURI(requestURI);
            if (!currentMember.getId().equals(id)) {
                handleUnauthenticatedUser(response, objectMapper, HttpStatus.UNAUTHORIZED, AUTHENTICATION_USER);
                return false;
            }
        }

        return true;
    }

    private Long extractIdFromURI(String requestURI) {
        String[] parts = requestURI.split("/");
        if (parts.length >= 4) {
            try {
                return Long.parseLong(parts[3]);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    private void handleUnauthenticatedUser(HttpServletResponse response, ObjectMapper objectMapper,
                                           HttpStatus httpStatus, ErrorCode errorCode) throws IOException {
        log.info("미인증 사용자 요청");
        String errorResponseToJSON = objectMapper.writeValueAsString(ErrorResponse.of(errorCode));

        response.setStatus(httpStatus.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(errorResponseToJSON);
        writer.flush();
    }
}
