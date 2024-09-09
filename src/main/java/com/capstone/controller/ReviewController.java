package com.capstone.controller;

import com.capstone.dto.ReviewRequestDto;
import com.capstone.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/addReview/{id}")
    public ResponseEntity<Void> addReview(@PathVariable Long id, @RequestBody ReviewRequestDto reviewRequestDto, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        reviewService.addReview(reviewRequestDto, session, id);
        return ResponseEntity.ok().build();
    }
}
