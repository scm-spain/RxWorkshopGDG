package com.scmspain.workshop.suggestion;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class SuggestResponse {
  @SerializedName("RESULTS")
  ArrayList<SuggestItem> results;
}
