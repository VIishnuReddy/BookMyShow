package org.bookMyShow.services;

import org.bookMyShow.entities.Show;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowService {

    private Map<Long, Show> shows = new HashMap<>();

    public void addShow(Show show){
        shows.put(show.getId(), show);
    }
    public List<Show> getShows(Long movieId, String city){
        return shows.values().stream()
                .filter((show)-> show.getMovie().getId().equals(movieId)).toList();
    }

    public Show getShow(Long showId){
        return shows.get(showId);
    }
}
