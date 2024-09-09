package com.capstone.service;

import com.capstone.domain.Cart;
import com.capstone.domain.CartItem;
import com.capstone.domain.Item.Item;
import com.capstone.domain.Member;
import com.capstone.domain.Review;
import com.capstone.dto.ItemResponseDto;
import com.capstone.dto.MyPageResponseDto;
import com.capstone.repository.ItemRepository;
import com.capstone.repository.MemberRepository;
import com.capstone.repository.ReviewRepository;
import com.capstone.repository.queryrepository.MemberCartReviewQueryRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberCartReviewQueryRepository memberCartReviewQueryRepository;

    @Transactional
    public MyPageResponseDto getMyPage(Long id, List<ItemResponseDto> recentProducts){
        Member member = memberCartReviewQueryRepository.findByIdWithReviewsAndCart(id);
        List<ItemResponseDto> reviewItems = new ArrayList<>();
        for (Review review : member.getReviews()) {
            Item item = review.getItem();
            ItemResponseDto itemResponseDto = new ItemResponseDto(item.getId(), item.getImage(), item.getItemName(), item.getPrice(), item.getCompany(), item.getSize());
            reviewItems.add(itemResponseDto);
        }

        List<ItemResponseDto> cartItems = new ArrayList<>();
        for(CartItem item : member.getCart().getCartItems()){
            Item finditem = item.getItem();
            ItemResponseDto itemResponseDto = new ItemResponseDto(finditem.getId(), finditem.getImage(), finditem.getItemName(), finditem.getPrice(), finditem.getCompany(), finditem.getSize());
            cartItems.add(itemResponseDto);
        }
        MyPageResponseDto myPageResponseDto = new MyPageResponseDto();
        myPageResponseDto.setEmail(member.getEmail());
        myPageResponseDto.setAge(member.getAge());
        myPageResponseDto.setGender(String.valueOf(member.getGender()));
        myPageResponseDto.setHeight(member.getHeight());
        myPageResponseDto.setWeight(member.getWeight());
        myPageResponseDto.setWaist(member.getWaist());
        myPageResponseDto.setMyReviewsCount(member.getReviews().size());
        myPageResponseDto.setShoppingCartCount(member.getCart().getCartItems().size());
        myPageResponseDto.setSearchedProductsCount(recentProducts.size());
        myPageResponseDto.setMyReviewItems(reviewItems);
        myPageResponseDto.setSearchedProducts(recentProducts);
        myPageResponseDto.setShoppingCartItems(cartItems);


        return myPageResponseDto;
    }
}
