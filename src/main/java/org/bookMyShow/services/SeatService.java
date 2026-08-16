package org.bookMyShow.services;



import org.bookMyShow.entities.ShowSeat;
import org.bookMyShow.enums.SeatStatus;

import java.time.LocalDateTime;
import java.util.List;

public class SeatService {

    private static final long LOCK_DURATION_MINUTES=2;
    public List<ShowSeat> getSeats(Long showId){
        return null;
    }

    public synchronized boolean lockSeats(Long userId, Long ShowId,
                             List<ShowSeat> showSeats){
        LocalDateTime lockedUntil =LocalDateTime.now().plusSeconds(10);
        for(ShowSeat showSeat: showSeats){
            if(showSeat.getSeatStatus()!=SeatStatus.AVAILABLE){
                return false;
            }
        }
        for(ShowSeat showSeat: showSeats){
            showSeat.setSeatStatus(SeatStatus.LOCKED);
            showSeat.setLockedUntil(lockedUntil);
            showSeat.setLockByUserId(userId);
        }
        return true;
    }

    public void releaseSeats(Long userId, Long showId, List<ShowSeat> showSeats){
        for(ShowSeat showSeat: showSeats){
            if(showSeat.getSeatStatus()==SeatStatus.LOCKED && showSeat.getLockByUserId().equals(userId)){
                showSeat.setSeatStatus(SeatStatus.AVAILABLE);
                showSeat.setLockedUntil(null);
                showSeat.setLockByUserId(null);
            }

        }
    }

    public void releaseExpiredSeats(List<ShowSeat> showSeats){
        LocalDateTime now= LocalDateTime.now();
        synchronized (showSeats){
            for(ShowSeat showSeat: showSeats){
                if(showSeat.getSeatStatus() == SeatStatus.LOCKED && showSeat.getLockedUntil().isBefore(now)){
                    showSeat.setSeatStatus(SeatStatus.AVAILABLE);
                    showSeat.setLockedUntil(null);
                    showSeat.setLockByUserId(null);
                }
            }
        }
    }

    public void confirmSeats(List<ShowSeat> showSeats){
        for(ShowSeat showSeat: showSeats){
            if(showSeat.getSeatStatus()==SeatStatus.LOCKED){
                showSeat.setSeatStatus(SeatStatus.BOOKED);
                showSeat.setLockedUntil(null);
                showSeat.setLockByUserId(null);
            }
        }
    }

    public void releaseBookedTickets(List<ShowSeat> showSeats){
        for(ShowSeat showSeat: showSeats){
            if(showSeat.getSeatStatus()==SeatStatus.BOOKED){
                showSeat.setSeatStatus(SeatStatus.AVAILABLE);
            }
        }
    }

    public boolean isLockExpired(ShowSeat showSeat){
       return showSeat.getLockedUntil() == null || LocalDateTime.now().isAfter(showSeat.getLockedUntil());
    }

    public void validateLock(Long userid, List<ShowSeat> showSeats){
        for(ShowSeat showSeat: showSeats){
            if(showSeat.getSeatStatus()!=SeatStatus.LOCKED){
                throw new RuntimeException("Selected seats are not locked");
            }
            if(!userid.equals(showSeat.getLockByUserId())){
                throw new RuntimeException("Selected seats are locked by other user");
            }
            if(isLockExpired(showSeat)){
                System.out.println("booking not confirmed until"+ LocalDateTime.now());
                showSeat.setSeatStatus(SeatStatus.AVAILABLE);
                showSeat.setLockByUserId(null);
                showSeat.setLockedUntil(null);

                throw new RuntimeException("Time limit exceeded, go back and select seats again");
            }
        }
    }
}
