package com.scmspain.workshop.flights;

import com.google.gson.Gson;
import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

public class FlightFakeServices {
    Gson g = new Gson();
    Flight[] flightsArray = {
        g.fromJson("{\"id\":\"sa\",\"airline\":\"Spaniards Airlines\",\"price\":200}", Flight.class),
        g.fromJson("{\"id\":\"us\",\"airline\":\"USA Airlines\",\"price\":3000}", Flight.class),
        g.fromJson("{\"id\":\"ba\",\"airline\":\"British Airlines\",\"price\":2000}", Flight.class),
        g.fromJson("{\"id\":\"ca\",\"airline\":\"Catalans Airlines\",\"price\":100}", Flight.class),
        g.fromJson("{\"id\":\"ia\",\"airline\":\"Italian Airlines\",\"price\":300}", Flight.class)
    };
    Comparator<Flight> flightComparator = new Comparator<Flight>() {
      @Override
      public int compare(Flight lhs, Flight rhs) {
        return lhs.price - rhs.price;
      }
    };

    public Observable<Collection<Flight>> flightsFakeService = Observable
        .interval(1, TimeUnit.SECONDS)
        .take(5)
        .scan(
            new TreeSet<>(flightComparator),
            new Func2<SortedSet<Flight>, Long, SortedSet<Flight>>() {
              @Override
              public SortedSet<Flight> call(SortedSet<Flight> flights, Long aLong) {
                SortedSet<Flight> newFlights = new TreeSet<>(flights);
                newFlights.add(flightsArray[aLong.intValue()]);
                return newFlights;
              }
            })
        .map(new Func1<SortedSet<Flight>, Collection<Flight>>() {
          @Override
          public Collection<Flight> call(SortedSet<Flight> flights) {
            return flights;
          }
        })
        .filter(new Func1<Collection<Flight>, Boolean>() {
          @Override
          public Boolean call(Collection<Flight> flights) {
            return !flights.isEmpty();
          }
        });

}
