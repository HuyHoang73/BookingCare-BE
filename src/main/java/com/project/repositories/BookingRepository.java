package com.project.repositories;

import com.project.models.Booking;
import com.project.requests.BookingSearchRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b WHERE " +
            "(:#{#dto.doctorId} IS NULL OR b.userBookingEntities.id = :#{#dto.doctorId}) AND " +
            "(:#{#dto.status} IS NULL OR b.status = :#{#dto.status}) AND " +
            "(:#{#dto.dateBookingFrom} IS NULL OR b.dateBooking >= :#{#dto.dateBookingFrom}) AND " +
            "(:#{#dto.dateBookingTo} IS NULL OR b.dateBooking <= :#{#dto.dateBookingTo})")
    List<Booking> searchBookings(@Param("dto") BookingSearchRequest bookingSearchRequest);

    @Query("SELECT b FROM Booking b WHERE b.dateBooking BETWEEN :startOfWeek AND :endOfWeek")
    List<Booking> getCalendar(@Param("startOfWeek") LocalDate startOfWeek,
                              @Param("endOfWeek") LocalDate endOfWeek);

}