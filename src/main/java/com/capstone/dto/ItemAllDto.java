package com.capstone.dto;

import com.capstone.page.PageInfo;
import lombok.Getter;

@Getter
public class ItemAllDto<T> {

    private T data;
    private PageInfo pageInfo;

    public ItemAllDto(T data, PageInfo pageInfo) {
        this.data = data;
        this.pageInfo = pageInfo;
    }
}
