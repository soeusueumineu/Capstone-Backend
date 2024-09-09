package com.capstone.service;

import com.capstone.domain.Gender;
import com.capstone.domain.Item.Bottom;
import com.capstone.domain.Item.Item;
import com.capstone.domain.Item.Top;
import com.capstone.domain.Member;
import com.capstone.domain.MemberSize;
import com.capstone.dto.ItemDetailDto;
import com.capstone.repository.ItemRepository;
import com.capstone.repository.MemberRepository;
import com.capstone.repository.MemberSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberSizeRepository memberSizeRepository;

    @Autowired
    private ItemRepository itemRepository;

    public List<ItemDetailDto> getRecommendedItem(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // 사용자의 상세 신체 정보 가져오기
        MemberSize memberSize = memberSizeRepository.findByMember(member)
                .orElseThrow(() -> new IllegalArgumentException("Member size not found"));

        // 모든 아이템 가져오기
        List<Item> items = itemRepository.findAll();

        // 사용자의 상체 정보와 가장 유사한 상의 3개 선택
        List<Item> topItems = items.stream()
                .filter(item -> item instanceof Top && item.getItemGender() == member.getGender())
                .sorted(Comparator.comparingDouble(item -> -calculateCosineSimilarity(memberSize, item)))
                .limit(3)
                .collect(Collectors.toList());

        // 사용자의 하체 정보와 가장 유사한 하의 3개 선택
        List<Item> bottomItems = items.stream()
                .filter(item -> item instanceof Bottom && item.getItemGender() == member.getGender())
                .sorted(Comparator.comparingDouble(item -> -calculateCosineSimilarity(memberSize, item)))
                .limit(3)
                .collect(Collectors.toList());

        // 선택된 상의와 하의를 합쳐서 추천 아이템 목록 생성
        List<Item> recommendedItems = new ArrayList<>(topItems);
        recommendedItems.addAll(bottomItems);

        // 추천 아이템을 ItemDetailDto로 변환하여 반환
        return recommendedItems.stream().map(this::toItemDetailDto).collect(Collectors.toList());
    }

    private double calculateCosineSimilarity(MemberSize memberSize, Item item) {
        double[] memberVector;
        double[] itemVector;

        if (item instanceof Top) {
            Top top = (Top) item;
            // 사용자의 상체 정보와 상의의 정보 비교
            memberVector = new double[]{memberSize.getSleeveLength(), memberSize.getShoulderWidth(), memberSize.getChestCrossSection(), memberSize.getTopLength()};
            itemVector = new double[]{top.getSleeveLength(), top.getShoulderWidth(), top.getChestCrossSection(), top.getLength()};
        } else if (item instanceof Bottom) {
            Bottom bottom = (Bottom) item;
            // 사용자의 하체 정보와 하의의 정보 비교
            memberVector = new double[]{memberSize.getBottomLength(), memberSize.getWaistWidth()};
            itemVector = new double[]{bottom.getLength(), bottom.getWaistWidth()};
        } else {
            // 지원되지 않는 아이템 유형
            return 0;
        }

        // 코사인 유사도 계산
        return cosineSimilarity(memberVector, itemVector);
    }

    private double cosineSimilarity(double[] vectorA, double[] vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }

        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    private ItemDetailDto toItemDetailDto(Item item) {
        Map<String, Double> sizeList = new HashMap<>();
        String size = null;

        if (item instanceof Top) {
            Top top = (Top) item;
            sizeList.put("length", top.getLength());
            sizeList.put("chestCrossSection", top.getChestCrossSection());
            sizeList.put("shoulderWidth", top.getShoulderWidth());
            sizeList.put("sleeveLength", top.getSleeveLength());
        } else if (item instanceof Bottom) {
            Bottom bottom = (Bottom) item;
            sizeList.put("length", bottom.getLength());
            sizeList.put("waistWidth", bottom.getWaistWidth());
        }

        return new ItemDetailDto(item.getId(), item.getCompany(), item.getImage(), item.getItemName(), item.getPrice(), item.getSiteUrl(), null, item.getSize(), sizeList);
    }
}
