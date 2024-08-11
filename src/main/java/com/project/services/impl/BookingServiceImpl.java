package com.project.services.impl;

import com.project.constants.BookingStatus;
import com.project.converters.BookingConverter;
import com.project.dto.BookingDTO;
import com.project.exceptions.DataNotFoundException;
import com.project.exceptions.ResourceAlreadyExitsException;
import com.project.models.*;
import com.project.repositories.*;
import com.project.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final MajorRepository majorRepository;
    private final DailyBookingRepository dailyBookingRepository;
    private final BookingConverter bookingConverter;
    private final TimeRepository timeRepository;

    @Override
    @Transactional
    public String createBooking(BookingDTO bookingDTO) throws ResourceAlreadyExitsException {
        Optional<User> optionalUser = userRepository.findById(bookingDTO.getDoctorId());
        if (!optionalUser.isPresent()) {
            throw new DataNotFoundException("Can not find doctor with id " + bookingDTO.getDoctorId());
        }

        Optional<Major> optionalMajor = majorRepository.findById(bookingDTO.getMajorId());
        if (!optionalMajor.isPresent()) {
            throw new DataNotFoundException("Can not find major with id " + bookingDTO.getMajorId());
        }

        //Kiểm tra bác sĩ có trùng lịch không
        LocalDate dateBooking = LocalDate.parse(bookingDTO.getDateBooking(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        DailyBooking dailyBooking = dailyBookingRepository.findByDoctorIdAndDateAndTimeId(bookingDTO.getDoctorId(), dateBooking, bookingDTO.getTimeBookingId());
        Booking booking = bookingConverter.fromBookingDTOToBooking(bookingDTO);
        if (dailyBooking == null) {
            //Lưu lịch khám
            booking.setStatus(BookingStatus.CONFIRMING);
            bookingRepository.save(booking);

            dailyBooking = new DailyBooking();
            dailyBooking.setDoctorId(bookingDTO.getDoctorId());
            dailyBooking.setDate(dateBooking);
            dailyBooking.setTimeId(bookingDTO.getTimeBookingId());
            dailyBooking.setCount(1);
            dailyBookingRepository.save(dailyBooking);
        }else if(dailyBooking.getCount() >= 3){
            throw new ResourceAlreadyExitsException("Lịch đặt bị trùng");
        } else {
            //Lưu lịch khám
            booking.setStatus(BookingStatus.CONFIRMING);
            bookingRepository.save(booking);

            dailyBooking.setCount(dailyBooking.getCount() + 1);
            dailyBookingRepository.save(dailyBooking);
        }

        Optional<Time> optionalTime = timeRepository.findById(bookingDTO.getTimeBookingId());
        StringBuilder sb = new StringBuilder();
        sb.append("<h1>Thư xác nhận lịch khám</h1>\n")
                .append("<p>Tên người khám: ").append(bookingDTO.getFullname()).append("</p>\n")
                .append("<p>Ngày sinh: ").append(bookingDTO.getDateOfBirth()).append("</p>\n")
                .append("<p>Số điện thoại: ").append(bookingDTO.getPhoneNumber()).append("</p>\n")
                .append("<p>Ngày khám: ").append(bookingDTO.getDateBooking()).append("</p>\n")
                .append("<p>Thòi gian khám: ").append(optionalTime.get().getStart()).append("h - ")
                .append(optionalTime.get().getEnd()).append("h</p>\n")
                .append("<p>Tên bác sĩ khám: ").append(optionalUser.get().getName()).append("</p>\n")
                .append("<p>Chuyên ngành: ").append(optionalMajor.get().getName()).append("</p>\n")
                .append("<h4>Hãy nhấn xác nhận để lịch khám được hoàn thành đăng ký.</h4>\n")
                .append("<a href='http://localhost:8080/api/confirm/").append(booking.getId())
                .append("?token=").append(booking.getToken())
                .append("' style='display:inline-block;padding:10px 20px;background-color:#1677ff;color:white;text-align:center;text-decoration:none;border-radius:5px;font-size:16px;'>")
                .append("<h2 style='margin:0;'>Xác nhận đặt lịch khám</h2></a>");
        return sb.toString();
    }

    @Override
    @Transactional
    public void updateBooking(Long id,BookingStatus status) {
        Optional<Booking> booking = bookingRepository.findById(id);
        if(booking.isPresent()) {
            booking.get().setStatus(status);
            if(status.equals(BookingStatus.CONFIRMING)) {
                booking.get().setToken(null);
            }
            bookingRepository.save(booking.get());
        }
    }
}
