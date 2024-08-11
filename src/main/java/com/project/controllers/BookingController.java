package com.project.controllers;

import com.project.dto.BookingDTO;
import com.project.models.Booking;
import com.project.repositories.BookingRepository;
import com.project.requests.BookingUpdateRequest;
import com.project.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/bookings")
public class BookingController {
    private final BookingService bookingService;
    private final BookingRepository bookingRepository;

    @PostMapping
    public ResponseEntity<?> createBooking(@Valid @RequestBody BookingDTO bookingDTO) {
        try{
            bookingService.createBooking(bookingDTO);
            return ResponseEntity.ok().body("Success");
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("confirm/{id}")
    public ResponseEntity<?> confirmBooking(@PathVariable("id") Long id,
                                            @RequestParam("token") String token) {
        try{
            Optional<Booking> booking = bookingRepository.findById(id);
            if(!booking.isPresent() || booking.get().getToken() == null || !booking.get().getToken().equals(token)) {
                return ResponseEntity.badRequest().body("Invalid path");
            }
            BookingUpdateRequest bookingUpdateRequest = new BookingUpdateRequest(id, "confirm");
            bookingService.updateBooking(bookingUpdateRequest);
            return ResponseEntity.ok().body("Success");
        }catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateBooking(@Valid @RequestBody BookingUpdateRequest bookingUpdateRequest) {
        try{
            bookingService.updateBooking(bookingUpdateRequest);
            return ResponseEntity.ok().body("Success");
        }catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
