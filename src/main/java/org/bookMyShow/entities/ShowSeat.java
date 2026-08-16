package org.bookMyShow.entities;

import lombok.Getter;
import lombok.Setter;
import org.bookMyShow.enums.SeatStatus;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Setter
@Getter
public class ShowSeat {
    private Long id;
    private Seat seat;
    private Show show;
    private SeatStatus seatStatus;
    private LocalDateTime lockedUntil;
    private Long lockByUserId;
}
