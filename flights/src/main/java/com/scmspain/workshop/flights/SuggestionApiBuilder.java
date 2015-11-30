package com.scmspain.workshop.flights;

import retrofit.RestAdapter;

public class SuggestionApiBuilder {
  static public SuggestionApi  getSuggestionApi(String endpoint) {
    RestAdapter restAdapter = new RestAdapter.Builder()
        .setEndpoint(endpoint)
        .build();
    return restAdapter.create(SuggestionApi.class);
  }
}
