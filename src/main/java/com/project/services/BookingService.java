package com.project.services;

import com.project.dto.BookingDTO;
import com.project.exceptions.ResourceAlreadyExitsException;
import com.project.requests.BookingUpdateRequest;

import javax.mail.MessagingException;

public interface BookingService {
    void createBooking(BookingDTO bookingDTO) throws ResourceAlreadyExitsException, MessagingException;
    void updateBooking(BookingUpdateRequest bookingUpdateRequest) throws MessagingException;
}
