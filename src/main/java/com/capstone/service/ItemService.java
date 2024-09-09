package com.capstone.service;

import com.capstone.domain.Item.Item;
import com.capstone.dto.ItemDetailDto;
import com.capstone.dto.ItemResponseDto;
import com.capstone.repository.ItemRepository;
import com.capstone.repository.queryrepository.ItemQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemQueryRepository itemQueryRepository;
    private final ItemRepository itemRepository;

    public ItemDetailDto getItemDetails(long itemId) {
        return itemQueryRepository.findItemDetailDtos(itemId);
    }

    public Page<ItemResponseDto> findAllItem(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return itemQueryRepository.findSearchedItems("", pageRequest);
    }

    public Page<ItemResponseDto> findSearchItem(String content, int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return itemQueryRepository.findSearchedItems(content, pageRequest);
    }

    public Page<ItemResponseDto> findTopItem(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return itemQueryRepository.findTopCategory(pageRequest);
    }

    public Page<ItemResponseDto> findBottomItem(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return itemQueryRepository.findBottomCategory(pageRequest);
    }

    public Page<ItemResponseDto> findMenItem(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return itemQueryRepository.findManCategory(pageRequest);
    }

    public Page<ItemResponseDto> findWomenItem(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return itemQueryRepository.findWomenCategory(pageRequest);
    }

}
