package com.capstone.dto;

import com.capstone.domain.Review;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ItemDetailDto {

    private Long id;
    private String company;
    private String image;
    private String itemName;
    private String price;
    private String siteUrl;
    private List<ItemReviewDto> itemReview;
    private String size;
    private Map<String, Double> sizeList;

    public ItemDetailDto(String company, String image, String itemName, String price, String siteUrl, String size) {
        this.company = company;
        this.image = image;
        this.itemName = itemName;
        this.price = price;
        this.siteUrl = siteUrl;
        this.size = size;
    }
    public ItemDetailDto(Long id, String company, String image, String itemName, String price, String siteUrl, String size) {
        this.id = id;
        this.company = company;
        this.image = image;
        this.itemName = itemName;
        this.price = price;
        this.siteUrl = siteUrl;
        this.size = size;
    }


    public ItemDetailDto(Long id, String company, String image, String itemName, String price, String siteUrl, List<ItemReviewDto> itemReview, String size, Map<String, Double> sizeList) {
        this.id = id;
        this.company = company;
        this.image = image;
        this.itemName = itemName;
        this.price = price;
        this.siteUrl = siteUrl;
        this.itemReview = itemReview;
        this.size = size;
        this.sizeList = sizeList;
    }
}
