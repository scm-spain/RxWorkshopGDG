package com.scmspain.workshop.flights;

import java.util.List;
import rx.Observable;
import rx.functions.Func1;

public class SuggestionBusiness {
  static final String ENDPOINT = "http://autocomplete.wunderground.com";
  final SuggestionApi suggestionApi;

  public SuggestionBusiness() {
    suggestionApi = SuggestionApiBuilder.getSuggestionApi(ENDPOINT);
  }

  public Observable<List<String>> requestSuggestions(CharSequence charSequence) {
    return null;
    //return suggestionApi.getSuggestions(charSequence.toString());
    //TODO: Make it List<String> instead of List<SuggestResponse>
  }
}
