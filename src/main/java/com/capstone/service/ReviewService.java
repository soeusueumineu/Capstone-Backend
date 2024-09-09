package com.capstone.service;

import com.capstone.domain.Item.Item;
import com.capstone.domain.Member;
import com.capstone.domain.Review;
import com.capstone.dto.MemberSessionDto;
import com.capstone.dto.ReviewRequestDto;
import com.capstone.repository.ItemRepository;
import com.capstone.repository.MemberRepository;
import com.capstone.repository.ReviewRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.capstone.SessionFactory.SESSION_KEY;


@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    public void addReview(ReviewRequestDto reviewRequestDto, HttpSession session, Long itemId){
        MemberSessionDto findedSession = (MemberSessionDto) session.getAttribute(SESSION_KEY);
        Optional<Member> member = memberRepository.findById(findedSession.getId());
            Optional<Item> item = itemRepository.findById(itemId);
            Review review = new Review();
            review.setMember(member.get());
            review.setItem(item.get());
            review.setTitle(reviewRequestDto.getTitle());
            review.setContent(reviewRequestDto.getContent());
            review.setStar(reviewRequestDto.getStar());
            reviewRepository.save(review);
    }

}
