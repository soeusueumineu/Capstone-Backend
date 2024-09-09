package com.capstone.repository;

import com.capstone.domain.Item.Item;
import com.capstone.dto.ItemResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByItemNameContaining(String itemName);

}
