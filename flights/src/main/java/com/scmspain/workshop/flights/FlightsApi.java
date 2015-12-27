package com.scmspain.workshop.flights;

import retrofit.Call;
import retrofit.http.GET;
import rx.Observable;

public interface FlightsApi {
  @GET("/flight")
  Call<Flight> getFlight();

  @GET("/flight")
  Observable<Flight> getRxFlight();
}
