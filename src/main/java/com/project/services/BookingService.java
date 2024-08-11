package com.project.services;

import com.project.constants.BookingStatus;
import com.project.dto.BookingDTO;
import com.project.exceptions.ResourceAlreadyExitsException;

public interface BookingService {
    String createBooking(BookingDTO bookingDTO) throws ResourceAlreadyExitsException;
    void updateBooking(Long id, BookingStatus status);
}
