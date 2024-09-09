package com.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ItemReviewDto {
    private String title;
    private String content;
    private int star;
    private String username;

    public ItemReviewDto(String username, String title, String content, int star) {
        this.username = username;
        this.title = title;
        this.content = content;
        this.star = star;
    }
}
