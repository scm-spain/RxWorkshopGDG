package com.scmspain.workshop.flights;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

public class FlightsApiBuilder {
  static public FlightsApi  getFlightsApi(String endpoint) {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(endpoint)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
    return retrofit.create(FlightsApi.class);
  }
}
