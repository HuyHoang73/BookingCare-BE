package com.project.converters;

import com.project.dto.BookingDTO;
import com.project.models.Booking;
import com.project.models.Major;
import com.project.models.Time;
import com.project.models.User;
import com.project.repositories.TimeRepository;
import com.project.repositories.UserRepository;
import com.project.responses.MajorResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BookingConverter {
    private final ModelMapper modelMapper;
    private final TimeRepository timeRepository;
    private final UserRepository userRepository;
    /**
     * Phương thức này để chuyển đổi từ bookingDTO sang BookingEntity
     * @param bookingDTO - dưới dạng Entity
     * @return dưới dạng Response
     */
    public Booking fromBookingDTOToBooking(BookingDTO bookingDTO) {
        modelMapper.typeMap(BookingDTO.class, Booking.class)
                .addMappings(mapper -> mapper.skip(Booking::setId));
        Booking booking = modelMapper.map(bookingDTO, Booking.class);
        Optional<User> user = userRepository.findById(bookingDTO.getDoctorId());
        booking.setUserBookingEntities(user.get());
        Optional<Time> time = timeRepository.findById(bookingDTO.getTimeBookingId());
        booking.setTimeBookingEntities(time.get());
        booking.setId(bookingDTO.getTimeBookingId());
        LocalDate dateBooking = LocalDate.parse(bookingDTO.getDateBooking(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        booking.setDateBooking(dateBooking);
        LocalDate dateOfBirth = LocalDate.parse(bookingDTO.getDateOfBirth(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        booking.setDateOfBirth(dateOfBirth);
        booking.setToken(UUID.randomUUID().toString());
        return booking;
    }
}
