package com.project.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MajorSearchRequest {
    private String name;
    private Integer minDoctors;
    private Integer maxDoctors;
}
