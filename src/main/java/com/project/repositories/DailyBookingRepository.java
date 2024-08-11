package com.project.repositories;

import com.project.models.DailyBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface DailyBookingRepository extends JpaRepository<DailyBooking, Long> {
    DailyBooking findByDoctorIdAndDateAndTimeId(Long doctorId, LocalDate date, Long timeId);
}
