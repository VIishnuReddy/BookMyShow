package org.bookMyShow.factories;

import org.bookMyShow.enums.PaymentMethod;
import org.bookMyShow.services.paymentMethods.*;

public class PaymentFactory {

    public static PaymentService getPaymentService(PaymentMethod paymentMethod){
        return switch(paymentMethod){
            case CARD -> new CardPayment();
            case UPI -> new UpIPayment();
            case CASH -> new CashPayment();
            case WALLET -> new WalletPayment();
        };
    }
}
