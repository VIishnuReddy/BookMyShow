package org.bookMyShow.enums;

import java.math.BigDecimal;

public enum SeatType {
    REGULAR(new BigDecimal("250")),
    GOLD(new BigDecimal("350")),
    RECLINER(new BigDecimal("450"));

    private final BigDecimal price;
    SeatType(BigDecimal price) {
        this.price=price;
    }

    public BigDecimal getPrice(){
        return price;
    }
}
