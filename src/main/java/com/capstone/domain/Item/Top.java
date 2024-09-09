package com.capstone.domain.Item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("T")
@Getter
@Setter
public class Top extends Item{
    private double length;          //총장
    private double shoulderWidth;
    private double chestCrossSection;
    private double sleeveLength;
}
