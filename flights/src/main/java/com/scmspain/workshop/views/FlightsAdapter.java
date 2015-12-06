package com.scmspain.workshop.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.scmspain.workshop.flights.Flight;
import com.scmspain.workshop.flights.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class FlightsAdapter extends RecyclerView.Adapter<FlightsAdapter.FlightsViewHolder> {

  public List<Flight> flights;

  public FlightsAdapter(List<Flight> flights) {
    this.flights = flights;
  }

  @Override
  public long getItemId(int position) {
    return flights.get(position).id.hashCode();
  }

  @Override
  public FlightsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

    View itemView = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.listitem_flight, viewGroup, false);

    FlightsViewHolder flightsViewHolder = new FlightsViewHolder(itemView);

    return flightsViewHolder;
  }

  @Override
  public void onBindViewHolder(FlightsViewHolder viewHolder, int pos) {
    Flight item = flights.get(pos);

    viewHolder.bindFlight(item);
  }

  @Override
  public int getItemCount() {
    return flights.size();
  }

  public void setFlights(Collection<Flight> flights) {
    this.flights.clear();
    this.flights.addAll(flights);
    notifyDataSetChanged();
  }

  public static class FlightsViewHolder extends RecyclerView.ViewHolder {

    private TextView airlineLabel;
    private TextView priceLabel;

    public FlightsViewHolder(View itemView) {
      super(itemView);

      airlineLabel = (TextView)itemView.findViewById(R.id.airlineLabel);
      priceLabel = (TextView)itemView.findViewById(R.id.priceLabel);
    }

    public void bindFlight(Flight flight) {
      airlineLabel.setText(flight.airline);
      priceLabel.setText(String.valueOf(flight.price));
    }
  }
}
