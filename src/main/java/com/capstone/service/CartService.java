package com.capstone.service;

import com.capstone.domain.Cart;
import com.capstone.domain.CartItem;
import com.capstone.domain.Item.Item;
import com.capstone.domain.Member;
import com.capstone.dto.MemberSessionDto;
import com.capstone.exception.ErrorCode;
import com.capstone.exception.ErrorException;
import com.capstone.exception.ErrorResponse;
import com.capstone.repository.CartRepository;
import com.capstone.repository.ItemRepository;
import com.capstone.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.capstone.SessionFactory.SESSION_KEY;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void addCart(Long itemId, HttpSession session) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("Item not found"));
        Cart cart = getCurrentCart(session);

        CartItem cartItem = CartItem.createCartItem(cart, item);
        if(cart.getCartItems().contains(cartItem)){
            throw ErrorException.type(ErrorCode.ALREADY_IN_CART);
        }
        cart.addCartItem(cartItem);

        cartRepository.save(cart);
    }

    private Cart getCurrentCart(HttpSession session) {
        MemberSessionDto getSession = (MemberSessionDto) session.getAttribute(SESSION_KEY);
        Member member = memberRepository.findById(getSession.getId()).orElseThrow(() -> new IllegalArgumentException("No Cart"));
        return member.getCart();
    }

    @Transactional
    public void deleteItemFromCart(Long itemId, Long userId) {
        Optional<Member> member = memberRepository.findById(userId);
        Member findMember = member.get();
        Cart cart = findMember.getCart();
        cart.deleteCartItem(itemId);
    }
}
