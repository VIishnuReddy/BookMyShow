package org.bookMyShow;

import org.bookMyShow.entities.*;
import org.bookMyShow.enums.PaymentMethod;
import org.bookMyShow.enums.SeatStatus;
import org.bookMyShow.enums.SeatType;
import org.bookMyShow.factories.PaymentFactory;
import org.bookMyShow.services.BookingService;
import org.bookMyShow.services.SeatService;
import org.bookMyShow.services.ShowService;
import org.bookMyShow.services.UserService;
import org.bookMyShow.services.paymentMethods.PaymentService;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Vishnu");

        User user2 = new User();
        user2.setId(2L);
        user1.setName("Ashok");

        // Movie
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Avengers");

        // Create seats
        Seat seat1 = new Seat();
        seat1.setId(1L);
        seat1.setRow("A");
        seat1.setSeatNumber("1");
        seat1.setSeatType(SeatType.REGULAR);

        Seat seat2 = new Seat();
        seat2.setId(2L);
        seat2.setRow("A");
        seat2.setSeatNumber("2");
        seat2.setSeatType(SeatType.GOLD);

        // Create ShowSeat
        ShowSeat showSeat1 = new ShowSeat();
        showSeat1.setSeat(seat1);
        showSeat1.setSeatStatus(SeatStatus.AVAILABLE);

        ShowSeat showSeat2 = new ShowSeat();
        showSeat2.setSeat(seat2);
        showSeat2.setSeatStatus(SeatStatus.AVAILABLE);

        List<ShowSeat> selectedSeats =
                List.of(showSeat1, showSeat2);

        // Create Show
        Show show = new Show();
        show.setId(1L);
        show.setMovie(movie);

        // Add everything to your repositories/services
        // according to how you've implemented them.

        UserService userService = new UserService();
        SeatService seatService = new SeatService();
        ShowService showService = new ShowService();
        PaymentService paymentService = PaymentFactory.getPaymentService(PaymentMethod.UPI);

        // Book
        BookingService bookingService = new BookingService(seatService, showService,userService);
        userService.addUser(user1);
        userService.addUser(user2);
        showService.addShow(show);

        //adding concurrency now 2 users book at same time
        Thread t1= new Thread(()-> {
            try{
                Booking booking = bookingService.bookTickets(
                        user1.getId(),
                        show.getId(),
                        selectedSeats,
                        PaymentMethod.UPI
                );
                System.out.println("User1 Booking: "+ booking.getBookingStatus());
            }catch(Exception e){
                System.out.println("User 1 Failed "+ e.getMessage());
            }
        });

        Thread t2= new Thread(()-> {
            try{
                Booking booking = bookingService.bookTickets(
                        user2.getId(),
                        show.getId(),
                        selectedSeats,
                        PaymentMethod.UPI
                );
                System.out.println("User2 Booking: "+ booking.getBookingStatus());
            }catch(Exception e){
                System.out.println("User 2 Failed "+ e.getMessage());
            }
        });


//        System.out.println("Booking ID: " + booking.getId());
//        System.out.println("Booked by: "
//                + booking.getUser().getName());
//        System.out.println(booking.getCreatedAt());
//        System.out.println("Booking Status: "
//                + booking.getBookingStatus());
//        System.out.println("Amount: "
//                + booking.getAmount());
//
//       for(int i=0;i<selectedSeats.size();i++){
//           System.out.println(selectedSeats.get(i).getSeat().getRow()+
//                   selectedSeats.get(i).getSeat().getSeatNumber()+" "
//                   +selectedSeats.get(i).getSeatStatus());
//       }
//        Payment payment = paymentService.pay(booking);
//        System.out.println("Payment Method: "
//                + booking.getPayment().getPaymentMethod() +" -> "+
//                booking.getPayment().getPaymentStatus());
//
//        bookingService.cancelTicket(booking);
//        System.out.println(booking.getBookingStatus());
//        System.out.println("booking cancelled at "+ booking.getCancelledAt());
//        for (ShowSeat selectedSeat : selectedSeats) {
//            System.out.println(selectedSeat.getSeat().getRow() +
//                    selectedSeat.getSeat().getSeatNumber() + " "
//                    + selectedSeat.getSeatStatus());
//        }

        t1.start();
        t2.start();
        t1.join();
        t2.join();


    }
}