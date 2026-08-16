package org.bookMyShow.services.paymentMethods;

import org.bookMyShow.entities.Booking;
import org.bookMyShow.entities.Payment;
import org.bookMyShow.enums.PaymentMethod;
import org.bookMyShow.enums.PaymentStatus;

import java.time.LocalDateTime;

public class UpIPayment implements PaymentService{
    @Override
    public Payment pay(Booking booking) {
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(booking.getAmount());
        payment.setPaymentMethod(PaymentMethod.UPI);
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setCreatedAt(LocalDateTime.now());
        return payment;
    }
}
