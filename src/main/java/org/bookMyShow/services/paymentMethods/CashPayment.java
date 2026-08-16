package org.bookMyShow.services.paymentMethods;

import org.bookMyShow.entities.Booking;
import org.bookMyShow.entities.Payment;

public class CashPayment implements PaymentService{
    @Override
    public Payment pay(Booking booking) {
        return null;
    }
}
