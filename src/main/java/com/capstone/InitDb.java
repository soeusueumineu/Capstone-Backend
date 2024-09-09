//package com.capstone;
//
//import com.capstone.domain.*;
//import com.capstone.domain.Item.Bottom;
//import com.capstone.domain.Item.Item;
//import com.capstone.domain.Item.Top;
//import jakarta.annotation.PostConstruct;
//import jakarta.persistence.EntityManager;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class InitDb {
//
//    private final InitService initService;
//    @PostConstruct
//    public void init(){
//        initService.initDB();
//    }
//
//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    static class InitService{
//
//        private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        private final EntityManager em;
//
//        public void initDB(){
//            // Create sample Member
//            Member member1 = new Member();
//            member1.setEmail("testMember@google.com");
//            member1.setPassword(passwordEncoder.encode("mypassword"));
//            member1.setUsername("홍길동");
//            member1.setAge(25);
//            member1.setGender(Gender.MALE);
//            Cart cart1 = Cart.createCart(member1);
//            em.persist(cart1);
//            em.persist(member1);
//
//            Member member2 = new Member();
//            member2.setEmail("member2@naver.com");
//            member2.setPassword(passwordEncoder.encode("gogogo"));
//            member2.setUsername("김나연");
//            member2.setAge(20);
//            member2.setGender(Gender.FEMALE);
//            Cart cart2 = Cart.createCart(member2);
//            em.persist(cart2);
//            em.persist(member2);
//
//
//            // Create sample Bottom
//            Bottom bottom = new Bottom();
//            bottom.setCompany("BottomCompany");
//            bottom.setItemName("이쁜청바지");
//            bottom.setPrice(65000);
//            bottom.setSiteUrl("https://bottomurl.com");
//            bottom.setItemGender(Gender.MALE);
//            bottom.setImage("bottom_image_url");
//            bottom.setLength(100.0);
//            bottom.setWaistCircum(80.0);
//            bottom.setHipCircum(100.0);
//            bottom.setThighCircum(60.0);
//            bottom.setInseam(75.0);
//            bottom.setLegOpeningCircum(40.0);
//            em.persist(bottom);
//
//            // Create sample Top
//            Top top = new Top();
//            top.setCompany("TopCompany");
//            top.setItemName("이쁜바람막이");
//            top.setPrice(127000);
//            top.setSiteUrl("https://topurl.com");
//            top.setItemGender(Gender.FEMALE);
//            top.setImage("top_image_url");
//            top.setLength(70.0);
//            top.setShoulderWidth(40.0);
//            top.setChestWidth(90.0);
//            top.setSleeveLength(60.0);
//            em.persist(top);
//
//            // Create sample Reviews
//
//            Review review1 = new Review();
//            review1.setTitle("미친듯한 청바지");
//            review1.setContent("청바지 너무 이뻐요 ㅠㅠ");
//            review1.setMember(member1);
//            review1.setStar(5);
//            review1.setItem(bottom);
//            em.persist(review1);
//            Review review2 = new Review();
//            review2.setTitle("아쉬운 바람막이");
//            review2.setContent("실물이 왜이래요");
//            review2.setMember(member1);
//            review2.setStar(2);
//            review2.setItem(top);
//            em.persist(review2);
//            Review review3 = new Review();
//            review2.setTitle("야무진 청바지");
//            review2.setContent("쫀쫀해요");
//            review2.setMember(member2);
//            review2.setStar(4);
//            review2.setItem(bottom);
//            em.persist(review3);
//
//            List<Review> itemReviewBottom = new ArrayList<>();
//            itemReviewBottom.add(review1);
//            List<Review> itemReviewTop = new ArrayList<>();
//            itemReviewTop.add(review2);
//            itemReviewTop.add(review3);
//
//            bottom.setItemReview(itemReviewBottom);
//            top.setItemReview(itemReviewTop);
//
//            // Create sample CartItem
//            Cart cart = member1.getCart();
//            System.out.println(cart);
//            CartItem cartItem1 = CartItem.createCartItem(cart1, bottom);
//            CartItem cartItem2 = CartItem.createCartItem(cart1, top);
//            cart.addCartItem(cartItem1);
//            cart.addCartItem(cartItem2);
//
//
//        }
//    }
//}
