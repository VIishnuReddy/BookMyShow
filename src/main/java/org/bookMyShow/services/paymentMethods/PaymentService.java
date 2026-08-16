package org.bookMyShow.services.paymentMethods;

import org.bookMyShow.entities.Booking;
import org.bookMyShow.entities.Payment;

public interface PaymentService {
    Payment pay(Booking booking);
}
