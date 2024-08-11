package com.project.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingSearchRequest {
    private Long doctorId;
    private String status;
    private LocalDate dateBookingFrom;
    private LocalDate dateBookingTo;
}
