package com.scmspain.workshop.views;

import com.scmspain.workshop.flights.Flight;
import com.scmspain.workshop.flights.FlightFakeServices;
import com.scmspain.workshop.flights.R;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class FlightsActivity extends AppCompatActivity {

    private RecyclerView flightsList;

    private List<Flight> flights;

    private FlightsAdapter adapter;

    private FloatingActionButton refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initAdaptor();
        initRefresh();
    }

    private void initRefresh() {
        refresh = (FloatingActionButton) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscribeService();
            }
        });
    }

    private void initAdaptor() {
        flights = new ArrayList<>();

        flightsList = (RecyclerView) findViewById(R.id.flightsList);
        flightsList.setHasFixedSize(true);

        adapter = new FlightsAdapter(flights);
        adapter.setHasStableIds(true);

        flightsList.setAdapter(adapter);

        flightsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        subscribeService();
    }

    private void subscribeService() {
        new FlightFakeServices().flightsFakeService
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Collection<Flight>>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("error");
                    }

                    @Override
                    public void onNext(Collection<Flight> flightList) {
                        adapter.setFlights(flightList);
                    }
                });
    }
}
