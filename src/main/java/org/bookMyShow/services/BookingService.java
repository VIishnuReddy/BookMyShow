package org.bookMyShow.services;


import org.bookMyShow.entities.*;
import org.bookMyShow.enums.BookingStatus;
import org.bookMyShow.enums.PaymentMethod;
import org.bookMyShow.enums.PaymentStatus;
import org.bookMyShow.factories.PaymentFactory;
import org.bookMyShow.services.paymentMethods.PaymentService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class BookingService {

    private SeatService seatService;
    private UserService userService;
    private ShowService showService;
    private PaymentService paymentService;

    public BookingService(SeatService seatService, ShowService showService, UserService userService) {
        this.seatService = seatService;
        this.showService = showService;
        this.userService = userService;
    }

    public Booking bookTickets(Long userId, Long showId, List<ShowSeat> showSeats, PaymentMethod paymentMethod){
        // validate user
      User user = userService.getUser(userId);
      if(user==null){
          throw new RuntimeException("User not found");
      }
      //validate show
      Show show = showService.getShow(showId);
      if(show==null){
          throw new RuntimeException("Show not found");
      }
      // check lock on seats
        boolean locked = seatService.lockSeats(userId, showId, showSeats);
        if(!locked){
            throw new RuntimeException("One or more seats are unavailable");
        }
        for(ShowSeat showSeat: showSeats){
            System.out.println(showSeat.getSeat().getRow()+
                    showSeat.getSeat().getSeatNumber()+ " locked until "+showSeat.getLockedUntil());
        }
        seatService.validateLock(userId,showSeats);
        // create booking
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setShowSeats(showSeats);
        booking.setBookingStatus(BookingStatus.PROCESSING);
        booking.setCreatedAt(LocalDateTime.now());
       BigDecimal totalAmount=BigDecimal.ZERO;
       for(ShowSeat showSeat: showSeats){
           totalAmount=totalAmount.add(showSeat.getSeat().getSeatType().getPrice());
       }
       booking.setAmount(totalAmount);

       // integrating payment

        PaymentService paymentService= PaymentFactory.getPaymentService(paymentMethod);
        Payment payment = paymentService.pay(booking);
        booking.setPayment(payment);
        if(payment.getPaymentStatus()== PaymentStatus.SUCCESS){
            booking.setBookingStatus(BookingStatus.SUCCESS);
            seatService.confirmSeats(showSeats);
        } else{
            booking.setBookingStatus(BookingStatus.FAILED);
            seatService.releaseSeats(userId,showId,showSeats);
        }
        return booking;

    }

    public void cancelTicket(Booking booking){
        if(booking.getBookingStatus()!=BookingStatus.SUCCESS){
            throw new RuntimeException("Only booked tickets can be cancelled");
        }
        booking.setCancelledAt(LocalDateTime.now());
        booking.setBookingStatus(BookingStatus.CANCELLED);
        seatService.releaseBookedTickets(booking.getShowSeats());
    }


}
