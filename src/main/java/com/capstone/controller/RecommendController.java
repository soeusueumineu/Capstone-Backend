package com.capstone.controller;

import com.capstone.domain.Member;
import com.capstone.domain.MemberSize;
import com.capstone.dto.ItemAllDto;
import com.capstone.dto.ItemDetailDto;
import com.capstone.dto.MemberSessionDto;
import com.capstone.page.PageInfo;
import com.capstone.service.MemberService;
import com.capstone.service.PythonService;
import com.capstone.service.RecommendService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import static com.capstone.SessionFactory.SESSION_KEY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RecommendController {
    private final RecommendService recommendService;
    private final PythonService pythonService;
    private final MemberService memberService;

    @Value("${upload-path}")
    private String uploadPath;

    @GetMapping("/recommend")
    public ResponseEntity getRecommendedItem(HttpServletRequest request) throws IOException, InterruptedException {
        HttpSession session = request.getSession(false);
        MemberSessionDto dto = (MemberSessionDto) session.getAttribute(SESSION_KEY);
        Member member = memberService.findById(dto.getId());
        String filePath = uploadPath + "/" +member.getMyImage();
        pythonService.setMemberSize(dto.getId(), filePath);
        List<ItemDetailDto> recommendedItem = recommendService.getRecommendedItem(dto.getId());
        return new ResponseEntity<>(new ItemAllDto<>(recommendedItem, new PageInfo(1, 6, 6, 1)), HttpStatus.OK);
    }
}
