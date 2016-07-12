package com.scmspain.workshop.rxintroduction;

import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

// http://reactivex.io/documentation/scheduler.html
public class RXSchedulersTest {

  public Observable<String> observable = Observable
    .defer(new Func0<Observable<String>>() {
      @Override
      public Observable<String> call() {
        return aNetworkRequest();
      }
    })
    .observeOn(Schedulers.computation())
    .map(new Func1<String, String>() {
      @Override
      public String call(String response) {
        return calculateStuffFor(response);
      }
    })
    .subscribeOn(Schedulers.io())
    .observeOn(Schedulers.newThread());

  private Observable<String> aNetworkRequest() {
    System.out.println("aNetworkRequest - " + Thread.currentThread().getName());
    return Observable.just("The network response","another"); //
  }

  private String calculateStuffFor(String response) {
    System.out.println("calculateStuffFor("  + response +") - " + Thread.currentThread().getName());
    return "Transformed(" + response + ")";
  }

  private void addToResults(String next) {
    System.out.println("addToResults("  + next +") - " + Thread.currentThread().getName());
  }

  @Test
  public void printChangeOfSchedulers() throws Exception {
    System.out.println("testChangeOfSchedulers - " + Thread.currentThread().getName());
    TestSubscriber<String> testSubscriber = new TestSubscriber<>(new Observer<String>() {
      @Override
      public void onCompleted() {}

      @Override
      public void onError(Throwable e) {}

      @Override
      public void onNext(String next) {
        addToResults(next);
      }
    });
    observable.subscribe(testSubscriber);
    testSubscriber.awaitTerminalEvent();
  }
}
