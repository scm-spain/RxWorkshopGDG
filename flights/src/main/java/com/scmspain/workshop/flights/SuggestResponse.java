package com.scmspain.workshop.flights;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class SuggestResponse {
  @SerializedName("RESULTS")
  ArrayList<SuggestItem> results;
}
