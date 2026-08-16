package org.bookMyShow.entities;

import lombok.Getter;
import lombok.Setter;
import org.bookMyShow.enums.SeatType;

@Getter
@Setter
public class Seat {
    private Long id;
    private String row;
    private String seatNumber;
    private SeatType seatType;
}
