package com.capstone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue
    @Column(name = "cart_id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @JsonIgnore
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    private int count;

    public static Cart createCart(Member member) {
        Cart cart = new Cart();
        cart.setMember(member);
        cart.setCount(0);
        return cart;
    }

    public void addCartItem(CartItem cartItem){
        cartItems.add(cartItem);
        cartItem.setCart(this);
        count++;
    }

    public void deleteCartItem(Long itemId){
        Iterator<CartItem> iterator = cartItems.iterator();
        while (iterator.hasNext()) {
            CartItem cartItem = iterator.next();
            if (Objects.equals(cartItem.getItem().getId(), itemId)) {
                iterator.remove();
                count--;
            }
        }
    }

}
