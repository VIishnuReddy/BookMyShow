package org.bookMyShow.entities;


import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter
public class Movie {
    private Long id;
    private String title;
    private String genre;
    private String language;
    private Duration duration;
}
