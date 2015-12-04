package com.scmspain.workshop.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.scmspain.workshop.flights.Flight;
import com.scmspain.workshop.flights.R;
import java.util.HashMap;
import java.util.List;

public class FlightsAdapter extends RecyclerView.Adapter<FlightsAdapter.FlightsViewHolder> {

  public List<Flight> flights;
  private HashMap<Long, Integer> idToPositionMap;

  public FlightsAdapter(List<Flight> flights) {
    this.flights = flights;
    idToPositionMap = new HashMap<>();
  }

  public void addFlight(Flight flight, int position) {
    if (notContainFlight(flight)) {
      flights.add(position, flight);
      notifyItemInserted(position);
      if (position + 2 <= flights.size()) {
        notifyDataSetChanged();
      }
    } else {
      notifyItemMoved(getPosition(flight), position);
    }

  }

  public int getPosition(Flight flight) {
    int position = 0;
    for (Flight flightElement : flights) {
      if (flight.id.equals(flightElement.id)) {
        return position;
      }
      position++;
    }
    return position;
  }

  public void addFlights(List<Flight> flights) {
    int position = 0;
    for (Flight flight : flights) {
      addFlight(flight, position);
      position++;
    }
  }

  private Boolean notContainFlight(Flight flight) {
    for (Flight flightElement : flights) {
      if (flight.id.equals(flightElement.id)) {
        return false;
      }
    }
    return true;
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
