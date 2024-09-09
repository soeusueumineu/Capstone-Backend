package com.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {
    private String email;
    private String password;
    private String username;
    private String gender;
    private String age;
    private double height;
    private double weight;
    private double waist;

}
