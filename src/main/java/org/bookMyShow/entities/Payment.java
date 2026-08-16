package org.bookMyShow.entities;

import lombok.Getter;
import lombok.Setter;
import org.bookMyShow.enums.PaymentMethod;
import org.bookMyShow.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Payment {
    private Long id;
    private Booking booking;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private BigDecimal amount;
    private LocalDateTime createdAt;
}
