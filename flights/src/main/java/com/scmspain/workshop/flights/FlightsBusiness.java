package com.scmspain.workshop.flights;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import rx.Observable;
import rx.functions.Func1;

public class FlightsBusiness {
  private final ArrayList<FlightsApi> flightProviders;
  private final Comparator<Flight> flightComparator = new Comparator<Flight>() {
    @Override
    public int compare(Flight lhs, Flight rhs) {
      return lhs.price - rhs.price;
    }
  };

  public FlightsBusiness(String[] flightProviderEndpoints) {
    flightProviders = new ArrayList<>(flightProviderEndpoints.length);
    for (String flightProviderEndpoint : flightProviderEndpoints) {
      flightProviders.add(FlightsApiBuilder.getFlightsApi(flightProviderEndpoint));
    }
  }

  public ArrayList<Flight> flights() throws IOException {
    ArrayList<Flight> flights = new ArrayList<>();
    for (FlightsApi flightProvider : flightProviders) {
      flights.add(flightProvider.getFlight().execute().body());
    }
    return flights;
  }

  public List<Flight> flightsByPrice() throws IOException {
    ArrayList<Flight> flights = flights();
    Collections.sort(flights, flightComparator);
    return flights;
  }

  public Observable<Flight> flightsObservable() {
    return Observable.from(flightProviders)
        .flatMap(new Func1<FlightsApi, Observable<Flight>>() {
          @Override
          public Observable<Flight> call(FlightsApi flightProvider) {
            return null;
            //TODO: get the Flight Observable
          }
        });
  }

  public Observable<List<Flight>> flightsByPriceObservable() {
    return null;
    //TODO: sort the list
    //return flightsObservable().???;
  }

  public Observable<Collection<Flight>> incrementalFlightsByPriceObservable() {
    return null;
    //TODO: return a collection each time we receive a flight, sorted by price
    //return flightsObservable().???;
  }
}
