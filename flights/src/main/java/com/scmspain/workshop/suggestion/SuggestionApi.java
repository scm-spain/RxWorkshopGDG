package com.scmspain.workshop.suggestion;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface SuggestionApi {
  @GET("/aq")
  Observable<SuggestResponse> getSuggestions(@Query("query") String query);
}
