package org.bookMyShow.entities;

import lombok.Getter;
import lombok.Setter;
import org.bookMyShow.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Booking {
    private Long id;
    private User user;
    private Show show;
    List<ShowSeat> showSeats;
    private BookingStatus bookingStatus;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private LocalDateTime cancelledAt;
    private Payment payment;

}
