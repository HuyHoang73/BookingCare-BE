package com.project.models;

import com.project.constants.BookingStatus;
import com.project.constants.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "booking")
public class Booking extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname", nullable = false, length = 50)
    private String fullname;

    @Column(name = "dateofbirth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "gmail", nullable = false)
    private String gmail;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "note", nullable = false)
    private String note;

    @Column(name = "major_id")
    private Long majorId;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Column(name = "token")
    private String token;

    @Column(name = "denyreason")
    private String denyReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userBookingEntities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_id")
    private Time timeBookingEntities;

}
