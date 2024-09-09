package com.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponseDto {

    private Long id;
    private String image;
    private String itemName;
    private String price;
    private String company;
    private String size;

}
