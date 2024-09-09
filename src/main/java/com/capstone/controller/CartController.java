package com.capstone.controller;

import com.capstone.dto.MemberSessionDto;
import com.capstone.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.capstone.SessionFactory.SESSION_KEY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CartController {

    private final CartService cartService;

    @PostMapping("/addCart/{id}")
    public ResponseEntity<Void> addCart(@PathVariable Long id, HttpServletRequest request){
        cartService.addCart(id, request.getSession(false));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteCartItem/{itemId}")
    public ResponseEntity<Void> deleteItemFromCart(@PathVariable Long itemId, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        MemberSessionDto dto = (MemberSessionDto) session.getAttribute(SESSION_KEY);
        cartService.deleteItemFromCart(itemId, dto.getId());
        return ResponseEntity.ok().build();
    }

}
