package com.scmspain.workshop.flights;

import android.support.annotation.NonNull;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import java.util.Collection;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import rx.functions.Action1;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */

//Never use FixMethodOrder, tests shouldn't depend on order to work
//here is used to have reliable times between executions because the first tests have a penalty
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FlightsUnitTest {
  public static final int PORT = 8080;
  public static final String WIREMOCK_URL = "http://127.0.0.1:"+PORT;
  @Rule
  public WireMockRule wireMockServer = new WireMockRule(PORT);

  @Test
  public void testOneSyncFlight() throws Exception {
    wireMockServer.stubFor(get(urlEqualTo("/flight")).willReturn(
        aResponse().withBody("{\"id\":\"sa\",\"airline\":\"Spaniards Airlines\",\"price\":200}")
            .withFixedDelay(100)));

    FlightsBusiness business = new FlightsBusiness(new String[] { WIREMOCK_URL });
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

    FlightsBusiness business = new FlightsBusiness(new String[] { WIREMOCK_URL });
    Flight result = business.flightsObservable().toBlocking().single();

    assertEquals("Spaniards Airlines", result.airline);
    assertEquals(200, result.price);
  }

  @Test
  public void testSomeRxFlights() throws Exception {
    FlightsBusiness flightsBusiness = new FlightsBusiness(getMockedProviders());

    Collection<Flight> list = flightsBusiness.flightsByPriceObservable()
        .toBlocking()
        .single();

    assertFlights(list);
  }

  @Test
  public void testIncrementalRxFlightsFirstResult() throws Exception {
    FlightsBusiness flightsBusiness = new FlightsBusiness(getMockedProviders());

    Collection<Flight> list = flightsBusiness.incrementalFlightsByPriceObservable()
        .toBlocking()
        .first();

    Flight firstFlight = list.iterator().next();
    assertEquals(200, firstFlight.price);
    assertEquals("Spaniards Airlines", firstFlight.airline);
  }

  @Test
  public void testIncrementalRxFlightsLastResult() throws Exception {
    FlightsBusiness flightsBusiness = new FlightsBusiness(getMockedProviders());

    Collection<Flight> list = flightsBusiness.incrementalFlightsByPriceObservable()
        .doOnNext(new Action1<Collection<Flight>>() {
          @Override
          public void call(Collection<Flight> flights) {
            printFlights(flights);
          }
        })
        .toBlocking()
        .last();

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
        WIREMOCK_URL+"/sa",
        WIREMOCK_URL+"/us",
        WIREMOCK_URL+"/ba",
        WIREMOCK_URL+"/ca"
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

  private void printFlights(Collection<Flight> flights) {
    System.out.println(flights.size());
    for (Flight flight : flights) {
      System.out.println("flight = " + flight.airline);
    }
  }
}