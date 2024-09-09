package com.capstone.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MyPageResponseDto {

    private int searchedProductsCount;
    private int myReviewsCount;
    private int shoppingCartCount;

    private String email;
    private String gender;
    private String age;
    private double height;
    private double weight;
    private double waist;

    private List<ItemResponseDto> searchedProducts;
    private List<ItemResponseDto> myReviewItems;
    private List<ItemResponseDto> shoppingCartItems;
}
