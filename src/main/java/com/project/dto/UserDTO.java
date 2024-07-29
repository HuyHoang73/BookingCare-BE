package com.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends BaseDTO{
    private String name;
    private String gmail;
    private String phoneNumber;
    private String avatar;
    private String address;
    private String dateOfBirth;
    private String description;
    private Integer experience;
    private Integer certification;
    private String degree;
    private String gender;
    private String ethnicity;
    private String identity;
    private String major;
}
