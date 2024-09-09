package com.capstone.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDto {

    private String title;
    private String content;
    private int star;

}
