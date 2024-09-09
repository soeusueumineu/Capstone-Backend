package com.capstone.controller;

import com.capstone.domain.Item.Item;
import com.capstone.dto.ItemAllDto;
import com.capstone.dto.ItemResponseDto;
import com.capstone.page.PageInfo;
import com.capstone.repository.ItemRepository;
import com.capstone.repository.queryrepository.ItemQueryRepository;
import com.capstone.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SearchController {

    private final ItemService itemService;

    @GetMapping("/men")
    public ResponseEntity findMenCategory(@RequestParam int page){
        Page<ItemResponseDto> menItem = itemService.findMenItem(page - 1, 12);
        PageInfo pageInfo = new PageInfo(page, 12,(int)menItem.getTotalElements(), menItem.getTotalPages());
        List<ItemResponseDto> items = menItem.getContent();
        return new ResponseEntity<>(new ItemAllDto<>(items, pageInfo), HttpStatus.OK);
    }

    @GetMapping("/women")
    public ResponseEntity findWomenCategory(@RequestParam int page){
        Page<ItemResponseDto> womenItem = itemService.findWomenItem(page - 1, 12);
        PageInfo pageInfo = new PageInfo(page, 12,(int)womenItem.getTotalElements(), womenItem.getTotalPages());
        List<ItemResponseDto> items = womenItem.getContent();
        return new ResponseEntity<>(new ItemAllDto<>(items, pageInfo), HttpStatus.OK);
    }

    @GetMapping("/top")
    public ResponseEntity findTopCategory(@RequestParam int page){
        Page<ItemResponseDto> topItem = itemService.findTopItem(page - 1, 12);
        PageInfo pageInfo = new PageInfo(page, 12,(int)topItem.getTotalElements(), topItem.getTotalPages());
        List<ItemResponseDto> items = topItem.getContent();
        return new ResponseEntity<>(new ItemAllDto<>(items, pageInfo), HttpStatus.OK);
    }

    @GetMapping("/bottom")
    public ResponseEntity findBottomCategory(@RequestParam int page){
        Page<ItemResponseDto> bottomItem = itemService.findBottomItem( page - 1, 12);
        PageInfo pageInfo = new PageInfo(page, 12,(int)bottomItem.getTotalElements(), bottomItem.getTotalPages());
        List<ItemResponseDto> items = bottomItem.getContent();
        return new ResponseEntity<>(new ItemAllDto<>(items, pageInfo), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity search(@RequestParam("content") String content, @RequestParam int page) {
        Page<ItemResponseDto> searchItem = itemService.findSearchItem(content, page - 1, 12);
        PageInfo pageInfo = new PageInfo(page, 12,(int)searchItem.getTotalElements(), searchItem.getTotalPages());
        List<ItemResponseDto> items = searchItem.getContent();
        return new ResponseEntity<>(new ItemAllDto<>(items, pageInfo), HttpStatus.OK);
    }
}
