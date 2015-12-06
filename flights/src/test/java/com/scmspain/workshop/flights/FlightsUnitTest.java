package com.scmspain.workshop.flights;

import android.support.annotation.NonNull;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.gson.Gson;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observers.TestSubscriber;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FlightsUnitTest {
  @Rule
  public WireMockRule wireMockServer = new WireMockRule(8080);

  @Test
  public void testOneSyncFlight() throws Exception {
    wireMockServer.stubFor(get(urlEqualTo("/flight")).willReturn(
        aResponse().withBody("{\"id\":\"sa\",\"airline\":\"Spaniards Airlines\",\"price\":200}")
            .withFixedDelay(100)));

    FlightsBusiness business = new FlightsBusiness(new String[] {"http://127.0.0.1:8080"});
    Flight result = business.flights().get(0);

    assertEquals("Spaniards Airlines", result.airline);
    assertEquals(200, result.price);
  }

  @Test
  public void testSomeSyncFlights() throws Exception {
    FlightsBusiness flightsBusiness = new FlightsBusiness(getMockedProviders());

    Collection<Flight> list = flightsBusiness.flightsByPrice();

    assertFlights(list);
  }

  @Test
  public void testOneRxFlight() throws Exception {
    wireMockServer.stubFor(get(urlEqualTo("/flight")).willReturn(
            aResponse().withBody("{\"id\":\"sa\",\"airline\":\"Spaniards Airlines\",\"price\":200}")
                .withFixedDelay(100)));

    FlightsBusiness business = new FlightsBusiness(new String[] {"http://127.0.0.1:8080"});
    Flight result = business.flightsObservable().toBlocking().single();

    assertEquals("Spaniards Airlines", result.airline);
    assertEquals(200, result.price);
  }

  @Test
  public void testSomeRxFlights() throws Exception {
    FlightsBusiness flightsBusiness = new FlightsBusiness(getMockedProviders());

    Collection<Flight> list = flightsBusiness.flightsByPriceObservable().toBlocking().single();

    assertFlights(list);
  }

  @Test
  public void testIncrementalRxFlightsFirstResult() throws Exception {
    FlightsBusiness flightsBusiness = new FlightsBusiness(getMockedProviders());

    Collection<Flight> list = flightsBusiness.incrementalFlightsByPriceObservable().toBlocking().first();

    Flight firstFlight = list.iterator().next();
    assertEquals(200, firstFlight.price);
    assertEquals("Spaniards Airlines", firstFlight.airline);
  }

  @Test
  public void testIncrementalRxFlightsLastResult() throws Exception {
    FlightsBusiness flightsBusiness = new FlightsBusiness(getMockedProviders());

    Collection<Flight> list = flightsBusiness.incrementalFlightsByPriceObservable().toBlocking().last();

    assertFlights(list);
  }

  @NonNull
  private String[] getMockedProviders() {
    wireMockServer.stubFor(get(urlEqualTo("/sa/flight")).willReturn(
        aResponse().withBody("{\"id\":\"sa\",\"airline\":\"Spaniards Airlines\",\"price\":200}")
            .withFixedDelay(100)));
    wireMockServer.stubFor(get(urlEqualTo("/us/flight")).willReturn(
        aResponse().withBody("{\"id\":\"us\",\"airline\":\"USA Airlines\",\"price\":3000}")
            .withFixedDelay(600)));
    wireMockServer.stubFor(get(urlEqualTo("/ba/flight")).willReturn(
        aResponse().withBody("{\"id\":\"ba\",\"airline\":\"British Airlines\",\"price\":2000}")
            .withFixedDelay(300)));
    wireMockServer.stubFor(get(urlEqualTo("/ca/flight")).willReturn(
        aResponse().withBody("{\"id\":\"ca\",\"airline\":\"Catalans Airlines\",\"price\":100}")
            .withFixedDelay(200)));

    return new String[] {
        "http://127.0.0.1:8080/sa",
        "http://127.0.0.1:8080/us",
        "http://127.0.0.1:8080/ba",
        "http://127.0.0.1:8080/ca"
    };
  }

  private void assertFlights(Collection<Flight> list) {
    Flight[] array = list.toArray(new Flight[list.size()]);
    assertEquals(100, array[0].price);
    assertEquals("Catalans Airlines", array[0].airline);

    assertEquals(200, array[1].price);
    assertEquals("Spaniards Airlines", array[1].airline);

    assertEquals(2000, array[2].price);
    assertEquals("British Airlines", array[2].airline);

    assertEquals(3000, array[3].price);
    assertEquals("USA Airlines", array[3].airline);
  }

  @Test
  public void printFake() throws Exception {
    FlightsFake flightsFake = new FlightsFake(new String[]{});
    TestSubscriber<Collection<Flight>> ts = TestSubscriber.create(new Observer<Collection<Flight>>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(Collection<Flight> flights) {
        System.out.println(flights.size());
        for (Flight flight : flights) {
          System.out.println("flight = " + flight.airline);
        }
      }
    });
    flightsFake.incrementalFlightsByPriceObservable().subscribe(ts);
    ts.awaitTerminalEvent();
  }
}