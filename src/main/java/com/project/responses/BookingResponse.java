package com.project.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private Long id;
    private String createdDate;
    private String dateOfBirth;
    private String major;
    private String doctor;
    private String gmail;
    private String fullname;
    private String gender;
    private String note;
    private String phoneNumber;
    private String status;
    private String dateBooking;
    private String timeBooking;

}
