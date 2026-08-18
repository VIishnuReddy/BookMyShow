## BookMyShow — Movie Ticket Booking LLD

A Java-based low-level design implementation of a movie ticket booking system, focused on domain modelling, seat locking, booking lifecycle management and extensible payment processing.

### Key Engineering Problems Solved

* **Show-specific seat availability:** Modelled `ShowSeat` separately from `Seat` so availability and booking state are maintained per show.
* **Concurrent seat selection:** Implemented seat locking with ownership and lock-expiry tracking to prevent multiple users from booking the same available seat within the application instance.
* **Booking lifecycle:** Designed booking states covering processing, success, failure and cancellation.
* **Payment extensibility:** Used a `PaymentService` abstraction with a `PaymentFactory` to support different payment methods without coupling payment logic to the booking workflow.
* **Failure handling:** Released locked seats when payment fails and released booked seats when a booking is cancelled.
* **Separation of responsibilities:** Split movie, show, user, seat and booking operations into dedicated service classes.

### Design Concepts

* Object-Oriented Design
* SOLID Principles
* Service-based separation of responsibilities
* Factory Pattern
* Strategy-style payment abstraction
* State management
* Seat locking and expiration
* Domain modelling

### Core Flow

`User → Show Selection → Seat Lock → Booking Creation → Payment → Seat Confirmation`

On successful payment:

`LOCKED → BOOKED`

On payment failure:

`LOCKED → AVAILABLE`

On cancellation:

`BOOKED → AVAILABLE`

### Technology

* Java 22
* Lombok
* Maven

### Important Design Note

The current seat-locking mechanism uses JVM-level synchronization. In a distributed deployment with multiple application instances, this would need to be replaced or complemented by a distributed/database-level concurrency mechanism such as optimistic locking, pessimistic locking or distributed locking.
