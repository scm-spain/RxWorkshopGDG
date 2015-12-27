package com.scmspain.workshop.flights;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

public class SuggestionApiBuilder {
  static public SuggestionApi  getSuggestionApi(String endpoint) {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(endpoint)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
    return retrofit.create(SuggestionApi.class);
  }
}
