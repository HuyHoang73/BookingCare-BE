package com.project.constants;

public enum BookingStatus {
    CONFIRMING("Chờ xác nhận"),
    PENDING("Chờ xử lý"),
    ACCEPTING("Đã xử lý"),
    SUCCESS("Thành công"),
    FAILURE("Không tới khám");

    private final String bookingStatusName;

    BookingStatus(String bookingStatusName) {
        this.bookingStatusName = bookingStatusName;
    }
}
