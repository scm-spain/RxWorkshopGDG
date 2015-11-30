package com.scmspain.workshop.flights;

import retrofit.http.GET;
import rx.Observable;

public interface FlightsApi {
  @GET("/flight")
  Flight getFlight();

  @GET("/flight")
  Observable<Flight> getRxFlight();
}
