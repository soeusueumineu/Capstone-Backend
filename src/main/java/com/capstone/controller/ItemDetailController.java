package com.capstone.controller;

import com.capstone.domain.Item.Item;
import com.capstone.dto.ItemDetailDto;
import com.capstone.dto.ItemResponseDto;
import com.capstone.repository.ItemRepository;
import com.capstone.service.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ItemDetailController {

    private final ItemService itemService;
    private final ItemRepository itemRepository;

    @GetMapping("/itemDetail/{id}")
    public ItemDetailDto itemDetail(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            List<ItemResponseDto> recentProducts = (List<ItemResponseDto>) session.getAttribute("recentProducts");
            Item item = itemRepository.findById(id).get();
            ItemResponseDto itemResponseDto = new ItemResponseDto(item.getId(), item.getImage(), item.getItemName(), item.getPrice(), item.getCompany(), item.getSize());

            if (!recentProducts.contains(itemResponseDto)) {
                recentProducts.add(0, itemResponseDto);
                if (recentProducts.size() > 5) {
                    recentProducts.remove(recentProducts.size() - 1);
                }
                session.setAttribute("recentProducts", recentProducts);
            }
        }
        return itemService.getItemDetails(id);
    }
}