package com.capstone.controller;

import com.capstone.dto.MemberSessionDto;
import com.capstone.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

import static com.capstone.SessionFactory.SESSION_KEY;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(false);
            MemberSessionDto dto = (MemberSessionDto) session.getAttribute(SESSION_KEY);
            fileService.saveFile(file, dto.getId());
            return ResponseEntity.ok("업로드 성공");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("업로드 실패");
        }
    }
}
