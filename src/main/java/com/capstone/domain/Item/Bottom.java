package com.capstone.domain.Item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("B")
@Getter
@Setter
public class Bottom extends Item{
    private double length;          //총장
    private double waistWidth;
}
