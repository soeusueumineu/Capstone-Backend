package com.capstone.controller;

import com.capstone.dto.ItemAllDto;
import com.capstone.dto.ItemResponseDto;
import com.capstone.page.PageInfo;
import com.capstone.repository.queryrepository.ItemQueryRepository;
import com.capstone.service.ItemService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ItemService itemService;

    @ResponseBody
    @GetMapping("/main")
    public ResponseEntity index(@Positive @RequestParam int page){
        Page<ItemResponseDto> allItemPage = itemService.findAllItem(page - 1, 5);
        PageInfo pageInfo = new PageInfo(page, 5,(int)allItemPage.getTotalElements(), allItemPage.getTotalPages());
        List<ItemResponseDto> items = allItemPage.getContent();
        return new ResponseEntity<>(new ItemAllDto<>(items, pageInfo), HttpStatus.OK);
    }


}
