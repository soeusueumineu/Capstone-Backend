package com.capstone.service;

import com.capstone.domain.Cart;
import com.capstone.domain.Gender;
import com.capstone.domain.Member;
import com.capstone.dto.MemberSessionDto;
import com.capstone.dto.SignUpDto;
import com.capstone.exception.ErrorException;
import com.capstone.repository.CartRepository;
import com.capstone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.capstone.exception.ErrorCode.*;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //회원가입
    @Transactional
    public Long signUp(SignUpDto request) {
        if(memberRepository.findByEmail(request.getEmail()).isPresent()){
            throw ErrorException.type(USER_ALREADY_JOIN);
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Member member = Member.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .username(request.getUsername())
                .gender(Gender.valueOf(request.getGender().toUpperCase()))
                .age(request.getAge())
                .height(request.getHeight())
                .weight(request.getWeight())
                .waist(request.getWaist())
                .build();
        Cart cart = Cart.createCart(member);
        cartRepository.save(cart);

        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    //로그인
    public MemberSessionDto login(String email, String password) {
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> ErrorException.type(USER_NOT_FOUND));
        if(!passwordEncoder.matches(password, findMember.getPassword())){
            throw ErrorException.type(WRONG_PASSWORD);
        }else{
            return new MemberSessionDto(findMember.getId(), findMember.getEmail(), findMember.getUsername());
        }
    }


    //모든 회원 조회
    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    //단일 회원 조회
    public Member findById(Long id){
        if(memberRepository.findById(id).isPresent()){
            return memberRepository.findById(id).get();
        }else{
            throw ErrorException.type(USER_NOT_FOUND);
        }
    }

    //회원정보 업데이트
    @Transactional
    public void updateMember(Long id, String loginId, String username){
        Optional<Member> findMember = memberRepository.findById(id);
        if(findMember.isPresent()){
            Member member = findMember.get();
            member.setEmail(loginId); //dirty checking
            member.setUsername(username);
        }else{
            throw ErrorException.type(USER_NOT_FOUND);
        }
    }

    //회원 삭제
    @Transactional
    public void deleteMember(Long id){
        memberRepository.deleteById(id);
    }
}

